import { test, expect, chromium } from '@playwright/test';
import fs from 'fs';

test('edit employee middle name and save cookies', async () => {
  test.setTimeout(60000); // 60s timeout

  const browser = await chromium.launch({ headless: false });
  const context = await browser.newContext();
  const page = await context.newPage();

  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');

  // Login
  await page.getByRole('textbox', { name: 'Username' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).fill('admin123');
  await page.getByRole('button', { name: 'Login' }).click();

  // Wait for dashboard element (e.g., the 'PIM' link)
  await page.getByRole('link', { name: 'PIM' }).waitFor({ timeout: 10000 });

  // Navigate to PIM
  await page.getByRole('link', { name: 'PIM' }).click();

  // Search employee
  await page.getByPlaceholder('Type for hints...').first().fill('vasanth');
  await page.getByRole('option', { name: /Vasanth Kumar/i }).first().click();
  await page.getByRole('button', { name: 'Search' }).click();

  // Click edit icon in row
  const row = page.getByRole('row', { name: /Vasanth Kumar/i });
  await row.getByRole('button').first().click();

  // Fill middle name
  await page.getByRole('textbox', { name: 'Middle Name' }).fill('vasu');

  // Save
  await page.locator('form').getByRole('button').click();

  // Confirm page change
  await expect(page).toHaveURL(/viewPersonalDetails/);

  // Save cookies
  const cookies = await context.cookies();
  fs.writeFileSync('playwright/cookies.json', JSON.stringify(cookies, null, 2));
  console.log('✅ Cookies saved!');

  await browser.close();
});
