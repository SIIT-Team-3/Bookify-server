package rs.ac.uns.ftn.Bookify.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
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
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ReservationRepositoryTest {

    @Autowired
    private IReservationRepository reservationRepository;

    @ParameterizedTest
    @MethodSource(value = "source")
    public void test_findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(Long accommodationId, LocalDate start, LocalDate end, Set<Status> statuses, int expectedNumber) {
        Optional<Reservation> reservation = reservationRepository.findById(1L);
        List<Reservation> reservations = reservationRepository.findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(accommodationId,end,start,statuses);
        Assertions.assertEquals(reservations.size(), expectedNumber);
        reservations.forEach(r -> {
            Assertions.assertFalse(statuses.contains(r.getStatus()));
            Assertions.assertTrue(start.isBefore(r.getStart()));
            Assertions.assertTrue(r.getEnd().isAfter(end));
        });
    }


    static Stream<Arguments> source(){
        return Stream.of(Arguments.arguments(1L, LocalDate.of(2024,6,1), LocalDate.of(2024, 6, 10), EnumSet.of(Status.ACCEPTED),6));
    }

}
