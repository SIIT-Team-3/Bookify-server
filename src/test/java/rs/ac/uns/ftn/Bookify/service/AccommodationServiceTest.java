package rs.ac.uns.ftn.Bookify.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
                new Object[]{1L, LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 7), 0L, false},
                new Object[]{1L, LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 7), 0L, false}
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
    public void getAccommodationSuccessTest() {
        Accommodation accommodation = new Accommodation(1L, "Test", "Desc", 2, 4,
                2, false, AccommodationStatusRequest.APPROVED,
                true, new ArrayList<PricelistItem>(), new ArrayList<Availability>(), new ArrayList<Review>(),
                new ArrayList<Filter>(), AccommodationType.HOTEL, PricePer.ROOM, new Address(), new ArrayList<Image>());
        when(accommodationRepository.getReferenceById(1L)).thenReturn(accommodation);

        Accommodation result = accommodationService.getAccommodation(1L);

        verify(accommodationRepository, times(1)).getReferenceById(1L);
        assertEquals(accommodation, result);
        verifyNoMoreInteractions(accommodationRepository);
    }

    @Test
    public void getAccommodationNotFoundTest() {
        Long accommodationId = 2L;
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(null);

        Accommodation result = accommodationService.getAccommodation(accommodationId);

        verify(accommodationRepository, times(1)).getReferenceById(accommodationId);
        assertNull(result);
        verifyNoMoreInteractions(accommodationRepository);
    }

    static Stream<Object[]> totalPriceData() {
        return Stream.of(
                new Object[]{1L, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 5), PricePer.PERSON, 2, 12.0, 96.0},
                new Object[]{1L, LocalDate.of(2024, 5, 3), LocalDate.of(2024, 5, 5), PricePer.ROOM, 2, 10.0, 20.0},
                new Object[]{1L, LocalDate.of(2024, 5, 3), LocalDate.of(2024, 5, 5), PricePer.ROOM, 4, 20.0, 40.0}
        );
    }

    @ParameterizedTest
    @MethodSource("totalPriceData")
    public void getTotalPriceTest(Long accommodationId, LocalDate begin, LocalDate end, PricePer pricePer, int persons, double pricePerDay, double expected) {
        when(accommodationRepository.findPriceForDay(any(LocalDate.class), eq(accommodationId))).thenReturn(Optional.of(pricePerDay));

        double result = accommodationService.getTotalPrice(accommodationId, begin, end, pricePer, persons);

        verify(accommodationRepository, times((int) begin.until(end, java.time.temporal.ChronoUnit.DAYS))).findPriceForDay(any(LocalDate.class), eq(accommodationId));
        assertEquals(expected, result);
        verifyNoMoreInteractions(accommodationRepository);
    }
}
