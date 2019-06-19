const mountVue = require("cypress-vue-unit-test")
const VsButton = require("./Button.vue")
const { getToken } = require("@cypress/helpers/index.js").default

const template = `
    <div id="app">
        <vs-button variant="primary">hello</vs-button>
    </div>
  `

const data = {
  variant: "success",
}

const component = {
  template,
  data,
}

const extensions = {
  components: { "vs-button": VsButton.default },
}

describe("My Vue", () => {
  beforeEach(mountVue(component, { extensions }))
  it("renders", () => {
    const primaryColour = getToken("color_theme_primary")

    cy.contains("button", "hello")
    cy.get("button").should("have.css", "background-color", primaryColour)
  })
})
