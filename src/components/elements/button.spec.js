const mountVue = require("cypress-vue-unit-test")
const VsButton = require("./Button.vue")

const template = `
    <div id="app">
        <vs-button variant="success">hello</vs-button>
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
    cy.contains("button", "hello")
  })
})
