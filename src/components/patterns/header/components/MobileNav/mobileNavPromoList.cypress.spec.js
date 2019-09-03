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
        cy.get('[data-test="mobile-promo-list"]').should("be.visible")
      })

      it("should render only the last list items as a promo", () => {
        cy.get('[data-test="mobile-promo-list-item"]')
          .should("be.visible")
          .should("have.length", "1")
        cy.get('[data-test="mobile-promo-image"]').should("have.length", "1")
        cy.get("li")
          .first()
          .find('[data-test="mobile-promo-image"]')
          .should("not.be.visible")
        cy.get("li")
          .last()
          .find('[data-test="mobile-promo-image"]')
          .should("be.visible")
      })

      it("the rest of the list items should render as regular nav items", () => {
        cy.get('[data-test="mobile-nav-list-item"]')
          .should("be.visible")
          .should("have.length", "4")
      })
    })
  })
})
