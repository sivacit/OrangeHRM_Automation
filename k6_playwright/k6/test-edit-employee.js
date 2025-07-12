import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';

// Load cookies saved from Playwright (make sure path is correct)
const cookies = new SharedArray('cookies', () =>
  JSON.parse(open('../playwright/cookies.json'))
);

// Convert cookies array into single "Cookie" header string
const cookieHeader = cookies.map(c => `${c.name}=${c.value}`).join('; ');

export const options = {
  vus: 10,
  duration: '20s',
};

export default function () {
  const empNumber = '392'; // Change to the employee you edited in Playwright
  const url = `https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/${empNumber}`;

  const res = http.get(url, {
    headers: {
      Cookie: cookieHeader,
    },
  });

  // 🔍 Debug log (remove later)
  console.log(`\n🔍 URL: ${url}`);
  console.log(`🔍 Status: ${res.status}`);
  console.log(`🔍 First 500 chars:\n${res.body.substring(0, 500)}\n`);

  // Checks
  check(res, {
    'status is 200': (r) => r.status === 200,
    'Edit page loaded': (r) => r.body.includes('Personal Details'),
  });
}
