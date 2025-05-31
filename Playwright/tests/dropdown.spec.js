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

    //await page.waitForTimeout(1000)

    await page.locator("#state").selectOption({value:"Goa"})

    //await page.waitForTimeout(1000)

    await page.locator("#state").selectOption({index:4})

    //await page.waitForTimeout(3000)


   /* const value= await page.locator("#state").textContent()


    console.log("All dropdown Value"+value);

    await expect(value.includes("Tamil Nadu")).toBeTruthy()
 */


    let state=await page.$("#state")


    let allElements=await state.$$("option")

    let ddstatus=false

    for(let i=0;i<allElements.length;i++)
    {
        let element=allElements[i]

        let value=await element.textContent()

        if(value.includes("Delhi"))
        {
            ddstatus=true
            break
        }   

        console.log("value from dropdown using for loop "+value);

    }

    await expect(ddstatus).toBeTruthy() 

    await page.locator("#hobbies").selectOption(['Playing','Reading'])

    await page.waitForTimeout(3000)

})
