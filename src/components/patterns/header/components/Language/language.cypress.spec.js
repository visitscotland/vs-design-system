const VsLanguage = require("./Language.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const languages = require("../../../../../assets/fixtures/languages.json")

const props = {
  name: "Languages",
  dropdownList: languages,
}

vueHelper.init("vs-language", VsLanguage, props)

describe("Language component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a nav with an aria-label containing the name prop", () => {
        cy.get('nav[aria-label="Languages"]').should("be.visible")
      })

      it("should render a dropdown button", () => {
        cy.get(".vs-dropdown__button")
          .should("be.visible")
          .should("have.attr", "aria-expanded", "false")
      })

      it("button should display screenreader text", () => {
        cy.get(".sr-only").should("contain", "Currently selected language is English")
      })

      it("button should contain an icon wrapper", () => {
        cy.get(".vs-dropdown__icon-wrapper").should("be.visible")
      })

      it("button should have a chevron down icon", () => {
        cy.get(".icon-chevron-down").should("be.visible")
      })

      it("should display a list when expanded", () => {
        cy.get(".vs-dropdown__button")
          .click()
          .should("have.attr", "aria-expanded", "true")

        cy.get(".vs-dropdown__link")
          .should("be.visible")
          .should("have.length", "6")
      })
    })
  })
})
