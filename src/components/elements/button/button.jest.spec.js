import Vue from "vue"
import { shallowMount } from "@vue/test-utils"
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

  it("should not render a dark b-button", () => {
    wrapper.setProps({ variant: "dark" })
    expect(wrapper.attributes("variant")).toBe("dark")
  })

  it("should contain the text", () => {
    expect(wrapper.text()).toBe("I'm a button")
  })
})
