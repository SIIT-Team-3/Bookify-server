package com.example.demo.e2e.tests;

import com.example.demo.e2e.pages.LandingPage;
import com.example.demo.e2e.pages.LoginPage;
import com.example.demo.e2e.pages.ResultsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest extends TestBase {

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

    @Test
    public void withoutResultsTest(){
        LandingPage landingPage = new LandingPage(driver);
        assertTrue(landingPage.isLoaded());

        landingPage.enterDestination("Budapest");
        landingPage.clickDate();
        landingPage.enterDate("6", "3", "2024");
        landingPage.enterDate("8", "3", "2024");
        landingPage.enterPersons("100");
        landingPage.clickSearch();

        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        assertTrue(resultsPage.noResults());
    }

    @Test
    public void resultsFromLandingTest(){
        LandingPage landingPage = new LandingPage(driver);
        assertTrue(landingPage.isLoaded());

        String destination = "De";
        String[] startDate = new String[] {"6", "3", "2024"};
        String[] endDate = new String[] {"8", "3", "2024"};
        String persons = "2";

        landingPage.enterDestination(destination);
        landingPage.clickDate();
        landingPage.enterDate(startDate[0], startDate[1], startDate[2]);
        landingPage.enterDate(endDate[0], endDate[1], endDate[2]);
        landingPage.enterPersons(persons);
        landingPage.clickSearch();

        ResultsPage resultsPage = new ResultsPage(driver);
        assertTrue(resultsPage.isLoaded());

        assertTrue(resultsPage.checkResults(destination, startDate, endDate, persons, new String[]{}, new String[]{}, new String[]{}));
    }
}
