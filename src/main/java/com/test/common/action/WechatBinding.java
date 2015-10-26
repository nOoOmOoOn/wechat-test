package com.test.common.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.web.WebErrors;
import com.test.common.entity.Account;
import com.test.common.manager.AccountMng;
import com.test.common.manager.BindingMng;
import com.test.common.manager.QrcodeMng;
import com.test.plugins.wechat.service.WechatConfigSvc;

@Controller
public class WechatBinding {

	@RequestMapping(value = { "/wechat/getSign.html" })
	public String getSign(HttpServletRequest request, ModelMap model, String token, 
			String url) {
		
		WebErrors errors = validateBinding(request, token, url);
		if (errors.hasErrors()) {
			String errorsMessage = "";
			for (String errorMessage : errors.getErrors()) {
				errorsMessage += errorMessage;
			}
			return "redirect:"+url+"?errors="+errorsMessage;
		}
		
		model.addAttribute("token", token);
		model.addAttribute("url", url);
		model.addAttribute("event", "sign");
		return "redirect:/common/wechat/oauth.html";
	}
	
	@RequestMapping(value = { "/wechat/getSign2.html" })
	public String getSign2(HttpServletRequest request, ModelMap model, 
			String url, String openId) {
		
		long qrcodeId = bindingMng.getByOpenId(openId).getQrcodeId();
		String sign = qrcodeMng.get(qrcodeId).getSign();
		
		return "redirect:"+url+"?sign="+sign;
	}
	
	protected WebErrors validateBinding(HttpServletRequest request, String token, 
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
	private AccountMng accountMng;
	@Autowired
	private BindingMng bindingMng;
	@Autowired
	private QrcodeMng qrcodeMng;
	@Autowired
	private WechatConfigSvc wechatConfigSvc;
	
}
