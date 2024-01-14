package rs.ac.uns.ftn.Bookify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.enumerations.*;
import rs.ac.uns.ftn.Bookify.mapper.ReservationDTOMapper;
import rs.ac.uns.ftn.Bookify.mapper.ReservationRequestDTOMapper;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.service.AccommodationService;
import rs.ac.uns.ftn.Bookify.service.ReservationService;
import rs.ac.uns.ftn.Bookify.service.UserService;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ReservationController.class)
public class ReservationControllerTest {
    @MockBean
    private AccommodationService accommodationService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationRequestDTOMapper reservationRequestDTOMapper;

    @MockBean
    private ReservationDTOMapper reservationDTOMapper;

    @Test
    @WithMockUser(roles = "GUEST")
    public void insertTest() throws Exception {
        Long accommodationId = 1L;
        Long guestId = 1L;
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(new Date(124, 2, 2), new Date(124, 2, 12), new Date(124, 2, 20), 3, 120.0);
        Accommodation accommodation = getAccommodationMock();
        Guest guest = getGuestMock();
        Reservation reservation = new Reservation(1L, LocalDate.of(2024, 3, 2),
                LocalDate.of(2024, 3, 12), LocalDate.of(2024, 3, 20),
                3, 120, guest, accommodation, Status.PENDING);
        ReservationDTO reservationDTO = new ReservationDTO(reservation.getId(), reservation.getCreated(), reservation.getStart().toString(), reservation.getEnd().toString(), reservation.getGuestNumber(), reservation.getPrice(), reservation.getStatus(), null, reservation.getAccommodation().getId(), reservation.getAccommodation().getName(), 0, 0L);

        when(reservationRequestDTOMapper.fromReservationRequestDTOToReservation(reservationRequestDTO)).thenReturn(reservation);
        when(reservationService.save(any(Reservation.class))).thenReturn(reservation);
        when(accommodationService.getAccommodation(anyLong())).thenReturn(accommodation);
        when(userService.get(anyLong())).thenReturn(guest);
        when(reservationDTOMapper.toReservationDTO(reservation)).thenReturn(reservationDTO);

        mockMvc.perform(post("/api/v1/reservations/create").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reservationRequestDTO))
                        .param("accommodationId", accommodationId.toString())
                        .param("guestId", guestId.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.created").value("2024-03-02"))
                .andExpect(jsonPath("$.start").value("2024-03-12"))
                .andExpect(jsonPath("$.end").value("2024-03-20"))
                .andExpect(jsonPath("$.guestNumber").value(3))
                .andExpect(jsonPath("$.price").value(120.0))
                .andExpect(jsonPath("$.accommodationId").value(1))
                .andExpect(jsonPath("$.accommodationName").value("Test"))
                .andExpect(jsonPath("$.avgRating").value(0.0))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(reservationService).save(any(Reservation.class));
        verify(accommodationService).getAccommodation(accommodationId);
        verify(userService).get(guestId);
        verify(reservationDTOMapper).toReservationDTO(reservation);
        verify(reservationRequestDTOMapper).fromReservationRequestDTOToReservation(reservationRequestDTO);
        verify(reservationService).setAccommodation(accommodation, reservation);
        verify(reservationService).setGuest(guest, reservation);
        verifyNoMoreInteractions(reservationService);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(reservationDTOMapper);
        verifyNoMoreInteractions(reservationRequestDTOMapper);
    }

    private static Guest getGuestMock(){
        Guest guest = new Guest(new HashMap<NotificationType, Boolean>(), new ArrayList<Accommodation>());
        guest.setId(1L);
        guest.setEmail("guest@example.com");
        guest.setPassword("password");
        guest.setFirstName("John");
        guest.setLastName("Doe");
        guest.setBlocked(false);
        guest.setPhone("8452793522");
        guest.setDeleted(false);
        return guest;
    }

    private static Accommodation getAccommodationMock() {
        List<PricelistItem> pricelistItemList = new ArrayList<>();
        PricelistItem p = new PricelistItem(1L, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 30), 20);
        pricelistItemList.add(p);

        List<Availability> availabilityList = new ArrayList<>();
        Availability a = new Availability(1L, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 30));
        availabilityList.add(a);

        return new Accommodation(1L, "Test", "Desc", 2, 4,
                2, false, AccommodationStatusRequest.APPROVED,
                true, pricelistItemList, availabilityList, new ArrayList<Review>(),
                new ArrayList<Filter>(), AccommodationType.HOTEL, PricePer.ROOM, new Address(), new ArrayList<Image>());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }
}
