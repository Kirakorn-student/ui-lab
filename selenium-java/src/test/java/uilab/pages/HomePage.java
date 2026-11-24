package uilab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private static final String URL = "https://quickpizza.grafana.com/";
    private static final By HEADING = By.tagName("h1");
    private static final By ADVANCED = By.xpath("//label[.//span[normalize-space()='Advanced']]");
    private static final By MAX_CALORIES = By.xpath(
            "//label[normalize-space()='Max Calories per Slice']/preceding-sibling::input");
    private static final By EXCLUDED_TOOLS = By.xpath(
            "//label[normalize-space()='Excluded tools']/following-sibling::select");
    private static final By VEGETARIAN = By.id("default-checkbox");
    private static final By PIZZA_NAME = By.cssSelector("input#pizza-name");
    private static final By PIZZA_PLEASE = By.name("pizza-please");
    private static final By LOVE_IT = By.name("rate-5");
    private static final By RATE_RESULT = By.id("rate-result");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get(URL);
    }

    public String heading() {
        return visible(HEADING).getText();
    }

    public void turnOnAdvanced() {
        wait.until(ExpectedConditions.elementToBeClickable(ADVANCED)).click();
    }

    public boolean advancedFieldsVisible() {
        return visible(MAX_CALORIES).isDisplayed() && visible(EXCLUDED_TOOLS).isDisplayed()
                && visible(VEGETARIAN).isDisplayed() && visible(PIZZA_NAME).isDisplayed();
    }

    public void fillRequest(int calories, String tool, String name) {
        replace(MAX_CALORIES, Integer.toString(calories));
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                EXCLUDED_TOOLS, By.cssSelector("option[value='" + tool + "']")));
        new Select(visible(EXCLUDED_TOOLS)).selectByVisibleText(tool);
        WebElement vegetarian = visible(VEGETARIAN);
        if (!vegetarian.isSelected()) {
            vegetarian.click();
        }
        replace(PIZZA_NAME, name);
    }

    public int maxCalories() {
        return Integer.parseInt(visible(MAX_CALORIES).getDomProperty("value"));
    }

    public String excludedTool() {
        return new Select(visible(EXCLUDED_TOOLS)).getFirstSelectedOption().getText();
    }

    public boolean vegetarianSelected() {
        return visible(VEGETARIAN).isSelected();
    }

    public String pizzaName() {
        return visible(PIZZA_NAME).getDomProperty("value");
    }

    public void askForPizza() {
        wait.until(ExpectedConditions.elementToBeClickable(PIZZA_PLEASE)).click();
    }

    public String recommendedName() {
        return result("Name:");
    }

    public String recommendedTool() {
        return result("Tool:");
    }

    public int caloriesPerSlice() {
        return Integer.parseInt(result("Calories per slice:"));
    }

    public void rate() {
        wait.until(ExpectedConditions.elementToBeClickable(LOVE_IT)).click();
    }

    public String rateMessage() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(RATE_RESULT, "Please log in first."));
        return visible(RATE_RESULT).getText();
    }

    private String result(String label) {
        By line = By.xpath("//*[@id='recommendations']//p[starts-with(normalize-space(), '"
                + label + "')]");
        return visible(line).getText().substring(label.length()).trim();
    }

    private WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void replace(By locator, String value) {
        WebElement input = visible(locator);
        input.clear();
        input.sendKeys(value);
    }
}
