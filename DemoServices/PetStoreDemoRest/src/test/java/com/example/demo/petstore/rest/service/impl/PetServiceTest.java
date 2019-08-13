package com.example.demo.petstore.rest.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.petstore.rest.common.PetSex;
import com.example.demo.petstore.rest.dto.PetDto;
import com.example.demo.petstore.rest.service.PetService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PetServiceTest {

	@Autowired
	private PetService petService;
	
	@Test
	public void testGetAllPetsGood() {
		List<PetDto> pets = petService.getAllPets();
		
		assertNotNull(pets);
		assertEquals(3, pets.size());
		
		// Expected list sort alphabetically in ascending order
		assertEquals("Bella", pets.get(0).getName());
		assertEquals("Dino", pets.get(1).getName());
		assertEquals("Lucky", pets.get(2).getName());
		// And with all statuses Available
		
		pets.forEach(pet -> assertEquals(PetSex.M, pet.getSex()));
	}
	
	@Test
	public void testFindPetByIdPass() {
		PetDto pet = petService.getPetById(1L);
		
		checkPet(pet, "Lucky", PetSex.M);
	}
	
	@Test
	public void testFindPetByIdFail() {
		PetDto pet = petService.getPetById(4L);
		assertNull(pet);
	}
	
	@Test
	public void testAddDeletePet() {
		Long id = petService.addPet(new PetDto("Jira", PetSex.F));
		checkPet(petService.getPetById(id), "Jira", PetSex.F);
		petService.deletePet(id);
		testFindPetByIdFail();
		
	}

	private void checkPet(PetDto pet, String name, PetSex status) {
		assertNotNull(pet);
		assertEquals(name, pet.getName());
		assertEquals(status, pet.getSex());
	}
}
