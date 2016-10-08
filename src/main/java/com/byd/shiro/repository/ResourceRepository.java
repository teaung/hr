package com.byd.shiro.repository;


import com.byd.repository.base.BaseRepository;
import com.byd.shiro.entity.Resource;

public interface ResourceRepository extends BaseRepository<Resource, Long>{
	public Resource findByName(String name);
	public void deleteAssociateById(Long... resourceIds);
}
