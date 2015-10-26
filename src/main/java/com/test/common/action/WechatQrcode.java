package com.test.common.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.web.WebErrors;
import com.test.common.entity.Account;
import com.test.common.entity.Qrcode;
import com.test.common.manager.AccountMng;
import com.test.common.manager.QrcodeMng;

@Controller
public class WechatQrcode {

	@RequestMapping(value = { "/wechat/getQrcode.html" })
	public String wechatQrcode(HttpServletRequest request, ModelMap model, String token, 
			String sign) {
		
		WebErrors errors = validateQrcode(request, token, sign);
		if (errors.hasErrors()) {
			return "/common/error.html";
		}
		
		//判断是否存在该二维码，不存在则生成
		Account account = accountMng.getByToken(token);
		Qrcode qrcode = qrcodeMng.getBySign(account.getId(), sign);
		
		if (qrcode==null) {
			long id = qrcodeMng.add(account.getId(), sign);
			qrcode = qrcodeMng.get(id);
		}
		String ticket = qrcode.getTicket();
		
		return "redirect:https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
	}
	
	protected WebErrors validateQrcode(HttpServletRequest request, String accountName, 
			String sign) {
		WebErrors errors = WebErrors.create(request);
		
		errors.ifBlank(accountName, "微信公众号", 200);
		errors.ifBlank(sign, "二维码标识", 200);
		
		if (accountMng.getByName(accountName)==null) {
			errors.addError("该微信公众号不存在");
		}
		
		return errors;
	}
	
	@Autowired
	private AccountMng accountMng;
	@Autowired
	private QrcodeMng qrcodeMng;
	
}
