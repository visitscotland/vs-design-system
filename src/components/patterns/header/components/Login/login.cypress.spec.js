const VsLogin = require("./Login.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]

const props = {
  username: "Alexis",
}

vueHelper.init("vs-login", VsLogin, props)

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
        cy.get('[data-test="login-container"]').should("be.visible")
      })

      it("should render a button", () => {
        cy.get('[data-test="login-button"]').should("be.visible")
      })

      it("should render an svg icon", () => {
        cy.get('[data-test="login-user-icon"]').should("be.visible")
      })

      it("when logged out", () => {
        cy.get('[data-test="not-logged-in"]')
          .should("be.visible")
          .should("contain", "Login")
        cy.get('[data-test="logged-in-no-greeting"]').should("not.be.visible")
        cy.get('[data-test="logged-in-greeting"]').should("not.be.visible")
      })

      it("when logged in", () => {
        cy.get('[data-test="login-button"]').click()
        cy.get('[data-test="not-logged-in"]').should("not.be.visible")
        if (size === "iphone-6" || size === "ipad-2") {
          cy.get('[data-test="no-greeting"]')
            .should("be.visible")
            .should("contain", "Log out")
        } else {
          cy.get('[data-test="greeting"]')
            .should("be.visible")
            .should("contain", "Hi Alexis... (not you?)")
        }
      })
    })
  })
})
