import Vue from "vue"
import { cloneDeep, split, initial, trimStart, trimEnd, last, set, merge, camelCase } from "lodash"

const fixtures = require.context("./", true, /^\.\/.*\.json$/)

let data = merge.apply(null, fixtures.keys().map(processFixture))

console.log(cloneDeep(data))

Vue.mixin({
  data() {
    return data
  },
})

function processFixture(path) {
  let parts = split(trimStart(path, "./"), "/")

  // temp guard
  if (parts.length === 1 && parts[0] !== "favourite.json") {
    return {}
  }

  let keys = [...initial(parts), last(parts).replace(new RegExp(".json$"), "")].map(camelCase)

  return set({}, keys, fixtures(path))
}
