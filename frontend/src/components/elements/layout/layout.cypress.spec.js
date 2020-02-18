const VsContainer = require("./Container.vue").default
const { vueHelper, getToken } = require("@cypress/helpers/index.js").default

const mockContents = "test contents"

const props = { fluid: null }

const breakpoints = {
  sm: getToken("breakpoint_sm"),
  md: getToken("breakpoint_md"),
  lg: getToken("breakpoint_lg"),
  xl: getToken("breakpoint_xl"),
  xxl: getToken("breakpoint_xxl"),
}

const maxWidths = {
  sm: getToken("max_container_width_sm"),
  md: getToken("max_container_width_md"),
  lg: getToken("max_container_width_lg"),
  xl: getToken("max_container_width_xl"),
  xxl: getToken("max_container_width_xxl"),
}

vueHelper.init("vs-container", VsContainer, { props, childContent: mockContents })

describe("Container component", () => {
  beforeEach(() => {
    cy.document().then(doc => {
      doc.body.setAttribute("style", "margin:0")
    })
  })

  it("should render a fluid width container below sm breakpoint", () => {
    let viewportWidth = 450

    cy.viewport(viewportWidth, 700)

    cy.contains(".container", mockContents).should("have.css", "width", viewportWidth + "px")
  })

  Cypress._.each(breakpoints, (breakpoint, breakpointName) => {
    let fixedWidth = maxWidths[breakpointName]

    it(
      "should render a fixed " +
        fixedWidth +
        " width container above " +
        breakpointName +
        " breakpoint",
      () => {
        cy.viewport(pixelStringToInteger(breakpoint) + 100, 700)

        cy.contains(".container", mockContents).should("have.css", "width", fixedWidth)
      }
    )

    it("should render a fluid width container  above " + breakpointName + " breakpoint", () => {
      let viewportWidth = pixelStringToInteger(breakpoint) + 100

      cy.viewport(viewportWidth, 700)

      vueHelper.setProp("fluid", true)

      cy.contains(".container-fluid", mockContents).should(
        "have.css",
        "width",
        viewportWidth + "px"
      )
    })
  })
})

function pixelStringToInteger(pixelValue) {
  return parseInt(pixelValue)
}
