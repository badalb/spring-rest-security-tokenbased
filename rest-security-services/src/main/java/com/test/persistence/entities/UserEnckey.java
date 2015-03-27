package com.test.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.test.util.CommonConstant;

@Entity
@Table(name = "tblhuenckey", schema = CommonConstant.EMBRACE_SCHEMA_NAME)
public class UserEnckey implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "hu_id_gen", strategy = "com.test.util.SystemIdGenerator")
	@GeneratedValue(generator = "hu_id_gen")
	@Column(name = "encrypt_ID")
	private String encryptID;
	
	@Column(name = "appID")
	private String appID;
	
	@Column(name = "access_key")
	private String accessKey;
	
	@Column(name = "encrypt_key")
	private String encryptKey;
	
	@Column(name = "hmac_key")
	private String hmacKey;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date")
	private Date lastModifiedDate;
	
	@Column(name = "is_deleted")
	private String isDeleted;

	
	
	public String getEncryptID() {
		return encryptID;
	}

	public void setEncryptID(String encryptID) {
		this.encryptID = encryptID;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getHmacKey() {
		return hmacKey;
	}

	public void setHmacKey(String hmacKey) {
		this.hmacKey = hmacKey;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}
