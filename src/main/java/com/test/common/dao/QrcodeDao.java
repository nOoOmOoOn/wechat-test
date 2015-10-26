package com.test.common.dao;

import com.test.common.entity.Qrcode;

public interface QrcodeDao {

	long add(Qrcode qrcode);
	
	void update(Long id, String ticket);
	
	Qrcode get(Long id);
	
	Qrcode getBySign(long accountId, String sign);

}
