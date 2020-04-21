const { vueHelper, getThemeColours } = require("@cypress/helpers/index.js").default
const { each } = require("lodash")
const VsButton = require("./Button.vue").default

describe("Button component", () => {
    const themeColours = getThemeColours()
    const props = {
        variant: null,
        type: null,
        href: null,
    }

    vueHelper.init("vs-button", VsButton, {
        props,
        childContent: "hello",
    })

    it("renders the contents", () => {
        cy.contains("button", "hello")
    })

    each(themeColours, (colourValue, colourName) => {
        it(`renders ${colourName} colour`, () => {
            vueHelper.setProp("variant", colourName)

            cy.get("button").should("have.css", "background-color", colourValue)
        })
    })

    it("renders an anchor", () => {
        const url = "http://www.visitscotland.com"

        vueHelper.setProp("href", url)

        cy.contains("a", "hello")

        cy.get("a")
            .should("have.attr", "href", url)
            .should("have.css", "background-color", themeColours.primary)
    })
})
