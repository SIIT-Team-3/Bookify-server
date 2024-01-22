package rs.ac.uns.ftn.Bookify.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationGuestViewDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.dto.UserCredentialsDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-iss.properties")
@ActiveProfiles("iss")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerTest extends AbstractTestNGSpringContextTests {
    /**
    * Should cover endpoint:
    *       acceptReservation
    *       rejectReservation
    *       insert (when accommodation attached to reservation is set to automatically/manually accept reservation)
    * */

    @Autowired
    private TestRestTemplate restTemplate;
    static String token;
    static String guestToken;

    @BeforeAll
    public void login() throws JSONException {
        ResponseEntity<String> responseEntity1 = restTemplate.postForEntity("/api/v1/users/login",
                new UserCredentialsDTO("owner@example.com", "123"), String.class);
        JSONObject json = new JSONObject(responseEntity1.getBody());
        token = json.getString("accessToken");

        responseEntity1 = restTemplate.postForEntity("/api/v1/users/login",
                new UserCredentialsDTO("guest@example.com", "123"), String.class);
        json = new JSONObject(responseEntity1.getBody());
        guestToken = json.getString("accessToken");

    }

    // Accepting reservation
    @Test
    @Order(1)
    @DisplayName("Should accept reservation")
    public void test_acceptReservation_success() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange("/api/v1/reservations/accept/4",
                HttpMethod.PUT,
                requestEntity,
                ReservationDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), 4);
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getStatus(), Status.ACCEPTED);
    }

    @Test
    @Order(2)
    @DisplayName("Should accept reservation")
    public void test_acceptReservation_success_overlapping_reservation_rejected() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange("/api/v1/reservations/accept/9",
                HttpMethod.PUT,
                requestEntity,
                ReservationDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), 9);
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getStatus(), Status.ACCEPTED);
        Assertions.assertNull(requestEntity.getBody());
    }


    @ParameterizedTest
    @DisplayName("Shouldn't accept reservation because of reservation status")
    @Order(3)
    @CsvSource(value = {"5","6","7"})
    public void test_acceptReservation_fail_status_not_pending(Long param){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = null;

        responseEntity = restTemplate.exchange("/api/v1/reservations/accept/" + param,
                HttpMethod.PUT,
                requestEntity,
                String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(responseEntity.getBody(), "Reservation is not in status 'PENDING'");

    }
    @ParameterizedTest
    @DisplayName("Shouldn't accept reservation because reservation is not found")
    @Order(4)
    @CsvSource(value = {"1000"})
    public void test_acceptReservation_fail_reservation_not_found(Long param){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = null;

        responseEntity = restTemplate.exchange("/api/v1/reservations/accept/" + param,
                HttpMethod.PUT,
                requestEntity,
                String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(responseEntity.getBody(), "Reservation not found");

    }

    @ParameterizedTest
    @Order(5)
    @DisplayName("Shouldn't accept reservation because of cancellation expired")
    @CsvSource(value = {"1"})
    public void test_acceptReservation_fail_reservation_response_date_expired(int param){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = null;

        responseEntity = restTemplate.exchange("/api/v1/reservations/accept/" + param,
                HttpMethod.PUT,
                requestEntity,
                String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(responseEntity.getBody(), "The date to respond to this reservation has expired");

    }

    // Rejecting reservation
    @Test
    @Order(6)
    @DisplayName("Should reject reservation")
    public void shouldRejectReservation() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange("/api/v1/reservations/reject/13",
                HttpMethod.PUT,
                requestEntity,
                ReservationDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), 13);
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getStatus(), Status.REJECTED);
        Assertions.assertNull(requestEntity.getBody());
    }

    @ParameterizedTest
    @Order(7)
    @DisplayName("Shouldn't reject reservation because of reservation status")
    @CsvSource(value = {"5","6","7"})
    public void test_rejectReservation_fail_status_not_pending(Long param){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = null;

        responseEntity = restTemplate.exchange("/api/v1/reservations/reject/" + param,
                HttpMethod.PUT,
                requestEntity,
                String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(responseEntity.getBody(), "Reservation is not in status 'PENDING'");
    }

    @ParameterizedTest
    @Order(8)
    @DisplayName("Shouldn't accept reservation because reservation is not found")
    @CsvSource(value = {"1000"})
    public void test_rejectReservation_fail_reservation_not_found(Long param){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = null;

        responseEntity = restTemplate.exchange("/api/v1/reservations/reject/" + param,
                HttpMethod.PUT,
                requestEntity,
                String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(responseEntity.getBody(), "Reservation not found");
    }

    // Reservation listing
    @ParameterizedTest
    @Order(9)
    @DisplayName("Should return 10 reservations")
    @CsvSource(value = {"2,5"})
    public void test_getReservationsByGuestId(Long guestId, int realSize){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + guestToken);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Collection<ReservationGuestViewDTO>> responseEntity = null;

        responseEntity = restTemplate.exchange("/api/v1/reservations/guest/" + guestId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Collection<ReservationGuestViewDTO>>() {
                }
        );
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).size(), realSize);
    }

    @ParameterizedTest
    @Order(10)
    @DisplayName("Should return 0 reservations")
    @CsvSource(value = {"5"})
    public void test_getReservationsByGuestId_not_reservations(Long guestId){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + guestToken);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Collection<ReservationGuestViewDTO>> responseEntity = null;

        responseEntity = restTemplate.exchange("/api/v1/reservations/guest/" + guestId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Collection<ReservationGuestViewDTO>>() {
                }
        );
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).size(), 0);
    }
    // Insert reservation - Automatic Reservation accepting

    @Test
    @Order(11)
    @DisplayName("Reservation should be accepted after it's saved in database")
    public void test_insert_reservation_automatic_accept(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + guestToken);

        Long accommodationId = 2L;
        Long guestId = 4L;
        LocalDate startLocalDate = LocalDate.of(2024, 10, 5);
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate createdLocalDate = LocalDate.of(2024, 10, 5);
        Date createdDate = Date.from(createdLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate endLocalDate = LocalDate.of(2024, 10, 5);
        Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(createdDate, startDate, endDate, 3, 120.0);
        HttpEntity<ReservationRequestDTO> requestEntity = new HttpEntity<>(reservationRequestDTO,headers);

        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange(
                "/api/v1/reservations/create?accommodationId={accommodationId}&guestId={guestId}",
                HttpMethod.POST,
                requestEntity,
                ReservationDTO.class,
                accommodationId,
                guestId
        );

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getStatus(), Status.ACCEPTED);
        Assertions.assertEquals(responseEntity.getBody().getAccommodationId(), accommodationId);

    }


    // Insert reservation - Manuel Reservation accepting
    @Test
    @Order(12)
    @DisplayName("Reservation shouldn't be accepted after it's saved in database")
    public void test_insert_reservation_manuel_accept(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + guestToken);

        Long accommodationId = 1L;
        Long guestId = 4L;
        LocalDate startLocalDate = LocalDate.of(2024, 4, 5);
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate createdLocalDate = LocalDate.of(2024, 4, 5);
        Date createdDate = Date.from(createdLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate endLocalDate = LocalDate.of(2024, 4, 5);
        Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(createdDate, startDate, endDate, 3, 120.0);
        HttpEntity<ReservationRequestDTO> requestEntity = new HttpEntity<>(reservationRequestDTO,headers);

        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange(
                "/api/v1/reservations/create?accommodationId={accommodationId}&guestId={guestId}",
                HttpMethod.POST,
                requestEntity,
                ReservationDTO.class,
                accommodationId,
                guestId
        );

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getStatus(), Status.PENDING);
        Assertions.assertEquals(responseEntity.getBody().getAccommodationId(), accommodationId);

    }
}
