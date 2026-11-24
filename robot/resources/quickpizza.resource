*** Settings ***
Documentation     เก็บตำแหน่งของช่องและคำสั่งที่ใช้กับ QuickPizza
Library           SeleniumLibrary    timeout=10s
Library           String

*** Variables ***
${URL}               https://quickpizza.grafana.com
${BROWSER}           chrome
${HEADING}           Looking to break out of your pizza routine?
${ADVANCED}          xpath://label[.//span[normalize-space()='Advanced']]
${MAX CALORIES}      xpath://label[normalize-space()='Max Calories per Slice']/preceding-sibling::input
${EXCLUDED TOOLS}    xpath://label[normalize-space()='Excluded tools']/following-sibling::select
${KNIFE OPTION}      xpath://label[normalize-space()='Excluded tools']/following-sibling::select/option[@value='Knife']
${VEGETARIAN}        id:default-checkbox
${PIZZA NAME}        css:input#pizza-name
${RECOMMENDATION}    id:recommendations

*** Keywords ***
Open QuickPizza
    [Documentation]    ขั้น 1 เปิดหน้าแรกและตรวจหัวข้อ
    Open Browser    ${URL}    ${BROWSER}
    ...    service=executable_path=r'%{CHROMEDRIVER}'
    Set Window Size    1280    1600
    Wait Until Element Is Visible    css:h1
    Element Text Should Be    css:h1    ${HEADING}

Turn On Advanced
    [Documentation]    ขั้น 2 เปิด Advanced และตรวจว่าช่องครบ
    Click Element    ${ADVANCED}
    Wait Until Element Is Visible    ${MAX CALORIES}
    Wait Until Element Is Visible    ${EXCLUDED TOOLS}
    Wait Until Element Is Visible    ${VEGETARIAN}
    Wait Until Element Is Visible    ${PIZZA NAME}
    Wait Until Page Contains Element    ${KNIFE OPTION}

Fill In The Pizza Request
    [Documentation]    ขั้น 3 ใส่เงื่อนไขแล้วตรวจค่าที่แสดง
    [Arguments]    ${calories}    ${tool}    ${name}
    Input Text    ${MAX CALORIES}    ${calories}
    Select From List By Label    ${EXCLUDED TOOLS}    ${tool}
    Select Checkbox    ${VEGETARIAN}
    Input Text    ${PIZZA NAME}    ${name}
    Textfield Value Should Be    ${MAX CALORIES}    ${calories}
    List Selection Should Be    ${EXCLUDED TOOLS}    ${tool}
    Checkbox Should Be Selected    ${VEGETARIAN}
    Textfield Value Should Be    ${PIZZA NAME}    ${name}

Ask For A Pizza
    [Documentation]    ขั้น 4 ขอพิซซ่าและรอผลแนะนำ
    Click Button    name:pizza-please
    Wait Until Element Contains    ${RECOMMENDATION}    Name:

Recommendation Should Keep The Restrictions
    [Documentation]    ขั้น 4 ตรวจชื่อ เครื่องมือ และแคลอรี
    [Arguments]    ${name}    ${excluded tool}    ${max calories}
    ${actual name}=    Read Recommendation Line    Name:
    Should Be Equal    ${actual name}    ${name}
    ${tool}=    Read Recommendation Line    Tool:
    Should Not Be Equal    ${tool}    ${excluded tool}
    ${calorie text}=    Read Recommendation Line    Calories per slice:
    ${calories}=    Convert To Integer    ${calorie text}
    Should Be True    $calories <= int($max_calories)
    RETURN    ${tool}    ${calories}

Read Recommendation Line
    [Arguments]    ${label}
    ${locator}=    Set Variable    xpath://div[@id='recommendations']//p[starts-with(normalize-space(),'${label}')]
    Wait Until Element Is Visible    ${locator}
    ${line}=    Get Text    ${locator}
    ${value}=    Remove String Using Regexp    ${line}    ^${label}
    ${value}=    Strip String    ${value}
    RETURN    ${value}

Rate The Pizza Without Logging In
    [Documentation]    ขั้น 5 กดให้คะแนนและตรวจข้อความที่ขอให้ล็อกอิน
    Click Button    name:rate-5
    Wait Until Element Contains    id:rate-result    Please log in first.
    Element Text Should Be    id:rate-result    Please log in first.

Log In As Admin
    [Documentation]    ขั้น 6 เข้าแอดมินในเบราว์เซอร์เดิม
    [Arguments]    ${user}    ${password}
    Go To    ${URL}/admin
    Wait Until Page Contains Element    id:svelte-announcer
    Input Text    id:username    ${user}
    Input Password    id:password    ${password}
    Click Button    css:button[type=submit]
    Wait Until Page Contains    Latest pizza recommendations

Admin List Should Show The Pizza
    [Documentation]    ขั้น 7 ค้นชื่อจากทั้งรายการและเทียบเครื่องมือ
    [Arguments]    ${name}    ${tool}
    ${entry}=    Set Variable    xpath://li[contains(.,'${name} (tool=')]
    Wait Until Element Is Visible    ${entry}
    Element Should Contain    ${entry}    (tool=${tool},
