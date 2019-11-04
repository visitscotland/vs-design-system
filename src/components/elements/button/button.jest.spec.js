import { shallowMount } from "@vue/test-utils"
import { mount } from "@vue/test-utils"
import VsButton from "./Button"

describe("VsButton", () => {
  const wrapper = shallowMount(VsButton, {
    slots: {
      default: "I'm a button",
    },
  })

  it("should render a b-button", () => {
    expect(wrapper.is("b-button-stub")).toBe(true)
    expect(wrapper.attributes("variant")).toBe("primary")
  })

  it("should accept an href property", () => {
    wrapper.setProps({ href: "https://www.visitscotland.com" })
    expect(wrapper.attributes("href")).toBe("https://www.visitscotland.com")
  })

  it("should accept a tabindex property", () => {
    wrapper.setProps({ tabindex: "2" })
    expect(wrapper.attributes("tabindex")).toBe("2")
  })

  it("should accept and render different variants as props", () => {
    wrapper.setProps({ variant: "success" })
    expect(wrapper.attributes("variant")).toBe("success")

    wrapper.setProps({ variant: "danger" })
    expect(wrapper.attributes("variant")).toBe("danger")

    wrapper.setProps({ variant: "warning" })
    expect(wrapper.attributes("variant")).toBe("warning")
  })

  it("should contain the text", () => {
    expect(wrapper.text()).toBe("I'm a button")
  })
})

describe("VsButton DOM tests", () => {
  const animateHandler = jest.fn()
  const mountedWrapper = mount(VsButton, {
    methods: {
      animateHandler,
    },
  })

  it("should *NOT* set a btn-animate class if animate is set to false", () => {
    mountedWrapper.setProps({ animate: false })
    expect(mountedWrapper.classes("btn-animate")).toBe(false)
  })

  it("should *NOT* call the animateHandler on click if animate prop is set to false", () => {
    mountedWrapper.setProps({ animate: false })
    mountedWrapper.trigger("click")
    expect(animateHandler).not.toBeCalled()
  })

  it("should set animateClass computed prop to null if animate prop is set to false", () => {
    mountedWrapper.setProps({ animate: false })
    expect(mountedWrapper.vm.animateClass).toBe(null)
  })

  it("should accept an animate boolean prop and set a class accordingly", () => {
    mountedWrapper.setProps({ animate: true })
    expect(mountedWrapper.vm.animateClass).toEqual("btn-animate")
    expect(mountedWrapper.classes("btn-animate")).toBe(true)
  })

  it("should call the animateHandler on click if animate prop is set to true", () => {
    mountedWrapper.setProps({ animate: true })
    mountedWrapper.trigger("click")
    expect(animateHandler).toBeCalled()
  })

  it("should set classes for different variants", () => {
    mountedWrapper.setProps({ variant: "success" })
    expect(mountedWrapper.classes()).toContain("btn-success")

    mountedWrapper.setProps({ variant: "dark" })
    expect(mountedWrapper.classes()).toContain("btn-dark")

    mountedWrapper.setProps({ variant: "transparent" })
    expect(mountedWrapper.classes()).toContain("btn-transparent")

    mountedWrapper.setProps({ variant: "outline-transparent" })
    expect(mountedWrapper.classes()).toContain("btn-outline-transparent")
  })

  it("accepts a block parameter, which sets a class of btn-block", () => {
    mountedWrapper.setProps({ block: "true" })
    expect(mountedWrapper.classes()).toContain("btn-block")
  })

  it("accepts a pill parameter, which sets a class of rounded-pill", () => {
    mountedWrapper.setProps({ pill: "true" })
    expect(mountedWrapper.classes()).toContain("rounded-pill")
  })

  it("accepts a disabled parameter, which sets an attribute of disabled and a class of disabled", () => {
    mountedWrapper.setProps({ disabled: "true" })
    expect(mountedWrapper.classes()).toContain("disabled")
    expect(mountedWrapper.attributes("disabled")).toBe("disabled")
  })

  it("should accept size props and set classes for different sizes", () => {
    mountedWrapper.setProps({ size: "sm" })
    expect(mountedWrapper.classes()).toContain("btn-sm")

    mountedWrapper.setProps({ size: "md" })
    expect(mountedWrapper.classes()).toContain("btn-md")

    mountedWrapper.setProps({ size: "lg" })
    expect(mountedWrapper.classes()).toContain("btn-lg")
  })
})
