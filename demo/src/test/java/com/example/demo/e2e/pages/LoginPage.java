package com.example.demo.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private static String URL = "http://localhost:4200/login";

    @FindBy(xpath = "//input[@id = 'mat-input-0']")
    WebElement email;

    @FindBy(xpath = "//input[@id = 'mat-input-1']")
    WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        driver.get(URL);
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String e){
        email.clear();
        email.sendKeys(e);
    }

    public void enterPassword(String p){
        password.clear();
        password.sendKeys(p);
    }

    public void clickLogIn(){
        loginButton.click();
    }
}
