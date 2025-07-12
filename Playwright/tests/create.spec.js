import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');

  await page.getByRole('textbox', { name: 'Username' }).click();

  await page.getByRole('textbox', { name: 'Username' }).fill('admin');

  await page.getByRole('textbox', { name: 'Password' }).click();

  await page.getByRole('textbox', { name: 'Password' }).fill('admin123');

  await page.getByRole('button', { name: 'Login' }).click();

  await page.getByRole('link', { name: 'PIM' }).click();

  await page.getByRole('button', { name: ' Add' }).click();

  await page.getByRole('textbox', { name: 'First Name' }).click();

  await page.getByRole('textbox', { name: 'First Name' }).fill('baiondata');

  await page.getByRole('textbox', { name: 'Last Name' }).click();

  await page.getByRole('textbox', { name: 'Last Name' }).fill('user');

  await page.getByRole('button', { name: 'Save' }).click();

  await page.getByRole('textbox').nth(4).click();

  await page.getByRole('textbox').nth(4).fill('1505');

  await page.getByRole('button', { name: 'Save' }).click();
  
});