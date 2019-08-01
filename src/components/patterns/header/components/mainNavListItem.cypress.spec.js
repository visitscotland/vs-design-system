const VsMainNavListItem = require("./MainNavListItem.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const mainNav = require("../../../../assets/fixtures/mainNav.json")
const item = mainNav[0]

const props = {
  level: "1",
  href: item.href,
  isExternal: item.isExternal,
  title: item.title,
  trackingId: item.trackingID,
  subnav: item.subnav,
  promoList: item.promoList,
  promoItem: item.promoItem,
}

vueHelper.init("vs-main-nav-list-item", VsMainNavListItem, props)

describe("Main Nav List Item component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a list element", () => {
        cy.get(".vs-main-nav__list-item").should("be.visible")
      })

      it("should render a button if an item has a submenu, promoItems or promoLists", () => {
        cy.get(".vs-main-nav__button").should("be.visible")
      })

      it("button should contain an icon", () => {
        cy.get(".vs-main-nav__button")
          .find("svg")
          .should("be.visible")
      })

      it("clicking the button should toggle a submenu", () => {
        cy.get(".vs-main-nav__list").should("not.be.visible")
        cy.get(".vs-main-nav__button").click()
        cy.get(".vs-main-nav__list").should("be.visible")
      })

      it("clicking the button should toggle the button aria-expanded state", () => {
        cy.get(".vs-main-nav__button")
          .should("not.have.attr", "aria-expanded", "true")
          .click()
          .should("have.attr", "aria-expanded", "true")
      })

      it("a button that opens a submenu should have an aria-haspopup property", () => {
        cy.get(".vs-main-nav__button")
          .should("have.attr", "aria-haspopup", "true")
          .click()
          .should("have.attr", "aria-expanded", "true")
      })

      it("the nav levels should rendered within the classes in each dom element", () => {
        cy.get(".vs-main-nav__list-item--level1").should("be.visible")
        cy.get(".vs-main-nav__button--level1")
          .should("be.visible")
          .click()
        cy.get(".vs-main-nav__list--level2").should("be.visible")
        cy.get(".vs-main-nav__list-item--level2").should("be.visible")
        cy.get(".vs-main-nav__button--level2")
          .should("be.visible")
          .first()
          .click()
        cy.get(".vs-main-nav__list--level3").should("be.visible")
        cy.get(".vs-main-nav__list-item--level3").should("be.visible")
        cy.get(".vs-main-nav__link--level3").should("be.visible")
      })
    })
  })
})
