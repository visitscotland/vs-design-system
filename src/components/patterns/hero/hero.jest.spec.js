import { mount } from "@vue/test-utils"
import VsHero from "./Hero"

// Need to use full mount function as the component renders
// the Hero image and a caption div with Icon Component, if caption is present
const factoryMount = propsData => {
    return mount(VsHero, {
        propsData: {
            caption: "This is a test caption",
            credit: "Kenny Lam &copy; VisitScotland 2020",
            ...propsData,
        },
    })
}

describe("VsHero", () => {
    it("should render a container with a caption and credit when both are provided", () => {
        const wrapper = factoryMount()
        expect(wrapper.classes()).toContain("d-flex", "flex-column")
    })
    it("should render a container when no caption nor credit is provided", () => {
        const caption = null
        const credit = null
        const wrapper = factoryMount({ propsData: { caption: caption, credit: credit } })
        expect(wrapper.classes()).toContain("d-flex", "flex-column")
    })
    it("should render a container with a caption when credit is not provided", () => {
        const credit = null
        const wrapper = factoryMount({ propsData: { credit: credit } })
        expect(wrapper.classes()).toContain("d-flex", "flex-column")
    })
    it("should render a container with a credit only when caption is not provided", () => {
        const caption = null
        const wrapper = factoryMount({ propsData: { caption: caption } })
        expect(wrapper.classes()).toContain("d-flex", "flex-column")
    })
})
