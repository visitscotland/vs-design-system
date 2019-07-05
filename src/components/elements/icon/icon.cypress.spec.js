const VsIcon = require("./Icon.vue").default
const { vueHelper, getToken } = require("@cypress/helpers/index.js").default
const { each } = require("lodash")

describe("Icon component", () => {
  let props = { size: "md", name: "favourite" }
  let sizeMd = getToken("icon_size_md")
  let nonDefaultSizes = {
    "x-small": "xs",
    small: "sm",
    large: "lg",
    "x-large": "xl",
  }
  let paths = {
    favourite: `M28.457,4.506A8.591,8.591,0,0,0,16.982,6.179,8.575,8.575,0,0,0,5.507,4.506,8.356,8.356,0,0,0,2,11.031c-.21,5.9,4.944,10.632,12.808,17.887l.15.137a2.957,2.957,0,0,0,4.03-.015l.165-.152c7.865-7.24,13-11.971,12.808-17.872A8.351,8.351,0,0,0,28.457,4.506ZM17.132,26.652l-.15.152-.15-.152C9.7,20.1,5,15.762,5,11.366a5.16,5.16,0,0,1,5.243-5.324,5.856,5.856,0,0,1,5.348,3.59h2.8a5.819,5.819,0,0,1,5.333-3.59,5.16,5.16,0,0,1,5.243,5.324C28.967,15.762,24.263,20.1,17.132,26.652Z`,
    search: `M26.608,24.548l-6.414-6.671a10.877,10.877,0,1,0-8.329,3.886A10.765,10.765,0,0,0,18.1,19.794l6.463,6.722a1.419,1.419,0,1,0,2.046-1.968ZM11.865,2.839a8.043,8.043,0,1,1-8.043,8.043A8.052,8.052,0,0,1,11.865,2.839Z`,
  }

  vueHelper.init("vs-icon", VsIcon, props)

  it("renders the default favourite icon with the appropriate class", () => {
    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-favourite")
  })

  it("renders the default favourite icon with the appropriate size", () => {
    cy.get("svg").should("have.attr", "height", sizeMd)
  })

  it("renders the default favourite icon with the correct contents", () => {
    cy.get("svg>path").should("have.attr", "d", paths.favourite)
  })

  each(nonDefaultSizes, (size, name) => {
    it("renders the favourite icon at " + name + " size", () => {
      let sizeValue = getToken("icon_size_" + size)

      vueHelper.setProp("size", size)

      cy.get("svg")
        .should("have.class", "icon")
        .should("have.attr", "height", sizeValue)
    })
  })

  it("renders the default search icon", () => {
    vueHelper.setProp("name", "search")

    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-search")
      .should("have.attr", "height", sizeMd)
      .get("path")
      .should("have.attr", "d", paths.search)
  })

  it("renders the favourite icon with a primary theme", () => {
    let primaryColour = getToken("color_theme_primary")

    vueHelper.setProp("theme", "primary")

    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-favourite")
      .should("have.css", "fill", primaryColour)
  })

  it("renders the search reverse variant icon", () => {
    let whiteColour = getToken("color_theme_white")
    let blackColour = getToken("color_theme_black")

    vueHelper.setProp("name", "search")

    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-search")
      .should("have.css", "fill", whiteColour)
      .should("have.css", "background-color", blackColour)
      .should("have.css", "border-radius", "20px")
  })
})
