const VsSearch = require("./Search.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

vueHelper.init("vs-search", VsSearch)

describe("Search component", () => {
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
        cy.get(".vs-search").should("be.visible")
      })

      it("button should contain an svg icon", () => {
        cy.get(".vs-search__button")
          .find("svg")
          .should("be.visible")
      })

      it("should render a search button", () => {
        cy.get(".vs-search__button").should("be.visible")
      })

      it("button should be accompanied by screen reader text", () => {
        cy.contains("Toggle Search").should("be.visible")
      })

      it("search form area should toggle when search button is click", () => {
        cy.get(".vs-search__form-wrapper").should("not.be.visible")
        cy.get("[data-test-trigger]").click()
        cy.get(".vs-search__form-wrapper").should("be.visible")
        cy.get("[data-test-trigger]").click()
      })

      it("should toggle aria-expanded property on the button", () => {
        cy.get("[data-test-trigger]")
          .should("have.attr", "aria-expanded", "false")
          .click()
          .should("have.attr", "aria-expanded", "true")
      })

      context("When the search form is expanded", () => {
        beforeEach(() => {
          cy.get("[data-test-trigger]").click()
        })

        it("should render a search form", () => {
          cy.get(".vs-search__form").should("be.visible")
        })

        it("search form should have a role of 'search'", () => {
          cy.get(".vs-search__form").should("have.attr", "role", "search")
        })

        it("should render a search label", () => {
          cy.get(".vs-search__label").should("be.visible")
        })

        it("should label should have a for attribute of 'search'", () => {
          cy.get(".vs-search__label").should("be.visible")
        })

        it("should label should contain an svg icon", () => {
          cy.get(".vs-search__label")
            .find("svg")
            .should("be.visible")
        })

        it("should render a search input", () => {
          cy.get(".vs-search__input").should("be.visible")
        })

        it("search input should have a type of 'search'", () => {
          cy.get(".vs-search__input").should("have.attr", "type", "search")
        })

        it("search input should have a placeholder of 'search'", () => {
          cy.get(".vs-search__input").should("have.attr", "placeholder", "Enter a search term")
        })

        it("search input should have an id of 'search' to match the label for attribute", () => {
          cy.get(".vs-search__input").should("have.attr", "id", "search")
        })

        it("search are should display a clear search button when any text is entered", () => {
          cy.get(".vs-search__clear-button").should("not.be.visible")
          cy.get(".vs-search__input").type("testing the search")
          cy.get(".vs-search__clear-button").should("be.visible")
        })

        it("should render a submit button", () => {
          cy.get(".vs-search__submit-button").should("be.visible")
        })

        it("submit button should have a type of 'submit'", () => {
          cy.get(".vs-search__submit-button").should("have.attr", "type", "submit")
        })

        context("Clear search function", () => {
          beforeEach(() => {
            cy.get(".vs-search__input").type("testing the search")
          })

          it("the clear search button should be accompanied by screen reader text", () => {
            cy.contains("Clear search").should("be.visible")
          })

          it("the clear search button should contain an icon", () => {
            cy.get(".vs-search__clear-button")
              .find("svg")
              .should("be.visible")
          })
        })
      })
    })
  })
})
