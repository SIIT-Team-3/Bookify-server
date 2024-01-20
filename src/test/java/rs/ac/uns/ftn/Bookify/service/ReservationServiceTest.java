package rs.ac.uns.ftn.Bookify.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.exception.BadRequestException;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    IReservationRepository reservationRepository;

    @Mock
    IAccommodationService accommodationService;

    @InjectMocks
    private ReservationService reservationService;

    @Captor
    private ArgumentCaptor<Reservation> ArgumentCaptor;

    // Accept
    @Test
    @DisplayName("Should accept reservation")
    public void test_accept_success(){
        // Arrange
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),2,300, new Guest(), new Accommodation(), Status.PENDING);
        Optional<Reservation> r = Optional.of(reservation);

        // Act
        Mockito.when(reservationRepository.findById(1L)).thenReturn(r);
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation res = reservationService.accept(reservation.getId());

        // Assert
        Assertions.assertEquals(Status.ACCEPTED, res.getStatus());
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).findById(1L);
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).save(reservation);
        Mockito.verifyNoMoreInteractions(reservationRepository);
    }

    @ParameterizedTest
    @DisplayName("Should return bad request exception wrong status")
    @CsvSource(value = {"ACCEPTED","CANCELED","REJECTED"})
    public void test_accept_fail_bad_request_wrong_status(String s){
        Status status = Status.valueOf(s);
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),2,300, new Guest(), new Accommodation(), status);
        Optional<Reservation> r = Optional.of(reservation);

        Mockito.when(reservationRepository.findById(1L)).thenReturn(r);
        Throwable t = Assertions.assertThrows(BadRequestException.class, () -> reservationService.accept(1L));

        // Assert
        Assertions.assertEquals(t.getMessage(), "Reservation is not in status 'PENDING'");
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).findById(1L);
        Mockito.verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @DisplayName("Should return bad request exception response date expired")
    public void test_accept_fail_bad_request_response_date_expired(){
        Reservation reservation = new Reservation(1L, LocalDate.now().minusDays(20), LocalDate.now().minusDays(10), LocalDate.now().minusDays(8),2,300, new Guest(), new Accommodation(), Status.PENDING);
        Optional<Reservation> r = Optional.of(reservation);

        Mockito.when(reservationRepository.findById(1L)).thenReturn(r);
        Throwable t = Assertions.assertThrows(BadRequestException.class, () -> reservationService.accept(1L));

        // Assert
        Assertions.assertEquals(t.getMessage(), "The date to respond to this reservation has expired");
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).findById(1L);
        Mockito.verifyNoMoreInteractions(reservationRepository);
    }
    @Test
    @DisplayName("Should return bad request exception response date expired")
    public void test_accept_fail_bad_request_reservation_not_found(){
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        Throwable t = Assertions.assertThrows(BadRequestException.class, () -> reservationService.accept(1L));

        // Assert
        Assertions.assertEquals(t.getMessage(), "Reservation not found");
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).findById(1L);
        Mockito.verifyNoMoreInteractions(reservationRepository);
    }

    // Reject
    @Test
    @DisplayName("Should get reservation")
    public void test_reject_success(){
        // Arrange
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),2,300, new Guest(), new Accommodation(), Status.PENDING);
        Optional<Reservation> r = Optional.of(reservation);

        // Act
        Mockito.when(reservationRepository.findById(1L)).thenReturn(r);
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation res = reservationService.reject(reservation.getId());

        // Assert
        Assertions.assertEquals(Status.REJECTED, res.getStatus());
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).findById(1L);
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).save(reservation);
        Mockito.verifyNoMoreInteractions(reservationRepository);
    }

    @ParameterizedTest
    @DisplayName("Should return bad request exception wrong status")
    @CsvSource(value = {"ACCEPTED","CANCELED","REJECTED"})
    public void test_reject_fail_bad_request_wrong_status(String s){
        Status status = Status.valueOf(s);
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),2,300, new Guest(), new Accommodation(), status);
        Optional<Reservation> r = Optional.of(reservation);

        Mockito.when(reservationRepository.findById(1L)).thenReturn(r);
        Throwable t = Assertions.assertThrows(BadRequestException.class, () -> reservationService.reject(1L));

        // Assert
        Assertions.assertEquals(t.getMessage(), "Reservation is not in status 'PENDING'");
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).findById(1L);
        Mockito.verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    @DisplayName("Should return bad request exception response date expired")
    public void test_reject_fail_bad_request_response_date_expired(){
        Reservation reservation = new Reservation(1L, LocalDate.now().minusDays(20), LocalDate.now().minusDays(10), LocalDate.now().minusDays(8),2,300, new Guest(), new Accommodation(), Status.PENDING);
        Optional<Reservation> r = Optional.of(reservation);

        Mockito.when(reservationRepository.findById(1L)).thenReturn(r);
        Throwable t = Assertions.assertThrows(BadRequestException.class, () -> reservationService.reject(1L));

        // Assert
        Assertions.assertEquals(t.getMessage(), "The date to respond to this reservation has expired");
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).findById(1L);
        Mockito.verifyNoMoreInteractions(reservationRepository);
    }
    @Test
    @DisplayName("Should return bad request exception response date expired")
    public void test_reject_fail_bad_request_reservation_not_found(){
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        Throwable t = Assertions.assertThrows(BadRequestException.class, () -> reservationService.reject(1L));

        // Assert
        Assertions.assertEquals(t.getMessage(), "Reservation not found");
        Mockito.verify(reservationRepository, Mockito.atMostOnce()).findById(1L);
        Mockito.verifyNoMoreInteractions(reservationRepository);
    }
    // Get guest reservations
    @Test
    @DisplayName("Should return all guest accepted requests (Reservations)")
    public void test_getReservationsByGuestId(){
        Guest guest = new Guest();
        guest.setId(1L);

    }

    // Accept automatic

    @Test
    @DisplayName("Should accept reservation after creating it")
    public void test_insert_automatic_accepting_requests(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setManual(false);
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),2,300, new Guest(), new Accommodation(), Status.PENDING);
        Optional<Reservation> r = Optional.of(reservation);




    }

    // rejectOverlappingReservations
    @Test
    @DisplayName("No overlaping reservations")
    public void test_rejectOverlappingReservations(){

    }
    // save

}
