const VsIcon = require("./Icon.vue").default
const { vueComponents, getToken } = require("@cypress/helpers/index.js").default

describe("Icon component", () => {
  let data = { variant: null, name: "favourite" }
  let sizeMd

  beforeEach(() => {
    sizeMd = getToken("icon_size_md")
  })

  vueComponents.init("vs-icon", VsIcon, data)

  it("renders the icon at medium size", () => {
    cy.get("svg")
      .should("have.class", "icon")
      .should("have.attr", "height", sizeMd)
  })
})
