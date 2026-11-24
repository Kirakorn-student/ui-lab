package uilab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import uilab.pages.AdminPage;
import uilab.pages.HomePage;

class QP01PageObjectTest extends BaseTest {
    // QP-01 ใช้ Page Object เพื่อให้ขั้นทดสอบอ่านได้โดยไม่ต้องไล่ locator
    @Test
    void customPizzaReachesTheAdminList() throws IOException {
        String stamp = timestamp();
        String name = "Lab Pizza " + stamp;
        HomePage home = new HomePage(driver, wait);
        AdminPage admin = new AdminPage(driver, wait);

        // ขั้น 1 เปิดหน้าแรก
        home.open();
        assertEquals("Looking to break out of your pizza routine?", home.heading());

        // ขั้น 2 เปิด Advanced และตรวจว่าช่องครบ
        home.turnOnAdvanced();
        assertTrue(home.advancedFieldsVisible());

        // ขั้น 3 ใส่เงื่อนไขแล้วตรวจค่าที่แสดง
        home.fillRequest(600, "Knife", name);
        assertEquals(600, home.maxCalories());
        assertEquals("Knife", home.excludedTool());
        assertTrue(home.vegetarianSelected());
        assertEquals(name, home.pizzaName());
        shot(stamp, "step-3");

        // ขั้น 4 ขอพิซซ่าและตรวจชื่อ เครื่องมือ กับแคลอรี
        home.askForPizza();
        assertEquals(name, home.recommendedName());
        String tool = home.recommendedTool();
        int calories = home.caloriesPerSlice();
        assertNotEquals("Knife", tool);
        assertTrue(calories <= 600, "calories per slice: " + calories);
        shot(stamp, "step-4");

        // ขั้น 5 กดให้คะแนนโดยยังไม่ล็อกอิน
        home.rate();
        assertEquals("Please log in first.", home.rateMessage());
        shot(stamp, "step-5");

        // ขั้น 6 เข้าแอดมินในเบราว์เซอร์เดิม
        admin.open();
        admin.logIn("admin", "admin");
        assertEquals("Latest pizza recommendations", admin.heading());

        // ขั้น 7 ค้นชื่อจากทั้งรายการและเทียบเครื่องมือ
        String entry = admin.entryFor(name);
        assertTrue(entry.contains("(tool=" + tool + ","), entry);
        shot(stamp, "step-7");
        System.out.printf("%s | %s | %d calories per slice%n", name, tool, calories);
    }
}
