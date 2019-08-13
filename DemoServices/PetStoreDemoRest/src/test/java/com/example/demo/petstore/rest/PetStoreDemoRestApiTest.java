package com.example.demo.petstore.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.petstore.rest.common.Constants;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PetStoreDemoRestApiTest {

	@Autowired
	private TestRestTemplate _rest;

	@Autowired
	private JdbcTemplate _jdbc;

	@Test
	public void testFindPetBad() {
		ResponseEntity<String> response = _rest.getForEntity(Constants.BASE_URL + "/pet/100", String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		response = _rest.getForEntity(Constants.BASE_URL + "/pet/a", String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testFindPetGood() {
		ResponseEntity<String> response = _rest.getForEntity(Constants.BASE_URL + "/pet/1", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertNotNull(response.getBody());
		assertEquals("{\"id\":1,\"name\":\"Lucky\",\"sex\":\"Male\"}", response.getBody());
	}

	@Test
	public void testAddPetBad() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Try empty request
		ResponseEntity<String> response = _rest.postForEntity(Constants.BASE_URL + "/pet", new HttpEntity<String>("", headers),
				String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// Try invalid payload
		response = _rest.postForEntity(Constants.BASE_URL + "/pet", new HttpEntity<String>("{\"name\": \"Jira\"}", headers), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testAddPetGood() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>("{\"name\": \"Jira\",\"sex\":\"Female\"}", headers);

		ResponseEntity<Integer> response = _rest.postForEntity(Constants.BASE_URL + "/pet", entity, Integer.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertNotNull(response.getBody());
		int id = response.getBody();

		// Check entry available in database
		assertEquals(1,
				_jdbc.queryForObject("select count(*) from " + "pets where id = " + id, Integer.class).intValue());

		// Delete that entry
		_jdbc.execute("delete from pets where id = " + id);

	}

	@Test
	public void testDeleteBad() {
		ResponseEntity<String> response = _rest.exchange(Constants.BASE_URL + "/pet/100", HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		response = _rest.exchange(Constants.BASE_URL + "/pet/a", HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testDeletePetGood() {
		int id = 99;
		// Inser new record
		_jdbc.execute("insert into pets(id, name, sex) values(" + id + ", 'Jira', 'F')");

		// Delete that entry
		ResponseEntity<String> response = _rest.exchange(Constants.BASE_URL + "/pet/" + id, HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// Check entry doesn't exists in database any more
		assertEquals(0,
				_jdbc.queryForObject("select count(*) from " + "pets where id = " + id, Integer.class).intValue());
	}
}
