const { chromium } = require('playwright');
const fs = require('fs');

(async () => {
  const browser = await chromium.launch();
  const page = await browser.newPage();

  await page.goto('https://opensource-demo.orangehrmlive.com/');
  await page.fill('input[name="username"]', 'Admin');
  await page.fill('input[name="password"]', 'admin123');
  await page.click('button[type="submit"]');

  // ✅ Wait for Dashboard text to appear after login
  await page.waitForSelector('h6.oxd-text.oxd-text--h6.oxd-topbar-header-breadcrumb-module');

  const cookies = await page.context().cookies();
  fs.writeFileSync('cookies.json', JSON.stringify(cookies, null, 2));

  console.log('✅ Cookies saved to cookies.json');
  await browser.close();
})();
