const VsDropdown = require("./Dropdown.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

const props = {
  name: "Our Sites",
  dropdownList: [
    {
      title: "Visit Scotland",
      href: "https://www.visitscotland.com",
      isExternal: false,
      isActive: false,
      trackingID: 1,
    },
    {
      title: "Business Events",
      href: "https://businessevents.visitscotland.com",
      isExternal: true,
      isActive: false,
      trackingID: 1,
    },
    {
      title: "Travel Trade",
      href: "https://traveltrade.visitscotland.org",
      isExternal: true,
      isActive: false,
      trackingID: 1,
    },
    {
      title: "Media Centre",
      href: "http://mediacentre.visitscotland.org",
      isExternal: true,
      isActive: false,
      trackingID: 1,
    },
    {
      title: "Corporate",
      href: "https://www.visitscotland.org",
      isExternal: true,
      isActive: false,
      trackingID: 1,
    },
  ],
}

vueHelper.init("vs-dropdown", VsDropdown, props)

describe("Dropdown component", () => {
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
        cy.get(".vs-dropdown").should("be.visible")
      })

      it("should render a nav with an aria-label containing the name prop", () => {
        cy.get('nav[aria-label="Our Sites"]').should("be.visible")
      })

      it("should render button containing text with the name prop", () => {
        cy.contains("Our Sites").should("be.visible")
      })

      it("button should be accompanied by screen reader text", () => {
        cy.contains("Toggle submenu for Our Sites").should("be.visible")
        cy.get(".sr-only").should("be.visible")
      })

      it("should display an svg icon", () => {
        cy.get("svg").should("be.visible")
      })

      it("should open and close a dropdown menu", () => {
        cy.get(".vs-dropdown__list").should("not.be.visible")
        cy.get(".vs-dropdown__button").click()
        cy.get(".vs-dropdown__list").should("be.visible")
        cy.get(".vs-dropdown__button").click()
        cy.get(".vs-dropdown__list").should("not.be.visible")
      })

      it("internal links should not display an external link icon", () => {
        cy.get(".vs-dropdown__button").click()
        cy.get(".vs-dropdown__list-item")
          .first()
          .find(".vs-dropdown__external-icon-wrapper")
          .should("not.be.visible")
      })

      it("External links should display an external link icon", () => {
        cy.get(".vs-dropdown__button").click()
        cy.get(".vs-dropdown__list-item")
          .last()
          .find(".vs-dropdown__external-icon-wrapper")
          .should("be.visible")
      })
    })
  })
})
