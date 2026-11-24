package uilab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

class WaitTest extends BaseTest {
    // ตรวจว่ากล่องสีแดงปรากฏหลังคลิก Add a box!
    @Test
    void addABox() {
        driver.get("https://www.selenium.dev/selenium/web/dynamic.html");
        driver.findElement(By.id("adder")).click();
        WebElement box = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("box0")));
        assertEquals("redbox", box.getDomAttribute("class"));
    }
}
