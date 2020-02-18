<template>
  <vs-button class="vs-header__button p-1" :class="{ 'vs-header__button--top': isTop }" size="sm">
    <slot />
  </vs-button>
</template>

<script>
import VsButton from "@components/elements/button"

export default {
  name: "VsHeaderButton",
  status: "prototype",
  release: "0.1.0",
  components: { VsButton },
  props: {
    section: {
      type: String,
      default: "bottom",
      validator: value => {
        return value.match(/(top|bottom)/)
      },
    },
  },
  computed: {
    isTop() {
      return this.section === "top"
    },
  },
}
</script>

<style lang="scss" scoped>
@import "../../styles/placeholders";

.vs-header__button {
  @extend %header-nav-item;
  text-transform: none !important;
  letter-spacing: normal;

  &.vs-header__button--top {
    @extend %header-nav-item-top;
  }
}
</style>

<docs>
  ```jsx
    <vs-header-button>
      Click me!
    </vs-header-button>
  ```
</docs>
