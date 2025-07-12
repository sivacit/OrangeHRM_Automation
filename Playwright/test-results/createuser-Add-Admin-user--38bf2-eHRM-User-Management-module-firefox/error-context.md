# Test info

- Name: Add Admin user in OrangeHRM User Management module
- Location: /home/vasanth/vasanth/Plyawright_Udemy/Playwright/tests/createuser.spec.js:6:1

# Error details

```
TimeoutError: locator.waitFor: Timeout 10000ms exceeded.
Call log:
  - waiting for locator('label:has-text("User Role") + div[role="combobox"]') to be visible

    at /home/vasanth/vasanth/Plyawright_Udemy/Playwright/tests/createuser.spec.js:31:26
```

# Page snapshot

```yaml
- complementary:
  - navigation "Sidepanel":
    - link "client brand banner":
      - /url: https://www.orangehrm.com/
      - img "client brand banner"
    - textbox "Search"
    - button ""
    - separator
    - list:
      - listitem:
        - link "Admin":
          - /url: /web/index.php/admin/viewAdminModule
      - listitem:
        - link "PIM":
          - /url: /web/index.php/pim/viewPimModule
      - listitem:
        - link "Leave":
          - /url: /web/index.php/leave/viewLeaveModule
      - listitem:
        - link "Time":
          - /url: /web/index.php/time/viewTimeModule
      - listitem:
        - link "Recruitment":
          - /url: /web/index.php/recruitment/viewRecruitmentModule
      - listitem:
        - link "My Info":
          - /url: /web/index.php/pim/viewMyDetails
      - listitem:
        - link "Performance":
          - /url: /web/index.php/performance/viewPerformanceModule
      - listitem:
        - link "Dashboard":
          - /url: /web/index.php/dashboard/index
      - listitem:
        - link "Directory":
          - /url: /web/index.php/directory/viewDirectory
      - listitem:
        - link "Maintenance":
          - /url: /web/index.php/maintenance/viewMaintenanceModule
      - listitem:
        - link "Claim":
          - /url: /web/index.php/claim/viewClaimModule
          - img
          - text: Claim
      - listitem:
        - link "Buzz":
          - /url: /web/index.php/buzz/viewBuzz
- banner:
  - heading "Admin" [level=6]
  - link "Upgrade":
    - /url: https://orangehrm.com/open-source/upgrade-to-advanced
    - button "Upgrade"
  - list:
    - listitem:
      - img "profile picture"
      - paragraph: manda user
      - text: 
  - navigation "Topbar Menu":
    - list:
      - listitem: User Management 
      - listitem: Job 
      - listitem: Organization 
      - listitem: Qualifications 
      - listitem:
        - link "Nationalities":
          - /url: "#"
      - listitem:
        - link "Corporate Branding":
          - /url: "#"
      - listitem: Configuration 
      - button ""
- heading "Add User" [level=6]
- separator
- text: User Role* -- Select --  Employee Name*
- textbox "Type for hints..."
- text: Status* -- Select --  Username*
- textbox
- text: Password*
- textbox
- paragraph: For a strong password, please use a hard to guess combination of text with upper and lower case characters, symbols and numbers
- text: Confirm Password*
- textbox
- separator
- paragraph: "* Required"
- button "Cancel"
- button "Save"
- paragraph: OrangeHRM OS 5.7
- paragraph:
  - text: © 2005 - 2025
  - link "OrangeHRM, Inc":
    - /url: http://www.orangehrm.com
  - text: . All rights reserved.
```

# Test source

```ts
   1 | const { test, expect } = require('@playwright/test');
   2 |
   3 | // Use a larger viewport for visibility
   4 | test.use({ viewport: { width: 1500, height: 1000 } });
   5 |
   6 | test('Add Admin user in OrangeHRM User Management module', async ({ page }) => {
   7 |   // Step 1: Login
   8 |   await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login');
   9 |   await page.waitForSelector('input[name="username"]');
  10 |
  11 |   await page.locator('input[name="username"]').fill('Admin');
  12 |   await page.locator('input[name="password"]').fill('admin123');
  13 |   await page.locator('button[type="submit"]').click();
  14 |
  15 |   // Step 2: Wait for dashboard to load
  16 |   await page.waitForURL('**/dashboard/**');
  17 |   await expect(page).toHaveURL(/dashboard/);
  18 |
  19 |   // Step 3: Navigate to Admin -> User Management
  20 |   await page.locator('a[href="/web/index.php/admin/viewAdminModule"]').click();
  21 |   await page.waitForURL('**/admin/viewSystemUsers');
  22 |
  23 |   // Step 4: Click "Add" button
  24 |   await page.getByRole('button', { name: 'Add' }).click();
  25 |
  26 |   // Step 5: Wait for "Add User" form
  27 |   await page.waitForSelector('h6:has-text("Add User")');
  28 |
  29 |   // Step 6: Select User Role = Admin
  30 |   const userRoleDropdown = page.locator('label:has-text("User Role") + div[role="combobox"]');
> 31 |   await userRoleDropdown.waitFor({ state: 'visible', timeout: 10000 });
     |                          ^ TimeoutError: locator.waitFor: Timeout 10000ms exceeded.
  32 |   await userRoleDropdown.click();
  33 |   await page.getByRole('option', { name: 'Admin' }).click();
  34 |
  35 |   // Step 7: Select Status = Enabled
  36 |   const statusDropdown = page.locator('label:has-text("Status") + div[role="combobox"]');
  37 |   await statusDropdown.waitFor({ state: 'visible' });
  38 |   await statusDropdown.click();
  39 |   await page.getByRole('option', { name: 'Enabled' }).click();
  40 |
  41 |   // Step 8: Fill in user details
  42 |   await page.getByPlaceholder('Type for hints...').fill('Paul Collings'); // existing employee
  43 |   await page.getByRole('textbox', { name: 'Username' }).fill('newadminuser123');
  44 |   await page.locator('input[type="password"]').nth(0).fill('Admin@123');
  45 |   await page.locator('input[type="password"]').nth(1).fill('Admin@123');
  46 |
  47 |   // Step 9: Save the user
  48 |   await page.getByRole('button', { name: 'Save' }).click();
  49 |
  50 |   // Step 10: Wait for redirection back to user list
  51 |   await page.waitForURL('**/admin/viewSystemUsers');
  52 |   await expect(page).toHaveURL(/viewSystemUsers/);
  53 |
  54 |   // Optional: Search for the new user to verify creation
  55 |   await page.getByPlaceholder('Search').fill('newadminuser123');
  56 |   await page.getByRole('button', { name: 'Search' }).click();
  57 |   await expect(page.getByText('newadminuser123')).toBeVisible();
  58 |
  59 |   // Step 11: Logout
  60 |   await page.getByAltText('profile picture').first().click();
  61 |   await page.getByText('Logout').click();
  62 |   await page.waitForURL('**/auth/login');
  63 | });
  64 |
```