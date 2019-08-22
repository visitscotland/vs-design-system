const VsMainNavPromoItem = require("./MainNavPromoItem.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const mainNav = require("../../../../assets/fixtures/mainNav.json")
const promoItem = mainNav[2].promoItem

const props = {
  href: promoItem.href,
  isExternal: promoItem.isExternal,
  title: promoItem.title,
  buttonText: promoItem.buttonText,
  description: promoItem.description,
  imageLink: promoItem.imageLink,
}

vueHelper.init("vs-main-nav-promo-item", VsMainNavPromoItem, props)

describe("Main Nav Promo Item component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a link element", () => {
        cy.get(".vs-promo-item__link").should("be.visible")
      })

      it("should render an href", () => {
        cy.get(".vs-promo-item__link").should(
          "have.attr",
          "href",
          "https://www.visitscotland.com/see-do/iconic-scotland/ben-nevis"
        )
      })

      it("should render a title", () => {
        cy.contains("Scotland's Tallest Peak").should("be.visible")
      })

      it("should render a promo image", () => {
        cy.get(".vs-promo-item__image")
          .should("be.visible")
          .should("have.attr", "src", "https://via.placeholder.com/360x200?text=Image+Placeholder")
      })

      it("should render alt text for the promo image that is the same as the title", () => {
        cy.get(".vs-promo-item__image").should("have.attr", "alt", "Scotland's Tallest Peak")
      })

      it("should render an icon", () => {
        cy.get("svg").should("be.visible")
      })
    })
  })
})
