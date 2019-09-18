<template>
  <vs-button class="vs-close-button" focus-variant="pink">
    <span class="sr-only"><slot /></span>
    <vs-icon name="close" size="xs" variant="dark" />
  </vs-button>
</template>

<script>
export default {
  name: "VsCloseButton",
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/screenreaders";

.vs-close-button {
  // @extend %button-reset;

  position: absolute;
  right: 0;
  top: 0;
  height: 50px;
  width: 50px;

  &:hover,
  &:focus {
    // @include focus-underline($color-pink);
  }
}
</style>
