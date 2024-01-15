package rs.ac.uns.ftn.Bookify.e2e.tests;

import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.Bookify.e2e.pages.*;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
@ActiveProfiles("test")
public class CalendarTest  extends TestBase{

    @Test
    public void testBooking() {
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoaded());
        loginPage.inputCredentials("owner@example.com", "123");
        loginPage.logIn();


        OwnerLandingPage ownerLandingPage = new OwnerLandingPage(driver);
        assertTrue(ownerLandingPage.isLoaded());
        ownerLandingPage.openMyAccommodations();


        OwnersAccommodationsPage ownersAccommodationsPage = new OwnersAccommodationsPage(driver);
        assertTrue(ownersAccommodationsPage.isLoaded());
        ownersAccommodationsPage.openAccommodationsCalendar(0);


        CalendarPage calendarPage = new CalendarPage(driver);
        assertTrue(calendarPage.isLoaded());

        String start, end;
        double price = 3.0;

        start = selectDate(calendarPage, 20, "March", 2037);
        end = selectDate(calendarPage, 2);
        addPrices(calendarPage, price, start, end);

        start = selectDate(calendarPage, 1);
        end = selectDate(calendarPage, 10);
        deletePrices(calendarPage, start, end);

        price = 7;
        start = selectDate(calendarPage, 15);
        end = selectDate(calendarPage, 17);
        addPrices(calendarPage, price, start, end);

        start = selectDate(calendarPage, 2);
        end = selectDate(calendarPage, 20);
        deletePrices(calendarPage, start, end);
    }

    private void addPrices(CalendarPage calendarPage, double price, String start, String end) {
        addPriceListItem(calendarPage, price);
        testCalendarPricesAdd(calendarPage, start, end, price);
    }

    private void deletePrices(CalendarPage calendarPage, String start, String end) {
        calendarPage.scrollToBottom();
        calendarPage.deletePrice();
        testCalendarPricesDelete(calendarPage, start, end);
    }

    private String selectDate(CalendarPage calendarPage, int day) {
        calendarPage.scrollToTop();
        return calendarPage.selectDate(day);
    }

    private String selectDate(CalendarPage calendarPage, int day, String month, int year) {
        calendarPage.scrollToTop();
        calendarPage.changeMonth(month);
        calendarPage.changeYear(year);
        return calendarPage.selectDate(day);
    }

    private void addPriceListItem(CalendarPage calendarPage, double price) {
        calendarPage.scrollToBottom();
        calendarPage.insertPrice(price);
        calendarPage.addPrice();
        calendarPage.scrollToTop();
    }

    private void testCalendarPricesAdd(CalendarPage calendarPage, String start, String end, double price) {
        calendarPage.scrollToTop();
        List<String> prices = calendarPage.getPrices(start, end);
        prices.forEach(priceString -> assertEquals(Double.valueOf(priceString), price));
    }

    private void testCalendarPricesDelete(CalendarPage calendarPage, String start, String end) {
        calendarPage.scrollToTop();
        List<String> prices = calendarPage.getPrices(start, end);
        prices.forEach(priceString -> assertEquals(priceString, ""));
    }
}
