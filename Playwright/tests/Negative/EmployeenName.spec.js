const { test, expect } = require('@playwright/test');

test('Check "No Records Found" appears under Employee Name when typing "vasanth"', async ({ page }) => {
    await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');

    // Login
    await page.fill('input[name="username"]', 'Admin');
    await page.fill('input[name="password"]', 'admin123');
    await page.click('button[type="submit"]');
    await page.waitForSelector('.oxd-topbar-header-title');

    // Go to Employee List
    await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList');
    await page.waitForSelector('.oxd-table-body');

    // Type "vasanth" into Employee Name field
    const employeeNameField = 'input[placeholder="Type for hints..."]';
    await page.fill(employeeNameField, 'vasanth');

    // Wait for the dropdown container to appear
    await page.waitForSelector('div[role="listbox"]');

    // Check for "No Records Found"
    const noRecordsMessage = page.locator('//div[@role="listbox"]//div[text()="No Records Found"]');
    await expect(noRecordsMessage).toBeVisible({ timeout: 10000 });
});
