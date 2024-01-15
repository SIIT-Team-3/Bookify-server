package rs.ac.uns.ftn.Bookify.repository;

import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootTest
@ActiveProfiles("test")
public class AccommodationRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private IAccommodationRepository accommodationRepository;


    @Test(dataProvider = "priceListDates", invocationCount = 10)
    public void getPriceListItems(Long accommodationId, List<PricelistItem> expectedPriceListItems) {
        List<PricelistItem> items = (List<PricelistItem>) accommodationRepository.getPriceListItems(accommodationId);

        assertEquals(2, items.size());
        for (int i = 0; i < items.size(); i++) {
            assertEquals(items.get(i).getStartDate(), expectedPriceListItems.get(i).getStartDate());
            assertEquals(items.get(i).getEndDate(), expectedPriceListItems.get(i).getEndDate());
        }
//        for (PricelistItem item : items1) {
//            assertFalse(item.getStartDate().isAfter(end));
//            assertFalse(item.getEndDate().isBefore(start));
//        }
    }

    @DataProvider(name = "priceListDates")
    public Object[][] priceListDates() {
        return new Object[][]{
                {1L, new ArrayList<PricelistItem>(List.of(
                        new PricelistItem(1L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 20), 10.99),
                        new PricelistItem(2L, LocalDate.of(2024, 3, 25), LocalDate.of(2024, 3, 30), 28.99)
                ))},
                {2L, new ArrayList<PricelistItem>(List.of(
                        new PricelistItem(3L, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 10), 39.99),
                        new PricelistItem(4L, LocalDate.of(2024, 3, 12), LocalDate.of(2024, 3, 20), 31.99)
                ))}
        };
    }

    @Test
    public void getPriceListItemsWrongId() {
        List<PricelistItem> items = (List<PricelistItem>) accommodationRepository.getPriceListItems(100L);

//        for (PricelistItem item : items1) {
//            assertFalse(item.getStartDate().isAfter(end));
//            assertFalse(item.getEndDate().isBefore(start));
//        }
        assertEquals(0, items.size());
    }

    @Test(dataProvider = "priceListOverlapsWithData")
    public void getPriceListItemsOverlapsWith(Long accommodationId, LocalDate startDate, LocalDate endDate, int expected) {
        List<PricelistItem> items = (List<PricelistItem>) accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, startDate, endDate);
        assertEquals(expected, items.size());
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

    @Test
    public void getAvailabilities() {
        List<Availability> items1 = (List<Availability>) accommodationRepository.getAvailabilities(1L);
        List<Availability> items2 = (List<Availability>) accommodationRepository.getAvailabilities(100L);

        assertEquals(2, items1.size());
        assertEquals(0, items2.size());
    }

    @Test(dataProvider = "availabilityOverlapsWithData")
    public void getAvailabilityItemsOverlapsWith(Long accommodationId, LocalDate startDate, LocalDate endDate, int expected) {
        List<Availability> items = (List<Availability>) accommodationRepository.getAvailabilityItemsOverlapsWith(accommodationId, startDate, endDate);
        assertEquals(expected, items.size());
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
