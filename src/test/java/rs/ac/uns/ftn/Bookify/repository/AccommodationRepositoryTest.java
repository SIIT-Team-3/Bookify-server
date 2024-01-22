package rs.ac.uns.ftn.Bookify.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@DataJpaTest
@ActiveProfiles("testrepo")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccommodationRepositoryTest {
    /**
     * Should test:
     * getAvailabilityItemsOverlapsWith*/
    @Autowired
    private IAccommodationRepository accommodationRepository;


    @ParameterizedTest
    @MethodSource(value = "dataGetAvailabilitiesToCheck")
    public void getAvailabilityItemsOverlapsWith(Long accommodationId, LocalDate startDate, LocalDate endDate, int expected) {
        List<Availability> items = (List<Availability>) accommodationRepository.getAvailabilityItemsOverlapsWith(accommodationId, startDate, endDate);

        Assertions.assertEquals(expected, items.size());
        for (Availability item : items) {
            Assertions.assertFalse(item.getStartDate().isAfter(endDate));
            Assertions.assertFalse(item.getEndDate().isBefore(startDate));
        }
    }

    static Stream<Arguments> dataGetAvailabilitiesToCheck() {
        return Stream.of(
                    Arguments.arguments(1L, LocalDate.of(2024, 2, 29), LocalDate.of(2024, 3, 2), 1),
                    Arguments.arguments(1L, LocalDate.of(2024, 2, 29), LocalDate.of(2024, 3, 21), 1),
                    Arguments.arguments(1L, LocalDate.of(2024, 3, 19), LocalDate.of(2024, 3, 22), 1),
                    Arguments.arguments(1L, LocalDate.of(2024, 3, 9), LocalDate.of(2024, 3, 12), 1),
                    Arguments.arguments(1L, LocalDate.of(2024, 2, 29), LocalDate.of(2027, 4, 2), 2),
                    Arguments.arguments(100L, LocalDate.of(2024, 2, 29), LocalDate.of(2027, 4, 2), 0),
                    Arguments.arguments(1L, LocalDate.of(2025, 2, 28), LocalDate.of(2027, 4, 2), 0)

        );
    }
}
