package com.byd.shiro.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import com.byd.entity.Constants;

import java.util.concurrent.atomic.AtomicInteger;


public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;//����ʹ�õ���Ehcache
    
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");//��Ehcache���ж���
       
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
       
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);//��ʼ������
            
        }  
        if(retryCount.incrementAndGet() > Constants.PASSWORD_RETRY_MAX) {              
         
            throw new ExcessiveAttemptsException();//����PASSWORD_RETRY_MAX���׳��쳣������1Сʱ��ʱ����timeToIdleSeconds������
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) { //�������ɹ���retryCount�������
            //clear retry count
            passwordRetryCache.remove(username);
        }     
        return matches;
    }
}
