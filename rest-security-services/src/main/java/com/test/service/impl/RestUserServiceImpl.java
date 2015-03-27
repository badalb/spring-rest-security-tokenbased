package com.test.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.persistence.entities.UserEnckey;
import com.test.persistence.repositories.UserEnckeyRepository;
import com.test.service.RestUserService;
import com.test.service.security.AESEncryptionImpl;
import com.test.service.security.AccessHMACKeyGeneratorServiceImpl;
import com.test.util.CommonConstant;

@Service
public class RestUserServiceImpl implements RestUserService {

	private static Logger log = LoggerFactory.getLogger(RestUserServiceImpl.class);

	@Autowired
	private UserEnckeyRepository userEnckeyRepository;
	
	@Autowired
	private AESEncryptionImpl aesEncryptionImpl;

	@Autowired
	private AccessHMACKeyGeneratorServiceImpl accessHMACKeyGeneratorServiceImpl;

	@Override
	public UserEnckey Subscribe(String appId) {
		try {
			log.info("Request to Subscribe");
			UserEnckey userEnckey = new UserEnckey();
			userEnckey.setAppID(appId);
			userEnckey.setAccessKey(accessHMACKeyGeneratorServiceImpl.generateAccessKey());
			userEnckey.setHmacKey(aesEncryptionImpl.encrypt(accessHMACKeyGeneratorServiceImpl.generateHMACKey()));
			//userEnckey.setEncryptKey(accessHMACKeyGeneratorServiceImpl.generateHMACKey());// need to be changed to generate Encryption key
			userEnckey.setCreatedDate(new Date());
			userEnckey.setIsDeleted(CommonConstant.IS_NOT_DELETED);
			userEnckey = userEnckeyRepository.save(userEnckey);
			log.info("Request to Subscribe : success :"+ userEnckey.getEncryptID());
			return userEnckey;
		} catch (GeneralSecurityException | IOException exp) {
			log.error("Exception in createUserEnckey:" + exp.getMessage(), exp);
			return null;
		}
	}

	@Override
	public boolean UnSubscribe(String accessKey) {
		try {
			log.info("Request to delete Subscriber : accessKey " + accessKey);
			List<UserEnckey> userEnckeys = findSubscriberByAccessKey(accessKey);
			for (UserEnckey userEnckey : userEnckeys) {
				userEnckey.setIsDeleted(CommonConstant.IS_DELETED);
				userEnckey.setLastModifiedDate(new Date());
			}
			if (!userEnckeys.isEmpty())
				userEnckeyRepository.save(userEnckeys);
			log.info("Request to delete Subscriber success : accessKey "+ accessKey);
			return true;
		} catch (Exception exp) {
			log.error("Exception in UnSubscribe for accessKey:" + accessKey+ ":" + exp.getMessage(), exp);
			return false;
		}
	}

	@Override
	public List<UserEnckey> findSubscriberByAccessKey(String accessKey) {
		log.info("Request to fetch Subscriber with accessKey : " + accessKey);
		List<UserEnckey> userEnckeys = userEnckeyRepository.findByAccessKeyAndIsDeleted(accessKey,CommonConstant.IS_NOT_DELETED);
		log.info("Request to fetch Subscriber with accessKey Success: "+ accessKey);
		return userEnckeys;
	}

	@Override
	public UserEnckey findByAccessKey(String accessKey) {
		log.info("Request to fetch Subscriber with accessKey : " + accessKey);
		List<UserEnckey> userEnckeys = userEnckeyRepository
				.findByAccessKeyAndIsDeleted(accessKey,
						CommonConstant.IS_NOT_DELETED);
		log.info("Request to fetch Subscriber with accessKey Success: "
				+ accessKey);
		UserEnckey key = userEnckeys.get(0);
		try {
			key.setHmacKey(aesEncryptionImpl.decrypt(key.getHmacKey()));
		} catch (Exception e) {
			log.debug("Error decrypting hmac key for accessKey: " + accessKey);
			return null;
		}
		return key;
	}
	
	
	@Override
	public UserEnckey validateSubscriber(String accessKey) {
		List<UserEnckey> userEnckeys = findSubscriberByAccessKey(accessKey);
		if(userEnckeys.size()==0){
			return null;
		}
		return userEnckeys.get(0);
	}
	
	@Override
	public boolean validateMessageHash(String hmacKey, String messageHash,String encReqParams) {
		try {
			String messageHashServer = accessHMACKeyGeneratorServiceImpl.generateHMAC(encReqParams,aesEncryptionImpl.decrypt(hmacKey));
			if (!messageHashServer.equals(messageHash))
				return false;
		} catch (GeneralSecurityException | IOException exp) {
			log.error("Exception in validateMessageHash:" + exp.getMessage(),exp);
			return false;
		}
		return true;
	}

	@Override
	public UserEnckey findSubscriberByEncryptKey(String encKey) {
		log.info("Request to fetch Subscriber with EncKey : " + encKey);
		UserEnckey userEnckey = userEnckeyRepository.findByEncryptKeyAndIsDeleted(encKey,CommonConstant.IS_NOT_DELETED);
		log.info("Request to fetch Subscriber with EncKey Success: " + encKey);
		return userEnckey;
	}

}
