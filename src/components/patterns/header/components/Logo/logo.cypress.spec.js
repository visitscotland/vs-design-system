const VsLogo = require("./Logo.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

vueHelper.init("vs-logo", VsLogo)

describe("Logo component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a container link element", () => {
        cy.get('[data-test="logo-link"]').should("be.visible")
      })

      it("should render screenreader text", () => {
        cy.get('[data-test="logo-screenreader-text"]')
          .should("be.visible")
          .should("contain", "VisitScotland Home")
      })

      if (size === "iphone-6" || size === "ipad-2") {
        it("should render an icon wrapper", () => {
          cy.get('[data-test="logo-wrapper-mobile"]').should("be.visible")
        })
        it("should render an svg icon", () => {
          cy.get('[data-test="logo-mobile"]')
            .should("be.visible")
            .should("have.attr", "height", "18")
        })
      } else {
        it("should render a different icon wrapper on desktop", () => {
          cy.get('[data-test="logo-wrapper-desktop"]').should("be.visible")
        })
        it("should render a different svg icon on desktop", () => {
          cy.get('[data-test="logo-desktop"]')
            .should("be.visible")
            .should("have.attr", "height", "22")
        })
      }
    })
  })
})
