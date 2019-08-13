package com.example.demo.petstore.rest.service;

import java.util.List;

import com.example.demo.petstore.rest.dto.PetDto;

/**
 * Interface for Pet Service
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public interface PetService {

	/**
	 * Get all pets
	 * 
	 * @return All Pets
	 */
	List<PetDto> getAllPets();
	
	/**
	 * Find Pet by Pet Id
	 * 
	 * @param id Pet Id
	 * @return Pet object or null if nothing found
	 */
	PetDto getPetById(Long id);
	
	/**
	 * Add new Pet
	 * 
	 * @param pet Pet Objec
	 * 
	 * @return Pet Id for newely added pet
	 */
	Long addPet(PetDto pet);
	
	/**
	 * Delete pet by Id
	 * 
	 * @param id Pet Id
	 */
	void deletePet(Long id);
	
}
