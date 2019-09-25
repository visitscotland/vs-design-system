<template>
  <vs-button
    class="vs-header__site-search__toggle-button pr-xl-2 py-xl-0 pl-xl-1"
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
@import "~bootstrap/scss/utilities/spacing";

.vs-site-search__button {
  z-index: 1;

  // &:hover,
  // &:focus {
  //   outline: none;
  //   box-shadow: inset 0 -3px 0 0 $color-white;

  //   @include media-breakpoint-up(xl) {
  //     box-shadow: 0 3px 0 0 $purple-shade-2;
  //   }
  // }

  // @include media-breakpoint-up(sm) {
  //   box-shadow: 0 5px 0 0 $color-pink;
  // }

  @include media-breakpoint-up(xl) {
    font-size: 1.125rem;
    font-weight: $font-weight-semi-bold;
    // padding: 0 1.25rem 0 0.5rem;
  }
}
</style>
