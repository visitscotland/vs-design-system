<template>
  <b-list-group-item
    :href="href"
    :active="active"
    :target="external ? '_blank' : null"
    :data-vs-track="trackingId"
    tabindex="0"
    class="vs-universal-nav--item text-white text-nowrap text-justify align-items-center d-flex"
  >
    <slot />
    <vs-icon
      v-if="external"
      class="d-lg-none ml-1"
      name="external-link"
      size="xxs"
      variant="reverse-white"
    />
  </b-list-group-item>
</template>

<script>
import { BListGroupItem } from "bootstrap-vue"

export default {
  name: "VsUniversalNavItem",
  status: "prototype",
  release: "0.1.0",
  props: {
    href: {
      required: true,
      type: String,
    },
    external: {
      type: [Boolean, String],
      default: false,
    },
    active: {
      type: [Boolean, String],
      default: false,
    },
    trackingId: {
      type: String,
    },
  },
  components: { BListGroupItem },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/spacing";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/list-group";

.vs-universal-nav--item {
  background-color: transparent;

  @include media-breakpoint-up(lg) {
    border: none;
    &:first-of-type {
      padding-left: 0 !important;
    }
  }

  // md and below is the collapsed version
  @include media-breakpoint-down(md) {
    background-color: $color-purple-shade-1;
    border: 1px none $color-purple-tint-3;
    border-style: solid none;
    margin-bottom: 0;
    padding-left: calc((100% - #{$max-container-width-md} + #{$grid-gutter-width}) / 2);
  }

  @include media-breakpoint-down(sm) {
    padding-left: calc((100% - #{$max-container-width-sm} + #{$grid-gutter-width}) / 2);
  }

  &:hover,
  &:focus {
    outline: none;
    background-color: $color-gray-shade-6;
  }

  &:focus {
    border-width: 2px;
    border-color: $color-purple-tint-5;
  }
}
</style>

<docs>
  ```jsx
  ```
</docs>
