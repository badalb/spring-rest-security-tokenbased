package com.test.service;

import java.util.List;

import com.test.persistence.entities.UserEnckey;

public interface RestUserService {
	
	public UserEnckey Subscribe(String appId);
	
	public boolean UnSubscribe(String accessKey);
	
	public List<UserEnckey> findSubscriberByAccessKey(String accessKey);
	
	public UserEnckey validateSubscriber(String accessKey);
	
	public boolean validateMessageHash(String hmacKey,String messageHash,String  hmacParams);
	
	public UserEnckey findSubscriberByEncryptKey(String encKey);
	
	public UserEnckey findByAccessKey(String accessKey);
	
}
