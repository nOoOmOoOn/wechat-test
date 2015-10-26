package com.test.common.manager.impl;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.test.plugins.wechat.service.WechatConfigSvc;
import com.test.common.dao.QrcodeDao;
import com.test.common.entity.Account;
import com.test.common.entity.Qrcode;
import com.test.common.manager.AccountMng;
import com.test.common.manager.QrcodeMng;

@Transactional(isolation = Isolation.REPEATABLE_READ)
@Service
public class QrcodeMngImpl implements QrcodeMng {

	@Override
	public long add(long accountId, String sign) {
		
		Qrcode qrcode = new Qrcode(accountId, null, sign);	
		long id = dao.add(qrcode);
		
		//创建微信二维码
		Account account = accountMng.get(accountId);
		wechatConfigSvc.modifyWxMpConfigStorage(account.getAppId(), account.getSecretKey(), account.getToken());
		WxMpService wxMpService = wechatConfigSvc.createWxMpService();
		
		
		int scene = (int) id;
		String ticket = null;
		try {
			ticket = wxMpService.qrCodeCreateLastTicket(scene).getTicket();
			dao.update(id, ticket);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
	@Override
	public Qrcode get(Long id) {
		return dao.get(id);
	}
	
	@Override
	public Qrcode getBySign(long accountId, String sign) {
		return dao.getBySign(accountId, sign);
	}
	
	@Autowired
	private AccountMng accountMng;
	@Autowired
	private QrcodeDao dao;
	@Autowired
	private WechatConfigSvc wechatConfigSvc;

}
