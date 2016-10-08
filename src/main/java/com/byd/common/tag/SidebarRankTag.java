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

import com.byd.entity.Constants;
import com.byd.shiro.entity.Resource;
import com.byd.shiro.entity.Role;
import com.byd.shiro.entity.User;

public class SidebarRankTag extends SimpleTagSupport{
	private User currentUser;
	@Override
	public void doTag() throws JspException, IOException {
		List<Resource> menuResources=new ArrayList<Resource>();
		List<Resource> submenuResources=new ArrayList<Resource>();
		new ArrayList<Resource>();
		for(Role role:currentUser.getRoles()){
			Set<Resource> resources=role.getResources();			
			for(Resource res : resources){
				if(res!=null&&res.getType().equals("menu")){
					menuResources.add(res);
				}
				if(res!=null&&res.getType().equals("submenu")){
					submenuResources.add(res);
				}
			}			
		}		
		Collections.sort(menuResources, new Comparator<Resource>() {
			@Override
			public int compare(Resource o1, Resource o2) {
				return o1.getPriority().compareTo(o2.getPriority());
			}
		});
		Collections.sort(submenuResources, new Comparator<Resource>() {
			@Override
			public int compare(Resource o1, Resource o2) {
				return o1.getPriority().compareTo(o2.getPriority());
			}
		});
		JspWriter out=getJspContext().getOut();		
		for(Resource res : menuResources){
			if(res.getUrl().equals("druid"))
			{
				out.println("<li><a  href='"+res.getUrl()+"' target='_blank'><i class='fa fa-laptop'></i><span>"+res.getName()+"</span></a></li>");
			}
			else if(res.getName().equals("用户管理")||res.getName().equals("角色管理")||res.getName().equals("资源管理"))
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
				for(Resource subres : submenuResources)
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
	public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	

}
