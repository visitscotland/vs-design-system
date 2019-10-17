<template>
  <vs-button
    class="vs-header__site-search__toggle-button p-1 pr-xl-3"
    :variant="isOn ? 'light' : 'primary-pink'"
    focus-style="underline"
    focus-colour="white"
  >
    <span class="d-xl-none sr-only"><slot /></span>
    <vs-icon name="search" size="sm" :variant="isOn ? 'secondary' : 'reverse-white'" />
    <span class="d-none d-xl-flex"><slot /></span>
  </vs-button>
</template>

<script>
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
@import "~bootstrap/scss/utilities/spacing";

.vs-header__site-search__toggle-button {
  font-size: 1.125rem;
  font-weight: $font-weight-bold;
}
</style>

<docs>
  ```jsx
    <vs-site-search-toggle-button>Search</vs-site-search-toggle-button>
  ```
</docs>
