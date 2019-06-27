const VsButton = require("./Button.vue").default
const { vueComponents, getThemeColours } = require("@cypress/helpers/index.js").default
const { each } = require("lodash")

describe("Button component", () => {
  const themeColours = getThemeColours()
  const data = { variant: null, type: null, href: null }

  vueComponents.init("vs-button", VsButton, data, "hello")

  it("renders the contents", () => {
    cy.contains("button", "hello")
  })

  each(themeColours, (colourValue, colourName) => {
    it("renders " + colourName + " colour", () => {
      Cypress.vue.variant = colourName

      cy.get("button").should("have.css", "background-color", colourValue)
    })
  })

  it("renders an anchor", () => {
    const url = "http://www.visitscotland.com"
    Cypress.vue.href = url

    cy.contains("a", "hello")

    cy.get("a")
      .should("have.attr", "href", url)
      .should("have.css", "background-color", themeColours.primary)
  })
})
