<template>
  <vs-button
    class="vs-header__site-search__toggle-button p-0 p-sm-1 pr-xl-3"
    :variant="isOn ? 'light' : 'primary-pink'"
    :animate="false"
  >
    <vs-icon name="search" size="sm" :variant="isOn ? 'secondary' : 'reverse-white'" />
    <span class="sr-only-xl-down d-xl-flex"><slot /></span>
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
@import "~bootstrap/scss/utilities/spacing";

.vs-header__site-search__toggle-button {
  font-size: 1.125rem;
  font-weight: $font-weight-normal;
  letter-spacing: 0px;
  position: relative;
  text-transform: none;
  transition: $transition-base;

  @include media-breakpoint-up(sm) {
    &:not(:focus) {
      box-shadow: 0 8px 0 0 $color-theme-light;
    }

    &.btn-primary-pink:not(:focus) {
      box-shadow: 0 5px 0 0 $color-pink;

      &:hover {
        box-shadow: 0 5px 0 0 #890056;
      }
    }
  }
}
</style>

<docs>
  ```jsx
    <vs-site-search-toggle-button>Search</vs-site-search-toggle-button>
  ```
</docs>
