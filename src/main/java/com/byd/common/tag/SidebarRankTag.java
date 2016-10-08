package com.byd.common.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.byd.shiro.entity.Resource;
import com.byd.shiro.entity.Role;
import com.byd.shiro.entity.User;

public class SidebarRankTag extends SimpleTagSupport{
	
	public final String RESOURCE_TYPE_MENU = "menu";
	public final String RESOURCE_TYPE_FUNC = "function";
	
	public final String ROLE_ADMIN = "admin";
	
	private User currentUser;
	@Override
	public void doTag() throws JspException, IOException {
		List<Resource> menuResources=new ArrayList<Resource>();
		List<Resource> functionResources=new ArrayList<Resource>();
		
		for(Role role:currentUser.getRoles()){
			Set<Resource> resources=role.getResources();
			for(Resource res : resources){
				if(res!=null&&res.getType().equals(RESOURCE_TYPE_MENU)){
					menuResources.add(res);
				}
				if(res!=null&&res.getType().equals(RESOURCE_TYPE_FUNC)){
					functionResources.add(res);
				}
			}			
		}		
		Collections.sort(menuResources, new Comparator<Resource>() {
			@Override
			public int compare(Resource o1, Resource o2) {
				return o1.getPriority().compareTo(o2.getPriority());
			}
		});
		Collections.sort(functionResources, new Comparator<Resource>() {
			@Override
			public int compare(Resource o1, Resource o2) {
				return o1.getPriority().compareTo(o2.getPriority());
			}
		});
		JspWriter out=getJspContext().getOut();
		
		Boolean bAdmin = false;
		Set<Role> roles = currentUser.getRoles();
		for (Role r : roles) {
			if (r.getName().toString().equals(ROLE_ADMIN)) {
				bAdmin = true;
				break;
			}
		}
		if (bAdmin) { // 管理员用户
			for(Resource res : menuResources){
				if(res.getUrl().equals("druid"))
				{
					out.println("<li><a  href='"+res.getUrl()+"' target='_blank'><i class='fa fa-laptop'></i><span>"+res.getName()+"</span></a></li>");
				}
				//else if(res.getName().equals("用户管理")||res.getName().equals("角色管理")||res.getName().equals("资源管理"))
				else if (!res.getUrl().toString().isEmpty())
				{
					out.println("<li><a class='sidebarMenuHref' href='"+res.getUrl()+"'><i class='fa  fa-circle-o'></i><span>"+res.getName()+"</span></a>");
					out.println("</li>");
				}
				else
				{	
					out.println(" <li class='treeview'>");
					out.println("<a href='#'>");
					out.println("<i class='fa  fa-circle-o'></i> <span>"+res.getName()+"</span>");
					out.println("<span class='pull-right-container'>");
					out.println("<i class='fa fa-angle-left pull-right'></i>");
					out.println("</span>");
					out.println("</a>");
					out.println("<ul class='treeview-menu'>");
					for(Resource subres : functionResources)
					{
						if(res.getId()==subres.getParentId())
						{
							out.println("<li><a class='sidebarMenuHref' href='"+subres.getUrl()+"'><i class='fa fa-circle-o'></i>"+subres.getName()+"</a></li>");
						}
	
					}
					out.println("</ul>");
					out.println("</li>");
				}
					
			}
		}
		else { // 普通用户
			for(Resource res : menuResources) {
				if (!res.getUrl().toString().isEmpty())
				{
					out.println("<li><a class='sidebarMenuHref' href='"+res.getUrl()+"'><i class='fa  fa-circle-o'></i><span>"+res.getName()+"</span></a>");
					out.println("</li>");
				}
			}
			for (Resource res: functionResources) {
				out.println("<li><a class='sidebarMenuHref' href='"+res.getUrl()+"'><i class='fa  fa-circle-o'></i><span>"+res.getName()+"</span></a>");
				out.println("</li>");
			}
		}
	}
	public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	

}
