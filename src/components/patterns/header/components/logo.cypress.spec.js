const VsLogo = require("./Logo.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

vueHelper.init("vs-logo", VsLogo)

describe("Logo component", () => {
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
        cy.get(".vs-logo").should("be.visible")
      })

      it("should render an svg icon", () => {
        cy.get("svg").should("be.visible")
      })
    })
  })
})
