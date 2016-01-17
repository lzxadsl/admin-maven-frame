package com.admin.controller.system;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面跳转控制（不需要登入就能访问的页面，可通过此接口访问）
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-11-13 上午9:19:53
 */
@Controller
@RequestMapping(value="/forword/*")
public class ForwordController {

	/**
     * 页面跳转
     * 返回的页面应该在    /WEB-INF/views/basic/{page}
     * 访问地址格式：forword/{page}.htm?params=xxx 
     * @param page /WEB-INF/views/basic/ 下页面的名称（不需要.jsp后缀）
     * @return
     */
    @RequestMapping(value = "basic/{page}.htm")
    public ModelAndView basic(ModelAndView model,HttpServletRequest request,@PathVariable String page){
    	//String page = "";
    	Enumeration<?> paramNames = request.getParameterNames();
    	while(paramNames.hasMoreElements()){
    		String paramName = (String)paramNames.nextElement();
    		model.addObject(paramName,request.getParameter(paramName));
    	}
        model.setViewName("basic/" + page);
        return model;
    }
}
