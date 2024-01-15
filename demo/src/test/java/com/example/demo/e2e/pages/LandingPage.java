package com.example.demo.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPage {
    private WebDriver driver;
    private static String URL = "http://localhost:4200/";

    @FindBy(xpath = "//div[@class = 'inline'][position()=1]/input")
    WebElement searchDestination;

    @FindBy(xpath = "//button[@class = 'btn btn-outline-secondary']")
    WebElement dateRange;

    @FindBy(xpath = "//select[@class = 'form-select'][position() = 1]")
    WebElement month;

    @FindBy(xpath = "//select[@class = 'form-select'][position() = 2]")
    WebElement year;

    @FindBy(xpath = "//div[@class='inline'][position()=2]/input")
    WebElement persons;

    @FindBy(xpath="//button[@class='button-right']")
    WebElement search;

    @FindBy(xpath = "//p[@class = 'subtitle top-dest']")
    WebElement topDestinations;

    public LandingPage(WebDriver driver){
        this.driver = driver;
        driver.get(URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded(){
        return (new WebDriverWait(driver, 10)).until(ExpectedConditions.textToBePresentInElement(topDestinations, "Top Destinations"));
    }

    public void enterDestination(String destination){
        searchDestination.clear();
        searchDestination.sendKeys(destination);
    }

    public void clickDate(){
        dateRange.click();
    }

    public void enterDate(String day, String month, String year){
        enterYear(year);
        enterMonth(month);
        enterDay(day);
    }

    private void enterYear(String yearVal){
        Select dropdown = new Select(year);
        dropdown.selectByValue(yearVal);
    }

    private void enterMonth(String monthVal){
        Select dropdown = new Select(month);
        dropdown.selectByValue(monthVal);
    }

    private void enterDay(String dayValue){
        String selector = "//span[contains(@class, 'custom-day')][text()=" + dayValue + "]";
        WebElement day = driver.findElement(By.xpath(selector));
        day.click();
    }

    public void enterPersons(String value){
        persons.clear();
        persons.sendKeys(value);
    }

    public void clickSearch(){
        search.click();
    }
}
