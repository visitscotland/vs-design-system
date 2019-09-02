const VsSkipToSearch = require("./SkipToSearch.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

props = {
  title: "Skip to Search",
}

vueHelper.init("vs-skip-to-search", VsSkipToSearch, props)

describe("Skip to Search component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a button", () => {
        cy.get("button")
          .should("be.visible")
          .should("have.class", "sr-only sr-only-focusable vs-skip-to")
      })

      it("button should render text", () => {
        cy.get("button").should("contain", "Skip to Search")
      })

      it("button show when it has focus", () => {
        cy.get(".vs-skip-to")
          .focus()
          .find("svg")
          .should("be.visible")
      })
    })
  })
})
