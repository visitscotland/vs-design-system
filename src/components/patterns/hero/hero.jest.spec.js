import { mount } from "@vue/test-utils"
import VsHero from "./Hero"

const factoryMount = propsData => {
  return mount(VsHero, {
    propsData: {
      ...propsData,
    },
  })
}

describe("VsHero", () => {
  it("should render a container", () => {
    const wrapper = factoryMount()
    expect(wrapper.classes()).toContain("d-flex flex-column")
  })
})
