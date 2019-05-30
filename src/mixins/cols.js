import { extend, mapValues, zipObject, pickBy, get } from "lodash"
import breakpoints from "../utils/breakpoints.bootstrap-vue.config"

/**
 * Creates dynamic breakpoint props
 */
const dynamicBreakpointProps = mapValues(zipObject(breakpoints, breakpoints), () => {
  return {
    type: [String, Number],
  }
})

const breakpointProps = extend({}, dynamicBreakpointProps, {
  /**
   * The number of columns this component should take up
   */
  cols: {
    type: [String, Number],
  },
})

export default {
  props: breakpointProps,
  computed: {
    /**
     * Returns a map of the breakpoints with values according to the component's
     * matching props
     */
    breakpointAttrs() {
      return pickBy(
        mapValues(breakpointProps, (prop, breakpoint) => {
          return get(this, breakpoint)
        })
      )
    },
  },
}
