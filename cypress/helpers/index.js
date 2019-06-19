import designTokens from "@/assets/tokens/tokens.raw.json"
import { get } from "lodash"

export default {
  getToken,
}

function getToken(tokenKey) {
  return get(designTokens, "props." + tokenKey + ".value")
}
