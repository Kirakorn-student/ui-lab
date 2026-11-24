package uilab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPage {
    private static final String URL = "https://quickpizza.grafana.com/admin";
    private static final By APP_READY = By.id("svelte-announcer");
    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By SIGN_IN = By.cssSelector("button[type=submit]");
    private static final By HEADING = By.tagName("h2");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public AdminPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get(URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(APP_READY));
    }

    public void logIn(String username, String password) {
        replace(USERNAME, username);
        replace(PASSWORD, password);
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_IN)).click();
    }

    public String heading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(HEADING)).getText();
    }

    public String entryFor(String name) {
        By entry = By.xpath("//li[contains(., '" + name + " (tool=')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(entry)).getText();
    }

    private void replace(By locator, String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        input.clear();
        input.sendKeys(value);
    }
}
