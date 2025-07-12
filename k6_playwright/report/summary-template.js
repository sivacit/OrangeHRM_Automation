import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';

export function handleSummary(data) {
  return {
    'report.html': textSummary(data, { indent: ' ', enableColors: true }),
    stdout: textSummary(data, { indent: ' ', enableColors: true }),
  };
}
