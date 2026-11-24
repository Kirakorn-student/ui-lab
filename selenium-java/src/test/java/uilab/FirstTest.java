package uilab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FirstTest extends BaseTest {
    // ตรวจว่าหน้าแบบฟอร์มเปิดได้และชื่อแท็บตรงตามที่คาด
    @Test
    void webFormOpens() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        assertEquals("Web form", driver.getTitle());
    }
}
