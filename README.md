# UI Lab ทดสอบหน้าเว็บแบบอัตโนมัติ

แบบฝึกหัดนี้ใช้ **Selenium WebDriver + Java (Part 2)** กับ **Robot Framework (Part 3)** ทดสอบหน้า Selenium Web Form และเคส QP-01 บน QuickPizza เคสนี้เริ่มจากขอพิซซ่าตามเงื่อนไข ลองให้คะแนนตอนยังไม่ได้ล็อกอิน แล้วเข้าแอดมินไปดูว่าพิซซ่าที่เพิ่งขอขึ้นในรายการจริง

## เตรียมก่อนรัน

ติดตั้ง Google Chrome และดาวน์โหลด [ChromeDriver](https://googlechromelabs.github.io/chrome-for-testing/) ที่ตรงกับ Chrome ในเครื่อง เก็บ ChromeDriver ไว้นอก repository แล้วตั้ง `CHROMEDRIVER` ให้ชี้ไปที่ไฟล์นั้น ตัวอย่างบน PowerShell

```powershell
$env:CHROMEDRIVER = 'C:\drivers\chromedriver-win64\chromedriver.exe'
```

แก้พาธให้ตรงกับเครื่อง ตั้งค่าในหน้าต่าง PowerShell ที่จะใช้รันเทสต์

## Part 2 Selenium WebDriver + Java

โฟลเดอร์ `selenium-java/` มีเทสต์ JUnit สำหรับหน้าแบบฟอร์ม การรอกล่องสีแดง และ QP-01 ทั้งแบบเขียน locator ในเทสต์กับแบบใช้ Page Object เตรียม JDK 21 ขึ้นไปและ Maven แล้วเปิด PowerShell ในโฟลเดอร์นี้เพื่อรันทั้งชุด

```powershell
mvn test '-Dheadless=true'
```

ดูภาพ QP-01 ใน [screenshots](selenium-java/screenshots/) และรายงาน JUnit ของรอบที่สองใน [reports](selenium-java/reports/)

## Part 3 Robot Framework

โฟลเดอร์ `robot/` มีเทสต์หน้าแบบฟอร์มและ QP-01 ส่วนคำสั่งย่อยกับ locator ของ QuickPizza อยู่ใน `resources/quickpizza.resource` เตรียม Python 3.10 ขึ้นไป แล้วเปิด PowerShell ในโฟลเดอร์ `robot/` และติดตั้งไลบรารีครั้งแรก

```powershell
python -m venv .venv
.\.venv\Scripts\python.exe -m pip install --no-cache-dir -r requirements.txt
```

รันทั้งสามเทสต์ด้วยคำสั่งนี้

```powershell
.\.venv\Scripts\python.exe -m robot -d results/local -v BROWSER:headlesschrome first.robot web_form.robot pizza_scenario.robot
```

หลักฐาน QP-01 สองรอบอยู่ใน [run1](robot/results/run1/) และ [run2](robot/results/run2/) แต่ละโฟลเดอร์มีรายงาน สี่ภาพ และไฟล์ผลการรัน เปิด `log.html` ในเบราว์เซอร์แล้วกดดูรายละเอียดทีละขั้นได้

ผลการลองด้วยมือ ผลการรันทั้งสองเครื่องมือ และคำตอบ Part B อยู่ใน [RESULTS.md](RESULTS.md)
