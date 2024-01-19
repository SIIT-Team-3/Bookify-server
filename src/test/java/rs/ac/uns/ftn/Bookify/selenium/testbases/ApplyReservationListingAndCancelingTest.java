package rs.ac.uns.ftn.Bookify.selenium.testbases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.uns.ftn.Bookify.selenium.pages.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class ApplyReservationListingAndCancelingTest extends TestBase {

    private final String username = "guest@example.com";
    private final String password = "123";

    @AfterEach
    public void logout(){
        WebElement account = driver.findElement(By.xpath("//span[contains(text(),'Account')]"));
        account.click();
        AccountPage accountPage = new AccountPage(driver);
        accountPage.isLoaded();
        accountPage.logout();
    }

    @Test
    public void reservationCancelledUnsuccessful(){
        // landing page
        BaseLandingPage landingPage = new BaseLandingPage(driver);
        Assertions.assertTrue(landingPage.isLoaded());
        landingPage.openLoginPage();

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
        // landing page
        BaseLandingPage landingPage = new BaseLandingPage(driver);
        Assertions.assertTrue(landingPage.isLoaded());
        landingPage.openLoginPage();
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
