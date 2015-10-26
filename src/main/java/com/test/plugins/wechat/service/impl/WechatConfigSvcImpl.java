package com.test.plugins.wechat.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.common.web.ResponseUtils;
import com.test.plugins.wechat.service.WechatConfigSvc;

@Transactional(isolation = Isolation.REPEATABLE_READ)
@Service
public class WechatConfigSvcImpl implements WechatConfigSvc {

	public WxMpConfigStorage GLOBAL_WX_MP_CONFIG_STORAGE = new WxMpInMemoryConfigStorage() {
		{
		}
	};
	
	public WxMpService GLOBAL_WX_SERVER = new WxMpServiceImpl() {
		{
			this.setWxMpConfigStorage(GLOBAL_WX_MP_CONFIG_STORAGE);
		}
	};

	public WxMpMessageRouter GLOBAL_ROUTER = new WxMpMessageRouter(
			GLOBAL_WX_SERVER) {
		{
		}
	};

	@Override
	public WxMpService createWxMpService() {
		return GLOBAL_WX_SERVER;
	}
	
	public void modifyWxMpConfigStorage(String appId, String secret, String token) {
		((WxMpInMemoryConfigStorage) GLOBAL_WX_MP_CONFIG_STORAGE).setAppId(appId);
		((WxMpInMemoryConfigStorage) GLOBAL_WX_MP_CONFIG_STORAGE).setSecret(secret);
		((WxMpInMemoryConfigStorage) GLOBAL_WX_MP_CONFIG_STORAGE).setToken(token);
	}

	@Override
	public void route(HttpServletRequest request, HttpServletResponse response,
			WxMpXmlMessage inputXmlMessage) {
		WxMpXmlOutMessage outMessage = GLOBAL_ROUTER.route(inputXmlMessage);
		if (outMessage != null) {
			ResponseUtils.renderXml(response, outMessage.toXml());
		}
	}
	
	@Override
	public void addMsgHandler(String msgType, String event, String eventKey,
			String content, String regex, WxMpMessageHandler handler) {
		GLOBAL_ROUTER.rule().async(false).msgType(msgType).event(event)
				.eventKey(eventKey).content(content).rContent(regex).handler(handler).end();
	}
}
