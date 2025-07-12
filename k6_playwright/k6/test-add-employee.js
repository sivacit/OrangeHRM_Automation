import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';

const cookies = new SharedArray('cookies', () =>
  JSON.parse(open('../playwright/cookies.json'))
);

const cookieHeader = cookies.map((c) => `${c.name}=${c.value}`).join('; ');

export let options = {
  vus: 10,
  duration: '20s',
};

export default function () {
  const payload = JSON.stringify({
    firstName: 'Vasanth',
    lastName: 'Kumar',
    empPicture: null
  });

  const headers = {
    'Content-Type': 'application/json',
    'Cookie': cookieHeader,
  };

  const res = http.post(
    'https://opensource-demo.orangehrmlive.com/web/index.php/api/v2/pim/employees',
    payload,
    { headers }
  );

  check(res, {
    'is status 200 or 201': (r) => r.status === 200 || r.status === 201,
    'employee added': (r) => r.body.includes('employeeId'),
  });
}
