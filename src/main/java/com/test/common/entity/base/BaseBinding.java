package com.test.common.entity.base;

import java.util.Date;

import javax.persistence.Column;

import com.common.jdbc.BaseEntity;

/**
 * 微信绑定
 * @author yz
 *
 */
@javax.persistence.MappedSuperclass
public class BaseBinding extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3322808443651410247L;

	public BaseBinding(){}
	
	public BaseBinding(long qrcodeId, String openId, Date createtime){
		this.setQrcodeId(qrcodeId);
		this.setOpenId(openId);
		this.setCreatetime(createtime);
	}

	/**
	 * 公众号二维码Id
	 */
	@Column(unique=true)
	private long qrcodeId;
	
	/**
	 * 公众号openId
	 */
	private String openId;
	
	/**
	 * 绑定时间
	 */
	private Date createtime;

	

	public long getQrcodeId() {
		return qrcodeId;
	}

	public void setQrcodeId(long qrcodeId) {
		this.qrcodeId = qrcodeId;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
