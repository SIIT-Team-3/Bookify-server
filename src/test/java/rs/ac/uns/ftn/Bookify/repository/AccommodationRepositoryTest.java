package rs.ac.uns.ftn.Bookify.repository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class AccommodationRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private IAccommodationRepository accommodationRepository;


    @Test(dataProvider = "priceListData")
    public void getPriceListItems(Long accommodationId, List<PricelistItem> expectedPriceListItems, int expected) {
        List<PricelistItem> items = (List<PricelistItem>) accommodationRepository.getPriceListItems(accommodationId);

        assertEquals(expected, items.size());
        for (int i = 0; i < items.size(); i++) {
            assertEquals(items.get(i).getStartDate(), expectedPriceListItems.get(i).getStartDate());
            assertEquals(items.get(i).getEndDate(), expectedPriceListItems.get(i).getEndDate());
            assertEquals(items.get(i).getPrice(), expectedPriceListItems.get(i).getPrice());
        }
    }

    @DataProvider(name = "priceListData")
    public Object[][] priceListData() {
        return new Object[][]{
                {1L, new ArrayList<PricelistItem>(List.of(
                        new PricelistItem(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 20), 10.99),
                        new PricelistItem(2L, LocalDate.of(2024, 3, 25), LocalDate.of(2024, 3, 30), 28.99),
                        new PricelistItem(73L, LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 10), 20)
                )), 3},
                {2L, new ArrayList<PricelistItem>(List.of(
                        new PricelistItem(3L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 10), 39.99),
                        new PricelistItem(4L, LocalDate.of(2024, 3, 12), LocalDate.of(2024, 3, 20), 31.99)
                )), 2}
        };
    }

    @Test
    public void getPriceListItemsWrongId() {
        List<PricelistItem> items = (List<PricelistItem>) accommodationRepository.getPriceListItems(100L);
        assertEquals(0, items.size());
    }

    @Test(dataProvider = "priceListOverlapsWithData")
    public void getPriceListItemsOverlapsWith(Long accommodationId, LocalDate startDate, LocalDate endDate, int expected) {
        List<PricelistItem> items = (List<PricelistItem>) accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, startDate, endDate);

        assertEquals(expected, items.size());
        for (PricelistItem item : items) {
            assertFalse(item.getStartDate().isAfter(endDate));
            assertFalse(item.getEndDate().isBefore(startDate));
        }
    }

    @DataProvider(name = "priceListOverlapsWithData")
    public Object[][] dataPL() {
        return new Object[][]{
                {1L, LocalDate.of(2024, 2, 29), LocalDate.of(2024, 3, 2), 1},
                {1L, LocalDate.of(2024, 2, 29), LocalDate.of(2024, 3, 21), 1},
                {1L, LocalDate.of(2024, 3, 19), LocalDate.of(2024, 3, 22), 1},
                {1L, LocalDate.of(2024, 3, 9), LocalDate.of(2024, 3, 12), 1},
                {1L, LocalDate.of(2024, 2, 29), LocalDate.of(2027, 4, 2), 2},
                {100L, LocalDate.of(2024, 2, 29), LocalDate.of(2027, 4, 2), 0},
                {1L, LocalDate.of(2025, 2, 28), LocalDate.of(2027, 4, 2), 0}

        };
    }

    @Test(dataProvider = "availabilityData")
    public void getAvailabilities(Long accommodationId, List<Availability> expectedPriceListItems, int expected) {
        List<Availability> items = (List<Availability>) accommodationRepository.getAvailabilities(accommodationId);

        assertEquals(expected, items.size());
        for (int i = 0; i < items.size(); i++) {
            assertEquals(items.get(i).getStartDate(), expectedPriceListItems.get(i).getStartDate());
            assertEquals(items.get(i).getEndDate(), expectedPriceListItems.get(i).getEndDate());
        }
    }

    @DataProvider(name = "availabilityData")
    public Object[][] availabilityData() {
        return new Object[][]{
                {1L, new ArrayList<Availability>(List.of(
                        new Availability(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 20)),
                        new Availability(2L, LocalDate.of(2024, 3, 25), LocalDate.of(2024, 3, 30)),
                        new Availability(73L, LocalDate.of(2023, 12, 4), LocalDate.of(2023, 12, 10))
                )), 3},
                {2L, new ArrayList<Availability>(List.of(
                        new Availability(3L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 10)),
                        new Availability(4L, LocalDate.of(2024, 3, 12), LocalDate.of(2024, 3, 20))
                )), 2}
        };
    }

    @Test
    public void getAvailabilitiesWrong() {
        List<Availability> items = (List<Availability>) accommodationRepository.getAvailabilities(100L);

        assertEquals(0, items.size());
    }

    @Test(dataProvider = "availabilityOverlapsWithData")
    public void getAvailabilityItemsOverlapsWith(Long accommodationId, LocalDate startDate, LocalDate endDate, int expected) {
        List<Availability> items = (List<Availability>) accommodationRepository.getAvailabilityItemsOverlapsWith(accommodationId, startDate, endDate);

        assertEquals(expected, items.size());
        assertEquals(expected, items.size());
        for (Availability item : items) {
            assertFalse(item.getStartDate().isAfter(endDate));
            assertFalse(item.getEndDate().isBefore(startDate));
        }
    }

    @DataProvider(name = "availabilityOverlapsWithData")
    public Object[][] dataA() {
        return new Object[][]{
                {1L, LocalDate.of(2024, 2, 29), LocalDate.of(2024, 3, 2), 1},
                {1L, LocalDate.of(2024, 2, 29), LocalDate.of(2024, 3, 21), 1},
                {1L, LocalDate.of(2024, 3, 19), LocalDate.of(2024, 3, 22), 1},
                {1L, LocalDate.of(2024, 3, 9), LocalDate.of(2024, 3, 12), 1},
                {1L, LocalDate.of(2024, 2, 29), LocalDate.of(2027, 4, 2), 2},
                {100L, LocalDate.of(2024, 2, 29), LocalDate.of(2027, 4, 2), 0},
                {1L, LocalDate.of(2025, 2, 28), LocalDate.of(2027, 4, 2), 0}

        };
    }
}
