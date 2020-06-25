import { shallowMount } from "@vue/test-utils"
import VsBreadcrumbItem from "./BreadcrumbItem"

const factoryShallowMount = (propsData) => shallowMount(VsBreadcrumbItem, {
    propsData: {
        ...propsData,
    },
})

describe("VsBreadcrumbItem", () => {
    it("should render a b-breadcrumb-item", () => {
        const wrapper = factoryShallowMount()
        expect(wrapper.is("b-breadcrumb-item-stub")).toBe(true)
        expect(wrapper.html()).toContain(
            "<b-breadcrumb-item-stub target=\"_self\" event=\"click\" routertag=\"a\" ariacurrent=\"page\" class=\"vs-breadcrumb-item\"></b-breadcrumb-item-stub>",
        )
    })

    it("should accept an active property", () => {
        const wrapper = factoryShallowMount({
            active: true,
        })
        expect(wrapper.attributes("active")).toBe("true")
        wrapper.setProps({
            active: "false",
        })
        expect(wrapper.attributes("active")).toBe("false")
    })

    it("should accept a text property", () => {
        const wrapper = factoryShallowMount({
            text: "Things to See and Do",
        })
        expect(wrapper.attributes("text")).toBe("Things to See and Do")
    })

    it("should accept an href property", () => {
        const wrapper = factoryShallowMount({
            href: "https://www.visitscotland.com/see-do/itineraries/",
        })
        expect(wrapper.attributes("href")).toBe("https://www.visitscotland.com/see-do/itineraries/")
    })
})
