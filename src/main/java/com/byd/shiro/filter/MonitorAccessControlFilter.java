package com.byd.shiro.filter;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.byd.shiro.service.UserService;
/**
 * isAccessAllowed�����Ƿ�������ʣ�����true��ʾ����
 * onAccessDenied����ʾ���ʾܾ�ʱ�Ƿ��Լ������������true��ʾ�Լ��������Ҽ�����������ִ�У�����false��ʾ�Լ��Ѿ������ˣ������ض�����һ��ҳ�棩��
 * @author vino007
 *
 */
public class MonitorAccessControlFilter extends AccessControlFilter {
	@Autowired
	private UserService userService;
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object arg2) throws Exception {
		
		Subject subject=getSubject(request, response);
		String username=(String) subject.getPrincipal();
		if(username == null)//δ��¼
			return false;
		Set<String> permissions=userService.findAllPermissionsByUsername(username);	
		for(String permission:permissions){
			if("monitor:view".equals(permission)){
				return true;
			}
		}	
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		 WebUtils.issueRedirect(request, response, "/static/404.html");//����404 not found,����login�����û��ѵ�¼���ᵼ��login��Ч
		return false;
	}
	  
	
		
}
