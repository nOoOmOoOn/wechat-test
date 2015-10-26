package com.test.common.entity.base;

import com.common.jdbc.BaseEntity;

/**
 * 微信公众号
 * @author yz
 *
 */
@javax.persistence.MappedSuperclass
public class BaseQrcode extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6418795879573657415L;

	public BaseQrcode(){}
	
	public BaseQrcode(long accountId, String ticket, String sign){
		this.setAccountId(accountId);
		this.setTicket(ticket);
		this.setSign(sign);
	}

	/**
	 * 公众号ID
	 */
	private long accountId;
	
	/**
	 * 二维码ticket
	 */
	private String ticket;

	/**
	 * 标识
	 */
	private String sign;
	
	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
