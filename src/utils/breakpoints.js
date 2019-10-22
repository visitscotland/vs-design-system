import designTokens from "@/assets/tokens/tokens.raw.json"

import { pickBy, startsWith, map, get, partial, keys, replace, zipObject } from "lodash"

const breakpointTokens = pickBy(get(designTokens, "props"), function(value, name) {
  return startsWith(name, "breakpoint_")
})

const breakpointNames = map(
  keys(breakpointTokens),
  partial(replace, partial.placeholder, "breakpoint_", "")
)

export default zipObject(breakpointNames, map(breakpointTokens, "value"))
