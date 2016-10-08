package com.byd.shiro.realm;



import java.util.Date;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.byd.shiro.entity.User;
import com.byd.shiro.service.UserService;

public class UserRealm extends AuthorizingRealm {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findAllRoleNamesByUsername(username));//��ѯ�û��Ľ�ɫ����ƾ֤��
        authorizationInfo.setStringPermissions(userService.findAllPermissionsByUsername(username));//��ѯ�û�Ȩ�޷���ƾ֤��

		
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        User user = userService.findByUsername(username);
        //���µ�¼ʱ��
        User curUser=userService.findByUsername(username);
        if(curUser.getLoginTime()!=null){
        	curUser.setLastLoginTime(curUser.getLoginTime());
        }
		curUser.setLoginTime(new Date());		
		userService.update(curUser);
		
        System.out.println("doGetAuthenticationInfo ��¼");
        if(user == null) {
            throw new UnknownAccountException();//û�ҵ��ʺ�
        }
        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //�ʺ�����
        }
        //����AuthenticatingRealmʹ��CredentialsMatcher��������ƥ�䣬��������˼ҵĲ��ÿ����Զ���ʵ��
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //�û���
                user.getPassword(), //����
                ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
