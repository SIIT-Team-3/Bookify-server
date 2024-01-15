package com.example.demo.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DetailsPage {
    private WebDriver driver;

    @FindBy(xpath = "//div[@id='amenities']/p[@class='subcategory white']")
    WebElement amenitiesText;

    @FindBy(css = ".amenities-name")
    List<WebElement> amenities;

    @FindBy(xpath = "//button[@class = 'btn btn-outline-secondary']")
    WebElement dateRange;

    @FindBy(xpath = "//select[@class = 'form-select'][position() = 1]")
    WebElement month;

    @FindBy(xpath = "//select[@class = 'form-select'][position() = 2]")
    WebElement year;

    @FindBy(xpath = "//div[@class='inline']/input")
    WebElement persons;

    @FindBy(xpath = "//button[@id = 'reserve-button']")
    WebElement reserve;

    @FindBy(xpath = "//app-reservation-dialog[@class = 'mat-mdc-dialog-component-host ng-star-inserted']/h1")
    WebElement popUp;

    @FindBy(css="#hotel-name")
    WebElement hotelName;

    @FindBy(css="#address")
    WebElement address;

    public DetailsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded(){
        return (new WebDriverWait(driver, 10)).until(ExpectedConditions.textToBePresentInElement(amenitiesText, "Amenities"));
    }

    public boolean checkAmenities(String[] checkedAmenities){
        for (String s : checkedAmenities){
            if (!haveAmenity(s))
                return false;
        }
        return true;
    }

    private boolean haveAmenity(String amenity){
        for (WebElement el : amenities){
            if (el.getText().equals(amenity))
                return true;
        }
        return false;
    }

    public void clickDate(){
        scrollToElement(dateRange);
        dateRange.click();
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateRange);
    }

    public void enterDate(String day, String month, String year){
        enterYear(year);
        enterMonth(month);
        enterDay(day);
    }

    private void enterYear(String yearVal){
        Select dropdown = new Select(year);
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(year));
        dropdown.selectByValue(yearVal);
    }

    private void enterMonth(String monthVal){
        Select dropdown = new Select(month);
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(month));
        dropdown.selectByValue(monthVal);
    }

    private void enterDay(String dayValue){
        String selector = "//span[contains(@class, 'custom-day')][text()=" + dayValue + "]";
        WebElement day = driver.findElement(By.xpath(selector));
        scrollToElement(day);
        day.click();
    }

    private void enterPersons(String value){
        persons.clear();
        persons.sendKeys(value);
    }

    private void clickReserve(){
        scrollToElement(reserve);
        reserve.click();
    }

    public boolean checkIfAvailable(String[] startDate, String[] endDate, String persons){
        clickDate();
        enterDate(startDate[0], startDate[1], startDate[2]);
        enterDate(endDate[0], endDate[1], endDate[2]);
        enterPersons(persons);
        clickReserve();

        (new WebDriverWait(driver, 10)).until(ExpectedConditions.textToBePresentInElement(popUp, "Reservation"));
        return Objects.equals(popUp.getText(), "Reservation");
    }

    public boolean checkDestination(String destination){
        String title = hotelName.getText();
        String addressVal = address.getText();
        return title.toLowerCase().contains(destination.toLowerCase()) || addressVal.toLowerCase().contains(destination.toLowerCase());
    }

    public void scrollToElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Point location = element.getLocation();
        js.executeScript("window.scrollBy(0, arguments[0]);", location.getY()-200);

        wait.until(webDriver -> {
            Number elementTop = (Number) js.executeScript("return arguments[0].getBoundingClientRect().top;", element);
            Number windowHeight = (Number) js.executeScript("return window.innerHeight;");

            double elementTopValue = elementTop.doubleValue();
            double windowHeightValue = windowHeight.doubleValue();

            System.out.println("Checking scroll position - Element Top: " + elementTopValue + ", Window Height: " + windowHeightValue);

            double margin = 10.0;
            return (elementTopValue - margin) <= windowHeightValue;
        });
    }
}
