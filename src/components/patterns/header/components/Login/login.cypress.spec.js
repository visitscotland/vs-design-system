const VsLogin = require("./Login.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

vueHelper.init("vs-login", VsLogin)

describe("Login component", () => {
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
        cy.get(".vs-login").should("be.visible")
      })

      it("should render an svg icon", () => {
        cy.get("svg").should("be.visible")
      })

      it("should render login text", () => {
        cy.contains("Login").should("be.visible")
      })

      it("clicking the login button should render logout text", () => {
        cy.contains("Log out").should("not.be.visible")
        cy.get(".vs-login__button").click()
        cy.contains("Log out").should("be.visible")
      })
    })
  })
})
