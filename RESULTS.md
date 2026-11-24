# ผลการทำ UI Lab

เลือก **Part 2 Selenium WebDriver + Java** และ **Part 3 Robot Framework**

## Part 0 เตรียมเครื่องและลอง QP-01 ด้วยมือ

- **0.1** Google Chrome `157.0.8103.79` บน Windows 64 บิต
- **0.3** ChromeDriver `157.0.8103.79` ตั้งตำแหน่งไฟล์ผ่าน `CHROMEDRIVER`
- **0.4** บน Selenium Web Form พบ `#my-text-id` 1 ช่อง, `input[type=checkbox]` 2 ช่อง และ XPath `//button[normalize-space()='Submit']` 1 ปุ่ม

เปิด Advanced ใน QuickPizza แล้วลองค้นด้วย CSS และ XPath ใน Chrome ได้ผลดังนี้

| องค์ประกอบ | Locator | จำนวนที่เจอ |
| --- | --- | ---: |
| Pizza, Please! | `button[name="pizza-please"]` | 1 |
| Excluded tools | `//label[normalize-space()='Excluded tools']/following-sibling::select` | 1 |
| Must be vegetarian | `input#default-checkbox` | 1 |
| Custom Pizza Name | `input#pizza-name` | 1 |
| Max Calories per Slice | `//label[normalize-space()='Max Calories per Slice']/preceding-sibling::input` | 1 |

`#floating_filled` เจอ 3 ช่องพร้อมกัน พอผลแนะนำขึ้น `#pizza-name` ก็เจอทั้งช่องกรอกและหัวข้อ รวมเป็น 2 รายการ ส่วน `input#pizza-name` ยังเจอแค่ช่องกรอก

### 0.5 QP-01 พิซซ่าที่ตั้งชื่อเองขึ้นในรายการแอดมิน

เริ่มจากหน้า QuickPizza ที่ยังไม่ได้ล็อกอิน ใช้ชื่อ **Lab Pizza 20261124-203908** ผลที่ได้คือ **Scissors** และ **275 แคลอรีต่อชิ้น**

| ขั้น | สิ่งที่ทำ | ผลที่คาด | ผลจริง | สถานะ |
| --- | --- | --- | --- | --- |
| 1 | เปิดหน้าแรก QuickPizza | เห็นหัวข้อ Looking to break out of your pizza routine? | เห็นหัวข้อตรงตามที่คาด | ผ่าน |
| 2 | เปิด Advanced | เห็นช่องแคลอรี เครื่องมือที่ตัดออก ช่องติ๊กมังสวิรัติ และช่องชื่อ | ขึ้นครบทั้งสี่ช่อง ค่าแคลอรีเริ่มที่ 1000 | ผ่าน |
| 3 | เปลี่ยนเป็น 600 เลือกตัด Knife ติ๊ก vegetarian และกรอกชื่อ | เห็นค่าที่ใส่ครบ | เห็น 600, Knife ถูกเลือก, vegetarian ถูกติ๊ก และชื่อ Lab Pizza 20261124-203908 | ผ่าน |
| 4 | กด Pizza, Please! | ผลแนะนำชื่อเดิม เครื่องมือไม่ใช่ Knife และไม่เกิน 600 แคลอรีต่อชิ้น | ขึ้นชื่อ Lab Pizza 20261124-203908 เครื่องมือ Scissors และ 275 แคลอรีต่อชิ้น | ผ่าน |
| 5 | กด Love it! ตอนยังไม่ล็อกอิน | ขึ้น Please log in first. | ขึ้นข้อความตามที่คาด | ผ่าน |
| 6 | เปิด /admin แล้วกด Sign in ด้วยบัญชีเดโม | เห็นหัวข้อ Latest pizza recommendations | หลังลงชื่อเข้าใช้ เห็นหัวข้อและรายการพิซซ่า | ผ่าน |
| 7 | ค้นชื่อที่เพิ่งสร้างในรายการ | ชื่อและเครื่องมือตรงกับผลแนะนำ | เจอ Lab Pizza 20261124-203908 (tool=Scissors, ingredients_number=5) | ผ่าน |

หลังคลิกขอพิซซ่าและกดให้คะแนน ข้อความใช้เวลาสักครู่กว่าจะขึ้น ส่วนในหน้าแอดมินรายการของรอบนี้อยู่ลำดับที่ 4 เพราะมีรายการจากคนอื่นเข้ามาระหว่างทำเคส
