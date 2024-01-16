package rs.ac.uns.ftn.Bookify.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.uns.ftn.Bookify.dto.UserCredentialsDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccommodationControllerIntegrationTest {
    String token;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public void login() throws JSONException {
        ResponseEntity<String> responseEntity1 = restTemplate.postForEntity("/api/v1/users/login",
                new UserCredentialsDTO("test@example.com", "123"), String.class);
        JSONObject json = new JSONObject(responseEntity1.getBody());
        token = json.getString("accessToken");
    }

    @Test
    public void getTotalPriceSuccessTest() {
        String url = "/api/v1/accommodations/price?id=1&begin=12.03.2024&end=15.03.2024&pricePer=ROOM&persons=2";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        Double totalPrice = Double.valueOf(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(45.0, totalPrice);
    }

    @Test
    public void getTotalPricePastTest() {
        String url = "/api/v1/accommodations/price?id=1&begin=12.03.2023&end=15.03.2023&pricePer=ROOM&persons=2";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        Double totalPrice = Double.valueOf(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(-1.0, totalPrice);
    }

    @Test
    public void getTotalPriceNotAvailableTest() {
        String url = "/api/v1/accommodations/price?id=1&begin=01.03.2024&end=03.03.2024&pricePer=ROOM&persons=2";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        Double totalPrice = Double.valueOf(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(-1.0, totalPrice);
    }

    @Test
    public void getTotalPriceNotPersonsTest() {
        String url = "/api/v1/accommodations/price?id=1&begin=12.03.2024&end=15.03.2024&pricePer=ROOM&persons=5";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        Double totalPrice = Double.valueOf(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(-1.0, totalPrice);
    }
}
