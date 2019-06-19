const VsButton = require("./Button.vue").default
const { getToken, vueComponents } = require("@cypress/helpers/index.js").default
const { partial } = require("lodash")

describe("My Vue", () => {
  const data = { type: "button", href: null, variant: "primary" }

  vueComponents.init("vs-button", VsButton, data, "hello")

  it("renders", () => {
    const primaryColour = getToken("color_theme_primary")

    cy.contains("button", "hello")
    cy.get("button").should("have.css", "background-color", primaryColour)
  })

  it("changes colour", () => {
    const secondaryColour = getToken("color_theme_secondary")

    Cypress.vue.variant = "secondary"

    cy.get("button").should("have.css", "background-color", secondaryColour)
  })
})
