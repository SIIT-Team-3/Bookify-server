package rs.ac.uns.ftn.Bookify.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ResultsPage {
    private WebDriver driver;

    @FindBy(xpath = ".//a[@data-testid='header-sign-in-button']")
    WebElement title;

    @FindBy(xpath = "//div[@data-testid='property-card-container']")
    List<WebElement> cards;

    @FindBy(xpath = "//div[@data-testid='price-for-x-nights']")
    List<WebElement> infos;

    @FindBy(xpath = "//*[@data-testid='address']")
    List<WebElement> locations;

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded(){
        return (new WebDriverWait(driver, Duration.ofSeconds(15))).until(ExpectedConditions.textToBePresentInElement(title, "Sign in"));
    }

    public List<Integer[]> getResults(){
        List<Integer[]> results = new ArrayList<>();
        for(WebElement card: infos){
            String all = card.getText();
            int nights = Integer.parseInt(all.split(" ")[0]);
            int adults = Integer.parseInt(all.split(" ")[2]);
            int children = Integer.parseInt(all.split(" ")[4]);
            results.add(new Integer[]{nights, adults, children});
        }
        return results;
    }

    public List<String> getLocations(){
        List<String> stringLocations = new ArrayList<>();
        for(WebElement location : locations){
            stringLocations.add(location.getText());
        }
        return stringLocations;
    }
}
