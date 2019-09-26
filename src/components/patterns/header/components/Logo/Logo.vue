<template>
  <component data-test="logo-link" :is="type" class="vs-logo p-0 px-1" href="#">
    <span data-test="logo-screenreader-text" class="sr-only">VisitScotland Home</span>
    <span data-test="logo-wrapper-mobile" class="d-flex align-self-center d-lg-none">
      <vs-svg data-test="logo-mobile" path="scotland-alba-logo" height="18" />
    </span>
    <span data-test="logo-wrapper-desktop" class="d-none d-lg-block">
      <vs-svg data-test="logo-desktop" path="scotland-alba-logo" height="22" />
    </span>
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
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/utilities/spacing";
@import "../../styles/placeholders";

.vs-logo {
  display: inline-flex;
  align-items: center;
  transition: box-shadow 250ms ease-in-out;

  &:hover,
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
