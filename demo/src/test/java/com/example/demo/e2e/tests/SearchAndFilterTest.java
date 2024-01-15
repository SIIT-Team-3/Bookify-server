package com.example.demo.e2e.tests;

import com.example.demo.e2e.pages.LandingPage;
import com.example.demo.e2e.pages.LoginPage;
import com.example.demo.e2e.pages.ResultsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchAndFilterTest extends TestBase {

    String destination = "De";
    String[] startDate = new String[] {"6", "3", "2024"};
    String[] endDate = new String[] {"8", "3", "2024"};
    String persons = "2";
    String[] setPrice = {"60", "130"};
    String[] getPrice;
    String[] types = new String[]{"Room"};
    String[] amenities = new String[]{"Garden", "Bar"};

    private static boolean setupDone = false;

    @BeforeMethod
    public void login(){
        if (!setupDone) {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterEmail("guest@example.com");
            loginPage.enterPassword("123");
            loginPage.clickLogIn();
            setupDone = true;
        }
    }

    @BeforeMethod
    void setupBeforeMethod() {
        Setup();
    }

    private void Setup(){
        LandingPage landingPage = new LandingPage(driver);

        landingPage.enterDestination(destination);
        landingPage.clickDate();
        landingPage.enterDate(startDate[0], startDate[1], startDate[2]);
        landingPage.enterDate(endDate[0], endDate[1], endDate[2]);
        landingPage.enterPersons(persons);
        landingPage.clickSearch();

        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());
    }

    @Test
    public void resultsFromResultsTest() {
        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        String destination = "Copenhagen";
        String[] startDate = new String[] {"10", "3", "2024"};
        String[] endDate = new String[] {"13", "3", "2024"};
        String persons = "3";

        resultsPage.enterDestination(destination);
        resultsPage.clickDate();
        resultsPage.enterDate(startDate[0], startDate[1], startDate[2]);
        resultsPage.enterDate(endDate[0], endDate[1], endDate[2]);
        resultsPage.enterPersons(persons);
        resultsPage.clickSearch();

        assertTrue(resultsPage.isLoaded());
        assertTrue(resultsPage.checkResults(destination, startDate, endDate, persons, new String[]{}, new String[]{}, new String[]{}));
    }

    @Test
    public void FilterByPriceTest() {
        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        resultsPage.setPriceRange(setPrice[0], setPrice[1]);
        resultsPage.clickFilter();

        String priceMin = resultsPage.getMinPrice();
        String priceMax = resultsPage.getMaxPrice();
        getPrice = new String[]{priceMin, priceMax};

        assertTrue(resultsPage.isLoaded());
        assertTrue(resultsPage.checkResults(destination, startDate, endDate, persons, new String[]{}, new String[]{}, getPrice));
    }

    @Test
    public void FilterByTypeTest() {
        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        resultsPage.clickCheckbox(types);
        resultsPage.clickFilter();

        assertTrue(resultsPage.isLoaded());
        assertTrue(resultsPage.checkResults(destination, startDate, endDate, persons, types, new String[]{}, new String[]{}));
    }

    @Test
    public void FilterByAmenitiesTest() {
        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        resultsPage.clickCheckbox(amenities);
        resultsPage.clickFilter();

        assertTrue(resultsPage.isLoaded());
        assertTrue(resultsPage.checkResults(destination, startDate, endDate, persons, new String[]{}, amenities, new String[]{}));
    }

    @Test
    public void FilterByPriceAndTypeTest() {
        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        resultsPage.setPriceRange(setPrice[0], setPrice[1]);
        resultsPage.clickCheckbox(types);
        resultsPage.clickFilter();

        String priceMin = resultsPage.getMinPrice();
        String priceMax = resultsPage.getMaxPrice();
        getPrice = new String[]{priceMin, priceMax};

        assertTrue(resultsPage.isLoaded());
        assertTrue(resultsPage.checkResults(destination, startDate, endDate, persons, types, new String[]{}, getPrice));
    }

    @Test
    public void FilterByPriceAndAmenitiesTest() {
        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        resultsPage.setPriceRange(setPrice[0], setPrice[1]);
        resultsPage.clickCheckbox(amenities);
        resultsPage.clickFilter();

        String priceMin = resultsPage.getMinPrice();
        String priceMax = resultsPage.getMaxPrice();
        getPrice = new String[]{priceMin, priceMax};

        assertTrue(resultsPage.isLoaded());
        assertTrue(resultsPage.checkResults(destination, startDate, endDate, persons, new String[]{}, amenities, getPrice));
    }

    @Test
    public void FilterByAmenitiesAndTypeTest() {
        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        resultsPage.clickCheckbox(amenities);
        resultsPage.clickCheckbox(types);
        resultsPage.clickFilter();

        assertTrue(resultsPage.isLoaded());
        assertTrue(resultsPage.checkResults(destination, startDate, endDate, persons, types, amenities, new String[]{}));
    }

    @Test
    public void FilterByPriceAndTypeAndAmenitiesTest() {
        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        resultsPage.setPriceRange(setPrice[0], setPrice[1]);
        resultsPage.clickCheckbox(amenities);
        resultsPage.clickCheckbox(types);
        resultsPage.clickFilter();

        String priceMin = resultsPage.getMinPrice();
        String priceMax = resultsPage.getMaxPrice();
        getPrice = new String[]{priceMin, priceMax};

        assertTrue(resultsPage.isLoaded());
        assertTrue(resultsPage.checkResults(destination, startDate, endDate, persons, types, amenities, getPrice));
    }
}
