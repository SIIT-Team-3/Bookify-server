package rs.ac.uns.ftn.Bookify.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OwnerLandingPage {

    private WebDriver driver;

    @FindBy(id = "moto")
    WebElement moto;

    @FindBy(xpath = "//span[text()=\"Accommodations\"]")
    WebElement accommodationsLink;

    public OwnerLandingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return (new WebDriverWait(driver, Duration.ofSeconds(15))).until(ExpectedConditions.visibilityOf(moto)).isDisplayed();
    }

    public void openMyAccommodations() {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.visibilityOf(accommodationsLink));
        accommodationsLink.click();
    }
}
