package rs.ac.uns.ftn.Bookify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.Bookify.config.utils.JWTUtils;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.dto.UserCredentialsDTO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerIntegrationTest {
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
    public void insertTest() throws JsonProcessingException {
        Long accommodationId = 1L;
        Long guestId = 1L;
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(new Date(124, 2, 2), new Date(124, 2, 12), new Date(124, 2, 20), 3, 120.0);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<ReservationRequestDTO> requestEntity = new HttpEntity<>(reservationRequestDTO, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/reservations/create?accommodationId={accommodationId}&guestId={guestId}",
                HttpMethod.POST,
                requestEntity,
                String.class,
                accommodationId,
                guestId
        );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        String responseBody = responseEntity.getBody();
        assertNotNull(responseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseBody);
        assertNotNull(responseJson);

        assertEquals(1, responseJson.path("id").asLong());
        assertEquals("2024-03-02", responseJson.path("created").asText());
        assertEquals("12.03.2024.", responseJson.path("start").asText());
        assertEquals("20.03.2024.", responseJson.path("end").asText());
        assertEquals(3, responseJson.path("guestNumber").asInt());
        assertEquals(120.0, responseJson.path("price").asDouble(), 0.01);  // Adjust delta based on your precision requirements
        assertEquals(1, responseJson.path("accommodationId").asLong());
        assertEquals("Downtown Loft", responseJson.path("accommodationName").asText());
        assertEquals(0.0, responseJson.path("avgRating").asDouble());
        assertEquals("PENDING", responseJson.path("status").asText());
    }
}
