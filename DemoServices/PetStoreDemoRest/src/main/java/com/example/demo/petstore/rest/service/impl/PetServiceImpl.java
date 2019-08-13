package com.example.demo.petstore.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.petstore.rest.service.PetService;
import com.example.demo.petstore.rest.common.PetSex;
import com.example.demo.petstore.rest.dto.PetDto;
import com.example.demo.petstore.rest.jpa.model.Pet;
import com.example.demo.petstore.rest.jpa.repo.PetsRepository;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private PetsRepository petRepo;
	
	@Override
	public List<PetDto> getAllPets() {
		List<PetDto> pets = new ArrayList<>();
		
		petRepo.findAllByOrderByNameAsc().forEach(pet -> pets.add(
			new PetDto(pet.getId(), pet.getName(), 
				PetSex.valueOf(pet.getSex().toUpperCase()))));
		
		return pets;
	}

	@Override
	public PetDto getPetById(Long id) {
		Pet pet = petRepo.findOne(id);
		return (pet != null ? new PetDto(pet.getId(), pet.getName(), 
			PetSex.valueOf(pet.getSex())): null);
	}

	@Override
	public Long addPet(PetDto pet) {
		return petRepo.save(new Pet(pet.getName(), pet.getSex().name())).getId();
	}

	@Override
	public void deletePet(Long id) {
		petRepo.delete(id);
	}

}
