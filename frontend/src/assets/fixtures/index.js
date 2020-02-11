import Vue from "vue"
import { cloneDeep, split, initial, trimStart, last, set, merge, camelCase } from "lodash"

const fixtures = require.context("./", true, /^\.\/.*\.json$/)

let data = merge.apply(null, fixtures.keys().map(processFixture))

Vue.mixin({
  data() {
    return data
  },
})

function processFixture(path) {
  let parts = split(trimStart(path, "./"), "/")

  let keys = [...initial(parts), last(parts).replace(new RegExp(".json$"), "")].map(camelCase)

  return set({}, keys, fixtures(path))
}
