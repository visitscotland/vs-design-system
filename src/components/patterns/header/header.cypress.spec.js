const VsHeader = require("./Header.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

vueHelper.init("vs-header", VsHeader)

describe("Header component", () => {
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
        cy.get(".vs-header").should("be.visible")
      })

      it("should render two dropdown components", () => {
        cy.get(".vs-dropdown")
          .should("be.visible")
          .should("have.length", "2")
      })

      it("should render a login component", () => {
        cy.get(".vs-login__button").should("be.visible")
      })

      it("should render a logo component", () => {
        cy.get(".vs-logo").should("be.visible")
      })

      it("should render a search component", () => {
        cy.get(".vs-search").should("be.visible")
      })

      it("should render a favourites component", () => {
        cy.get(".vs-favourites").should("be.visible")
      })

      it("should render a main navigation component", () => {
        cy.get(".vs-main-nav").should("be.visible")
      })

      it("If a component is open, it should close before another component is triggered", () => {
        cy.get(".vs-search__button").click()
        cy.get(".vs-search__form-wrapper").should("be.visible")
        cy.get(".vs-favourites__button").click()
        cy.get(".vs-search__form-wrapper").should("not.be.visible")
      })
    })
  })
})
