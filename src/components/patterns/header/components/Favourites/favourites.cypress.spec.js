const VsFavourites = require("./Favourites.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

vueHelper.init("vs-favourites", VsFavourites)

describe("Favourites component", () => {
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
        cy.get(".vs-favourites").should("be.visible")
      })

      it("should render an svg icon", () => {
        cy.get("svg").should("be.visible")
      })

      it("button should be accompanied by screen reader text", () => {
        cy.contains("Add to Favourites").should("be.visible")
      })

      it("screen reader text should update when button is click", () => {
        cy.get(".vs-favourites__button").click()
        cy.contains("Current favourites count: 1").should("be.visible")
        cy.get(".vs-favourites__button")
          .click()
          .click()
          .click()
        cy.contains("Current favourites count: 4").should("be.visible")
      })

      it("icon should switch to a filled icon when the button is clicked", () => {
        cy.get(".icon-favourite").should("be.visible")
        cy.get(".icon-favourite-filled").should("not.be.visible")
        cy.get(".vs-favourites__button").click()
        cy.get(".icon-favourite").should("not.be.visible")
        cy.get(".icon-favourite-filled").should("be.visible")
      })

      it("icon should render a favourites count when the button is clicked", () => {
        cy.get(".vs-favourites__count").should("not.be.visible")
        cy.get(".vs-favourites__button").click()
        cy.get(".vs-favourites__count").should("be.visible")
        cy.contains("1").should("be.visible")
      })

      it("icon should increment a favourites count when the button is clicked", () => {
        cy.get(".vs-favourites__button")
          .click()
          .click()
          .click()
        cy.contains("3").should("be.visible")
      })
    })
  })
})
