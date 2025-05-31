import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');
  
  await page.getByRole('textbox', { name: 'Username' }).fill('admin');
  
  await page.getByRole('textbox', { name: 'Password' }).fill('admin123');

  await page.getByRole('button', { name: 'Login' }).click();

  await page.getByRole('link', { name: 'PIM' }).click();

  await page.getByRole('textbox', { name: 'Type for hints...' }).first().click();

  await page.getByRole('textbox', { name: 'Type for hints...' }).first().fill('baiondata user');

  await page.getByText('baiondata User', { exact: true }).click();

  await page.getByRole('button', { name: 'Search' }).click();

  await page.getByRole('cell', { name: '' }).locator('i').click();

  await page.getByRole('button', { name: '' }).click();

  await page.getByRole('button', { name: ' Yes, Delete' }).click();
  
});