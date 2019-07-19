const VsMainNavPromoItem = require("./MainNavPromoItem.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

const props = {
  item: {
    title: "Unusual Accommodation",
    href: "https://www.visitscotland.com/accommodation/unusual-places-to-stay/",
    isExternal: false,
    isActive: false,
    buttonText: "Read more",
    description: "Why not stay in a castle, a lighthouse or on a working farm?",
    imageLink:
      "https://cimg.visitscotland.com/cms-images/accommodation/accommodation-new/glamping-domes-sauchope-holiday-park?size=xs",
  },
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
        cy.get(".vs-promo-panel__link").should("be.visible")
      })

      it("should render an href", () => {
        cy.get(".vs-promo-panel__link").should(
          "have.attr",
          "href",
          "https://www.visitscotland.com/accommodation/unusual-places-to-stay/"
        )
      })

      it("should render a title", () => {
        cy.contains("Unusual Accommodation").should("be.visible")
      })

      it("should render a promo image", () => {
        cy.get(".vs-promo-panel__image").should("be.visible")
        cy.get(".vs-promo-panel__image").should(
          "have.attr",
          "src",
          "https://cimg.visitscotland.com/cms-images/accommodation/accommodation-new/glamping-domes-sauchope-holiday-park?size=xs"
        )
      })

      it("should render alt text for the promo image that is the same as the title", () => {
        cy.get(".vs-promo-panel__image").should("have.attr", "alt", "Unusual Accommodation")
      })

      it("should render an icon", () => {
        cy.get("svg").should("be.visible")
      })
    })
  })
})
