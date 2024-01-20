package rs.ac.uns.ftn.Bookify.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RequestCallback;
import rs.ac.uns.ftn.Bookify.config.utils.JWTUtils;
import rs.ac.uns.ftn.Bookify.dto.PriceListItemDTO;
import rs.ac.uns.ftn.Bookify.dto.UserCredentialsDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.testng.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccommodationControllerTest extends AbstractTestNGSpringContextTests {
    String token;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public void login() throws JSONException {
        ResponseEntity<String> responseEntity1 = restTemplate.postForEntity("/api/v1/users/login",
                new UserCredentialsDTO("owner@example.com", "123"), String.class);
        JSONObject json = new JSONObject(responseEntity1.getBody());
        token = json.getString("accessToken");
    }

    @Test
    @Order(1)
    public void getAccommodationPriceListItems(){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<PriceListItemDTO>> responseEntity = restTemplate.exchange("/api/v1/accommodations/1/getPrice",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<PriceListItemDTO>>() {
                });

        List<PriceListItemDTO> items = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(items);
        assertEquals(3, items.size());
    }

    @Test
    @Order(2)
    public void addPriceListItem(){
        PriceListItemDTO dto = new PriceListItemDTO(java.sql.Date.valueOf(LocalDate.of(2024, 12,12)),
                java.sql.Date.valueOf(LocalDate.of(2024, 12, 13)), 100);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        HttpEntity<PriceListItemDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<Long> responseEntity = restTemplate.exchange("/api/v1/accommodations/1/addPrice",
                HttpMethod.POST,
                requestEntity,
                Long.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody());
    }

    @Test
    @Order(3)
    public void addPriceListItemBadRequest(){
        PriceListItemDTO dto = new PriceListItemDTO(java.sql.Date.valueOf(LocalDate.of(2027, 12,4)),
                java.sql.Date.valueOf(LocalDate.of(2027, 12, 13)), 100);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        HttpEntity<PriceListItemDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/v1/accommodations/1/addPrice",
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Accommodation has reservations", responseEntity.getBody());
    }

    @Test
    @Order(4)
    public void deletePriceListItem(){
        PriceListItemDTO dto = new PriceListItemDTO(Date.from(LocalDate.of(2024, 3,2).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2024, 3, 13).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10.99);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        HttpEntity<PriceListItemDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<PriceListItemDTO> responseEntity = restTemplate.exchange("/api/v1/accommodations/price/1",
                HttpMethod.DELETE,
                requestEntity,
                PriceListItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    @Order(5)
    public void deletePriceListItemBadRequest(){
        PriceListItemDTO dto = new PriceListItemDTO(Date.from(LocalDate.of(2027, 12,1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2027, 12, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()), 13);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        HttpEntity<PriceListItemDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/v1/accommodations/price/1",
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Accommodation has reservations", responseEntity.getBody());
    }
}
