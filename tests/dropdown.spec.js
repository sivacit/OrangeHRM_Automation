const {test,expect} = require('@playwright/test')

test("select value from dropdown",async function({page})
{
    await page.goto("https://freelance-learn-automation.vercel.app/signup")

    /*
        first preference 
        1. label
        2. Value
        3. Text
    */

    await page.locator("#state").selectOption({label:"Tamil Nadu"})

    await page.waitForTimeout(1000)

    await page.locator("#state").selectOption({value:"Goa"})

    await page.waitForTimeout(1000)

    await page.locator("#state").selectOption({index:4})

    await page.waitForTimeout(3000)


    const value= await page.locator("#state").textContent()


    console.log("All dropdown Value"+value);

})
