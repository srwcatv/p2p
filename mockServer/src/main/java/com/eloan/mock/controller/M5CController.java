package com.eloan.mock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class M5CController extends BaseController {

	@RequestMapping("/send")
	@ResponseBody
	public String send(String username, String password, String apiKey, String phoneNumber, String content) {
		System.out.println("发送短信给手机：" + phoneNumber + "，发送短信内容为：" + content);
		return "success:" + UUID.randomUUID().toString();
	}
}
