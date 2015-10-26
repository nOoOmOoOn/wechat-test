package com.test.common.manager;

import com.test.common.entity.Qrcode;

public interface QrcodeMng {

	long add(long accountId, String sign);
	
	Qrcode get(Long id);
		
	Qrcode getBySign(long accountId, String sign);
	
}
