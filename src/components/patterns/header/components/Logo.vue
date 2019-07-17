<template>
  <component :is="type" class="vs-logo" href="#">
    <span class="sr-only">VisitScotland Home</span>
    <vs-svg path="scotland-alba-logo" height="18" />
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"

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
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../styles/placeholders";

.vs-logo {
  display: flex;
  align-items: stretch;
  padding: 10px 0;

  &:focus {
    @extend %focus-pink-inset;
  }

  svg {
    align-self: center;
  }
}
</style>

<docs>
  ```jsx
    <vs-logo

    />
  ```
</docs>
