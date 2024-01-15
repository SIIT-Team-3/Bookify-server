package rs.ac.uns.ftn.Bookify.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private IReservationRepository reservationRepository;


    @Test(dataProvider = "reservations")
    public void getPriceListItems(Long accommodationId, LocalDate start, LocalDate end, int expected) {
        List<Reservation> items = reservationRepository.findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(accommodationId, end, start, EnumSet.of(Status.CANCELED, Status.REJECTED));

        assertEquals(expected, items.size());
        for (int i = 0; i < items.size(); i++) {
            assertFalse(items.get(i).getStart().isAfter(end));
            assertFalse(items.get(i).getEnd().isBefore(start));
        }
    }

    @DataProvider(name = "reservations")
    public Object[][] reservations() {
        return new Object[][]{
                {1L, LocalDate.of(2027, 1, 1), LocalDate.of(2028, 1, 1), 1},
                {1L, LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), 0},
                {1L, LocalDate.of(2037, 1, 1), LocalDate.of(2038, 1, 1), 1}
        };
    }
}
