<template>
  <vs-button
    class="vs-header__site-search__toggle-button"
    :variant="isOn ? 'light' : 'danger'"
    focus-style="underline"
    focus-colour="black"
  >
    <span class="d-xl-none sr-only">Toggle Search</span>
    <vs-icon name="search" size="sm" :variant="isOn ? null : 'reverse-white'" />
    <span class="d-none d-xl-flex"> <span class="sr-only">Toggle</span> Search </span>
  </vs-button>
</template>

<script>
import headerStore from "../header/header.store"

export default {
  name: "VsSiteSearchToggleButton",
  props: {
    isOn: {
      type: Boolean,
      default: false,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/display";
</style>
