const {test,expect} = require('@playwright/test')

test("verify Error Message",async function({page}) 
{

    await page.goto("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login")

    await page.getByPlaceholder("Username").type("Admin")

    await page.getByPlaceholder("Password").type("admijfusfhhi")

    await page.locator("//button[normalize-space()='Login']").click()

    const errorMessage=await page.locator("//p[contains(@class,'alert-content-text')]").textContent()

    console.log("Error Message is "+errorMessage);
    

    expect(errorMessage.includes("Invalid")).toBeTruthy()
    

})