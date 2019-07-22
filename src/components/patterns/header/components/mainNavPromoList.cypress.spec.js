const VsMainNavPromoList = require("./MainNavPromoList.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

const props = {
  list: [
    {
      title: "Romantic",
      href: "https://www.visitscotland.com/inspiration/romantic",
      isExternal: false,
      isActive: false,
      trackingID: 1,
      imageLink:
        "https://cimg.visitscotland.com/cms-images/about/couple-beach-south-queensferry-walk?size=sm",
    },
    {
      title: "Island hopping",
      href: "https://www.visitscotland.com/inpiration/island-hopping",
      isExternal: false,
      isActive: false,
      trackingID: 1,
      imageLink:
        "https://cimg.visitscotland.com/cms-images/destinations/argyll-and-the-isles/kiloran-bay?size=sm",
    },
    {
      title: "Family",
      href: "https://www.visitscotland.com/inpiration/family",
      isExternal: false,
      isActive: false,
      trackingID: 1,
      imageLink:
        "https://cimg.visitscotland.com/cms-images/itineraries/loch-insh-family-paddleboarding?size=sm",
    },
    {
      title: "Farm stays",
      href: "https://www.visitscotland.com/inpiration/farm-stays",
      isExternal: false,
      isActive: false,
      trackingID: 1,
      imageLink:
        "https://cimg.visitscotland.com/cms-images/attractions/cattle-grazing-cliffs-westray?size=md",
    },
    {
      title: "Wellness",
      href: "https://www.visitscotland.com/inpiration/wellness",
      isExternal: false,
      isActive: false,
      trackingID: 1,
      imageLink:
        "https://cimg.visitscotland.com/cms-images/holidays/outdoor-spa-gleneagles?size=md",
    },
  ],
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

      it("should render a link element", () => {
        cy.get(".vs-promo__list").should("be.visible")
      })
    })
  })
})
