package com.admin.shiro.realm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.admin.authority.model.Permission;
import com.admin.authority.model.Role;
import com.admin.authority.model.User;
import com.admin.authority.service.IUserService;

public class AuthorityRealm extends AuthorizingRealm{
	/**
	 * 权限规则说名
	 * anon:例子/admins/**=anon 没有参数，表示可以匿名使用，无需任何验证就可以访问。 
	 * authc:例如/admins/user/**=authc表示需要认证(登录)才能使用，没有参数 
	 * roles：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。 
	 * perms：例子/admins/user/**=perms[user:add:*],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。 
	 * rest：例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。 
	 * port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。 
	 * authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证 
	 * ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https 
	 * user:例如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
	 */
	
	@Autowired
	private IUserService userService;
	
	/** 
     * 为当前登录的Subject授予角色和权限 
     * @see  经测试:本例中该方法的调用时机为需授权资源被访问时(如：perms、roles) 
     * @see  经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache 
     * @see  个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache 
     * @see  比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){  
        //获取当前登录的用户名  
        String username = (String) principals.getPrimaryPrincipal();
        User user = userService.getUserByName(username);
        if(user != null){
        	Set<Role> roleSet =  user.getRoleSet();
            //角色名的集合
            Set<String> roles = new HashSet<String>();
            //权限名的集合
            Set<String> permissions = new HashSet<String>();
            
            Iterator<Role> it = roleSet.iterator();
            while(it.hasNext()){
            	Role role = it.next();
            	roles.add(role.getName());
            	for(Permission per:role.getPermissionSet()){
            		permissions.add(per.getName());
            	}
            }
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色   
            authorizationInfo.addRoles(roles);
            //添加权限  
            authorizationInfo.addStringPermissions(permissions);
            //若该方法什么都不做直接返回null的话,就会导致任何用户访问时会根据配置文件中filterChainDefinitions来进行处理
            //详见applicationContext.xml中的<bean id="shiroFilter">的配置  
            return authorizationInfo;  
        }else{
        	return null;
        }
    }  
       
    /** 
     * 验证当前登录的Subject 
     * @see  经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {  
        //获取基于用户名和密码的令牌  
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的  
        //两个token的引用都是一样的
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;  
        System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));  
        User user = userService.getUserByName(token.getUsername());  
      	if(null != user){ 
      		//根据数据库查询结果自动根界面输入比对
      		SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),this.getName());  
      		authcInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getUsername()+user.getSalt()));
      		this.setSession("user", user);  
      		return authcInfo;  
      	}else{  
      		throw new UnknownAccountException("用户不存在，请输入正确的用户名");  
      	}  
    }  
       
       
    /** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see  比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }  
}
