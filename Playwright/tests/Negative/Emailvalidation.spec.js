import { test, expect } from '@playwright/test';

test('test - negative email format', async ({ page }) => {
  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');
  await page.getByRole('textbox', { name: 'Username' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).fill('admin123');
  await page.getByRole('button', { name: 'Login' }).click();

  await page.getByRole('link', { name: 'PIM' }).click();
  await page.getByRole('textbox', { name: 'Type for hints...' }).first().fill('baiondata');
  await page.getByText('baiondata solutions Users').click();
  await page.getByRole('button', { name: 'Search' }).click();
  await page.getByRole('row', { name: ' 1505 baiondata solutions' }).click();

  await page.getByRole('link', { name: 'Contact Details' }).click();

  
  const emailField = page.locator('div:nth-child(9) > .oxd-grid-3 > div > .oxd-input-group > div:nth-child(2) > .oxd-input').first();
  await emailField.fill('vasane');  
  await page.getByRole('button', { name: 'Save' }).click();

  const errorMessage = page.locator('span.oxd-input-field-error-message');  
  await expect(errorMessage).toHaveText('Expected format: admin@example.com');
});
