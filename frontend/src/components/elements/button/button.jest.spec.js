import { shallowMount, mount } from "@vue/test-utils"

import VsButton from "./Button"

const animateHandler = jest.fn()

const factoryShallowMount = (propsData) => shallowMount(VsButton, {
    slots: {
        default: "Button text",
    },
    propsData: {
        ...propsData,
    },
})

const factoryMount = (propsData) => mount(VsButton, {
    propsData: {
        ...propsData,
    },
    methods: {
        animateHandler,
    },
})

describe("VsButton", () => {
    it("should render a b-button", () => {
        const wrapper = factoryShallowMount()
        expect(wrapper.is("b-button-stub")).toBe(true)
        expect(wrapper.attributes("variant")).toBe("primary")
    })

    it("should accept an href property", () => {
        const wrapper = factoryShallowMount({
            href: "https://www.visitscotland.com",
        })
        expect(wrapper.attributes("href")).toBe("https://www.visitscotland.com")
    })

    it("should accept a tabindex property", () => {
        const wrapper = factoryShallowMount({
            tabindex: "2",
        })
        expect(wrapper.attributes("tabindex")).toBe("2")
    })

    it("should accept and render different variants as props", () => {
        const wrapper = factoryShallowMount({
            variant: "success",
        })
        expect(wrapper.attributes("variant")).toBe("success")

        wrapper.setProps({
            variant: "danger",
        })
        expect(wrapper.attributes("variant")).toBe("danger")

        wrapper.setProps({
            variant: "warning",
        })
        expect(wrapper.attributes("variant")).toBe("warning")
    })

    it("should contain the text", () => {
        const wrapper = factoryShallowMount()
        expect(wrapper.text()).toBe("Button text")
    })
})

describe("VsButton DOM tests", () => {
    it("should contain several classes by default", () => {
        const wrapper = factoryMount()
        expect(wrapper.classes()).toContain("btn")
        expect(wrapper.classes()).toContain("text-uppercase")
        expect(wrapper.classes()).toContain("d-flex")
        expect(wrapper.classes()).toContain("align-items-center")
        expect(wrapper.classes()).toContain("justify-content-center")
        expect(wrapper.classes()).toContain("btn-primary")
        expect(wrapper.classes()).toContain("btn-md")
        expect(wrapper.classes()).toContain("btn-animate")
    })

    describe("VsButton animate property set to false", () => {
        it("should *NOT* set a btn-animate class if animate is set to false", () => {
            const wrapper = factoryMount({
                animate: false,
            })
            expect(wrapper.classes("btn-animate")).toBe(false)
        })

        it("should *NOT* call the animateHandler on click if animate prop is set to false", () => {
            const wrapper = factoryMount({
                animate: false,
            })
            wrapper.trigger("click")
            expect(animateHandler).not.toBeCalled()
        })

        it("should set animateClass computed prop to null if animate prop is set to false", () => {
            const wrapper = factoryMount({
                animate: false,
            })
            expect(wrapper.vm.animateClass).toBe(null)
        })
    })

    describe("VsButton animate property set to true", () => {
        it("should accept an animate boolean prop and set a class accordingly", () => {
            const wrapper = factoryMount({
                animate: true,
            })
            expect(wrapper.vm.animateClass).toEqual("btn-animate")
            expect(wrapper.classes("btn-animate")).toBe(true)
        })

        it("should call the animateHandler on click if animate prop is set to true", () => {
            const wrapper = factoryMount({
                animate: true,
            })
            wrapper.trigger("click")
            expect(animateHandler).toBeCalled()
        })
    })

    it("should set classes for different variants", () => {
        const wrapper = factoryMount({
            variant: "success",
        })
        expect(wrapper.classes()).toContain("btn-success")

        wrapper.setProps({
            variant: "dark",
        })
        expect(wrapper.classes()).toContain("btn-dark")

        wrapper.setProps({
            variant: "transparent",
        })
        expect(wrapper.classes()).toContain("btn-transparent")

        wrapper.setProps({
            variant: "outline-transparent",
        })
        expect(wrapper.classes()).toContain("btn-outline-transparent")
    })

    it("accepts a block parameter, which sets a class of btn-block", () => {
        const wrapper = factoryMount({
            block: true,
        })
        expect(wrapper.classes()).toContain("btn-block")
    })

    it("accepts a disabled parameter, which sets an attribute of disabled and a class of disabled", () => {
        const wrapper = factoryMount({
            disabled: true,
        })
        expect(wrapper.classes()).toContain("disabled")
        expect(wrapper.attributes("disabled")).toBe("disabled")
    })

    it("should accept size props and set classes for different sizes", () => {
        const wrapper = factoryMount({
            size: "sm",
        })
        expect(wrapper.classes()).toContain("btn-sm")

        wrapper.setProps({
            size: "md",
        })
        expect(wrapper.classes()).toContain("btn-md")

        wrapper.setProps({
            size: "lg",
        })
        expect(wrapper.classes()).toContain("btn-lg")
    })
})
