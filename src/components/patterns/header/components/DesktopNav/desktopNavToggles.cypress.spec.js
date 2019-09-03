const VsDesktopNavToggles = require("./DesktopNavToggles.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const mainNav = require("../../../../../assets/fixtures/mainNav.json")
const item = mainNav[0]

const props = {
  level: 1,
  href: item.href,
  subnav: item.subnav,
  isExternal: item.isExternal,
  title: item.title,
  promoList: item.promoList,
  promoItem: item.promoItem,
  toggleId: 1,
}

vueHelper.init("vs-desktop-nav-toggles", VsDesktopNavToggles, props)

describe("Desktop Nav Toggles component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a list item", () => {
        cy.get('[data-test="desktop-nav-list-item"]').should("be.visible")
      })

      it("should render a button", () => {
        cy.get('[data-test="desktop-nav-button"]')
          .should("be.visible")
          .should("have.class", "vs-desktop-nav__button--level1")
          .should("have.attr", "aria-controls", "collapse-subnav-1")
          .should("have.attr", "aria-expanded", "false")
          .should("contain", "Destinations")
      })
    })
  })
})
