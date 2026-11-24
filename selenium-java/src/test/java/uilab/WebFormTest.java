package uilab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

class WebFormTest extends BaseTest {
    // กรอกแบบฟอร์มหลายชนิดแล้วตรวจว่าเว็บรับข้อมูล
    @Test
    void fillAndSubmitTheForm() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement text = driver.findElement(By.id("my-text-id"));
        text.sendKeys("Hello");
        assertEquals("Hello", text.getDomProperty("value"));
        driver.findElement(By.name("my-password")).sendKeys("Secret-26");
        driver.findElement(By.name("my-textarea")).sendKeys("Two lines");
        assertFalse(driver.findElement(By.name("my-disabled")).isEnabled());

        Select dropdown = new Select(driver.findElement(By.name("my-select")));
        dropdown.selectByVisibleText("Two");
        assertEquals("2", dropdown.getFirstSelectedOption().getDomProperty("value"));
        driver.findElement(By.name("my-datalist")).sendKeys("Seattle");
        driver.findElement(By.name("my-file")).sendKeys(Path.of("pom.xml").toAbsolutePath().toString());

        WebElement checkbox = driver.findElement(By.id("my-check-2"));
        checkbox.click();
        assertTrue(checkbox.isSelected());
        driver.findElement(By.id("my-radio-2")).click();
        assertFalse(driver.findElement(By.id("my-radio-1")).isSelected());
        WebElement date = driver.findElement(By.name("my-date"));
        date.sendKeys("11/05/2026", Keys.ESCAPE);

        driver.findElement(By.cssSelector("button[type=submit]")).click();
        wait.until(ExpectedConditions.titleIs("Web form - target page"));
        assertEquals("Received!", driver.findElement(By.id("message")).getText());
    }
}
