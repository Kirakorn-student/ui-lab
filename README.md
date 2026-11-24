# UI Lab

แล็บนี้ใช้ **Selenium WebDriver + Java (Part 2)** และ **Robot Framework (Part 3)** ทดสอบหน้าแบบฟอร์มของ Selenium กับเคส QP-01 บน QuickPizza เคสนี้เริ่มจากขอพิซซ่าตามเงื่อนไข ลองให้คะแนนก่อนล็อกอิน แล้วเข้าไปดูว่าหน้าแอดมินมีพิซซ่ารายการเดียวกันหรือไม่

## เตรียม ChromeDriver

ดาวน์โหลด [ChromeDriver](https://googlechromelabs.github.io/chrome-for-testing/) รุ่นที่ตรงกับ Chrome ในเครื่อง แล้วแตกไฟล์ไว้นอกโฟลเดอร์งาน ตั้งค่า `CHROMEDRIVER` ใน PowerShell ที่จะใช้รัน โดยแก้ตำแหน่งไฟล์ให้ตรงกับเครื่อง

```powershell
$env:CHROMEDRIVER = 'C:\drivers\chromedriver-win64\chromedriver.exe'
```

## Part 2 Selenium WebDriver + Java

ใน `selenium-java/` มีเทสต์ JUnit สำหรับเช็กชื่อแท็บ ส่งแบบฟอร์ม รอกล่องสีแดง และรัน QP-01 สองแบบ แบบแรกเขียนตำแหน่งของปุ่มและช่องกรอกไว้ในเทสต์ ส่วนอีกแบบแยกไว้ใน `HomePage` กับ `AdminPage`

ใช้ JDK 21 ขึ้นไปกับ Maven แล้วเปิด PowerShell ในโฟลเดอร์ `selenium-java/` เพื่อรันทั้งห้าเทสต์

```powershell
mvn test '-Dheadless=true'
```

ดู [ผลจาก Maven สองรอบ](selenium-java/reports/maven-runs.txt), [รายงาน JUnit รอบสอง](selenium-java/reports/uilab.QP01PageObjectTest.txt) และ [ภาพของ QP-01](selenium-java/screenshots/)

## Part 3 Robot Framework

ใน `robot/` มีเทสต์เช็กชื่อแท็บ ส่งแบบฟอร์ม และรัน QP-01 คำสั่งที่ใช้กับ QuickPizza รวมอยู่ใน `resources/quickpizza.resource` จึงอ่านไฟล์เทสต์ตามขั้นของเคสได้เลย

ใช้ Python 3.10 ขึ้นไป เปิด PowerShell ในโฟลเดอร์ `robot/` แล้วติดตั้งไลบรารีครั้งแรก

```powershell
python -m venv .venv
.\.venv\Scripts\python.exe -m pip install --no-cache-dir -r requirements.txt
```

รันทั้งสามเทสต์ด้วยคำสั่งนี้

```powershell
.\.venv\Scripts\python.exe -m robot -d results/local -v BROWSER:headlesschrome first.robot web_form.robot pizza_scenario.robot
```

หลักฐาน QP-01 อยู่ใน [รอบแรก](robot/results/run1/) และ [รอบที่สอง](robot/results/run2/) เปิด `log.html` จากโฟลเดอร์ในเครื่องเพื่อดูแต่ละขั้นพร้อมภาพประกอบ

ตารางผลรันด้วยมือ ผลของแต่ละรอบ และคำตอบท้ายแล็บอยู่ใน [RESULTS.md](RESULTS.md)
