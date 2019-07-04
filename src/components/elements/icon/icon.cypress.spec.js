const VsIcon = require("./Icon.vue").default
const { vueHelper, getToken } = require("@cypress/helpers/index.js").default

describe("Icon component", () => {
  let props = { size: "md", name: "favourite" }
  let sizeMd, sizeLg

  beforeEach(() => {
    sizeMd = getToken("icon_size_md")
    sizeLg = getToken("icon_size_lg")
  })

  vueHelper.init("vs-icon", VsIcon, props)

  it("renders the icon at medium size", () => {
    cy.get("svg")
      .should("have.class", "icon")
      .should("have.attr", "height", sizeMd)
  })

  it("renders the icon at large size", () => {
    vueHelper.setProp("size", "lg")
    cy.get("svg")
      .should("have.class", "icon")
      .should("have.attr", "height", sizeLg)
  })
})
