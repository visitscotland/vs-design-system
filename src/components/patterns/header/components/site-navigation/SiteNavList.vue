<template>
  <ul
    class="vs-site-nav__list list-unstyled"
    :class="{
      'vs-site-nav__list--open': isOpen,
      ['vs-site-nav__list--level' + level]: true,
    }"
  >
    <slot />
  </ul>
</template>

<script>
// import { BCollapse } from "bootstrap-vue"

/**
 * The nearest ancestor with position relative
 */
export default {
  name: "VsSiteNavList",
  status: "prototype",
  release: "0.1.0",
  components: {
    // BCollapse,
  },
  props: {
    isOpen: {
      type: Boolean,
    },
    level: {
      type: Number,
      required: true,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../../styles/placeholders";

// .collapse:not(.show) {
//   display: none;
//   opacity: 0;
// }

// .collapsing {
//   position: relative;
//   height: 0;
//   overflow: hidden;
//   opacity: 1;
//   transition: all 50ms ease-in-out;
// }

.vs-site-nav__list {
  width: 100%;
  display: flex;
  // z-index: 2;

  &.vs-site-nav__list--level1 {
    display: none;
    position: fixed;
    width: 100vw;
    top: 69px;
    left: 0;
    height: 56px;
    margin-bottom: 0;

    &.vs-site-nav__list--open {
      display: initial;
    }

    @include media-breakpoint-up(md) {
      top: 92px;
    }

    @include media-breakpoint-up(lg) {
      display: flex;
      &.vs-site-nav__list--open {
        display: flex;
      }
      position: initial;
    }
  }

  &.vs-site-nav__list--level2 {
    @include media-breakpoint-up(lg) {
      position: fixed;
      top: 92px;
      width: 100vw;
      left: 0;
    }
  }
}
</style>

<docs>
  ```jsx

  ```
</docs>
