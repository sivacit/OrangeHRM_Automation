const { chromium } = require('playwright');
const fs = require('fs');

(async () => {
  const browser = await chromium.launch({ headless: false });
  const context = await browser.newContext();

  // Load cookies
  const cookies = JSON.parse(fs.readFileSync('./playwright/cookies.json', 'utf-8'));
  await context.addCookies(cookies);

  const page = await context.newPage();

  // Go directly to dashboard (login already done via cookies)
  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index');

  // Go to PIM → Add Employee
  await page.click('a:has-text("PIM")');
  await page.waitForTimeout(1000);
  await page.click('text="Add Employee"');

  // Fill form
  await page.fill('input[name="firstName"]', 'Vasanth');
  await page.fill('input[name="lastName"]', 'Kumar');

  // Click Save
  await page.click('button:has-text("Save")');

  // Optional: Wait and check result
  await page.waitForTimeout(2000);
  await page.screenshot({ path: 'playwright/employee-added.png' });

  console.log('✅ Employee added!');
  await browser.close();
})();
