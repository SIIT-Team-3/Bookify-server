package com.example.demo.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultsPage {
    private WebDriver driver;

    @FindBy(xpath = "//p[@class='subcategory'][text()='Filters']")
    WebElement filterTitle;

    @FindBy(xpath = "//div[@class = 'inline'][position()=1]/input")
    WebElement searchDestination;

    @FindBy(xpath = "//button[@class = 'btn btn-outline-secondary'][position() = 1]")
    WebElement dateRange;

    @FindBy(xpath = "//select[@class = 'form-select'][position() = 1]")
    WebElement month;

    @FindBy(xpath = "//select[@class = 'form-select'][position() = 2]")
    WebElement year;

    @FindBy(xpath = "//div[@class='inline'][position()=2]/input")
    WebElement persons;

    @FindBy(xpath="//button[@class='button-right']")
    WebElement search;

    @FindBy(xpath = "//button[@class = 'button-right'][text()='Filter']")
    WebElement filter;

    @FindBy(xpath = "//input[@type='range'][position()=1]")
    WebElement sliderMin;

    @FindBy(xpath = "//input[@type='range'][position()=2]")
    WebElement sliderMax;

    @FindBy(xpath = "//button[contains(@class, 'mat-mdc-tooltip-trigger mat-mdc-paginator-navigation-next mdc-icon-button mat-mdc-icon-button mat-unthemed mat-mdc-button-base')]")
    WebElement nextPage;

    public ResultsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded(){
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElement(filterTitle, "Filter"));
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

    public void clickFilter() {
        scrollToElement(filter);
        filter.click();
    }

    public void clickCheckbox(String[] types){
        for (String type : types){
            String selector = "//label[text()='" + type + "']";
            WebElement checkbox = driver.findElement(By.xpath(selector));
            scrollToElement(checkbox);
            checkbox.click();
        }
    }

    public void setPriceRange(String min, String max){
        setRangeInputValue(driver, sliderMin, min);
        setRangeInputValue(driver, sliderMax, max);
    }

    public String getMinPrice(){
        return sliderMin.getAttribute("value");
    }

    public String getMaxPrice(){
        return sliderMax.getAttribute("value");
    }

    private static void setRangeInputValue(WebDriver driver, WebElement element, String value) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].value = arguments[1];", element, value);
        jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('input'));", element);
    }

    public boolean noResults(){
        List<WebElement> accommodationCards = driver.findElements(By.xpath("//div[@class='accommodation-card']"));
        return accommodationCards.isEmpty();
    }

    public boolean checkResults(String destination, String[] startDate, String[] endDate, String persons, String[] uncheckedTypes, String[] amenities, String[] price){
        String[] types = getCheckedTypes(uncheckedTypes);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> accommodationCards = driver.findElements(By.xpath("//div[@class='accommodation-card']"));
        int size = accommodationCards.size();
        System.out.println(size);
        for (int i = 0; i < size; i++){
            if (types.length != 3 && !checkType(i, types))
                return false;
            if (price.length != 0 && !checkPriceRange(i, price))
                return false;
            if (!checkInDetails(i, startDate, endDate, persons, amenities, destination))
                return false;

            if (i == size-1 && clickNextPage())
                checkResults(destination, startDate, endDate, persons, uncheckedTypes, amenities, price);
        }
        return true;
    }

    private boolean checkInDetails(int i, String[] startDate, String[] endDate, String persons, String[] amenities, String destination){
        String locator = String.format("//app-accommodation-basic[position()=%d]//div[@class='accommodation-card']//button[@class='details-button']", i+1);
        WebElement details = driver.findElement(By.xpath(locator));
        scrollToElement(details);
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(details)).click();

        DetailsPage detailsPage = new DetailsPage(driver);
        assertTrue(detailsPage.isLoaded());

        if (!detailsPage.checkAmenities(amenities))
            return false;
        if (!detailsPage.checkDestination(destination))
            return false;
        if (!detailsPage.checkIfAvailable(startDate, endDate, persons))
            return false;

        driver.navigate().back();
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.textToBePresentInElement(filterTitle, "Filter"));
        return true;
    }


    private String[] getCheckedTypes(String[] uncheckedTypes){
        String[] allTypes = new String[]{"Hotel", "Room", "Apartment"};
        List<String> allTypesList = new ArrayList<>(Arrays.asList(allTypes));
        List<String> uncheckedTypesList = Arrays.asList(uncheckedTypes);

        allTypesList.removeAll(uncheckedTypesList);

        return allTypesList.toArray(new String[0]);
    }

    private Boolean checkType(int i, String[] types){
        String locator = String.format("//app-accommodation-basic[position()=%d]//div[@class='accommodation-card']//p[@class='subtitle']", i+1);
        WebElement typeEl = driver.findElement(By.xpath(locator));
        String type = typeEl.getText();
        for (String s : types)
            if (s.toUpperCase().equals(type))
                return true;
        return false;
    }

    private boolean checkPriceRange(int i, String[] price){
        String locator = String.format("//app-accommodation-basic[position()=%d]//div[@class='accommodation-card']//p[@class='price']", i+1);
        WebElement currPriceEl = driver.findElement(By.xpath(locator));
        String currPrice = currPriceEl.getText().split(" ")[0];
        return Float.parseFloat(price[0]) <= Float.parseFloat(currPrice) && Float.parseFloat(price[1]) >= Float.parseFloat(currPrice);
    }

    private boolean clickNextPage(){
        if (nextPage.isEnabled()){
            scrollToElement(nextPage);
            nextPage.click();
            return true;
        }
        return false;
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

            double margin = 30.0;
            return (elementTopValue - margin) <= windowHeightValue;
        });
    }
}
