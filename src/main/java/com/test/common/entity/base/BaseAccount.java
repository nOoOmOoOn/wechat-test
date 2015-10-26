package com.test.common.entity.base;

import javax.persistence.Column;

import com.common.jdbc.BaseEntity;

/**
 * 微信公众号
 * @author yz
 *
 */
@javax.persistence.MappedSuperclass
public class BaseAccount extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5086179935270931118L;

	public BaseAccount(){}
	
	public BaseAccount(String name, String appId, String secretKey, String token){
		this.setName(name);
		this.setSecretKey(secretKey);
	}

	/**
	 * 公众号名称
	 */
	@Column(unique=true)
	private String name;
	
	/**
	 * 公众号AppId
	 */
	private String appId;
	
	/**
	 * 公众号密钥
	 */
	private String secretKey;

	/**
	 * 公众号填写的token
	 */
	private String token;

	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
