import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';

// ✅ Proper way to load and share cookies
const cookies = new SharedArray('cookies', () => {
  return JSON.parse(open('../playwright/cookies.json'));
});

// ✅ Construct the cookie header string
const cookieHeader = cookies
  .map((cookie) => `${cookie.name}=${cookie.value}`)
  .join('; ');

// ✅ Load test options
export let options = {
  vus: 10,
  duration: '20s',
};

// ✅ Main test function
export default function () {
  const res = http.get('https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index', {
    headers: {
      Cookie: cookieHeader,
    },
  });

  // ✅ Check for status and content
  check(res, {
    'is status 200': (r) => r.status === 200,
    'dashboard loaded': (r) => r.body.includes('Dashboard'),
  });
}
