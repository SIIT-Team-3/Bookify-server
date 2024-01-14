package rs.ac.uns.ftn.Bookify.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Transactional
    @Rollback
    public void insertTest() throws JsonProcessingException {
        Long accommodationId = 1L;
        Long guestId = 1L;
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(new Date(124, 2, 2), new Date(124, 2, 12), new Date(124, 2, 20), 3, 120.0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-CSRF-TOKEN", "your-csrf-token-here");

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
        assertEquals("2024-03-12", responseJson.path("start").asText());
        assertEquals("2024-03-20", responseJson.path("end").asText());
        assertEquals(3, responseJson.path("guestNumber").asInt());
        assertEquals(120.0, responseJson.path("price").asDouble(), 0.01);  // Adjust delta based on your precision requirements
        assertEquals(1, responseJson.path("accommodationId").asLong());
        assertEquals("Test", responseJson.path("accommodationName").asText());
        assertEquals(0.0, responseJson.path("avgRating").asDouble());
        assertEquals("PENDING", responseJson.path("status").asText());
    }
}
