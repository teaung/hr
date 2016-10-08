package com.byd.shiro.service;


import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.byd.service.base.BaseService;
import com.byd.shiro.entity.Resource;
import com.byd.shiro.entity.User;
import com.byd.shiro.exception.ResourceDuplicateException;
import com.byd.shiro.exception.RoleDuplicateException;



public interface ResourceService extends  BaseService<Resource, Long> {

	void update(Resource resource);

	void saveWithCheckDuplicate(Resource resource,User user) throws ResourceDuplicateException;

	Page<Resource> findResourceByCondition(Map<String, Object> searchParams,
			Pageable pageable);
	Resource findByName(String name);

}
