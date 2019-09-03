const VsDesktopUniversalNav = require("./DesktopUniversalNav.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const ourSites = require("../../../../../assets/fixtures/ourSites.json")

const props = {
  name: "Our Sites",
  dropdownList: ourSites,
}

vueHelper.init("vs-desktop-universal-nav", VsDesktopUniversalNav, props)

describe("Desktop Universal Nav component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a nav with an aria-label containing the name prop", () => {
        cy.get('[data-test="universal-nav"]').should("have.attr", "aria-label", "Our Sites")
      })

      it("should render a list", () => {
        cy.get('[data-test="universal-nav-list"]').should("be.visible")
      })

      it("should render list items", () => {
        cy.get('[data-test="universal-nav-list-item"]')
          .should("be.visible")
          .should("have.length", "5")
      })

      it("should render links", () => {
        cy.get('[data-test="universal-nav-link"]')
          .should("be.visible")
          .should("have.length", "5")
      })

      it("should render text within the links", () => {
        cy.get('[data-test="universal-nav-link"]')
          .first()
          .should("contain", "VisitScotland")
      })
    })
  })
})
