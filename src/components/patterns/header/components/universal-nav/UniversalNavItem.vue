<template>
  <b-list-group-item
    :href="href"
    :active="active"
    :target="external ? '_blank' : null"
    :data-vs-track="trackingId"
    class="text-white vs-universal-nav--item text-nowrap text-justify py-1 px-2 align-items-center d-flex"
  >
    <slot />
    <vs-icon
      v-if="external"
      class="d-lg-none"
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
  font-size: $font-size-sm;
  border: none;

  &:hover {
    background-color: $color-theme-primary-pink;
  }
}
</style>

<docs>
  ```jsx
  ```
</docs>
