import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');
 
  await page.getByRole('textbox', { name: 'Username' }).fill('admin');
  
  await page.getByRole('textbox', { name: 'Password' }).fill('admin123');

  await page.getByRole('button', { name: 'Login' }).click();

  await page.getByRole('link', { name: 'PIM' }).click();

  await page.getByRole('textbox', { name: 'Type for hints...' }).first().click();

  await page.getByRole('textbox', { name: 'Type for hints...' }).first().fill('baiondata solution user');

  await page.getByText('baiondata solution User').click();

  await page.getByRole('button', { name: 'Search' }).click();

  await page.getByRole('row', { name: ' 0381 baiondata solution' }).click();

  await page.getByRole('textbox', { name: 'Middle Name' }).click();

  await page.getByRole('textbox', { name: 'Middle Name' }).fill('solutions');

  await page.getByRole('textbox', { name: 'Last Name' }).click();

  await page.getByRole('textbox', { name: 'Last Name' }).fill('Users');
  
  await page.locator('form').filter({ hasText: 'Employee Full NameEmployee' }).getByRole('button').click();
});