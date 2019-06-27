const VsButton = require("./Button.vue").default
const { getToken, vueComponents } = require("@cypress/helpers/index.js").default

describe("Button component", () => {
  const primaryColour = getToken("color_theme_primary")
  const secondaryColour = getToken("color_theme_secondary")
  const data = { variant: null, type: null, href: null }

  vueComponents.init("vs-button", VsButton, data, "hello")

  it("renders the default button", () => {
    cy.contains("button", "hello")
    cy.get("button").should("have.css", "background-color", primaryColour)
  })

  it("renders secondary colour", () => {
    Cypress.vue.variant = "secondary"

    cy.get("button").should("have.css", "background-color", secondaryColour)
  })
})
