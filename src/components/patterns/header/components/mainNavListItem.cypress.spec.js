const VsMainNavListItem = require("./MainNavListItem.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

const props = {
  level: 1,
  item: {
    title: "Places to stay",
    href: "https://www.visitscotland.com/accommodation",
    isExternal: false,
    isActive: false,
    trackingID: 1,
    subnav: [
      {
        title: "Accommodation Types",
        href: null,
        trackingID: null,
        subnav: [
          {
            title: "Accessible accommodation",
            href: "https://www.visitscotland.com/accommodation/accessible",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "B&Bs and Guesthouses",
            href: "https://www.visitscotland.com/accommodation/bandbs-guesthouses",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Cabins and lodges",
            href: "https://www.visitscotland.com/accommodation/cabins-lodges-chalets",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Caravan holidays and camping in Scotland",
            href: "https://www.visitscotland.com/accommodation/caravan-camping",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Eco accommodation",
            href: "https://www.visitscotland.com/accommodation/eco-green",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Exclusive use venues",
            href: "https://www.visitscotland.com/accommodation/exclusive-use-venues",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Glamping",
            href: "https://www.visitscotland.com/accommodation/caravan-camping/glamping",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Hostels",
            href: "https://www.visitscotland.com/accommodation/hostels",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Hotels",
            href: "https://www.visitscotland.com/accommodation/hotels",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Restaurants with rooms and inns",
            href: "https://www.visitscotland.com/accommodation/inns-restaurants-with-rooms",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Quality assurance",
            href: "https://www.visitscotland.com/accommodation/quality-assurance",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Self-catering",
            href: "https://www.visitscotland.com/accommodation/self-catering",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Unusual places to stay",
            href: "https://www.visitscotland.com/accommodation/unusual-places-to-stay",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Wedding venues",
            href: "https://www.visitscotland.com/accommodation/wedding-venues",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
        ],
      },
      {
        title: "Top Accommodation Searches",
        href: null,
        isExternal: false,
        isActive: false,
        trackingID: null,
        subnav: [
          {
            title: "Aberdeen",
            href: "https://www.visitscotland.com/destinations-maps/aberdeen/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Aviemore",
            href: "https://www.visitscotland.com/destinations-maps/aviemore/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Arran",
            href: "https://www.visitscotland.com/destinations-maps/arran/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Edinburgh",
            href: "https://www.visitscotland.com/destinations-maps/edinburgh/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Fort William",
            href: "https://www.visitscotland.com/destinations-maps/fort-william/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Glasgow",
            href: "https://www.visitscotland.com/destinations-maps/glasgow/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Inverness",
            href: "https://www.visitscotland.com/destinations-maps/inverness/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Loch Lomond",
            href: "https://www.visitscotland.com/destinations-maps/loch-lomond/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "Oban",
            href: "https://www.visitscotland.com/destinations-maps/oban/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
          {
            title: "St Andrews",
            href: "https://www.visitscotland.com/destinations-maps/st-andrews/accommodation",
            isExternal: false,
            isActive: false,
            trackingID: 1,
          },
        ],
      },
    ],
    promoItem: {
      title: "Unusual Accommodation",
      href: "https://www.visitscotland.com/accommodation/unusual-places-to-stay/",
      isExternal: false,
      isActive: false,
      buttonText: "Read more",
      description: "Why not stay in a castle, a lighthouse or on a working farm?",
      imageLink:
        "https://cimg.visitscotland.com/cms-images/accommodation/accommodation-new/glamping-domes-sauchope-holiday-park?size=xs",
    },
  },
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
        cy.get(".vs-main-nav__button--level1").should("be.visible")
        cy.get(".vs-main-nav__button--level1").click()
        cy.get(".vs-main-nav__list--level2").should("be.visible")
        cy.get(".vs-main-nav__list-item--level2").should("be.visible")
        cy.get(".vs-main-nav__button--level2").should("be.visible")
        cy.get(".vs-main-nav__button--level2")
          .first()
          .click()
        cy.get(".vs-main-nav__list--level3").should("be.visible")
        cy.get(".vs-main-nav__list-item--level3").should("be.visible")
        cy.get(".vs-main-nav__link--level3").should("be.visible")
      })
    })
  })
})
