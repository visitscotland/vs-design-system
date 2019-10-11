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
    favourite: `M18.206,3.961A5.118,5.118,0,0,0,11.176,5.03,5.109,5.109,0,0,0,4.147,3.961,5.4,5.4,0,0,0,2,8.128C1.871,11.9,5.028,14.916,9.846,19.549l.092.087a1.758,1.758,0,0,0,2.469-.01l.1-.1c4.818-4.623,7.965-7.643,7.846-11.411A5.4,5.4,0,0,0,18.206,3.961ZM11.268,18.1l-.092.1-.092-.1c-4.368-4.186-7.25-6.954-7.25-9.76a3.231,3.231,0,0,1,3.212-3.4,3.59,3.59,0,0,1,3.276,2.292h1.716a3.568,3.568,0,0,1,3.267-2.292,3.231,3.231,0,0,1,3.212,3.4C18.518,11.148,15.636,13.916,11.268,18.1Z`,
    search: `M29.1513,28.0775l-4.459-4.637a7.5609,7.5609,0,1,0-5.789,2.7,7.4836,7.4836,0,0,0,4.334-1.369l4.493,4.673a.987.987,0,0,0,1.4858-1.3Q29.1854,28.11,29.1513,28.0775Zm-10.252-15.091a5.591,5.591,0,1,1-5.591,5.591A5.591,5.591,0,0,1,18.8993,12.9865Z`,
  }

  let primaryColour = getToken("color_theme_primary")
  let secondaryColour = getToken("color_theme_secondary")
  let whiteColour = getToken("color_white")

  vueHelper.init("vs-icon", VsIcon, { props })

  it("renders the default favourite icon with the appropriate class", () => {
    cy.get("svg")
      .should("have.class", "icon")
      .should("have.class", "icon-md")
      .should("have.class", "icon-favourite")
  })

  it("renders the default favourite icon with the correct contents", () => {
    cy.get("svg")
      .find("path")
      .should("have.attr", "d", paths.favourite)
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
