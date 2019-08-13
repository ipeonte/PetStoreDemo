package com.example.demo.petstore.rest.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.petstore.rest.jpa.model.Pet;

@Repository
public interface PetsRepository extends JpaRepository<Pet, Long> {

	public List<Pet> findAllByOrderByNameAsc();
}
