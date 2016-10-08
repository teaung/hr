package com.byd.shiro.service;


import java.util.List;
import java.util.Map;
import java.util.Set;











import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.byd.service.base.BaseService;
import com.byd.shiro.entity.User;
import com.byd.shiro.exception.UserDuplicateException;

public interface UserService extends  BaseService<User, Long>{
	//public List<Resource>findAllResources();
	public User findByUsername(String username);
//	public List<User> findUserByContidionAndPage(User user,Pageable pageable);
	public Page<User> findUserByCondition(Map<String,Object> searchParams,Pageable pageable);
	public Set<String> findAllRoleNamesByUsername(String username);
	public Set<String> findAllPermissionsByUsername(String username);
	void changePassword(Long userId, String newPassword);
	public void clearAllUserAndRoleConnection(Long userId);
	//����User��Role,����ʹ�õ���Set���ϣ���ʹ��β�����ͬ�ģ����ݿ���Ҳ�������ظ���
	public void connectUserAndRole(Long userId,Long ...roleIds);
	//ȡ������
	public void disconnectUserAndRole(Long userId,Long... roleIds);
	public void update(User user);
	public void saveWithCheckDuplicate(User user) throws UserDuplicateException;
	public void saveWithCheckDuplicate(List<User> users)throws UserDuplicateException;
}
