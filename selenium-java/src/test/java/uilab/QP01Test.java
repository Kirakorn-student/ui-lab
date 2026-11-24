package uilab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

class QP01Test extends BaseTest {
    private static final String HOME = "https://quickpizza.grafana.com/";

    // QP-01 ตรวจเงื่อนไขพิซซ่า การขอให้ล็อกอิน และรายการของแอดมิน
    @Test
    void customPizzaReachesTheAdminList() {
        String pizzaName = "Lab Pizza " + timestamp();

        // ขั้น 1 เปิดหน้าแรก
        driver.get(HOME);
        WebElement heading = visible(By.tagName("h1"));
        assertEquals("Looking to break out of your pizza routine?", heading.getText());

        // ขั้น 2 เปิด Advanced และตรวจว่าช่องครบ
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[.//span[normalize-space()='Advanced']]"))).click();
        WebElement calories = visible(By.xpath(
                "//label[normalize-space()='Max Calories per Slice']/preceding-sibling::input"));
        By toolList = By.xpath(
                "//label[normalize-space()='Excluded tools']/following-sibling::select");
        Select tools = new Select(visible(toolList));
        WebElement vegetarian = visible(By.id("default-checkbox"));
        WebElement name = visible(By.cssSelector("input#pizza-name"));

        // ขั้น 3 ใส่เงื่อนไขแล้วตรวจค่าที่แสดง
        calories.clear();
        calories.sendKeys("600");
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                toolList, By.cssSelector("option[value='Knife']")));
        tools.selectByVisibleText("Knife");
        vegetarian.click();
        name.sendKeys(pizzaName);
        assertEquals("600", calories.getDomProperty("value"));
        assertEquals("Knife", tools.getFirstSelectedOption().getText());
        assertTrue(vegetarian.isSelected());
        assertEquals(pizzaName, name.getDomProperty("value"));

        // ขั้น 4 ขอพิซซ่าและตรวจชื่อ เครื่องมือ กับแคลอรี
        driver.findElement(By.name("pizza-please")).click();
        assertEquals(pizzaName, result("Name:"));
        String tool = result("Tool:");
        int perSlice = Integer.parseInt(result("Calories per slice:"));
        assertNotEquals("Knife", tool);
        assertTrue(perSlice <= 600, "calories per slice: " + perSlice);

        // ขั้น 5 กดให้คะแนนโดยยังไม่ล็อกอิน
        driver.findElement(By.name("rate-5")).click();
        wait.until(ExpectedConditions.textToBe(
                By.id("rate-result"), "Please log in first."));

        // ขั้น 6 เข้าแอดมินในเบราว์เซอร์เดิม
        driver.get(HOME + "admin");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("svelte-announcer")));
        WebElement user = visible(By.id("username"));
        user.clear();
        user.sendKeys("admin");
        WebElement password = visible(By.id("password"));
        password.clear();
        password.sendKeys("admin");
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        assertEquals("Latest pizza recommendations", visible(By.tagName("h2")).getText());

        // ขั้น 7 ค้นชื่อจากทั้งรายการและเทียบเครื่องมือ
        WebElement entry = visible(By.xpath("//li[contains(., '" + pizzaName + " (tool=')]"));
        assertTrue(entry.getText().contains("(tool=" + tool + ","), entry.getText());
        System.out.printf("%s | %s | %d calories per slice%n", pizzaName, tool, perSlice);
    }

    private WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private String result(String label) {
        By line = By.xpath("//*[@id='recommendations']//p[starts-with(normalize-space(), '"
                + label + "')]");
        return visible(line).getText().substring(label.length()).trim();
    }
}
