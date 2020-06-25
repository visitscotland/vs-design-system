import { mount } from "@vue/test-utils"
import VsFormInput from "./FormInput"

describe("VsFormInput", () => {
    const wrapper = mount(VsFormInput)

    it("should render a form input with default type of text and default size of medium", () => {
        expect(wrapper.attributes("type")).toBe("text")
        expect(wrapper.classes()).toContain("form-control-md")
    })

    it("should accept various input type properties and render as attributes", () => {
        wrapper.setProps({
            type: "search",
        })
        expect(wrapper.attributes("type")).toBe("search")

        wrapper.setProps({
            type: "date",
        })
        expect(wrapper.attributes("type")).toBe("date")

        wrapper.setProps({
            type: "color",
        })
        expect(wrapper.attributes("type")).toBe("color")

        wrapper.setProps({
            type: "range",
        })
        expect(wrapper.attributes("type")).toBe("range")

        wrapper.setProps({
            type: "number",
        })
        expect(wrapper.attributes("type")).toBe("number")
    })

    it("should accept size properties", () => {
        wrapper.setProps({
            size: "sm",
        })
        expect(wrapper.classes()).toContain("form-control-sm")

        wrapper.setProps({
            size: "md",
        })
        expect(wrapper.classes()).toContain("form-control-md")

        wrapper.setProps({
            size: "lg",
        })
        expect(wrapper.classes()).toContain("form-control-lg")
    })

    it("should accept and set value properties", () => {
        wrapper.setProps({
            value: "My Test Value",
        })
        expect(wrapper.vm.inputVal).toBe("My Test Value")
    })
})
