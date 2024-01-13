package rs.ac.uns.ftn.Bookify.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OwnersAccommodationsPage {
    private WebDriver driver;

    @FindBy(id = "title")
    WebElement title;

    @FindBy(xpath = "//button[text()=\"Price\"]")
    List<WebElement> btnPrice;

    public OwnersAccommodationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return (new WebDriverWait(driver, Duration.ofSeconds(15))).until(ExpectedConditions.visibilityOf(title)).isDisplayed();
    }

    public void openAccommodationsCalendar(int index) {
        WebElement accommodationPrice = btnPrice.get(index);
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.visibilityOf(accommodationPrice));
        accommodationPrice.click();
    }
}
