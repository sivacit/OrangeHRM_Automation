import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');
  
  await page.getByRole('textbox', { name: 'Username' }).fill('admin');
  
  await page.getByRole('textbox', { name: 'Password' }).fill('admin123');
  await page.getByRole('button', { name: 'Login' }).click();
  await page.waitForTimeout(5000)
  await page.getByText('Sushma Sushreddy').click();
  
  await page.getByRole('menuitem', { name: 'Logout' }).click();
});