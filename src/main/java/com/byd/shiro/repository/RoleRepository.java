package com.byd.shiro.repository;


import com.byd.repository.base.BaseRepository;
import com.byd.shiro.entity.Role;

public interface RoleRepository extends BaseRepository<Role, Long>{
	public Role findByName(String name);
	public void deleteAssociateById(Long... roleIds);
}
