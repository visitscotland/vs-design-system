import { shallowMount } from "@vue/test-utils"
import VsBreadcrumb from "./Breadcrumb"

describe("VsBreadcrumb", () => {
  const wrapper = shallowMount(VsBreadcrumb, {
    slots: {
      default: "",
    },
  })

  it("should render a b-breadcrumb", () => {
    expect(wrapper.is("b-breadcrumb-stub")).toBe(true)
    expect(wrapper.html()).toContain(
      '<b-breadcrumb-stub class="flex-nowrap bg-transparent p-0"></b-breadcrumb-stub>'
    )
  })
})
