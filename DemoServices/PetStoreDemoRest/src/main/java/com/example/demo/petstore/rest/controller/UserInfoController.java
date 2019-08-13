package com.example.demo.petstore.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.petstore.rest.common.Constants;
import com.example.rest.common.security.service.UserInfo;
import com.example.rest.common.security.service.UserInfoService;

/**
 * PetStore Controller
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
@RestController
@RequestMapping(Constants.BASE_URL)
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping("/user_info")
	public UserInfo getUserInfo() {
		return userInfoService.getUserInfo();
	}
}
