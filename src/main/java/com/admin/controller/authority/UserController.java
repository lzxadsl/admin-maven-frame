package com.admin.controller.authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.Destination;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.admin.authority.model.SysUser;
import com.admin.authority.model.User;
import com.admin.authority.service.IUserService;
import com.admin.basic.model.PageData;
import com.admin.system.service.IProducerService;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-16 上午8:54:55
 */
@Controller
@SessionAttributes("username")//将ModelMap中show_msg属性添加到sessiong中
@RequestMapping(value="/user/*")
public class UserController {

	/*@Reference
	IUserService userService;//调用Dubbo暴露的接口

	@RequestMapping(value="getUser.htm")
	public @ResponseBody String getUser(){
		return userService.getUser();
	}*/
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProducerService pService;
	
	@Autowired
	Destination destination;
	
	@RequestMapping(value="getUser.htm")
	public @ResponseBody List<User> getUser(SysUser user){
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(user.getUsername())){
			params.put("where","and username like '%"+user.getUsername()+"%'");
		}
		TestClass.test();
		return userService.list(params);
	}
	
	@RequestMapping(value="getUserList.htm")
	public @ResponseBody Map<String, Object> getUserList(SysUser user,PageData pageData){
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(user.getUsername())){
			params.put("where","and username like '%"+user.getUsername()+"%' ");
		}
		
		/*User user1 = new User();
		user1.setUsername("aop2");
		user1.setSex("男");
		user1.setNick("admin");
		userService.saveUser(user1);
		System.out.println(user1);*/
		List<User> list = userService.listPage(params, pageData);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows",list);
		map.put("total",pageData.getTotalSize());
		return map;
	}
	
	@RequestMapping(value="ajaxTest.htm")
	public @ResponseBody Map<String, Object> ajaxTest(ModelMap model,String json,@ModelAttribute("username") String username){
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(username);
		model.addAttribute("username","lsssssssssssss");
		return map;
	}
	
	@RequestMapping(value="ajaxmsg.htm")
	public String ajaxmsg(String json,HttpServletRequest request,ModelMap model){
		HttpSession session = request.getSession();
		for(Cookie co:request.getCookies()){
			session.setAttribute(co.getName(),co.getValue());
		}
		System.out.println(request.getSession().getAttribute("show_msg"));
		model.addAttribute("show_msg","lzxxxx");
		return "show_ajax_msg";
	}
	
	@RequestMapping(value="sendMsg.htm")
	public @ResponseBody Map<String, Object> sendMsg(String json,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		pService.sendMessage(destination, "欢迎光临.......");
		return map;
	}
	@RequestMapping(value="save.htm")
	public @ResponseBody String save(){
		SysUser user = new SysUser();
		user.setUsername("aop2");
		user.setSex("男");
		user.setNick("admin");
		//userService.transation(user,"更新名称");
		return "200";
	}
	private static String SESSION_ID = null;
	@RequestMapping(value="session.htm")
	public String list(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		//4BDF9DA4098797AEBE9A8DE49BDD80CD
		if(SESSION_ID == null){
			System.out.println("----------------------8090设置SESSION_ID-------------------------");
			SESSION_ID = session.getId();
		}
		System.out.println("sessionid:"+session.getId());
		
		//Cookie cookie = new Cookie("JSESSIONID","");
		//cookie.setValue(SESSION_ID);
		//response.addCookie(cookie);
		System.out.println("获取hello值:"+session.getAttribute("hello"));
		if(session.getAttribute("hello") == null){
			System.out.println("----------------------设置hello值-------------------------");
			session.setAttribute("hello","欢迎光临-lzx");
		}
		return "user/list";
	}
}
