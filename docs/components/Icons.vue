<template>
  <div>
    <pre>{{ icons }}</pre>
  </div>
</template>

<script>
const allIcons = getAllIcons()
import VsIcon from "@components/elements/icon/"
import { trimStart, map, partial, trimEnd } from "lodash"

export default {
  name: "Icons",
  components: { VsIcon },
  data() {
    return {
      icons: allIcons,
    }
  },
}

function getAllIcons() {
  const all = require.context("@/assets/svg/icons", true, /^\.\/.*\.svg$/)

  return map(all.keys(), path => {
    return trimEnd(trimStart(path, "./"), ".svg")
  })
}
</script>

<style lang="scss" scoped>
@import "../styles/docs.tokens.scss";
</style>

<docs>
  ```jsx

  ```
</docs>
