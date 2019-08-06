<template>
  <card-grid>
    <card v-for="path in icons" v-bind:key="path">
      <vs-icon :name="path" />
      <h3>{{ iconName(path) }}</h3>
      <pre>{{ path }}</pre>
    </card>
  </card-grid>
</template>

<script>
const allIcons = getAllIcons()
import VsIcon from "@components/elements/icon/"
import Card from "./common/Card"
import CardGrid from "./common/CardGrid"
import { trimStart, map, partial, trimEnd, capitalize, last, split } from "lodash"

export default {
  name: "Icons",
  components: { VsIcon, Card, CardGrid },
  data() {
    return {
      icons: allIcons,
    }
  },
  methods: {
    iconName(path) {
      return capitalize(last(split(path, "/")))
    },
  },
}

function getAllIcons() {
  const all = require.context("@/assets/svg/icons", true, /^\.\/.*\.svg$/)

  return map(all.keys(), key => {
    return trimEnd(trimStart(key, "./"), ".svg")
  })
}
</script>

<style lang="scss" scoped>
.card {
  text-align: center;
}
</style>

<docs>
  ```jsx

  ```
</docs>
