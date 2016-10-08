package com.byd.shiro.service;


import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.byd.service.base.BaseService;
import com.byd.shiro.entity.Role;
import com.byd.shiro.entity.User;
import com.byd.shiro.exception.RoleDuplicateException;

public interface RoleService extends  BaseService<Role, Long> {


	/**
	 * ������ɫ����Դ֮�����ϵ
	 * @param roleId
	 * @param resourceIds
	 */
	public void connectRoleAndResource(Long roleId,Long ...resourceIds );
	public void disconnnectRoleAndResource(Long roledId,Long... resourceIds);
	public void clearAllRoleAndResourceConnection(Long roleId);
	public void update(Role role);
	public void saveWithCheckDuplicate(Role role,User user) throws RoleDuplicateException;
	public Page<Role> findRoleByCondition(Map<String, Object> searchParams,
			Pageable pageable);
}
