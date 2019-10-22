import breakpoints from "@/utils/breakpoints"
import { mapValues, extend } from "lodash"

const breakpointProps = extend(
  {},
  /**
   * Creates dynamic breakpoint props
   */
  mapValues(breakpoints, () => {
    return {
      type: [String, Number],
    }
  }),
  {
    /**
     * The number of columns this component should take up
     */
    cols: {
      type: [String, Number],
    },
  }
)

export default {
  props: breakpointProps,
}
