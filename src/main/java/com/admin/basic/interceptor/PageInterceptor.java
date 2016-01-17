package com.admin.basic.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import com.admin.basic.model.PageData;


/**
 * 分页查询拦截器
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-2 下午5:44:26
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})  
public class PageInterceptor implements Interceptor {

	private String dialect;
	private String defaultDialect = "PostgreSQL";
	private String pageSqlId;
	/**
	 * 匹配mapper.xml中查询id以Page结尾的时候才执行分页
	 */
	private String defaultPageSqlId = ".*PageList$";
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();  
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler); 
        while(metaStatementHandler.hasGetter("h")){
        	Object object = metaStatementHandler.getValue("h");  
        	metaStatementHandler = SystemMetaObject.forObject(object);  
        }
        // 分离最后一个代理对象的目标类  
   	    while (metaStatementHandler.hasGetter("target")) {  
   	        Object object = metaStatementHandler.getValue("target");  
   	        metaStatementHandler = SystemMetaObject.forObject(object);  
   	    } 
   	    Configuration configuration = (Configuration) metaStatementHandler.  
   	    		 getValue("delegate.configuration"); 
   	    //configuration.getVariables()获取的是mybatis-config.xml中properties下的属性
   	 
   	    dialect = configuration.getVariables() == null ? "" : configuration.getVariables().getProperty("dialect");  
	    if (null == dialect || "".equals(dialect)) {  
	        dialect = configuration.getDatabaseId() == null ? defaultDialect : configuration.getDatabaseId();  
	    }
	    pageSqlId = configuration.getVariables() == null ? "" : configuration.getVariables().getProperty("pageSqlId");  
	    if (null == pageSqlId || "".equals(pageSqlId)) {  
	        pageSqlId = defaultPageSqlId;  
	    }
	    MappedStatement mappedStatement = (MappedStatement)   
	    metaStatementHandler.getValue("delegate.mappedStatement");  
	    // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的  
	    //  MappedStatement的sql  
	    if (mappedStatement.getId().matches(pageSqlId)){  
	        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");  
	        Object parameterObject = boundSql.getParameterObject();  
	        if (parameterObject == null) {  
	            throw new NullPointerException("parameterObject is null!");  
	        } else {  
	            // 分页参数作为参数对象parameterObject的一个属性  
	        	PageData page = (PageData) metaStatementHandler  
	                     .getValue("delegate.boundSql.parameterObject.pageData");  
	            String sql = boundSql.getSql();  
	            // 重写sql  
	            String pageSql = buildPageSql(sql, page);  
	            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);  
	            // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数  
	            metaStatementHandler.setValue("delegate.rowBounds.offset",RowBounds.NO_ROW_OFFSET);  
	            metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);  
	            Connection connection = (Connection) invocation.getArgs()[0];  
	            // 重设分页参数里的总页数等  
	            setPageParameter(sql, connection, mappedStatement, boundSql, page);  
	        }  
	    }
		// 将执行权交给下一个拦截器  
        return invocation.proceed();
	}

	private String buildPageSql(String sql, PageData page) {  
	    if (page != null) {  
	        StringBuilder pageSql = new StringBuilder();  
	        if ("POSTGRESQL".equals(dialect.toUpperCase())) {  
	            pageSql = buildPageSqlForPostgreSQL(sql, page);  
	        } else if ("ORACLE".equals(dialect.toUpperCase())) {  
	            //pageSql = buildPageSqlForOracle(sql, page);  
	        } else if("MYSQL".equals(dialect.toUpperCase())){
	        	pageSql = buildPageSqlForMySQL(sql, page);  
	        }
	        else {  
	            return sql;  
	        }  
	        return pageSql.toString();  
	    } else {  
	        return sql;  
	    }  
	}  
	
	public StringBuilder buildPageSqlForPostgreSQL(String sql, PageData page) {  
	    StringBuilder pageSql = new StringBuilder(100);  
	    pageSql.append(sql);  
	    pageSql.append(" limit " + page.getPageSize() + " offset " + page.getStartRow());  
	    return pageSql;  
	}
	
	public StringBuilder buildPageSqlForMySQL(String sql, PageData page) {  
	    StringBuilder pageSql = new StringBuilder(100);
	    pageSql.append(sql);  
	    pageSql.append(" limit " + page.getStartRow() + "," + page.getPageSize());  
	    return pageSql;  
	}
	
	/** 
	 * 从数据库里查询总的记录数并计算总页数，回写进分页参数<code>PageParameter</code>,这样调用  
	 * 者就可用通过 分页参数<code>PageParameter</code>获得相关信息。 
	 *  
	 * @param sql 
	 * @param connection 
	 * @param mappedStatement 
	 * @param boundSql 
	 * @param page 
	 */  
	private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,  
	        BoundSql boundSql, PageData page) {  
	    // 记录总记录数  
	    String countSql = "select count(0) from (" + sql + ") as total";  
	    PreparedStatement countStmt = null;  
	    ResultSet rs = null;  
	    try {  
	        countStmt = connection.prepareStatement(countSql);  
	        BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,  
	                boundSql.getParameterMappings(), boundSql.getParameterObject());  
	        setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());  
	        rs = countStmt.executeQuery();  
	        int totalCount = 0;  
	        if (rs.next()) {  
	            totalCount = rs.getInt(1);  
	        }  
	        page.setTotalSize(totalCount);  
	        int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);  
	        page.setTotalPage(totalPage);  
	    } catch (SQLException e) {  
	        e.printStackTrace();
	    } finally {  
	        try {  
	            rs.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace(); 
	        }  
	        try {  
	            countStmt.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace();
	        }  
	    }  
	}  
	
	/** 
	 * 对SQL参数(?)设值 
	 *  
	 * @param ps 
	 * @param mappedStatement 
	 * @param boundSql 
	 * @param parameterObject 
	 * @throws SQLException 
	 */  
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,  
	        Object parameterObject) throws SQLException {  
	    ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);  
	    parameterHandler.setParameters(ps);  
	} 
	
	@Override
	public Object plugin(Object target) {
		/**
		 * 当目标类是StatementHandler和ResultSetHandler类型时，
		 * 才包装目标类，否者直接返回目标本身,减少目标被代理的 次数
		 */
		if (target instanceof StatementHandler || target instanceof ResultSetHandler) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        }  
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub
	}

}
