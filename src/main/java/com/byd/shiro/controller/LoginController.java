package com.byd.shiro.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.byd.controller.base.BaseController;
import com.byd.entity.Constants;

@Controller
public class LoginController extends BaseController{
	//�����ظ���¼
	
	  @RequestMapping(value = "/login")
	    public String showLoginForm(HttpServletRequest req, Model model) {
	
	        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
	      
	        String error = null;
	        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
	            error = "用户名/密码错误";
	        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
	            error = "用户名/密码错误";
	        }else if(LockedAccountException.class.getName().equals(exceptionClassName)){
	        	error = "锁定的帐号";
	        	 	
	        }else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
	            error = "登录失败次数过多"+Constants.PASSWORD_RETRY_MAX;
	        }else if(exceptionClassName != null) {
	            error = "其他错误：" + exceptionClassName;
	        }
	        model.addAttribute("loginError", error);
	        return "login";
	    }
	

}