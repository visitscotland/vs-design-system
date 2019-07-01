const VsSvg = require("./Svg.vue").default
const { vueComponents, getThemeColours } = require("@cypress/helpers/index.js").default
const { each } = require("lodash")

describe("Svg component", () => {
  const data = { path: "logo" }

  vueComponents.init("vs-svg", VsSvg, data)

  it("renders the logo svg", () => {})

  it("renders the svg at specific height and width", () => {})

  it("renders the svg with a specific fill colour", () => {})
})
