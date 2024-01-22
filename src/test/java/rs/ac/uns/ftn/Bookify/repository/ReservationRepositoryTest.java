package rs.ac.uns.ftn.Bookify.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;


@DataJpaTest
@ActiveProfiles("testrepo")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationRepositoryTest {
    /**
     * Should test:
     * findReservationByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn
     */
    @Autowired
    private IReservationRepository reservationRepository;

    @ParameterizedTest
    @MethodSource(value = "source")
    @DisplayName("Find all overlapping availabilities that are in status PENDING")
    public void test_findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(Long accommodationId, LocalDate start, LocalDate end, int expectedSize) {
        Optional<Reservation> reservation = reservationRepository.findById(1L);
        List<Reservation> reservations = reservationRepository.findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(
                accommodationId,
                end,
                start,
                EnumSet.of(Status.CANCELED, Status.ACCEPTED, Status.REJECTED));
        Assertions.assertEquals(expectedSize, reservations.size());
        reservations.forEach(r -> {
            Assertions.assertEquals(Status.PENDING, r.getStatus());
        });
    }


    static Stream<Arguments> source() {
        return Stream.of(
                Arguments.arguments(1L, LocalDate.of(2024, 3, 15), LocalDate.of(2024, 3, 25), 0),
                Arguments.arguments(1L, LocalDate.of(2025, 4, 15), LocalDate.of(2025, 4, 20), 1),
                Arguments.arguments(1L, LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 15), 1),
                Arguments.arguments(1L, LocalDate.of(2025, 6, 15), LocalDate.of(2025, 6, 30), 1),
                Arguments.arguments(1L, LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 20), 1),
                Arguments.arguments(1L, LocalDate.of(2025, 8, 15), LocalDate.of(2025, 8, 20), 1),
                Arguments.arguments(1L, LocalDate.of(2025, 9, 10), LocalDate.of(2025, 9, 20), 1),
                Arguments.arguments(1L, LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 30), 1)
        );
    }


}
