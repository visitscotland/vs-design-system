<template>
  <b-nav-item-dropdown v-bind="$attrs"><slot /></b-nav-item-dropdown>
</template>

<script>
import { BNavItemDropdown } from "bootstrap-vue"

export default {
  name: "VsNavItemDropdown",
  components: {
    BNavItemDropdown,
  },
}
</script>

<style lang="scss">
@import "~bootstrap/scss/dropdown";
</style>

<docs>
  ```js
  <vs-dropdown>
    <vs-dropdown-item>sdafdfsaf</vs-dropdown-item>
    <vs-dropdown-item>bbbb</vs-dropdown-item>
    <vs-dropdown-item>feeeeee</vs-dropdown-item>
  </vs-dropdown>
</docs>
