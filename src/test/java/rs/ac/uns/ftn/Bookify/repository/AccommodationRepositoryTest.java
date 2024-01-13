package rs.ac.uns.ftn.Bookify.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class AccommodationRepositoryTest {

    @Autowired
    private IAccommodationRepository accommodationRepository;

     static Stream<Object[]> availableData() {
        return Stream.of(
                new Object[]{1L, LocalDate.of(2024, 3, 20), LocalDate.of(2024, 3, 23), 1L},
                new Object[]{1L, LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 7), 0L}
        );
    }

    @ParameterizedTest
    @MethodSource("availableData")
    public void isAvailableTest(Long accommodationId, LocalDate begin, LocalDate end, Long expected){
        long result = accommodationRepository.checkIfAccommodationAvailable(accommodationId, begin, end);
        assertEquals(expected, result);
    }

    static Stream<Object[]> personsData() {
        return Stream.of(
                new Object[]{1L, 3, 1L},
                new Object[]{1L, 5, 0L}
        );
    }

    @ParameterizedTest
    @MethodSource("personsData")
    public void checkPersonsTest(Long accommodationId, int persons, long expected){
        long result = accommodationRepository.checkPersons(accommodationId, persons);
        assertEquals(expected, result);
    }

    @Test
    public void findPriceForDayTest(){
         LocalDate date = LocalDate.of(2024, 3, 11);
         Long accommodationId = 1L;

         Optional<Double> price = accommodationRepository.findPriceForDay(date, accommodationId);
         assertEquals(15, price.get());
    }
}
