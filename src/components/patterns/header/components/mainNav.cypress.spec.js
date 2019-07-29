const VsMainNav = require("./MainNav.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const mainNav = require("../../../../assets/fixtures/mainNav.json")

const props = {
  name: "Main Navigation",
  mainNavigationList: mainNav,
}

vueHelper.init("vs-main-nav", VsMainNav, props)

describe("Main Nav component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a container element", () => {
        cy.get(".vs-main-nav").should("be.visible")
      })

      it("should render a menu button", () => {
        cy.get(".vs-main-nav__button").should("be.visible")
      })

      it("button should be accompanied by screen reader text", () => {
        cy.contains("Toggle Main Navigation").should("be.visible")
      })

      it("should toggle a navigation menu", () => {
        cy.get(".vs-main-nav__list").should("not.be.visible")
        cy.get("[data-toggle-trigger]")
          .first()
          .click()
        cy.get(".vs-main-nav__list").should("be.visible")
        cy.get("[data-toggle-trigger]")
          .first()
          .click()
        cy.get(".vs-main-nav__list").should("not.be.visible")
      })

      it("should toggle aria-expanded property on the button", () => {
        cy.get("[data-toggle-trigger]")
          .should("have.attr", "aria-expanded", "false")
          .click()
          .should("have.attr", "aria-expanded", "true")
      })

      it("should display list items when expanded", () => {
        cy.get(".vs-main-nav__list-item").should("not.be.visible")
        cy.get("[data-toggle-trigger]").click()
        cy.get(".vs-main-nav__list-item")
          .should("be.visible")
          .should("have.length", "5")
      })
    })
  })
})
