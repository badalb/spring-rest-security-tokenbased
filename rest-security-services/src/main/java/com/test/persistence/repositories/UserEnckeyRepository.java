package com.test.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.test.persistence.entities.UserEnckey;

public interface UserEnckeyRepository extends CrudRepository<UserEnckey, String>{
	
	public List<UserEnckey> findByAccessKeyAndIsDeleted(String accessKey,String isDeleted);
	
	public UserEnckey findByEncryptKeyAndIsDeleted(String encryptKey,String isDeleted);
	
}
