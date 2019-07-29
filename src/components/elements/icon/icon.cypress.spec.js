const VsIcon = require("./Icon.vue").default
const { vueHelper, getToken } = require("@cypress/helpers/index.js").default
const { each } = require("lodash")

describe("Icon component", () => {
  let props = { size: "md", name: "favourite", reverse: null, variant: null }
  let allSizes = {
    "x-small": "xs",
    small: "sm",
    medium: "md",
    large: "lg",
    "x-large": "xl",
  }
  let paths = {
    favourite: `M19.93,4.005a5.884,5.884,0,0,0-7.777,1.117A5.873,5.873,0,0,0,4.376,4.005,5.555,5.555,0,0,0,2,8.361c-.142,3.939,3.35,7.1,8.681,11.94l.1.091a2.026,2.026,0,0,0,2.731-.01l.112-.1c5.33-4.833,8.813-7.99,8.681-11.929A5.552,5.552,0,0,0,19.93,4.005ZM12.254,18.787l-.1.1-.1-.1c-4.833-4.376-8.021-7.269-8.021-10.2A3.47,3.47,0,0,1,7.584,5.031a3.969,3.969,0,0,1,3.625,2.4h1.9a3.944,3.944,0,0,1,3.614-2.4,3.47,3.47,0,0,1,3.553,3.553C20.275,11.518,17.087,14.412,12.254,18.787Z`,
    search: `M18.8,17.064l-4.459-4.637a7.561,7.561,0,1,0-5.789,2.7,7.483,7.483,0,0,0,4.334-1.369l4.493,4.673A.987.987,0,1,0,18.8,17.064ZM8.548,1.973A5.591,5.591,0,1,1,2.957,7.564,5.6,5.6,0,0,1,8.548,1.973Z`,
  }

  let primaryColour = getToken("color_theme_primary")
  let secondaryColour = getToken("color_theme_secondary")
  let whiteColour = getToken("color_white")

  vueHelper.init("vs-icon", VsIcon, props)

  it("renders the default favourite icon with the appropriate class", () => {
    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-favourite")
  })

  it("renders the default favourite icon with the correct contents", () => {
    cy.get("svg>path").should("have.attr", "d", paths.favourite)
  })

  each(allSizes, (size, name) => {
    it("renders the favourite icon at " + name + " size", () => {
      let sizeValue = getToken("icon_size_" + size)
      let sizeInt = parseInt(sizeValue)
      let padding = sizeInt / 4
      let dimension = sizeInt + padding * 2

      vueHelper.setProp("size", size)

      cy.get("svg")
        .should("have.class", "icon-" + size)
        .should("have.css", "height", dimension + "px")
        .should("have.css", "width", dimension + "px")
        .should("have.css", "padding", padding + "px")
    })
  })

  it("renders the default search icon", () => {
    vueHelper.setProp("name", "search")

    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-search")
      .get("path")
      .should("have.attr", "d", paths.search)
  })

  it("renders the favourite icon with a primary theme", () => {
    vueHelper.setProp("variant", "primary")

    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-favourite")
      .should("have.css", "fill", primaryColour)
  })

  it("renders the search icon in reverse", () => {
    let blackColour = getToken("color_black")

    vueHelper.setProp("name", "search")
    vueHelper.setProp("reverse", true)

    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-search")
      .should("have.class", "icon-reverse")
      .should("have.css", "fill", whiteColour)
      .should("have.css", "background-color", blackColour)
  })

  it("renders the secondary variant of the favourite icon in reverse", () => {
    vueHelper.setProp("reverse", true)
    vueHelper.setProp("variant", "secondary")

    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-favourite")
      .should("have.class", "icon-reverse")
      .should("have.css", "fill", whiteColour)
      .should("have.css", "background-color", secondaryColour)
  })
})
