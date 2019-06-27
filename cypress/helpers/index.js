const vueComponents = require("./vue-components").default
const { getToken, getThemeColours } = require("./tokens").default
const { getSvg, getSvgPath, getSvgAttr } = require("./svg").default

export default {
  getSvg,
  getSvgPath,
  getSvgAttr,
  getToken,
  getThemeColours,
  vueComponents,
}
