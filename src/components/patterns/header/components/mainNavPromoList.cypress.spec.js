const VsMainNavPromoList = require("./MainNavPromoList.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const mainNav = require("../../../../assets/fixtures/mainNav.json")
const promoList = mainNav[4].promoList

const props = {
  list: promoList,
}

vueHelper.init("vs-main-nav-promo-list", VsMainNavPromoList, props)

describe("Main Nav Promo List component", () => {
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
        cy.get(".vs-promo__list").should("be.visible")
      })

      it("should render list items", () => {
        cy.get(".vs-promo__list-item")
          .should("be.visible")
          .should("have.length", "5")
      })
    })
  })
})
