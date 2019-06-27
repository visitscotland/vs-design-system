const VsSvg = require("./Svg.vue").default
const { vueComponents, getThemeColours } = require("@cypress/helpers/index.js").default
const { each } = require("lodash")

describe("Svg component", () => {
  const themeColours = getThemeColours()
  const data = { path: "logo" }

  vueComponents.init("vs-svg", VsSvg, data)

  it("renders", () => {
    cy.contains("svg", "hello")
  })
})
