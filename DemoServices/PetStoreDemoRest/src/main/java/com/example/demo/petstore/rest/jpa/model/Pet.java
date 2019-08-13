package com.example.demo.petstore.rest.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PETS")
public class Pet {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String sex;

	/**
	 * Default constructor. Required by JPA to work properly since one more 
	 * custom constructor defined.
	 */
	public Pet() {
		// TODO Auto-generated constructor stub
	}
	
	public Pet(String name, String sex) {
		this.name = name;
		this.setSex(sex);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	
	
}
