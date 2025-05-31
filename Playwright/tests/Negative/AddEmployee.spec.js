const { test, expect } = require('@playwright/test');

test('Add Employee validation errors', async ({ page }) => {
    test.setTimeout(60000); // Optional extended timeout

   
    await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');
    await page.fill('input[name="username"]', 'Admin');
    await page.fill('input[name="password"]', 'admin123');

  
    await Promise.all([
      page.waitForNavigation({ waitUntil: 'networkidle' }),
      page.click('button[type="submit"]')
    ]);

 
    await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/pim/addEmployee');

 
    await page.click('button[type="submit"]');


    await page.waitForSelector('.oxd-input-field-error-message');

   
    const errors = await page.$$eval('.oxd-input-field-error-message', nodes =>
        nodes.map(n => n.innerText.trim())
    );

    console.log('Validation Errors:', errors);

    
    expect(errors).toContain('Required');
});
