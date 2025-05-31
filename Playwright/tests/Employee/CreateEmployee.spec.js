const { test, expect } = require('@playwright/test');

test('Add employee in OrangeHRM PIM module', async ({ page }) => {
  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');

  await page.fill('input[name="username"]', 'Admin');
  await page.fill('input[name="password"]', 'admin123');
  await page.click('button[type="submit"]');

  await expect(page.locator('h6:has-text("Dashboard")')).toBeVisible();

  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/pim/addEmployee');

  await page.fill('input[name="firstName"]', 'baiondata');
  await page.fill('input[name="lastName"]', 'User');

  await Promise.all([
    page.waitForNavigation({ waitUntil: 'networkidle' }),
    page.click('button:has-text("Save")'),
  ]);

  
  await expect(page.getByRole('heading', { name: 'Personal Details' })).toBeVisible({ timeout: 15000 });
});
