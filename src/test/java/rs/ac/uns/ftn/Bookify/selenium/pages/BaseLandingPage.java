package rs.ac.uns.ftn.Bookify.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseLandingPage {

    private final WebDriver driver;
    private static String PAGE_URL = "http://localhost:4200/";
    @FindBy(xpath = "//span[text()='Login']")
    WebElement loginButton;

    public BaseLandingPage(WebDriver driver){
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded(){
        return (new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(loginButton)).isDisplayed());
    }

    public void openLoginPage(){
        loginButton.click();
    }

}
