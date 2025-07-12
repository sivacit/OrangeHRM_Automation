import { browser } from 'k6/experimental/browser';
import { check } from 'k6';

export const options = {
  scenarios: {
    ui: {
      executor: 'shared-iterations',
      vus: 5,
      iterations: 10,
      maxDuration: '2m',
      options: {
        browser: {
          type: 'chromium',
        },
      },
    },
  },
};

export default async function () {
  const page = browser.newPage();

  try {
    await page.goto('https://opensource-demo.orangehrmlive.com/');

    await page.locator('input[name="username"]').type('Admin');
    await page.locator('input[name="password"]').type('admin123');

    // Wait for navigation after login click
    await Promise.all([
      page.locator('button[type="submit"]').click(),
      page.waitForNavigation(),
    ]);

    // Wait for dashboard element after navigation
    await page.waitForSelector('.oxd-topbar-header-title');

    const title = await page.title();
    console.log('✅ Page title after login:', title);

    check(title, {
      'Title contains OrangeHRM': (t) => t.includes('OrangeHRM'),
    });
  } finally {
    await page.close();
  }
}
