package com.test.common.manager.impl;

import java.util.Date;
import java.util.Map;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.test.common.dao.BindingDao;
import com.test.common.entity.Binding;
import com.test.common.manager.BindingMng;
import com.test.plugins.wechat.service.WechatConfigSvc;

@Transactional(isolation = Isolation.REPEATABLE_READ)
@Service
public class BindingMngImpl implements BindingMng, InitializingBean {

	@Override
	public void add(long qrcodeId, String openId, Date createtime) {
		Binding binding = new Binding(qrcodeId, openId, createtime);	
		dao.add(binding);
	}
	
	@Override
	public Binding get(Long id) {
		return dao.get(id);
	}

	@Override
	public Binding getByOpenId(String openId) {
		return dao.getByOpenId(openId);
	}	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		wechatConfigSvc.addMsgHandler(WxConsts.XML_MSG_EVENT, WxConsts.EVT_SUBSCRIBE, null, null, null, new BindingHandler());
		wechatConfigSvc.addMsgHandler(WxConsts.XML_MSG_EVENT, WxConsts.EVT_SCAN, null, null, null, new BindingHandler());
	}
	
	class BindingHandler implements WxMpMessageHandler{

		@Override
		public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
				Map<String, Object> context, WxMpService wxMpService,
				WxSessionManager sessionManager) throws WxErrorException {
			
			String eventKey = wxMessage.getEventKey();
			//非通过二维码关注，则直接返回
			if (eventKey==null) return null;
			
			String openId = wxMessage.getFromUserName();
			if (dao.getByOpenId(openId)==null) {
				if (wxMessage.getEvent().equals(WxConsts.EVT_SUBSCRIBE)) {
					eventKey = eventKey.substring(8);
				}
				dao.add(new Binding(Long.parseLong(eventKey), openId, new Date()));
			}
			
			System.out.println(eventKey);
			System.out.println("here is binding handler");
			
			return null;
		}

	}
	
	@Autowired
	private WechatConfigSvc wechatConfigSvc;
	@Autowired
	private BindingDao dao;

}
