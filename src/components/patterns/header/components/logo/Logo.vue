<template>
  <a
    data-test="logo-link"
    class="vs-logo d-flex align-items-center ml-1 mr-3 ml-sm-2 mr-sm-0"
    href="/"
  >
    <span data-test="logo-screenreader-text" class="sr-only">VisitScotland Home</span>
    <vs-svg path="visitscotland" width="100%" />
  </a>
</template>

<script>
import VsSvg from "@components/elements/svg/Svg"

export default {
  name: "VsLogo",
  status: "prototype",
  release: "0.1.0",
  components: { VsSvg },
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
