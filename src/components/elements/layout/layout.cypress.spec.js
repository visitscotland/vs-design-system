const VsContainer = require("./Container.vue").default
const { vueHelper, getToken } = require("@cypress/helpers/index.js").default
const { each } = require("lodash")

describe("Container component", () => {
  let props = { fluid: null }
  let mockContents = "test contents"

  let breakpoints = {
    sm: getToken("breakpoint_sm"),
    md: getToken("breakpoint_md"),
    lg: getToken("breakpoint_lg"),
    xl: getToken("breakpoint_xl"),
    xxl: getToken("breakpoint_xxl"),
  }

  let maxWidths = {
    sm: getToken("max_container_width_sm"),
    md: getToken("max_container_width_md"),
    lg: getToken("max_container_width_lg"),
    xl: getToken("max_container_width_xl"),
    xxl: getToken("max_container_width_xxl"),
  }

  vueHelper.init("vs-container", VsContainer, props, mockContents)

  it("wraps the contents in a static container", () => {
    cy.contains("div", mockContents).should("have.class", "container")
  })

  it("should render a fixed width container below sm breakpoint", () => {
    cy.viewport(500, 700)

    cy.document(doc => {
      debugger
    })

    cy.contains("div", mockContents).should("have.css", "width", maxWidths.sm)
  })

  // it("renders the default favourite icon with the correct contents", () => {
  //   cy.get("svg>path").should("have.attr", "d", paths.favourite)
  // })
})
