package rs.ac.uns.ftn.Bookify.selenium.testbases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.testng.Assert;
import rs.ac.uns.ftn.Bookify.selenium.pages.GuestLandingPage;
import rs.ac.uns.ftn.Bookify.selenium.pages.GuestReservationsPage;
import rs.ac.uns.ftn.Bookify.selenium.pages.LoginPage;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class ApplyReservationListingAndCancelingTest extends TestBase {

    private final String username = "guest@example.com";
    private final String password = "123";

    @Test
    public void reservationCancelledUnsuccessful(){
        // login
        LoginPage loginPage = new LoginPage(driver);
        Assertions.assertTrue(loginPage.isLoaded());
        loginPage.inputCredentials(username, password);
        loginPage.logIn();

        // landing page
        GuestLandingPage guestLandingPage = new GuestLandingPage(driver);
        Assertions.assertTrue(guestLandingPage.isLoaded());
        guestLandingPage.openMyReservations();

        // opening guest reservations page
        GuestReservationsPage guestReservationsPage = new GuestReservationsPage(driver);
        Assertions.assertTrue(guestReservationsPage.isLoaded());

        Assertions.assertTrue(guestReservationsPage.cancelReservation(false));
    }


    @Test
    public void reservationCancelledSuccessful(){
        // login
        LoginPage loginPage = new LoginPage(driver);
        Assertions.assertTrue(loginPage.isLoaded());
        loginPage.inputCredentials(username, password);
        loginPage.logIn();

        // landing page
        GuestLandingPage guestLandingPage = new GuestLandingPage(driver);
        Assertions.assertTrue(guestLandingPage.isLoaded());
        guestLandingPage.openMyReservations();

        // opening guest reservations page
        GuestReservationsPage guestReservationsPage = new GuestReservationsPage(driver);
        Assertions.assertTrue(guestReservationsPage.isLoaded());

        // cancel reservation should not be allowed
        Assertions.assertTrue(guestReservationsPage.cancelReservation(true));
    }
}
