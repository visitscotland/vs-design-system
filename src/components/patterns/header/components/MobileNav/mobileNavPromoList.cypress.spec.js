const VsMobileNavPromoList = require("./MobileNavPromoList.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const mainNav = require("../../../../../assets/fixtures/mainNav.json")
const promoList = mainNav[4].promoList

const props = {
  list: promoList,
}

vueHelper.init("vs-mobile-nav-promo-list", VsMobileNavPromoList, props)

describe("Mobile Nav Promo List component", () => {
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
        cy.get("ul").should("be.visible")
      })

      it("should render list items", () => {
        cy.get("li")
          .should("be.visible")
          .should("have.length", "5")
      })

      it("should only display the image of the last promo item", () => {
        cy.get("img").should("have.length", "1")
        cy.get("li")
          .last()
          .find("img")
          .should("be.visible")
      })
    })
  })
})
