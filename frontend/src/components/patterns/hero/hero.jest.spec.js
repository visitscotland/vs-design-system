import { shallowMount } from "@vue/test-utils"
import VsHero from "./Hero"

const factoryShallowMount = propsData => {
    return shallowMount(VsHero, {
        propsData: {
            ...propsData,
        },
    })
}

describe("VsHero", () => {
    it("should render a caption when is provided", () => {
        const wrapper = factoryShallowMount({ caption: "Test caption" })
        expect(wrapper.html()).toContain('<p class="vs-hero__image-caption">')
        expect(wrapper.html()).toContain("Test caption")
    })
    it("should render a credit and copyright symbol when a credit is provided", () => {
        const wrapper = factoryShallowMount({ credit: "Test credit" })
        expect(wrapper.html()).toContain('<p class="vs-hero__image-credit m-0">')
        expect(wrapper.html()).toContain("© Test credit")
    })
    it("should render a map when when a longitude and latitude are provided", () => {
        const wrapper = factoryShallowMount({ longitude: "11.11111", latitude: "11.11111" })
        expect(wrapper.html()).toContain('class="map__wrapper"')
        expect(wrapper.html()).toContain(
            '<vs-image-location-map-stub latitude="11.11111" longitude="11.11111" mapoutlinecolor="#FFFFFF" mapmarkercolor="#7CC9CC"></vs-image-location-map-stub>'
        )
    })
    it("should *not* render a map when when only a longitude is provided", () => {
        const wrapper = factoryShallowMount({ longitude: "11.11111" })
        expect(wrapper.html()).not.toContain('class="map__wrapper"')
    })
    it("should *not* render a map when when only a latitude is provided", () => {
        const wrapper = factoryShallowMount({ latitude: "11.11111" })
        expect(wrapper.html()).not.toContain('class="map__wrapper"')
    })
    it("should *not* render a figcaption when neither a credit, a caption nor coordinates are provided", () => {
        const wrapper = factoryShallowMount({})
        expect(wrapper.html()).not.toContain("<figcaption>")
    })
    it("should *not* render a caption toggle button when neither a credit, a caption nor coordinates are provided", () => {
        const wrapper = factoryShallowMount({})
        expect(wrapper.html()).not.toContain("vs-button-stub")
    })
})
