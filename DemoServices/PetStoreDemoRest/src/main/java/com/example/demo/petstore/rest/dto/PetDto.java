package com.example.demo.petstore.rest.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.example.demo.petstore.rest.common.PetSex;

/**
 * Class for Pet DTO
 */
public class PetDto {
	
	private Long id;

	@NotBlank
	private String name;

	@NotNull
	private PetSex sex;

	/**
	 * Default constructor
	 */
	public PetDto() {
	}
	
	public PetDto(Long id, String name, PetSex sex) {
		this.id = id;
		this.name = name;
		this.sex = sex;
	}
	
	public PetDto(String name, PetSex sex) {
		this(null, name, sex);
	}
	
	/**
	 * Get id
	 * 
	 * @return id
	 **/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get name
	 * 
	 * @return name
	 **/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * pet status in the store
	 * 
	 * @return status
	 **/
	public PetSex getSex() {
		return sex;
	}

	public void setSex(PetSex sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return id + ":" + name + ":" + getSex();
	}
}
