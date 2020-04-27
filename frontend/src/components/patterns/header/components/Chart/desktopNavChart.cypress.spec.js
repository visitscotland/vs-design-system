const { vueHelper } = require("@cypress/helpers/index.js").default
const VsDesktopNavChart = require("./DesktopNavChart.vue").default

const sizes = ["iphone-6", "ipad-2", [1024, 768]]

const mainNav = require("@/assets/fixtures/header/mainNav.json")

const chart = mainNav[3].chartWidgets[0]

const props = {
    chartTitle: chart.chartTitle,
    labels: chart.labels,
    datasets: chart.datasets,
    options: chart.options,
    chartId: "chartId1",
}

vueHelper.init("vs-desktop-nav-chart", VsDesktopNavChart, {
    props,
})

describe("Desktop Nav Chart component", () => {
    sizes.forEach((size) => {
        context(`${size} resolution`, () => {
            beforeEach(() => {
                if (Cypress._.isArray(size)) {
                    cy.viewport(size[0], size[1])
                } else {
                    cy.viewport(size)
                }
            })

            it("should render a container element", () => {
                cy.get("[data-test=\"chart-container\"]").should("be.visible")
            })

            it("should render a chart title", () => {
                cy.get("[data-test=\"chart-header\"]")
                    .should("be.visible")
                    .should("have.text", "Average Temperatures")
            })

            it("should render a canvas element with a chartjs class", () => {
                cy.get("[data-test=\"chart-canvas\"]")
                    .should("be.visible")
                    .should("have.class", "chartjs-render-monitor")
            })
        })
    })
})
