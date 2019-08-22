const VsMobileNav = require("./MobileNav.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const mainNav = require("../../../../assets/fixtures/mainNav.json")
const level1 = mainNav[0]
const level2 = level1.subnav[0]
const level3 = level2.subnav[0]

const props = {
  name: "Mobile Navigation",
}

const content = `
  <vs-main-nav-list-item
    slot="subnav"
    level=1
    href="${level1.href}"
    subnav="${level1.subnav}"
    is-external="${level1.isExternal}"
    title="${level1.title}"
  >
    <vs-main-nav-list-item
      slot="subnav"
      level=2
      href="${level2.href}"
      subnav="${level2.subnav}"
      is-external="${level2.isExternal}"
      title="${level2.title}"
    >
      <vs-main-nav-list-item
        slot="subnav"
        level=3
        href="${level3.href}"
        subnav="${level3.subnav}"
        is-external="${level3.isExternal}"
        title="${level3.title}"
      >
      </vs-main-nav-list-item>
    </vs-main-nav-list-item>
  </vs-main-nav-list-item>
`

vueHelper.init("vs-mobile-nav", VsMobileNav, props, content)

describe("Mobile Nav component", () => {
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
        cy.get("[data-test-trigger]")
          .first()
          .click()
        cy.get(".vs-main-nav__list").should("be.visible")
        cy.get("[data-test-trigger]")
          .first()
          .click()
        cy.get(".vs-main-nav__list").should("not.be.visible")
      })

      it("should toggle aria-expanded property on the button", () => {
        cy.get("[data-test-trigger]")
          .should("have.attr", "aria-expanded", "false")
          .click()
          .should("have.attr", "aria-expanded", "true")
      })
    })
  })
})
