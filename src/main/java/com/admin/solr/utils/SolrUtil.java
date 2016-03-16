package com.admin.solr.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import com.admin.solr.model.SolrPage;

/**
 * solrj工具类
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-11 下午5:43:46
 */
public class SolrUtil {

	//solr服务器地址
	private static String SOLR_SERVICE_URL = "http://127.0.0.1:8080/solr/userDoc";
		
	/** 
     * 为多个文档对象的，所以属性建立索引 
     * @param list 数据列表
     * @param solrClient 
     * @date 2015-9-11 下午2:33:29
     */  
    public static <T> void addDocs(List<T> list,SolrClient solrClient) {  
        if(null == list || list.size() == 0 ) {  
            return;  
        }  
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();  
        try {  
        	Field[] fields = list.get(0).getClass().getDeclaredFields();
            for (T obj : list) {  
                SolrInputDocument doc = new SolrInputDocument();  
                for (Field field : fields) {  
                	doc.addField(field.getName(),invokeGetterMethod(obj, field.getName())); 
                }  
                docs.add(doc);  
            }  
            solrClient.add(docs);  
            solrClient.commit();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
	/** 
     * 为多个文档对象的，某些属性建立索引 
     * @date 2015-9-11 下午5:33:29 
     * @param list 数据列表
     * @param properties 属性名称列表
     * @param solrClient 
     */  
    public static <T> void addDocs(List<T> list, List<String> properties, SolrClient solrClient) {  
        if(null == list || list.size() == 0 ) {  
            return;  
        }  
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();  
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        for (T obj : list) {  
            SolrInputDocument doc = new SolrInputDocument();  
            for (Field field : fields) {  
                for (String property : properties) {  
                    if(property.equals(field.getName())) {  
                        doc.addField(property,invokeGetterMethod(obj, property));  
                    }  
                }  
            }  
            docs.add(doc);  
        }  
        try {  
            solrClient.add(docs);  
            solrClient.commit();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 建立单个索引 
     * 
     * @param <T> 
     * @date 2015-9-11 下午5:33:58 
     * @param student 
     * @param properties 
     */  
    public static <T> void addDoc(T obj, List<String> properties, SolrClient solrClient) {  
        List<T> list = new ArrayList<T>();  
        list.add(obj);  
        addDocs(list, properties, solrClient);  
    }  
      
    /** 
     * 将整个对象都添加到索引 
     * @param <T> 
     * @date 2015-9-11 下午5:35:34 
     * @param student 
     */  
    public static <T> void addBean(T obj, SolrClient solrClient) {  
        try {  
            solrClient.addBean(obj);  
            solrClient.commit();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 添加多个索引对象 
     * 
     * @param <T> 
     * @date 2015-9-11 下午5:36:37 
     * @param students 
     */  
    public static <T> void addBeans(List<T> list, SolrClient solrClient) {  
        try {  
            solrClient.addBeans(list);  
            solrClient.commit();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 根据id删除某条索引 
     *  
     * @date 2015-9-11 下午5:37:46 
     * @param id 
     */  
    public static void deleteById(String id, SolrClient solrClient) {  
        try {  
            solrClient.deleteById(id);  
            solrClient.commit();  
        } catch (Exception e) {  
           e.printStackTrace();  
        }  
    }  
      
    /** 
     * 根据查询语句删除索引 
     * 
     * @date 2015-9-11 下午5:38:46 
     * @param query 
     */  
    public static void deleteByQuery(String query, SolrClient solrClient) {  
        try {  
            solrClient.deleteByQuery(query);  
            solrClient.commit();  
        } catch (Exception e) {  
           e.printStackTrace();  
        }  
    }  
      
    /** 
     * 根据id查询
     * 
     * @date 2015-9-11 下午5:38:46 
     * @param id 
     * @param entityClass 实体对象
     */
    public static <T> T queryById(String id, Class<?> entityClass) {
    	SolrClient client = new HttpSolrClient(SOLR_SERVICE_URL);
    	return queryById(id,entityClass,client);
    }
    /** 
     * 根据id查询
     * 
     * @date 2015-9-11 下午5:38:46 
     * @param id 
     * @param entityClass 实体对象
     * @param solrClient
     */
    @SuppressWarnings("unchecked")
	public static <T> T queryById(String id, Class<?> entityClass, SolrClient solrClient) {  
        T obj = null;  
        try {  
        	obj = (T) entityClass.newInstance(); 
            SolrQuery query = new SolrQuery();  
            query.setQuery("id:" + id);  
            QueryResponse response = solrClient.query(query);  
            SolrDocumentList docs = response.getResults();  
            if(null == docs || docs.size() == 0) {  
                return null;  
            }  
            SolrDocument doc = docs.get(0);  
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {  
                String propertyName = field.getName();  
                Object propertyValue = (Object) doc.getFieldValue(propertyName);  
                //获取set方法
                Method m = obj.getClass().getMethod("set"+UpperCaseField(propertyName));
				//执行set方法赋值
                m.invoke(obj,propertyValue);
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return obj;  
    } 
    
    /** 
     * solr获取的分页对象 
     *  
     * @param <T> 
     * @date 2015-9-11 下午5:39:36 
     * @param page SolrPage分页对象
     * @param solrQuery 查询对象件 
     * @param entityClass 实体对象
     * @return 
     */
    public static <T> SolrPage<T> getSolrPage(SolrPage<T> page, SolrQuery solrQuery,Class<?> entityClass) {
    	SolrClient solrClient = new HttpSolrClient(SOLR_SERVICE_URL);
    	return getSolrPage(page,solrQuery,solrClient,entityClass);
    }  
    
    /** 
     * solr获取的分页对象 
     *  
     * @param <T> 
     * @date 2015-9-11 下午5:39:36 
     * @param page SolrPage分页对象
     * @param solrQuery 查询对象件 
     * @param solrClient
     * @param entityClass 实体对象 
     * 注意：实体对象的类型必须和schema.xml中field配置的type一致
     * @return 
     */  
    public static <T> SolrPage<T> getSolrPage(SolrPage<T> page, SolrQuery solrQuery, SolrClient solrClient, Class<?> entityClass) {  
        try {  
        	solrQuery.setStart(page.getStart());//开始索引位置  
            solrQuery.setRows(page.getPageSize());//每页的数量  
            QueryResponse queryResponse = solrClient.query(solrQuery); 
            SolrDocumentList docs = queryResponse.getResults();  
            List<T> list = new ArrayList<T>();  
            for(SolrDocument doc : docs){  
            	T obj = document2Bean(doc,entityClass);
                list.add(obj);  
            }  
            page.setTotalSize(docs.size());  
            page.setResults(list);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return page;  
    }  
      
    /** 
     * 优化solr索引 
     * 
     * @date 2015-9-11 上午12:02:49 
     * @param solrClient 
     */  
    public static void optimize(String collection, SolrClient solrClient) {  
        try {  
            solrClient.optimize(collection);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    /**
	 * 从对象中获取指定属性值
	 * @param obj 对象
	 * @param property 属性名称
	 * @return String
	 */
	private static Object invokeGetterMethod(Object obj,String property){
		Object value = null; 
		try {
			Method m = obj.getClass().getMethod("get"+UpperCaseField(property));
			value = m.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return value;
	}
	
	/**
	 * 将SolrDocument转换成Bean
	 * @author LiZhiXian
	 * @param <T>
	 * @param doc SolrDocument
	 * @param entityClass 实体类
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T document2Bean(SolrDocument doc, Class<?> entityClass) {  
        Method[] methods = entityClass.getMethods();  
        T obj = null;
        try {
        	 obj = (T) entityClass.newInstance();
             for (Method method : methods) {  
                 String methodName = method.getName();  
                 if (methodName.startsWith("set")) {  
                     String propertyName = LowerCaseField(methodName.substring(3)); 
                     Object value = doc.get(propertyName);  
                     method.invoke(obj, value); 
                 }  
             }  
		} catch (Exception e) {
			e.printStackTrace();  
		}
        return obj;  
    }
	
	/**
	 * 转化字段首字母为大写  
	 * @param fieldName 字段名
	 */
    private static String UpperCaseField(String fieldName) {  
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName  
                .substring(0, 1).toUpperCase());  
        return fieldName;  
    } 
    
    /**
	 * 转化字段首字母为小写 
	 * @param fieldName 字段名.
	 *  @return String.
	 */
    private static String LowerCaseField(String fieldName) {  
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName  
                .substring(0, 1).toLowerCase());  
        return fieldName;  
    } 
}
