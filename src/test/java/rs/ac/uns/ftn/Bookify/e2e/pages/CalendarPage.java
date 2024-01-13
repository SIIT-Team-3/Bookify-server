package rs.ac.uns.ftn.Bookify.e2e.pages;

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
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarPage {
    private WebDriver driver;

    @FindBy(className = "calendar")
    WebElement calendar;

    @FindBy(xpath = "//button[text()=\"Add\"]")
    WebElement btnAdd;

    @FindBy(xpath = "//button[text()=\"Delete\"]")
    WebElement btnDelete;

    @FindBy(xpath = "//div[@id='year-picker']/span[@class='year-change']/pre[text()=\">\"]")
    WebElement nextYear;

    @FindBy(xpath = "//div[@id='year-picker']/span[@class='year-change']/pre[text()=\"<\"]")
    WebElement previousYear;

    @FindBy(xpath = "//div[@class='calendar-header']/span[3]")
    WebElement nextMonth;

    @FindBy(xpath = "//div[@class='calendar-header']/span[1]")
    WebElement previousMonth;

    @FindBy(id = "month-picker")
    WebElement month;

    @FindBy(id = "year")
    WebElement year;

    @FindBy(className = "calendar-days")
    WebElement calendarDays;

    @FindBy(xpath = "//mat-form-field//input")
    WebElement priceInput;

    Map<String, Integer> months;

    public CalendarPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        months = Map.ofEntries(
                Map.entry("January", 1),
                Map.entry("February", 2),
                Map.entry("March", 3),
                Map.entry("April", 4),
                Map.entry("May", 5),
                Map.entry("June", 6),
                Map.entry("July", 7),
                Map.entry("August", 8),
                Map.entry("September", 9),
                Map.entry("October", 10),
                Map.entry("November", 11),
                Map.entry("December", 12)
        );
    }

    public boolean isLoaded() {
        return (new WebDriverWait(driver, Duration.ofSeconds(15))).until(ExpectedConditions.visibilityOf(calendar)).isDisplayed();
    }

    public void changeMonth(String month) {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.visibilityOf(this.month));
        while (true) {
            if (months.get(month) > months.get(this.month.getText())) {
                increaseMonth();
            } else if (months.get(month) < months.get(this.month.getText())) {
                decreaseMonth();
            } else {
                break;
            }
        }
    }

    public void changeYear(int year) {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.visibilityOf(this.year));
        while (true) {
            int currentYear = Integer.parseInt(this.year.getText());
            if (year > currentYear) {
                increaseYear();
            } else if (year < currentYear) {
                decreaseYear();
            } else {
                break;
            }
        }
    }

    private void increaseMonth() {
        nextMonth.click();
    }

    private void decreaseMonth() {
        previousMonth.click();
    }

    private void increaseYear() {
        nextYear.click();
    }

    private void decreaseYear() {
        previousYear.click();
    }

    public String selectDate(int day) {
        String locator = String.format(".//span[text() = '%s']", day);
        calendarDays.findElement(By.xpath(locator)).click();
        return year.getText() + "-" + month.getText() + "-" + day;
    }

    public void insertPrice(double price) {
        priceInput.sendKeys(String.valueOf(price));
    }

    public void addPrice() {
        btnAdd.click();
        priceInput.clear();
    }

    public void deletePrice() {
        btnDelete.click();
    }

    public List<String> getPrices(String start, String end) {
        int startYear = Integer.parseInt(start.split("-")[0]);
        String startMonth = start.split("-")[1];
        int startDay = Integer.parseInt(start.split("-")[2]);

        int endYear = Integer.parseInt(end.split("-")[0]);
        String endMonth = end.split("-")[1];
        int endDay = Integer.parseInt(end.split("-")[2]);

        LocalDate startDate = LocalDate.of(startYear, Month.valueOf(startMonth.toUpperCase()), startDay);
        LocalDate endDate = LocalDate.of(endYear, Month.valueOf(endMonth.toUpperCase()), endDay);
        LocalDate currentDate = startDate;
        LocalDate lastDate = endDate;

        if (startDate.isAfter(endDate)) {
            currentDate = endDate;
            lastDate = startDate;
        }

        List<String> prices = new ArrayList<>();

        while (!currentDate.isAfter(lastDate)) {

            changeMonth(startMonth);
            changeYear(startYear);
            prices.add(getPrice(startDay));
            currentDate = currentDate.plusDays(1);
        }

        return prices;
    }

    private String getPrice(int day) {
        String locator = String.format(".//span[text() = '%s']/../span[2]", day);
        return calendarDays.findElement(By.xpath(locator)).getText().replace("€", "");
    }

    public void scrollToBottom() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        wait.until(webDriver -> {
            Number innerHeight = (Number) js.executeScript("return window.innerHeight;");
            Number scrollY = (Number) js.executeScript("return window.scrollY;");
            Number bodyScrollHeight = (Number) js.executeScript("return document.body.scrollHeight;");

            double innerHeightValue = innerHeight.doubleValue();
            double scrollYValue = scrollY.doubleValue();
            double bodyScrollHeightValue = bodyScrollHeight.doubleValue();

            double margin = 10.0;
            return (innerHeightValue + scrollYValue + margin) >= bodyScrollHeightValue;
        });
    }

    public void scrollToTop() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, 0)");

        wait.until(webDriver -> {
            Number scrollY = (Number) js.executeScript("return window.scrollY;");

            double scrollYValue = scrollY.doubleValue();

            double margin = 10.0;
            return scrollYValue <= margin;
        });
    }
}
