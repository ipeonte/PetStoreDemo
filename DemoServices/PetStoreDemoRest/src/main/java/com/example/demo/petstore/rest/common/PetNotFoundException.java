package com.example.demo.petstore.rest.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PetNotFoundException extends RuntimeException {

	// Default Serial Version UID
	private static final long serialVersionUID = 1L;
}
