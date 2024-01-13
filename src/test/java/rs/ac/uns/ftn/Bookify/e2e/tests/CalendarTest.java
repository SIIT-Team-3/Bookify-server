package rs.ac.uns.ftn.Bookify.e2e.tests;

import org.testng.annotations.Test;
import rs.ac.uns.ftn.Bookify.e2e.pages.*;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
        calendarPage.changeMonth("March");
        calendarPage.changeYear(2037);

        String start = calendarPage.selectDate(20);
        String end = calendarPage.selectDate(2);

        calendarPage.scrollToBottom();
        double price = 3;
        calendarPage.insertPrice(price);
        calendarPage.addPrice();

        List<String> prices = calendarPage.getPrices(start, end);
        double finalPrice = price;
        prices.forEach(priceString -> assertEquals(Double.valueOf(priceString), finalPrice));

        calendarPage.scrollToTop();
        start = calendarPage.selectDate(1);
        end = calendarPage.selectDate(10);

        calendarPage.scrollToBottom();
        calendarPage.deletePrice();

        prices = calendarPage.getPrices(start, end);
        prices.forEach(priceString -> assertEquals(priceString, ""));

        calendarPage.scrollToTop();
        start = calendarPage.selectDate(15);
        end = calendarPage.selectDate(17);

        calendarPage.scrollToBottom();
        price = 7;
        calendarPage.insertPrice(price);
        calendarPage.addPrice();

        prices = calendarPage.getPrices(start, end);
        double finalPrice1 = price;
        prices.forEach(priceString -> assertEquals(Double.valueOf(priceString), finalPrice1));

        calendarPage.scrollToTop();
        start = calendarPage.selectDate(2);
        end = calendarPage.selectDate(20);

        calendarPage.scrollToBottom();
        calendarPage.deletePrice();

        calendarPage.scrollToTop();
        prices = calendarPage.getPrices(start, end);
        prices.forEach(priceString -> assertEquals(priceString, ""));
    }

    
}
