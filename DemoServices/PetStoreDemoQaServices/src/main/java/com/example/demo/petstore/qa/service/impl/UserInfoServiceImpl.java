package com.example.demo.petstore.qa.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.example.rest.common.security.service.UserInfo;
import com.example.rest.common.security.service.UserInfoService;

@Service
@SessionScope
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private HttpSession session;
	
	@Override
	public UserInfo getUserInfo() {
		return session.getAttribute("user_info") != null ? 
				(UserInfo) session.getAttribute("user_info") : 
					new UserInfo("Amonymous", "0");
	}

}
