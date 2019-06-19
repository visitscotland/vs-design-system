import designTokens from "@/assets/tokens/tokens.raw.json"
import vueComponents from "./vue-components"
import { get } from "lodash"

export default {
  getToken,
  vueComponents,
}

function getToken(tokenKey) {
  return get(designTokens, "props." + tokenKey + ".value")
}
