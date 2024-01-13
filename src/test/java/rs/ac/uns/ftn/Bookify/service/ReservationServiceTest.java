package rs.ac.uns.ftn.Bookify.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.uns.ftn.Bookify.enumerations.*;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private IReservationRepository reservationRepository;

    @Captor
    private ArgumentCaptor<Reservation> reservationArgumentCaptor;

    @Test
    public void saveTest(){
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 12, 10),
                LocalDate.of(2024, 5, 5), LocalDate.of(2024, 5, 7),
                3, 40, null, null, Status.PENDING);

        reservationService.save(reservation);

        verify(reservationRepository, times(1)).save(reservationArgumentCaptor.capture());
        assertEquals(reservation, reservationArgumentCaptor.getValue());
    }

    @Test
    public void setAccommodationTest(){
        Accommodation accommodation = new Accommodation(1L, "Test", "Desc", 2, 4, 2, false, AccommodationStatusRequest.APPROVED,
                true, new ArrayList<PricelistItem>(), new ArrayList<Availability>(), new ArrayList<Review>(),
                new ArrayList<Filter>(), AccommodationType.HOTEL, PricePer.ROOM, new Address(), new ArrayList<Image>());
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 12, 10),
                LocalDate.of(2024, 5, 5), LocalDate.of(2024, 5, 7),
                3, 40, null, null, Status.PENDING);

        reservationService.setAccommodation(accommodation, reservation);

        assertEquals(accommodation, reservation.getAccommodation());
        verify(reservationRepository, times(1)).save(reservationArgumentCaptor.capture());
        assertEquals(accommodation, reservationArgumentCaptor.getValue().getAccommodation());
    }

    @Test
    public void setGuestTest(){
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 12, 10),
                LocalDate.of(2024, 5, 5), LocalDate.of(2024, 5, 7),
                3, 40, null, null, Status.PENDING);
        Guest guest = new Guest(new HashMap<NotificationType, Boolean>(), new ArrayList<Accommodation>());
        guest.setId(1L);
        guest.setEmail("guest@example.com");
        guest.setPassword("password");
        guest.setFirstName("John");
        guest.setLastName("Doe");
        guest.setBlocked(false);
        guest.setPhone("8452793522");
        guest.setDeleted(false);

        reservationService.setGuest(guest, reservation);

        assertEquals(guest, reservation.getGuest());
        verify(reservationRepository, times(1)).save(reservationArgumentCaptor.capture());
        assertEquals(guest, reservationArgumentCaptor.getValue().getGuest());
    }
}
