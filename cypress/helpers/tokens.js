import designTokens from "@/assets/tokens/tokens.raw.json"
import { get, map, zipObject } from "lodash"

const themeColours = [
  "primary",
  "secondary",
  "success",
  "danger",
  "warning",
  "info",
  "light",
  "dark",
]

export default {
  getThemeColours,
  getToken,
}

function getThemeColours(colours) {
  let values = map(themeColours, colour => {
    return getToken("color_theme_" + colour)
  })

  return zipObject(themeColours, values)
}

function getToken(tokenKey) {
  return get(designTokens, "props." + tokenKey + ".value")
}
