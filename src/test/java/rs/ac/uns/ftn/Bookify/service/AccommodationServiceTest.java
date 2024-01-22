package rs.ac.uns.ftn.Bookify.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAvailabilityRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IPriceListItemRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;
import rs.ac.uns.ftn.Bookify.service.interfaces.INotificationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceTest {
    @Mock
    private IAccommodationRepository accommodationRepository;
    @Mock
    private IReservationService reservationService;
    @Mock
    private IReservationRepository reservationRepository;
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
    @DisplayName("Reservation should not get accepted")
    public void test_acceptReservationIfAutomaticConformation_manuel() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setManual(true);
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10), 2, 300, new Guest(), accommodation, Status.PENDING);

        accommodationService.acceptReservationIfAutomaticConformation(reservation);

        Mockito.verifyNoInteractions(reservationService);
        Mockito.verifyNoInteractions(accommodationRepository);
        Mockito.verifyNoInteractions(availabilityRepository);
        Mockito.verifyNoInteractions(notificationService);
    }

    @Test
    @DisplayName("Reservation should proceed to get accepted")
    public void test_acceptReservationIfAutomaticConformation_automatic() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setManual(false);
        Reservation reservation = new Reservation(1L, LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().plusDays(10), 2, 300, new Guest(), accommodation, Status.PENDING);

        accommodationService.acceptReservationIfAutomaticConformation(reservation);

        Mockito.verify(reservationService).accept(reservation.getId());
        Mockito.verify(reservationService).rejectOverlappingReservations(reservation.getAccommodation().getId(), reservation.getStart(), reservation.getEnd());
        Mockito.verifyNoMoreInteractions(reservationService);
        Mockito.verify(accommodationRepository).getReferenceById(accommodation.getId());
        Mockito.verifyNoInteractions(availabilityRepository);
        Mockito.verify(notificationService).createNotificationGuestRequestResponse(reservation);
        Mockito.verifyNoMoreInteractions(notificationService);
    }

    @ParameterizedTest
    @MethodSource(value = "overlappingTestData")
    @DisplayName("Reservation should get accepted and accommodation's availability shoud get shirnked")
    public void test_acceptReservationForAccommodation_trimOverlapingAvailabilityIntervals(
            LocalDate startReservation,
            LocalDate endReservation,
            List<Availability> accommodationAvailabilitiesBefore,
            List<Availability> accommodationAvailabilitiesAfter
    ) {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setManual(false);
        accommodation.setAvailability(accommodationAvailabilitiesBefore);
        Reservation reservation = new Reservation();
        reservation.setStart(startReservation);
        reservation.setEnd(endReservation);
        reservation.setAccommodation(accommodation);

        Mockito.when(accommodationRepository.getReferenceById(accommodation.getId())).thenReturn(accommodation);
        Mockito.when(accommodationRepository.getAvailabilityItemsOverlapsWith(accommodation.getId(), reservation.getStart(), reservation.getEnd())).thenReturn(new ArrayList<Availability>(accommodationAvailabilitiesBefore));

        accommodationService.acceptReservationIfAutomaticConformation(reservation);
        Assertions.assertEquals(accommodationAvailabilitiesAfter.size(), accommodation.getAvailability().size());
        for (Availability a : accommodationAvailabilitiesAfter) {
            Assertions.assertTrue(() -> {
                for(Availability availability :accommodation.getAvailability()){
                    if(availability.getStartDate().equals(a.getStartDate()) && availability.getEndDate().equals(a.getEndDate())) return true;
                }
                return false;
            });
        }

        Mockito.verify(reservationService).accept(reservation.getId());
        Mockito.verify(reservationService).rejectOverlappingReservations(reservation.getAccommodation().getId(), reservation.getStart(), reservation.getEnd());
        Mockito.verifyNoMoreInteractions(reservationService);
        Mockito.verify(accommodationRepository).getReferenceById(accommodation.getId());
        Mockito.verify(notificationService).createNotificationGuestRequestResponse(reservation);
        Mockito.verifyNoMoreInteractions(notificationService);

    }

    static Stream<Arguments> overlappingTestData() {
        return Stream.of(
                // starts overlapping
                Arguments.arguments(LocalDate.of(2024, 4, 15), LocalDate.of(2024, 4, 20),
                        new ArrayList<>(Arrays.asList(new Availability(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 20)))),
                        new ArrayList<>(Arrays.asList(new Availability(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 14))))),

                // endings overlapping
                Arguments.arguments(LocalDate.of(2024, 4, 20), LocalDate.of(2024, 5, 1),
                        new ArrayList<>(Arrays.asList(new Availability(1L, LocalDate.of(2024, 4, 20), LocalDate.of(2024, 5, 5)))),
                        new ArrayList<>(Arrays.asList(new Availability(1L, LocalDate.of(2024, 5, 2), LocalDate.of(2024, 5, 5))))),

                // containing
                Arguments.arguments(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 10),
                        new ArrayList<>(Arrays.asList(new Availability(1L, LocalDate.of(2024, 5, 20), LocalDate.of(2024, 6, 15)))),
                        new ArrayList<>(Arrays.asList(
                                new Availability(1L, LocalDate.of(2024, 5, 20), LocalDate.of(2024, 5, 31)),
                                new Availability(2L, LocalDate.of(2024, 6, 11), LocalDate.of(2024, 6, 15)))
                        )),

                // start and ending overlapping
                Arguments.arguments(LocalDate.of(2024, 4, 15), LocalDate.of(2024, 4, 20),
                        new ArrayList<>(Arrays.asList(new Availability(1L, LocalDate.of(2024, 4, 15), LocalDate.of(2024, 4, 20)))),
                        new ArrayList<>()));
    }
}
