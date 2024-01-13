package rs.ac.uns.ftn.Bookify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.enumerations.*;
import rs.ac.uns.ftn.Bookify.mapper.ReservationDTOMapper;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.service.AccommodationService;
import rs.ac.uns.ftn.Bookify.service.ReservationService;
import rs.ac.uns.ftn.Bookify.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    @WithMockUser(roles = "GUEST")
    public void insertTest() throws Exception {
        Long accommodationId = 1L;
        Long guestId = 1L;

        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(new Date(2024, 3, 2), new Date(2024, 3, 12), new Date(2024, 3, 20), 3, 160.0);
        Accommodation accommodation = getAccommodationMock();
        Guest guest = getGuestMock();

        Reservation reservation = new Reservation(1L, LocalDate.of(2024, 3, 2),
                LocalDate.of(2024, 3, 12), LocalDate.of(2024, 3, 20),
                3, 120, guest, accommodation, Status.PENDING);

        when(reservationService.save(any(Reservation.class))).thenReturn(reservation);
        when(accommodationService.getAccommodation(anyLong())).thenReturn(accommodation);
        when(userService.get(anyLong())).thenReturn(guest);

//        ReservationDTO reservationDTO = ReservationDTOMapper.toReservationDTO(ra);

        mockMvc.perform(post("/api/v1/reservations/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reservationRequestDTO))
                        .param("accommodationId", accommodationId.toString())
                        .param("guestId", guestId.toString()))
                .andExpect(status().isCreated());

//        mockMvc.perform(post("/api/v1/reservations/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(reservationRequestDTO))
//                        .param("accommodationId", accommodationId.toString())
//                        .param("guestId", guestId.toString()))
//                .andExpect(status().isCreated());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
//                .andExpect(jsonPath("$.id").value(reservation.getId()))
//                .andExpect(jsonPath("$.created").value(reservation.getCreated()))
//                .andExpect(jsonPath("$.start").value(reservation.getStart()))
//                .andExpect(jsonPath("$.end").value(reservation.getEnd()))
//                .andExpect(jsonPath("$.guestNumber").value(reservation.getGuestNumber()))
//                .andExpect(jsonPath("$.price").value(reservation.getPrice()))
//                .andExpect(jsonPath("$.status").value(reservation.getStatus()))
//                .andExpect(jsonPath("$.accommodationId").value(reservation.getAccommodation().getId()))
//                .andExpect(jsonPath("$.accommodationName").value(reservation.getAccommodation().getName()))
//                .andExpect(jsonPath("$.avgRating").value(reservation.getAccommodation().))
//                .andExpect(jsonPath("$.imageId").value(reservation.getAccommodation().getId()));

//        verify(reservationService).save(any(Reservation.class));
//        verify(accommodationService).getAccommodation(accommodationId);
//        verify(userService).get(guestId);
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
        return objectMapper.writeValueAsString(obj);
    }
}
