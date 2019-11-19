<template>
  <component
    data-test="logo-link"
    :is="type"
    class="vs-logo d-flex align-items-center ml-1 mr-3 ml-sm-2 mr-sm-0"
    href="#"
  >
    <span data-test="logo-screenreader-text" class="sr-only">VisitScotland Home</span>
    <vs-svg path="visitscotland" width="100%" />
  </component>
</template>

<script>
import VsSvg from "../../../../elements/svg/Svg"

export default {
  name: "VsLogo",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
  data() {
    return {}
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "a",
    },
  },
}
</script>

<style lang="scss" scoped>
@import "../../styles/placeholders";

.vs-logo {
  transition: $transition-base;

  &:focus,
  &:hover {
    @extend %focus-pink-inset;
  }

  svg {
    max-width: 183px;

    @include media-breakpoint-up(md) {
      min-width: 183px;
    }
  }
}
</style>

<docs>
  ```jsx
    <vs-logo

    />
  ```
</docs>
