import { browser } from 'k6/x/browser';

export const options = {
  scenarios: {
    ui: {
      executor: 'shared-iterations',
      options: {
        browser: {
          type: 'chromium', // <-- THIS LINE IS REQUIRED
        },
      },
      vus: 1,
      iterations: 1,
    },
  },
};

export default async function () {
  const context = browser.newContext();
  const page = context.newPage();

  await page.goto('https://example.com');
  console.log('✅ Title:', await page.title());

  await page.close();
  await context.close();
}
