package com.example.demo.petstore.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.petstore.rest.common.Constants;
import com.example.demo.petstore.rest.dto.PetDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PetStoreDemoRestStoryTest {

	@Autowired
	private TestRestTemplate _rest;
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddFindDelPet() {
		ResponseEntity<String> response;
		String petInfo = "\"name\":\"Jira\",\"sex\":\"Female\"";
		
		// 0. Get total number of all pets
		List<PetDto> pets = (List<PetDto>) _rest.getForObject(Constants.BASE_URL + "/pets",
				List.class);
		assertNotNull(pets);
		int cnt = pets.size();
		
		// 1. Add new pet
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(
				"{" + petInfo + "}" ,headers);
		
		ResponseEntity<Integer> resp = _rest.postForEntity(Constants.BASE_URL + "/pet", 
				entity, Integer.class);
		
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		
		assertNotNull(resp.getBody());
		int petId = resp.getBody();

		// 2. Find new pet
		response = _rest.getForEntity(Constants.BASE_URL + "/pet/" + petId, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		assertNotNull(response.getBody());
		assertEquals("{\"id\":" + petId + "," + petInfo + "}", response.getBody());
		
		// 3. Delete pet
		response = _rest.exchange(Constants.BASE_URL + "/pet/" + petId, 
								HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// 4. Check the total number of pets are the same
		pets = (List<PetDto>) _rest.getForObject(Constants.BASE_URL + "/pets", List.class);
		assertNotNull(pets);
		assertEquals(cnt, pets.size());
	}
	
}
