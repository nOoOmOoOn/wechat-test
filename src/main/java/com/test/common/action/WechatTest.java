package com.test.common.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WechatTest {

	@RequestMapping(value = { "/wechat/test.html" })
	public String wechatTest(HttpServletRequest request, ModelMap model) {
		System.out.println("wechat test !");
		return "redirect:/common/wechat/test2.html";
	}
	
	@RequestMapping(value = { "/wechat/test2.html" })
	public String wechatTest2(HttpServletRequest request, ModelMap model) {
		System.out.println("wechat test2 !");
		return "/common/index";
	}
}
