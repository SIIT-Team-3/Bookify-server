package rs.ac.uns.ftn.Bookify.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceTest {
    @InjectMocks
    private AccommodationService accommodationService;

    @Mock
    private IAccommodationRepository accommodationRepository;

    static Stream<Object[]> availableData() {
        return Stream.of(
                new Object[]{1L, LocalDate.of(2024, 3, 20), LocalDate.of(2024, 3, 23), 1L, true},
                new Object[]{1L, LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 7), 0L, false}
        );
    }

    @ParameterizedTest
    @MethodSource("availableData")
    public void isAvailableTest(Long accommodationId, LocalDate begin, LocalDate end, long returnVal, boolean expected) {
        when(accommodationRepository.checkIfAccommodationAvailable(accommodationId, begin, end)).thenReturn(returnVal);

        boolean result = accommodationService.isAvailable(accommodationId, begin, end);

        verify(accommodationRepository, times(1)).checkIfAccommodationAvailable(accommodationId, begin, end);
        assertEquals(expected, result);
        verifyNoMoreInteractions(accommodationRepository);
    }

    static Stream<Object[]> personsData() {
        return Stream.of(
                new Object[]{1L, 3, 1L, true},
                new Object[]{1L, 5, 0L, false}
        );
    }

    @ParameterizedTest
    @MethodSource("personsData")
    public void checkPersonsTest(Long accommodationId, int persons, long returnVal, boolean expected) {
        when(accommodationRepository.checkPersons(accommodationId, persons)).thenReturn(returnVal);

        boolean result = accommodationService.checkPersons(accommodationId, persons);

        verify(accommodationRepository, times(1)).checkPersons(accommodationId, persons);
        assertEquals(expected, result);
        verifyNoMoreInteractions(accommodationRepository);
    }

    @Test
    public void getAccommodationTest(){

    }
}
