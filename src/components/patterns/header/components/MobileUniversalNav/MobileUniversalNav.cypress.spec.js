const VsMobileUniversalNav = require("./MobileUniversalNav.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const ourSites = require("@/assets/fixtures/header/ourSites.json")

const props = {
  name: "Our Sites",
  dropdownList: ourSites,
}

vueHelper.init("vs-mobile-universal-nav", VsMobileUniversalNav, { props })

describe("Mobile Universal Nav component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a container nav element", () => {
        cy.get('[data-test="mobile-universal-nav"]').should("be.visible")
      })

      it("the nav should have an aria-label containing the name prop", () => {
        cy.get('[data-test="mobile-universal-nav"]').should("have.attr", "aria-label", "Our Sites")
      })

      it("should render button containing text with the name prop", () => {
        cy.get('[data-test="mobile-universal-nav-button"]').should("contain", "Our Sites")
      })

      it("button should be accompanied by screen reader text", () => {
        cy.get('[data-test="mobile-universal-nav-button"]')
          .find(".sr-only")
          .should("contain", "Toggle menu for")
      })

      it("should display an svg icon", () => {
        cy.get('[data-test="mobile-univeral-nav-chevron-icon"]').should("be.visible")
      })

      it("should open and close a dropdown menu", () => {
        cy.get('[data-test="mobile-univeral-nav-list"]').should("not.be.visible")
        cy.get('[data-test="mobile-universal-nav-button"]').click()
        cy.get('[data-test="mobile-univeral-nav-list"]').should("be.visible")
        cy.get('[data-test="mobile-universal-nav-button"]').click()
        cy.get('[data-test="mobile-univeral-nav-list"]').should("not.be.visible")
      })

      it("internal links should not display an external link icon", () => {
        cy.get('[data-test="mobile-universal-nav-button"]').click()
        cy.get('[data-test="mobile-universal-nav-list-item"]')
          .first()
          .find('[data-test="mobile-universal-nav-external-link-icon"]')
          .should("not.be.visible")
      })

      it("External links should display an external link icon", () => {
        cy.get('[data-test="mobile-universal-nav-button"]').click()
        cy.get('[data-test="mobile-universal-nav-list-item"]')
          .last()
          .find('[data-test="mobile-universal-nav-external-link-icon"]')
          .should("be.visible")
      })
    })
  })
})
