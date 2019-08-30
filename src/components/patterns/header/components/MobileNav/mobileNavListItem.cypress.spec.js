const VsMobileNavListItem = require("./MobileNavListItem.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const mainNav = require("../../../../../assets/fixtures/mainNav.json")
const item = mainNav[0]
const level2 = item.subnav[0]
const level3 = level2.subnav[0]

const props = {
  level: 1,
  href: item.href,
  isExternal: item.isExternal,
  title: item.title,
  subnav: item.subnav,
  promoList: item.promoList,
  promoItem: item.promoItem,
}

const content = `
  <vs-mobile-nav-list-item
    slot="subnav"
    level=2
    href="${level2.href}"
    subnav="${level2.subnav}"
    is-external="${level2.isExternal}"
    title="${level2.title}"
  >
    <vs-mobile-nav-list-item
      slot="subnav"
      level=3
      href="${level3.href}"
      subnav="${level3.subnav}"
      is-external="${level3.isExternal}"
      title="${level3.title}"
    >
    </vs-mobile-nav-list-item>
  </vs-mobile-nav-list-item>
`
vueHelper.init("vs-mobile-nav-list-item", VsMobileNavListItem, props, content)

describe("Mobile Nav List Item component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a button if an item has a submenu, promoItems or promoLists", () => {
        cy.get(".vs-mobile-nav__button").should("be.visible")
      })

      it("button should contain an icon", () => {
        cy.get(".vs-mobile-nav__button")
          .find("svg")
          .should("be.visible")
      })

      it("clicking the button should toggle a submenu", () => {
        cy.get("ul").should("not.be.visible")
        cy.get(".vs-mobile-nav__button")
          .first()
          .click()
        cy.get("ul").should("be.visible")
      })

      it("clicking the button should toggle the button aria-expanded state", () => {
        cy.get(".vs-mobile-nav__button")
          .first()
          .should("not.have.attr", "aria-expanded", "true")
          .click()
          .should("have.attr", "aria-expanded", "true")
      })

      it("a button that opens a submenu should have an aria-haspopup property", () => {
        cy.get(".vs-mobile-nav__button")
          .first()
          .should("have.attr", "aria-haspopup", "true")
          .click()
          .should("have.attr", "aria-expanded", "true")
      })

      it("the nav levels should rendered within the classes in each dom element", () => {
        cy.get(".vs-mobile-nav__button--level1")
          .first()
          .should("be.visible")
          .click()
        cy.get(".vs-mobile-nav__list-item--level2")
          .first()
          .should("be.visible")
        cy.get(".vs-mobile-nav__button--level2")
          .first()
          .should("be.visible")
          .first()
          .click()
        cy.get(".vs-mobile-nav__list-item--level3").should("be.visible")
      })
    })
  })
})
