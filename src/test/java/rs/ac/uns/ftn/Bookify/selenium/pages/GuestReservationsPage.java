package rs.ac.uns.ftn.Bookify.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GuestReservationsPage {
    private WebDriver driver;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
    @FindBy(xpath = "//h1[text()='My Reservations']")
    WebElement title;

    @FindBy(xpath = "//app-guest-reservation-card")
    List<WebElement> allReservations;

    public GuestReservationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded(){
        return (new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(title)).isDisplayed());
    }
    public boolean cancelReservation(boolean reservationCancelSuccess){
        /*
         * Method finds first reservation on page that correspond to reservation cancel success and click on button Cancel
         * if reservationCancelSuccess equals false
         *      Reservation should not be changed to CANCELLED we can see that Status does not change
         *      And we get a little pop-dialog that should have message 'Cancellation date expired'
         * else if reservationCancelSuccess equals true
         *      Reservation should change status to CANCELLED
         */
        String properStatus = reservationCancelSuccess ? "CANCELED" : "ACCEPTED";
        WebElement reservation = null;
        reservation = getReservation(reservationCancelSuccess);
        if(reservation == null){
            throw new RuntimeException("Failed due to not proper data, please fix this then run the test again");
        }

        WebElement cancelButton = reservation.findElement(By.xpath(".//button[text()='Cancel']"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", cancelButton);
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(cancelButton));
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(cancelButton));
        js.executeScript("arguments[0].click()", cancelButton);

        if(!reservationCancelSuccess) {
            WebElement popDialog = driver.findElement(By.xpath("//app-message-dialog"));
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(popDialog));

            WebElement errorMessageContainer = popDialog.findElement(By.xpath(".//div[1]"));
            if(!errorMessageContainer.getText().equals("Cancellation date expired")) return false;

            WebElement closeButton = popDialog.findElement(By.xpath(".//button"));
            closeButton.click();
        }
        WebElement statusHolder = reservation.findElement(By.xpath(".//p[contains(text(),'Status:')]"));
        if(reservationCancelSuccess){
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.textToBePresentInElement(statusHolder, "Status: CANCELED"));
        }
        String status = statusHolder.getText().split(":")[1].trim();
        return status.equals(properStatus);
    }

    private WebElement getReservation(boolean canBeCancelled) {
        for (WebElement res : allReservations) {
            WebElement reservationTemp = res.findElement(By.xpath(".//p[contains(text(), 'Cancellation due')]"));
            String cancellation = reservationTemp.getText().split(":")[1].trim();
            LocalDate date = LocalDate.parse(cancellation, dateTimeFormatter);
            if(canBeCancelled && LocalDate.now().isBefore(date)){
                return res;
            }
            if(!canBeCancelled && LocalDate.now().isAfter(date)) {
                return res;
            }
        }
        return null;
    }

}
