package rs.ac.uns.ftn.Bookify.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAvailabilityRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IPriceListItemRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;
import rs.ac.uns.ftn.Bookify.service.interfaces.INotificationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceTest {
    @Mock
    private IAccommodationRepository accommodationRepository;
    @Mock
    private IReservationService reservationService;
    @Mock
    private IUserService userService;
    @Mock
    private IImageService imageService;
    @Mock
    private INotificationService notificationService;
    @Mock
    private IAvailabilityRepository availabilityRepository;
    @Mock
    private IPriceListItemRepository priceListItemRepository;
    @InjectMocks
    private AccommodationService accommodationService;

    @Test
    @DisplayName("")
    public void test_acceptReservationForAccommodation(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setManual(true);
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),2,300, new Guest(),accommodation, Status.PENDING);
        Optional<Reservation> r = Optional.of(reservation);

        accommodationService.acceptReservationForAccommodation(reservation);

        Assertions.assertEquals(reservation.getStatus(), Status.ACCEPTED);
    }


    @Test
    @DisplayName("Reservation should not be accepted")
    public void test_acceptReservationIfAutomaticConformation_manuel(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setManual(true);
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),2,300, new Guest(),accommodation, Status.PENDING);

        accommodationService.acceptReservationIfAutomaticConformation(reservation);

        Assertions.assertEquals(reservation.getStatus(), Status.PENDING);

        Mockito.verifyNoInteractions(reservationService);
        Mockito.verifyNoInteractions(accommodationRepository);
        Mockito.verifyNoInteractions(availabilityRepository);
        Mockito.verifyNoInteractions(notificationService);
    }

    @Test
    @DisplayName("")
    public void test_acceptReservationIfAutomaticConformation_automatic(){
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setManual(false);
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10),2,300, new Guest(),accommodation, Status.PENDING);
        Optional<Reservation> r = Optional.of(reservation);
        accommodationService.acceptReservationIfAutomaticConformation(reservation);

        Assertions.assertEquals(reservation.getStatus(), Status.ACCEPTED);

        Mockito.verify(reservationService).accept(reservation.getId());
        Mockito.verify(accommodationRepository).getReferenceById(accommodation.getId());
        Mockito.verifyNoInteractions(availabilityRepository);
    }

    //trimOverlapingAvailabilityIntervals

}
