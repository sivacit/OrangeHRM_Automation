import { test, expect } from '@playwright/test';

test('Add Admin user in OrangeHRM User Management module', async ({ page }) => {
  await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');
  
  await page.getByRole('textbox', { name: 'Username' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).fill('admin123');
  await page.getByRole('button', { name: 'Login' }).click();

  await expect(page.getByRole('heading', { name: 'Dashboard' })).toBeVisible();

  await page.getByRole('link', { name: 'Admin' }).click();

  await expect(page.getByRole('heading', { name: 'System Users' })).toBeVisible();

  await page.getByRole('button', { name: 'Add' }).click();

  await expect(page.getByRole('heading', { name: 'Add User' })).toBeVisible();

  // Wait for the User Role dropdown using the label text
  const userRoleDropdown = page.locator('label:has-text("User Role") + div[role="combobox"]');
  await userRoleDropdown.waitFor({ state: 'visible' });
  await userRoleDropdown.click();

  // Click the "Admin" option
  await page.locator('div[role="option"]', { hasText: 'Admin' }).click();

  // Fill Employee Name and select suggestion
  const employeeNameInput = page.locator('input[placeholder="Type for hints..."]');
  await employeeNameInput.fill('Linda Anderson');
  // Wait for suggestions and pick first one
  const suggestion = page.locator('div[role="listbox"] div[role="option"]').first();
  await suggestion.waitFor({ state: 'visible' });
  await suggestion.click();

  // Fill Username
  await page.getByLabel('Username').fill('baiondata');

  // Status dropdown
  const statusDropdown = page.locator('label:has-text("Status") + div[role="combobox"]');
  await statusDropdown.click();
  await page.locator('div[role="option"]', { hasText: 'Enabled' }).click();

  // Fill Password and Confirm Password
  await page.getByLabel('Password').fill('admin123');
  await page.getByLabel('Confirm Password').fill('admin123');

  // Save button
  await page.getByRole('button', { name: 'Save' }).click();

  // Confirm success toast
  await expect(page.locator('div.orangehrm-toast-container')).toContainText('Successfully Saved');
});
