package com.example.demo.petstore.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.petstore.rest.common.Constants;
import com.example.demo.petstore.rest.common.PetNotFoundException;
import com.example.demo.petstore.rest.dto.PetDto;
import com.example.demo.petstore.rest.service.PetService;

/**
 * PetStore Controller
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
@RestController
@RequestMapping(Constants.BASE_URL)
public class PetController {

	@Autowired
	private PetService petService;
	
	@RequestMapping("/pets")
	public List<PetDto> getAllPets() {
		return petService.getAllPets();
	}
	
	@RequestMapping(value = "/pet/{petId}")
	public PetDto findPetById(@PathVariable("petId") Long petId) {
		PetDto res = petService.getPetById(petId);
		
		if (res == null)
			throw new PetNotFoundException();
		
		return res;
	}
	
	@RequestMapping(value = "/pet", method = RequestMethod.POST)
	public String addPet(@Valid @RequestBody PetDto pet) {
		return "\"" + petService.addPet(pet).toString() + "\"";
	}
	
	@RequestMapping(value = "/pet/{petId}", method = RequestMethod.DELETE)
	public void deletePet(@PathVariable("petId") Long petId) {
		PetDto res = petService.getPetById(petId);
		
		if (res == null)
			throw new PetNotFoundException();
		
		petService.deletePet(petId);
	}
}
