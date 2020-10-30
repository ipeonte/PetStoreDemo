package com.example.rest.common.security.service;

public class UserInfo {

	private final String name;
	
	private final String mask;
	
	public UserInfo(String name, String mask) {
		this.name = name;
		this.mask = mask;
	}
	
	public final String getName() {
		return name;
	}
	
	public final String getMask() {
		return mask;
	}
}
