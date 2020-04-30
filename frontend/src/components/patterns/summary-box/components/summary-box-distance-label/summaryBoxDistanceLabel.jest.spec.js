import { shallowMount, mount } from "@vue/test-utils"

import VsSummaryBoxDistanceLabel from "./SummaryBoxDistanceLabel"

const handleClick = jest.fn()

const factoryShallowMount = (propsData) => shallowMount(VsSummaryBoxDistanceLabel, {
    propsData: {
        ...propsData,
    },
})

const factoryMount = (propsData) => mount(VsSummaryBoxDistanceLabel, {
    propsData: {
        ...propsData,
    },
    methods: {
        handleClick,
    },
})

describe("VsSummaryBoxDistanceLabel", () => {
    it("should render a wrapper", () => {
        const wrapper = factoryShallowMount()
        expect(wrapper.html()).toContain(
            "<div class=\"vs-summary-box-distance-label text-center d-block position-absolute w-100\">",
        )
    })

    it("should accept an distanceLabel property", () => {
        const wrapper = factoryShallowMount({
            distanceLabel: "Entfernung",
        })
        expect(wrapper.html()).toContain("<strong class=\"d-block\">Entfernung</strong>")
    })

    it("should accept an milesLabel property", () => {
        const wrapper = factoryShallowMount({
            milesLabel: "meilen",
        })
        expect(wrapper.html()).toContain(
            "<vs-button-stub variant=\"transparent\" size=\"md\" animate=\"true\" aria-expanded=\"true\" aria-controls=\"display_miles\" class=\"active\"><abbr title=\"meilen\">mi</abbr></vs-button-stub>",
        )
    })

    it("should accept an milesAbbr property", () => {
        const wrapper = factoryShallowMount({
            milesAbbr: "test-abbreviation",
        })
        expect(wrapper.html()).toContain("<abbr title=\"miles\">test-abbreviation</abbr>")
    })

    it("should accept an kilometresLabel property", () => {
        const wrapper = factoryShallowMount({
            kilometresLabel: "kilomètres",
        })
        expect(wrapper.html()).toContain(
            "<vs-button-stub variant=\"transparent\" size=\"md\" animate=\"true\" aria-expanded=\"false\" aria-controls=\"display_kilometres\" class=\"\"><abbr title=\"kilomètres\">km</abbr></vs-button-stub>",
        )
    })

    it("should accept an kilometresAbbr property", () => {
        const wrapper = factoryShallowMount({
            kilometresAbbr: "booya!",
        })
        expect(wrapper.html()).toContain("<abbr title=\"kilometers\">booya!</abbr>")
    })

    it("should call the handleClick method on click of miles button", () => {
        const wrapper = factoryMount()
        wrapper.find("[aria-controls=\"display_miles\"]").trigger("click")
        expect(handleClick).toBeCalled()
        expect(handleClick).toHaveBeenCalledWith(true)
    })

    it("should call the handleClick method on click of kilometres button", () => {
        const wrapper = factoryMount()
        wrapper.find("[aria-controls=\"display_kilometres\"]").trigger("click")
        expect(handleClick).toBeCalled()
        expect(handleClick).toHaveBeenCalledWith(false)
    })
})
