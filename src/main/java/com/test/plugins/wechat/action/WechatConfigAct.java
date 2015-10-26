package com.test.plugins.wechat.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.util.JsonUtils;
import com.common.web.ResponseUtils;
import com.common.web.WebErrors;
import com.test.common.entity.Account;
import com.test.common.manager.AccountMng;
import com.test.plugins.wechat.service.WechatConfigSvc;

@Controller
public class WechatConfigAct {

	private Logger log = LoggerFactory.getLogger(WechatConfigAct.class);

	@RequestMapping(value = "/wechat_config/receiver_{token}.html")
	public void receiver(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@PathVariable("token") String token, String echostr,
			String signature, String nonce, String timestamp)
			throws IOException {
		
		Account account = accountMng.getByToken(token);
		
		wechatConfigSvc.modifyWxMpConfigStorage(account.getAppId(), account.getSecretKey(), account.getToken());
		if (!wechatConfigSvc.createWxMpService().checkSignature(timestamp,
				nonce, signature)) {// 消息签名不正确，说明不是公众平台发过来的消息
			ResponseUtils.renderText(response, "非法请求");
			return;
		}
		if (echostr != null && echostr.length() > 0) {// 说明是一个仅仅用来验证的请求，回显echostr
			ResponseUtils.renderText(response, echostr);
			return;
		}
		
		WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
		wechatConfigSvc.route(request, response, inMessage);

		
	}
	
	@RequestMapping(value = "/wechat/config.html")
	public void config(HttpServletRequest request, ModelMap model, String url, long accountId) {
		try {
			//wechatConfigSvc.setWxMpConfigStorage(account.getAppId(), account.getSecretKey(), account.getToken());
			WxMpService wxMpService = wechatConfigSvc.createWxMpService();
			wxMpService.getJsapiTicket();
			WxJsapiSignature signature = wxMpService.createJsapiSignature(url);
			model.addAttribute("url", url);
			model.addAttribute("nonceStr", signature.getNoncestr());
			model.addAttribute("timestamp", signature.getTimestamp());
			model.addAttribute("signature", signature.getSignature());
			model.addAttribute("appId", signature.getAppid());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/wechat/oauth.html")
	public void oauth(HttpServletRequest request, ModelMap model, String token, 
			String url, String event) {
		System.out.println("oauth");
		Account account = accountMng.getByToken(token);
		wechatConfigSvc.modifyWxMpConfigStorage(account.getAppId(), account.getSecretKey(), account.getToken());
		WxMpService wxMpService = wechatConfigSvc.createWxMpService();
	
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", token);
		map.put("url",url);
		map.put("event", event);
		String state = JsonUtils.renderJson(map);
		//System.out.println(wxMpService.oauth2buildAuthorizationUrl(WxConsts.OAUTH2_SCOPE_BASE, null));
		//System.out.println(wxMpService.oauth2buildAuthorizationUrl("www.daily3.rosepie.com/common/wechat/code.html",WxConsts.OAUTH2_SCOPE_BASE, null));
		System.out.println(wxMpService.oauth2buildAuthorizationUrl("www.daily3.rosepie.com/common/wechat/test.html",WxConsts.OAUTH2_SCOPE_BASE, state));
	}
	
	@RequestMapping(value = "/wechat/code.html")
	public String getOpenId(HttpServletRequest request, ModelMap model, String code, String state) {
		
		System.out.println("code");
		Map<String, Object> map = JsonUtils.toMap(state);
		String token = (String) map.get("token");
		String url = (String)map.get("url");
		String event = (String)map.get("event");
		
		Account account = accountMng.getByToken(token);
		wechatConfigSvc.modifyWxMpConfigStorage(account.getAppId(), account.getSecretKey(), account.getToken());
		
		WxMpService wxMpService = wechatConfigSvc.createWxMpService();
		try {
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
			String openId = wxMpOAuth2AccessToken.getOpenId();
			
			if (event.equals("sign")) {
				model.addAttribute("url", url);
				model.addAttribute("openId", openId);
				return "wechat/getSign2";
			} else {
				return "redirect:"+url+"?openId="+openId;
			}
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected WebErrors validateOauth(HttpServletRequest request, String token, 
			String url) {
		WebErrors errors = WebErrors.create(request);
		
		errors.ifBlank(url, "url is null", 200);
		errors.ifBlank(token, "token is null", 200);
		if (errors.hasErrors()) 
			return errors;
		
		Account account = accountMng.getByToken(token); 
		if (account==null) {
			errors.addError("token is error");
			return errors;
		}
		
		return errors;
	}

	@Autowired
	private WechatConfigSvc wechatConfigSvc;
	@Autowired
	private AccountMng accountMng;
}
