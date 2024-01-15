package rs.ac.uns.ftn.Bookify.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ReservationServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ReservationService reservationService;

    @MockBean
    IReservationRepository reservationRepository;

    @Test
    private void hasReservationInRangeEmpty() {
        LocalDate start = LocalDate.of(2024, 2, 2);
        LocalDate end = LocalDate.of(2024, 2, 6);
        Set<Status> statuses = EnumSet.of(Status.CANCELED, Status.REJECTED);

        when(reservationRepository.findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(1L, start, end, statuses)).thenReturn(new ArrayList<>());

        boolean result = reservationService.hasReservationInRange(1L, start, end);

        assertFalse(result);
        verify(reservationRepository).findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(1L, end, start, statuses);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    private void hasReservationInRange() {
        LocalDate start = LocalDate.of(2024, 2, 2);
        LocalDate end = LocalDate.of(2024, 2, 6);
        Set<Status> statuses = EnumSet.of(Status.CANCELED, Status.REJECTED);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());

        when(reservationRepository.findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(1L, end, start, statuses)).thenReturn(reservations);

        boolean result = reservationService.hasReservationInRange(1L, start, end);

        assertTrue(result);
        verify(reservationRepository).findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(1L, end, start, statuses);
        verifyNoMoreInteractions(reservationRepository);
    }
}
