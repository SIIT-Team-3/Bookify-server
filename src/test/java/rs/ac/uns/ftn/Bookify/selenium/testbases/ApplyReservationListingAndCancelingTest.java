package rs.ac.uns.ftn.Bookify.selenium.testbases;

import org.testng.Assert;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.Bookify.selenium.pages.GuestLandingPage;
import rs.ac.uns.ftn.Bookify.selenium.pages.GuestReservationsPage;
import rs.ac.uns.ftn.Bookify.selenium.pages.LoginPage;

public class ApplyReservationListingAndCancelingTest extends TestBase {

    private final String username = "guest@example.com";
    private final String password = "123";

    @Test
    public void reservationCancelledUnsuccessful(){
        // login
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoaded());
        loginPage.inputCredentials(username, password);
        loginPage.logIn();

        // landing page
        GuestLandingPage guestLandingPage = new GuestLandingPage(driver);
        Assert.assertTrue(guestLandingPage.isLoaded());
        guestLandingPage.openMyReservations();

        // opening guest reservations page
        GuestReservationsPage guestReservationsPage = new GuestReservationsPage(driver);
        Assert.assertTrue(guestReservationsPage.isLoaded());

        Assert.assertTrue(guestReservationsPage.cancelReservation(false));
    }


    @Test
    public void reservationCancelledSuccessful(){
        // login
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoaded());
        loginPage.inputCredentials(username, password);
        loginPage.logIn();

        // landing page
        GuestLandingPage guestLandingPage = new GuestLandingPage(driver);
        Assert.assertTrue(guestLandingPage.isLoaded());
        guestLandingPage.openMyReservations();

        // opening guest reservations page
        GuestReservationsPage guestReservationsPage = new GuestReservationsPage(driver);
        Assert.assertTrue(guestReservationsPage.isLoaded());

        // cancel reservation should not be allowed
        Assert.assertTrue(guestReservationsPage.cancelReservation(true));
    }
}
