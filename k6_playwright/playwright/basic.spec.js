import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {

    await page.goto('https://www.google.com/');

    await page.getByRole('combobox', { name: 'Search' }).click();

    await page.getByRole('combobox', { name: 'Search' }).fill('playwright');

    await page.goto('https://www.google.com/sorry/index?continue=https://www.google.com/search%3Fq%3Dplaywright%26sca_esv%3D7bf8a2f3baef1f90%26source%3Dhp%26ei%3DDuJwaN7rAs655OUP-saggAw%26iflsig%3DAOw8s4IAAAAAaHDwHjBa8N3i2Y5c8iwYzDgAaf0vF_GQ%26ved%3D0ahUKEwjeqsuqx7SOAxXOHLkGHXojCMAQ4dUDCA8%26uact%3D5%26oq%3Dplaywright%26gs_lp%3DEgdnd3Mtd2l6IgpwbGF5d3JpZ2h0MgUQABiABDIIEAAYgAQYsQMyCxAAGIAEGLEDGIMBMgUQABiABDILEAAYgAQYsQMYgwEyCxAAGIAEGLEDGIMBMggQABiABBixAzIIEAAYgAQYsQMyCBAAGIAEGLEDMgUQABiABEiGYFCmDljsXHAFeACQAQCYAaIBoAH-DqoBBDAuMTS4AQPIAQD4AQGYAhOgAqkQqAIKwgIKEAAYAxjqAhiPAcICChAuGAMY6gIYjwHCAggQLhiABBixA8ICBRAuGIAEwgIOEAAYgAQYsQMYgwEYigXCAg4QLhiABBixAxjRAxjHAcICERAuGIAEGLEDGNEDGIMBGMcBwgILEC4YgAQYxwEYrwHCAgsQLhiABBjRAxjHAcICBxAAGIAEGArCAg0QABiABBixAxiDARgKwgIKEAAYgAQYsQMYCsICCxAuGIAEGLEDGOUEmAMH8QVaeorQU3dss5IHBDUuMTSgB7tbsgcEMC4xNLgHgxDCBwgwLjEuNS4xM8gHlgE%26sclient%3Dgws-wiz%26sei%3DHeJwaPLWG5fm1e8PhoujqAY&q=EhAkAUkAiCfKAa0xSeHRjY2MGJ7Ew8MGIjBf43kQB7uLOjC-BxPQSiLSddYX_jm9tEZtHelYB8k1OduVvHEJ_pXcK_Ott8qrOIwyAVJaAUM');

});