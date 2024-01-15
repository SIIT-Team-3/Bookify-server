package rs.ac.uns.ftn.Bookify.controller;

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
import org.testng.annotations.Test;
import rs.ac.uns.ftn.Bookify.config.utils.JWTUtils;
import rs.ac.uns.ftn.Bookify.dto.PriceListItemDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.testng.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccommodationControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test(priority = 1)
    public void getAccommodationPriceListItems(){
        JWTUtils jwtUtils = new JWTUtils();
        String jwtToken = jwtUtils.generateToken("owner@example.com", 100L,"OWNER", "web");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a RequestCallback to set the headers in the request
//        RequestCallback requestCallback = restTemplate.httpEntityCallback(new HttpEntity<>(headers));
//
//        // Define the endpoint URL
//        String url = "your_api_url";
//
//        // Make the authorized request
//        ResponseEntity<Collection<MyObject>> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<Collection<MyOb

//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);
//        restTemplate.
//        restTemplate.setMessageConverters(messageConverters);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<PriceListItemDTO>> responseEntity = restTemplate.exchange("/api/v1/accommodations/1/getPrice",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<PriceListItemDTO>>() {
                });

        List<PriceListItemDTO> items = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, items.size());
    }

    @Test(priority = 2)
    public void addPriceListItem(){
        PriceListItemDTO dto = new PriceListItemDTO(java.sql.Date.valueOf(LocalDate.of(2024, 12,12)),
                java.sql.Date.valueOf(LocalDate.of(2024, 12, 13)), 100);

        JWTUtils jwtUtils = new JWTUtils();
        String jwtToken = jwtUtils.generateToken("owner@example.com", 100L,"ROLE_OWNER", "web");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<PriceListItemDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<Long> responseEntity = restTemplate.exchange("/api/v1/accommodations/1/addPrice",
                HttpMethod.POST,
                requestEntity,
                Long.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody());
    }

    @Test(priority = 3)
    public void deletePriceListItem(){
        PriceListItemDTO dto = new PriceListItemDTO(Date.from(LocalDate.of(2024, 3,2).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2024, 3, 13).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10.99);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<PriceListItemDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<PriceListItemDTO> responseEntity = restTemplate.exchange("/api/v1/accommodations/price/1",
                HttpMethod.DELETE,
                requestEntity,
                PriceListItemDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dto, responseEntity.getBody());
    }
}
