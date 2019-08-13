package com.example.demo.petstore.qa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.common.security.service.UserInfo;

/**
 * PetStore Controller
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
@RestController
@RequestMapping("/api/v1")
public class UserInfoQaController {

	@RequestMapping(value = "/user_info", method = RequestMethod.POST)
	public void setUserInfo(@RequestParam("name") String name, 
			@RequestParam("mask") String mask, HttpServletRequest request) {
		request.getSession().setAttribute("user_info", new UserInfo(name, mask));
	}
}