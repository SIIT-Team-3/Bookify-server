package rs.ac.uns.ftn.Bookify.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccommodationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getTotalPriceSuccessTest() {
        String url = "/api/v1/accommodations/price?id=1&begin=12.03.2024&end=15.03.2024&pricePer=ROOM&persons=2";

//        JWTUtils jwtUtils = new JWTUtils();
//        String token = jwtUtils.generateToken("test@example.com", 1L, "GUEST", "web");
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        Double totalPrice = Double.valueOf(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(45.0, totalPrice);
    }

    @Test
    public void getTotalPricePastTest() {
        String url = "/api/v1/accommodations/price?id=1&begin=12.03.2023&end=15.03.2023&pricePer=ROOM&persons=2";

//        JWTUtils jwtUtils = new JWTUtils();
//        String token = jwtUtils.generateToken("test@example.com", 1L, "GUEST", "web");
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        Double totalPrice = Double.valueOf(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(-1.0, totalPrice);
    }

    @Test
    public void getTotalPriceNotAvailableTest() {
        String url = "/api/v1/accommodations/price?id=1&begin=01.03.2024&end=03.03.2024&pricePer=ROOM&persons=2";

//        JWTUtils jwtUtils = new JWTUtils();
//        String token = jwtUtils.generateToken("test@example.com", 1L, "GUEST", "web");
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        Double totalPrice = Double.valueOf(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(-1.0, totalPrice);
    }

    @Test
    public void getTotalPriceNotPersonsTest() {
        String url = "/api/v1/accommodations/price?id=1&begin=12.03.2024&end=15.03.2024&pricePer=ROOM&persons=5";

//        JWTUtils jwtUtils = new JWTUtils();
//        String token = jwtUtils.generateToken("test@example.com", 1L, "GUEST", "web");
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        Double totalPrice = Double.valueOf(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(-1.0, totalPrice);
    }
}
