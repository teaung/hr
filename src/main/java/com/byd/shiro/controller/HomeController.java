package com.byd.shiro.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;





import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.byd.controller.base.BaseController;
import com.byd.entity.Constants;
import com.byd.shiro.entity.Resource;
import com.byd.shiro.entity.User;
import com.byd.shiro.service.ResourceService;
import com.byd.shiro.service.UserService;


@Controller
public class HomeController extends BaseController{

	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;

	@RequestMapping("/")
	public String home(Model model,HttpServletRequest request){
		
		Subject curUser=SecurityUtils.getSubject();
		Session session=curUser.getSession();	
		String username=(String) curUser.getPrincipal();
		User currentUser=userService.findByUsername(username);
		session.setAttribute(Constants.CURRENT_USER, currentUser);//当前用户
		session.setAttribute(Constants.CURRENT_USERNAME, username);
		List<Resource> resources=resourceService.findAll();//当前用户名
		request.setAttribute("resources", resources);
		if (currentUser.getLoginTime() != null) { 
			currentUser.setLastLoginTime(currentUser.getLoginTime());
		}
		currentUser.setLoginTime(new Date());
		userService.update(currentUser);
		return "index";
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}


	
}
