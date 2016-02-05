package com.admin.controller.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.admin.authority.model.Login;


/**
 * 登入控制
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-4 下午1:42:57
 */
@RequestMapping(value="/shiro/*")
@Controller
public class ShiroLoginController {

	/**
	 * 跳转登入页面
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-7 下午2:50:14
	 */
	@RequestMapping(value="login.htm")
    public String showLoginForm(Model model,@ModelAttribute Login login){
        return "authority/login/login";
    }

	/**
	 * 登入验证
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-4 下午1:45:38
	 */
	@RequestMapping(value="doLogin.htm",method=RequestMethod.POST)
	public String doLogin(Model model,@ModelAttribute Login login,BindingResult errors){
		
		UsernamePasswordToken token = new UsernamePasswordToken(login.getUsername(),login.getPassword());
		Subject subject = null;
		try{
			subject = SecurityUtils.getSubject();  
			subject.login(token);
		}catch(UnknownAccountException ue){
			model.addAttribute("error",ue.getMessage());
			errors.reject( "error.login.generic",ue.getMessage());
		}catch(AuthenticationException e){
			model.addAttribute("error",e.getMessage());
			errors.reject("error.login.generic", "密码有误");
		}
		System.out.println("是否验证通过："+subject.isAuthenticated());
		if(errors.hasErrors()){
            return showLoginForm(model,login);
        }else{
            return "redirect:/service/index/index.htm";
        }
	}
	/**
	 * 退出登入
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-4 下午2:16:50
	 */
	@RequestMapping("/logout.htm")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/service/shiro/login.htm";
    }
}
