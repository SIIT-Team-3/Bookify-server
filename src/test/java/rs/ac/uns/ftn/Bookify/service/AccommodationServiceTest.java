package rs.ac.uns.ftn.Bookify.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.exception.BadRequestException;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Address;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAvailabilityRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IPriceListItemRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AccommodationServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AccommodationService accommodationService;

    @MockBean
    private IAccommodationRepository accommodationRepository;

    @MockBean
    private IReservationService reservationService;

    @MockBean
    private IUserService userService;

    @MockBean
    private IImageService imageService;

    @MockBean
    private IAvailabilityRepository availabilityRepository;

    @MockBean
    private IPriceListItemRepository priceListItemRepository;

    @Test(expectedExceptions = BadRequestException.class,
            expectedExceptionsMessageRegExp = "Accommodation has reservations")
    private void testAddPriceListBadRequestException() {
        Long accommodationId = 1L;
        PricelistItem pricelistItem = new PricelistItem(1L, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 20), 200.99);
        when(reservationService.hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(true);

        Long id = accommodationService.addPriceList(accommodationId, pricelistItem);

        assertNull(id);
        verify(reservationService).hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verifyNoInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceListItemRepository);
    }

    @Test
    private void testAddPriceListNoTrimNoMerge() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        accommodation.getPriceList().add(new PricelistItem(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0));
        PricelistItem pricelistItem = new PricelistItem(1L, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 20), 200.99);
        PricelistItem checkPricelistItem = new PricelistItem(null, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 20), 200.99);

        when(reservationService.hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(new ArrayList<>());
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getPriceListItems(accommodationId)).thenReturn(accommodation.getPriceList());
        when(priceListItemRepository.save(checkPricelistItem)).thenReturn(pricelistItem);

        Long id = accommodationService.addPriceList(accommodationId, pricelistItem);

        assertEquals(accommodationId, id);
        assertEquals(2, accommodation.getPriceList().size());

        verify(reservationService).hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository).save(accommodation);
        verify(accommodationRepository, times(2)).getPriceListItems(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verify(priceListItemRepository).save(checkPricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }

    @Test
    private void testAddPriceListNoTrimMerge() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        accommodation.getPriceList().add(new PricelistItem(2L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 9), 100.0));

        PricelistItem mergedPricelistItem = new PricelistItem(2L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 20), 100.0);
        PricelistItem pricelistItem = new PricelistItem(1L, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 20), 100.0);
        PricelistItem checkPricelistItem = new PricelistItem(null, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 20), 100.0);

        when(reservationService.hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(new ArrayList<>());
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getPriceListItems(accommodationId)).thenReturn(accommodation.getPriceList());
        when(priceListItemRepository.save(checkPricelistItem)).thenReturn(pricelistItem);

        Long id = accommodationService.addPriceList(accommodationId, pricelistItem);

        assertEquals(accommodationId, id);
        assertEquals(1, accommodation.getPriceList().size());
        assertEquals(LocalDate.of(2024, 3, 20), ((List<PricelistItem>) accommodation.getPriceList()).get(0).getEndDate());

        verify(reservationService).hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository, times(2)).save(accommodation);
        verify(accommodationRepository, times(2)).getPriceListItems(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verify(priceListItemRepository).save(checkPricelistItem);
        verify(priceListItemRepository).save(mergedPricelistItem);
        verify(priceListItemRepository).delete(pricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }

    @Test
    private void testAddPriceListTrimLeft() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        accommodation.getPriceList().add(new PricelistItem(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0));
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 4, 2), 200.99);
        PricelistItem checkPricelistItem = new PricelistItem(null, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 4, 2), 200.99);
        PricelistItem trimmedPricelistItem = new PricelistItem(1L, LocalDate.of(2024, 4, 3), LocalDate.of(2024, 5, 1), 100.0);

        when(reservationService.hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(accommodation.getPriceList());
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getPriceListItems(accommodationId)).thenReturn(accommodation.getPriceList());
        when(priceListItemRepository.save(checkPricelistItem)).thenReturn(pricelistItem);

        Long id = accommodationService.addPriceList(accommodationId, pricelistItem);

        assertEquals(accommodationId, id);
        assertEquals(2, accommodation.getPriceList().size());
        assertEquals(LocalDate.of(2024, 4, 3), ((List<PricelistItem>) accommodation.getPriceList()).get(1).getStartDate());

        verify(reservationService).hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository).save(accommodation);
        verify(accommodationRepository, times(2)).getPriceListItems(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verify(priceListItemRepository).save(trimmedPricelistItem);
        verify(priceListItemRepository).save(checkPricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }

    @Test
    private void testAddPriceListTrimRight() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        accommodation.getPriceList().add(new PricelistItem(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0));
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 4, 10), LocalDate.of(2024, 5, 2), 200.99);
        PricelistItem checkPricelistItem = new PricelistItem(null, LocalDate.of(2024, 4, 10), LocalDate.of(2024, 5, 2), 200.99);
        PricelistItem trimmedPricelistItem = new PricelistItem(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 9), 100.0);

        when(reservationService.hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(accommodation.getPriceList());
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getPriceListItems(accommodationId)).thenReturn(accommodation.getPriceList());
        when(priceListItemRepository.save(checkPricelistItem)).thenReturn(pricelistItem);

        Long id = accommodationService.addPriceList(accommodationId, pricelistItem);

        assertEquals(accommodationId, id);
        assertEquals(2, accommodation.getPriceList().size());
        assertEquals(LocalDate.of(2024, 4, 9), ((List<PricelistItem>) accommodation.getPriceList()).get(0).getEndDate());

        verify(reservationService).hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository).save(accommodation);
        verify(accommodationRepository, times(2)).getPriceListItems(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verify(priceListItemRepository).save(trimmedPricelistItem);
        verify(priceListItemRepository).save(checkPricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }

    @Test
    private void testAddPriceListTrimWhole() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        accommodation.getPriceList().add(new PricelistItem(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0));
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 5, 2), 200.99);
        PricelistItem checkPricelistItem = new PricelistItem(null, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 5, 2), 200.99);
        PricelistItem deletedPricelistItem = new PricelistItem(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0);

        when(reservationService.hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(new PricelistItem(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0))));
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getPriceListItems(accommodationId)).thenReturn(accommodation.getPriceList());
        when(priceListItemRepository.save(checkPricelistItem)).thenReturn(pricelistItem);

        Long id = accommodationService.addPriceList(accommodationId, pricelistItem);

        assertEquals(accommodationId, id);
        assertEquals(1, accommodation.getPriceList().size());
        assertEquals(LocalDate.of(2024, 5, 2), ((List<PricelistItem>) accommodation.getPriceList()).get(0).getEndDate());
        assertEquals(LocalDate.of(2024, 3, 10), ((List<PricelistItem>) accommodation.getPriceList()).get(0).getStartDate());

        verify(reservationService).hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository, times(2)).save(accommodation);
        verify(accommodationRepository, times(2)).getPriceListItems(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verify(priceListItemRepository).delete(deletedPricelistItem);
        verify(priceListItemRepository).save(checkPricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }

    @Test
    private void testAddPriceListTrimMiddle() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        PricelistItem base = new PricelistItem(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0);
        accommodation.getPriceList().add(base);
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 4, 10), LocalDate.of(2024, 4, 20), 200.99);
        PricelistItem checkPricelistItem = new PricelistItem(null, LocalDate.of(2024, 4, 10), LocalDate.of(2024, 4, 20), 200.99);
        PricelistItem trimmedPricelistItem = new PricelistItem(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 9), 100.0);
        PricelistItem newPricelistItem = new PricelistItem(null, LocalDate.of(2024, 4, 21), LocalDate.of(2024, 5, 1), 100.0);

        when(reservationService.hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(base)));
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getPriceListItems(accommodationId)).thenReturn(accommodation.getPriceList());
        when(priceListItemRepository.save(checkPricelistItem)).thenReturn(pricelistItem);
        when(priceListItemRepository.save(new PricelistItem(null, LocalDate.of(2024, 4, 21), LocalDate.of(2024, 5, 1), 100.0))).thenReturn(newPricelistItem);

        Long id = accommodationService.addPriceList(accommodationId, pricelistItem);

        assertEquals(accommodationId, id);
        assertEquals(3, accommodation.getPriceList().size());
        assertEquals(LocalDate.of(2024, 4, 9), ((List<PricelistItem>) accommodation.getPriceList()).get(0).getEndDate());
        assertEquals(LocalDate.of(2024, 4, 21), ((List<PricelistItem>) accommodation.getPriceList()).get(2).getStartDate());

        verify(reservationService).hasReservationInRange(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, pricelistItem.getStartDate(), pricelistItem.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository, times(2)).save(accommodation);
        verify(accommodationRepository, times(2)).getPriceListItems(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verify(priceListItemRepository).save(trimmedPricelistItem);
        verify(priceListItemRepository).save(newPricelistItem);
        verify(priceListItemRepository).save(checkPricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }

    @Test
    private void testAddAvailabilityNoMerge() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        accommodation.getAvailability().add(new Availability(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 9)));

        Availability availability = new Availability(2L, LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 20));

        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getAvailabilities(accommodationId)).thenReturn(accommodation.getAvailability());

        Long id = accommodationService.addAvailability(accommodationId, availability);

        assertEquals(accommodationId, id);
        assertEquals(2, accommodation.getAvailability().size());

        verifyNoInteractions(reservationService);
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository).save(accommodation);
        verify(accommodationRepository).getAvailabilities(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verify(availabilityRepository).save(availability);
        verifyNoMoreInteractions(availabilityRepository);
        verifyNoInteractions(priceListItemRepository);
    }

    @Test
    private void testAddAvailabilityMergeLeft() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        Availability base = new Availability(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 9));
        accommodation.getAvailability().add(base);

        Availability availability = new Availability(2L, LocalDate.of(2024, 2, 22), LocalDate.of(2024, 3, 2));

        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getAvailabilities(accommodationId)).thenReturn(accommodation.getAvailability());

        Long id = accommodationService.addAvailability(accommodationId, availability);

        assertEquals(accommodationId, id);
        assertEquals(1, accommodation.getAvailability().size());
        assertEquals(LocalDate.of(2024, 3, 9), ((List<Availability>) accommodation.getAvailability()).get(0).getEndDate());
        assertEquals(LocalDate.of(2024, 2, 22), ((List<Availability>) accommodation.getAvailability()).get(0).getStartDate());

        verifyNoInteractions(reservationService);
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository, times(2)).save(accommodation);
        verify(accommodationRepository).getAvailabilities(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verify(availabilityRepository, times(2)).save(availability);
        verify(availabilityRepository).delete(base);
        verifyNoMoreInteractions(availabilityRepository);
        verifyNoInteractions(priceListItemRepository);
    }

    @Test
    private void testAddAvailabilityMergeRight() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        Availability base = new Availability(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 9));
        accommodation.getAvailability().add(base);

        Availability availability = new Availability(2L, LocalDate.of(2024, 3, 2), LocalDate.of(2024, 3, 11));
        Availability newAvailability = new Availability(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 11));

        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getAvailabilities(accommodationId)).thenReturn(accommodation.getAvailability());

        Long id = accommodationService.addAvailability(accommodationId, availability);

        assertEquals(accommodationId, id);
        assertEquals(1, accommodation.getAvailability().size());
        assertEquals(LocalDate.of(2024, 3, 1), ((List<Availability>) accommodation.getAvailability()).get(0).getStartDate());
        assertEquals(LocalDate.of(2024, 3, 11), ((List<Availability>) accommodation.getAvailability()).get(0).getEndDate());

        verifyNoInteractions(reservationService);
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository, times(2)).save(accommodation);
        verify(accommodationRepository).getAvailabilities(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verify(availabilityRepository).save(availability);
        verify(availabilityRepository).save(newAvailability);
        verify(availabilityRepository).delete(availability);
        verifyNoMoreInteractions(availabilityRepository);
        verifyNoInteractions(priceListItemRepository);
    }

    @Test
    private void testAddAvailabilityMergeWhole() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        Availability base = new Availability(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 9));
        accommodation.getAvailability().add(base);

        Availability availability = new Availability(2L, LocalDate.of(2024, 2, 22), LocalDate.of(2024, 3, 11));

        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodationRepository.getAvailabilities(accommodationId)).thenReturn(accommodation.getAvailability());

        Long id = accommodationService.addAvailability(accommodationId, availability);

        assertEquals(accommodationId, id);
        assertEquals(1, accommodation.getAvailability().size());
        assertEquals(LocalDate.of(2024, 2, 22), ((List<Availability>) accommodation.getAvailability()).get(0).getStartDate());
        assertEquals(LocalDate.of(2024, 3, 11), ((List<Availability>) accommodation.getAvailability()).get(0).getEndDate());

        verifyNoInteractions(reservationService);
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository, times(2)).save(accommodation);
        verify(accommodationRepository).getAvailabilities(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verify(availabilityRepository, times(2)).save(availability);
        verify(availabilityRepository).delete(base);
        verifyNoMoreInteractions(availabilityRepository);
        verifyNoInteractions(priceListItemRepository);
    }

    @Test
    private void testGetAccommodationPriceListItems() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0);
        accommodation.getPriceList().add(pricelistItem);

        when(accommodationRepository.getPriceListItems(accommodationId)).thenReturn(accommodation.getPriceList());

        List<PricelistItem> items = (List<PricelistItem>) accommodationService.getAccommodationPriceListItems(accommodationId);

        assertEquals(1, items.size());
        assertEquals(pricelistItem, items.get(0));

        verifyNoInteractions(reservationService);
        verify(accommodationRepository).getPriceListItems(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceListItemRepository);
    }

    @Test(expectedExceptions = BadRequestException.class,
            expectedExceptionsMessageRegExp = "Accommodation has reservations")
    private void testDeletePriceListItemBadRequestException() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0);
        accommodation.getPriceList().add(pricelistItem);
        Availability availability = new Availability(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        accommodation.getAvailability().add(availability);

        PricelistItem forDelete = new PricelistItem(4L, LocalDate.of(2024, 5, 5), LocalDate.of(2024, 5, 5), 10);

        when(reservationService.hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(true);

        Boolean result = accommodationService.deletePriceListItem(accommodationId, forDelete);

        assertNull(result);

        verify(reservationService).hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verifyNoInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceListItemRepository);
    }

    @Test
    private void testDeletePriceListItemNoTrim() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0);
        accommodation.getPriceList().add(pricelistItem);
        Availability availability = new Availability(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        accommodation.getAvailability().add(availability);

        PricelistItem forDelete = new PricelistItem(4L, LocalDate.of(2024, 5, 5), LocalDate.of(2024, 5, 5), 10);

        when(reservationService.hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>());
        when(accommodationRepository.getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>());
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);

        Boolean result = accommodationService.deletePriceListItem(accommodationId, forDelete);

        assertTrue(result);

        verify(reservationService).hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository).getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verifyNoInteractions(availabilityRepository);
        verifyNoInteractions(priceListItemRepository);
    }

    @Test
    private void testDeletePriceListItemTrimLeft() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0);
        accommodation.getPriceList().add(pricelistItem);
        Availability availability = new Availability(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        accommodation.getAvailability().add(availability);

        PricelistItem forDelete = new PricelistItem(4L, LocalDate.of(2024, 3, 22), LocalDate.of(2024, 4, 5), 10);

        when(reservationService.hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(pricelistItem)));
        when(accommodationRepository.getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(availability)));
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);

        Boolean result = accommodationService.deletePriceListItem(accommodationId, forDelete);

        assertTrue(result);
        assertEquals(1, accommodation.getPriceList().size());
        assertEquals(1, accommodation.getAvailability().size());
        assertEquals(LocalDate.of(2024, 4, 6), ((List<PricelistItem>) accommodation.getPriceList()).get(0).getStartDate());
        assertEquals(LocalDate.of(2024, 4, 6), ((List<Availability>) accommodation.getAvailability()).get(0).getStartDate());

        verify(reservationService).hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository).getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verify(availabilityRepository).save(availability);
        verifyNoMoreInteractions(availabilityRepository);
        verify(priceListItemRepository).save(pricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }

    @Test
    private void testDeletePriceListItemTrimRight() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0);
        accommodation.getPriceList().add(pricelistItem);
        Availability availability = new Availability(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        accommodation.getAvailability().add(availability);

        PricelistItem forDelete = new PricelistItem(4L, LocalDate.of(2024, 4, 22), LocalDate.of(2024, 5, 5), 10);

        when(reservationService.hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(pricelistItem)));
        when(accommodationRepository.getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(availability)));
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);

        Boolean result = accommodationService.deletePriceListItem(accommodationId, forDelete);

        assertTrue(result);
        assertEquals(1, accommodation.getPriceList().size());
        assertEquals(1, accommodation.getAvailability().size());
        assertEquals(LocalDate.of(2024, 4, 21), ((List<PricelistItem>) accommodation.getPriceList()).get(0).getEndDate());
        assertEquals(LocalDate.of(2024, 4, 21), ((List<Availability>) accommodation.getAvailability()).get(0).getEndDate());

        verify(reservationService).hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository).getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verify(availabilityRepository).save(availability);
        verifyNoMoreInteractions(availabilityRepository);
        verify(priceListItemRepository).save(pricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }

    @Test
    private void testDeletePriceListItemTrimWhole() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0);
        accommodation.getPriceList().add(pricelistItem);
        Availability availability = new Availability(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        accommodation.getAvailability().add(availability);

        PricelistItem forDelete = new PricelistItem(4L, LocalDate.of(2024, 3, 22), LocalDate.of(2024, 5, 5), 10);

        when(reservationService.hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(pricelistItem)));
        when(accommodationRepository.getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(availability)));
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);

        Boolean result = accommodationService.deletePriceListItem(accommodationId, forDelete);

        assertTrue(result);
        assertTrue(accommodation.getPriceList().isEmpty());
        assertTrue(accommodation.getAvailability().isEmpty());

        verify(reservationService).hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository).getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository, times(2)).save(accommodation);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verify(availabilityRepository).delete(availability);
        verifyNoMoreInteractions(availabilityRepository);
        verify(priceListItemRepository).delete(pricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }

    @Test
    private void testDeletePriceListItemTrimMiddle() {
        Accommodation accommodation = new Accommodation(1L, "Name", "Description", 1, 2,
                1, false, AccommodationStatusRequest.APPROVED, true, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), AccommodationType.APARTMENT, PricePer.ROOM, new Address(), new ArrayList<>());
        Long accommodationId = accommodation.getId();
        PricelistItem pricelistItem = new PricelistItem(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1), 100.0);
        PricelistItem newPricelistItem = new PricelistItem(3L, LocalDate.of(2024, 4, 26), LocalDate.of(2024, 5, 1), 100.0);
        PricelistItem createdPricelistItem = new PricelistItem(null, LocalDate.of(2024, 4, 26), LocalDate.of(2024, 5, 1), 100.0);
        accommodation.getPriceList().add(pricelistItem);
        Availability availability = new Availability(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 5, 1));
        Availability newAvailability = new Availability(3L, LocalDate.of(2024, 4, 26), LocalDate.of(2024, 5, 1));
        Availability createdAvailability = new Availability(null, LocalDate.of(2024, 4, 26), LocalDate.of(2024, 5, 1));
        accommodation.getAvailability().add(availability);

        PricelistItem forDelete = new PricelistItem(4L, LocalDate.of(2024, 4, 20), LocalDate.of(2024, 4, 25), 10);

        when(reservationService.hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(false);
        when(accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(pricelistItem)));
        when(accommodationRepository.getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate())).thenReturn(new ArrayList<>(Collections.singletonList(availability)));
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(availabilityRepository.save(createdAvailability)).thenReturn(newAvailability);
        when(priceListItemRepository.save(createdPricelistItem)).thenReturn(newPricelistItem);

        Boolean result = accommodationService.deletePriceListItem(accommodationId, forDelete);

        assertTrue(result);
        assertEquals(2, accommodation.getPriceList().size());
        assertEquals(2, accommodation.getAvailability().size());
        assertEquals(LocalDate.of(2024, 4, 19), ((List<PricelistItem>) accommodation.getPriceList()).get(0).getEndDate());
        assertEquals(LocalDate.of(2024, 4, 19), ((List<Availability>) accommodation.getAvailability()).get(0).getEndDate());
        assertEquals(LocalDate.of(2024, 4, 26), ((List<PricelistItem>) accommodation.getPriceList()).get(1).getStartDate());
        assertEquals(LocalDate.of(2024, 4, 26), ((List<Availability>) accommodation.getAvailability()).get(1).getStartDate());

        verify(reservationService).hasReservationInRange(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verifyNoMoreInteractions(reservationService);
        verify(accommodationRepository).getPriceListItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository).getAvailabilityItemsOverlapsWith(accommodationId, forDelete.getStartDate(), forDelete.getEndDate());
        verify(accommodationRepository, times(2)).getReferenceById(accommodationId);
        verify(accommodationRepository, times(2)).save(accommodation);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(userService);
        verifyNoInteractions(imageService);
        verify(availabilityRepository).save(availability);
        verify(availabilityRepository).save(createdAvailability);
        verifyNoMoreInteractions(availabilityRepository);
        verify(priceListItemRepository).save(pricelistItem);
        verify(priceListItemRepository).save(createdPricelistItem);
        verifyNoMoreInteractions(priceListItemRepository);
    }
}
