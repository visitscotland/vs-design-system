import { shallowMount } from "@vue/test-utils"
import VsBreadcrumb from "./Breadcrumb"

describe("VsBreadcrumb", () => {
  const wrapper = shallowMount(VsBreadcrumb, {
    slots: {
      default: "",
    },
  })

  it("should render a b-breadcrumb", () => {
    expect(wrapper.is("nav")).toBe(true)
    expect(wrapper.html()).toContain(
      '<nav aria-label="breadcrumbs"><b-breadcrumb-stub class="flex-nowrap p-0"></b-breadcrumb-stub></nav>'
    )
  })
})
