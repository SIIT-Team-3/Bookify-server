package rs.ac.uns.ftn.Bookify.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private static String PAGE_URL = "http://localhost:4200/login";

    @FindBy(className = "logo")
    WebElement logo;

    @FindBy(xpath = "//input[@formControlName='email']")
    WebElement inputUsername;

    @FindBy(xpath = "//input[@formControlName='password']")
    WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnLogin;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return (new WebDriverWait(driver, Duration.ofSeconds(15))).until(ExpectedConditions.visibilityOf(logo)).isDisplayed();
    }

    public void inputCredentials(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
    }

    public void logIn() {
        btnLogin.click();
    }
}
