<template>
  <vs-button
    class="vs-header__site-search__toggle-button"
    variant="danger"
    focus-style="underline"
    focus-colour="black"
  >
    <span class="d-xl-none sr-only">Toggle Search</span>
    <vs-icon name="search" size="sm" variant="reverse-white" />
    <span class="d-none d-xl-flex vs-site-search__search-button-text">
      <span class="sr-only">Toggle</span> Search
    </span>
  </vs-button>
</template>

<script>
export default {
  name: "VsSiteSearchToggleButton",
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/utilities/display";

.vs-header__site-search__toggle-button {
  // background-color: $color-pink;
  // color: $color-white;
  z-index: 1;

  &:hover,
  &:focus {
    // outline: none;
    // box-shadow: inset 0 -3px 0 0 $color-white;

    @include media-breakpoint-up(xl) {
      // box-shadow: 0 3px 0 0 $purple-shade-2;
    }
  }

  @include media-breakpoint-up(sm) {
    // box-shadow: 0 5px 0 0 $color-pink;
  }

  @include media-breakpoint-up(xl) {
    // align-items: center;
    // box-shadow: 0 5px 0 0 $color-pink;
    // display: flex;
    // font-size: 1.125rem;
    // font-weight: $font-weight-semi-bold;
    // justify-content: center;
    // padding: 0 1.25rem 0 0.5rem;
    // width: auto;
  }

  // &[aria-expanded="true"] {
  //   background-color: $gray-tint-7;
  //   color: $color-base-text;

  //   @include media-breakpoint-up(md) {
  //     box-shadow: 0 5px 0 0 $gray-tint-7;
  //   }

  //   svg {
  //     fill: $gray-shade-2 !important;
  //   }

  //   &:after {
  //     height: 0;
  //   }
  // }
}
</style>
