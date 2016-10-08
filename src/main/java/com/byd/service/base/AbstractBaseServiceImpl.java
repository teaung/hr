package com.byd.service.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.byd.entity.Constants;
import com.byd.entity.base.BaseEntity;
import com.byd.repository.base.BaseRepository;
import com.byd.shiro.entity.User;
import com.byd.shiro.exception.UserDuplicateException;
@Transactional
public abstract class AbstractBaseServiceImpl<T extends BaseEntity<PK>,PK extends Serializable> {
	@Autowired
	protected BaseRepository<T, PK> baseRepository;//������ע��
	@Autowired  
	private HttpSession session; 
	/**
	 * ��ȡ��ǰ�û�
	 * @return
	 */
	public User getCurrentUser(){
		return (User) session.getAttribute(Constants.CURRENT_USER);
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public List<T> findAll(){
		return baseRepository.findAll();
	}
	public T findOne(PK id){
		return baseRepository.findOne(id);
	}
	public void save(T obj) {
		baseRepository.save(obj);
	}
	public void save(List<T> objs){
		baseRepository.save(objs);
	}
	public void update(T obj){
		baseRepository.save(obj);
	}
	@SuppressWarnings("unchecked")
	public void delete(PK ...ids){
		for(PK id:ids)
		baseRepository.delete(id);
		
	}
	public void deleteAll(){
		baseRepository.deleteAll();		
	}
	public long getCount(){
		return baseRepository.count();
	}
	public Page<T> findAll(Pageable pageable){
		return baseRepository.findAll(pageable);
	}
	public List<T> find(PK...ids){
		List<PK> idList=new ArrayList<PK>();
		
		for(PK id :ids){
			idList.add(id);
		}
		return baseRepository.findAll(idList);
	}
	public BaseRepository<T, PK> getBaseRepository() {
		return baseRepository;
	}
	public void setBaseRepository(BaseRepository<T, PK> baseRepository) {
		this.baseRepository = baseRepository;
	}
	
}
