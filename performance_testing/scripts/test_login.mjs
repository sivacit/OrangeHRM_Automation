import { chromium } from 'k6/x/browser';
import { check } from 'k6';

export const options = {
  scenarios: {
    ui_test: {
      executor: 'shared-iterations',
      iterations: 5,
      maxDuration: '1m',
    },
  },
};

export default async function () {
  const browser = await chromium.launch({ headless: true });
  const context = browser.newContext();
  const page = context.newPage();

  try {
    await page.goto('https://opensource-demo.orangehrmlive.com/');

    check(await page.title(), {
      'title contains OrangeHRM': (t) => t.includes('OrangeHRM'),
    });

    await page.locator('input[name="username"]').type('Admin');
    await page.locator('input[name="password"]').type('admin123');
    await page.locator('button:has-text("Login")').click();

    await page.waitForSelector('text=Dashboard', { timeout: 5000 });

    check(await page.url(), {
      'redirected to dashboard': (url) => url.includes('dashboard'),
    });
  } finally {
    page.close();
    browser.close();
  }
}
