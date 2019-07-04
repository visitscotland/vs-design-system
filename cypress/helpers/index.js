// const vueHelpers = require("./vue").default
// const { getToken, getThemeColours }

export const vueHelper = require("./vue").default

export const { getToken, getThemeColours } = require("./tokens").default

export default {
  getToken,
  getThemeColours,
  vueHelper,
}
