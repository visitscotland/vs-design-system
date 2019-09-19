<template>
  <vs-button
    class="vs-close-button"
    variant="transparent"
    focus-style="underline"
    focus-colour="pink"
  >
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
  position: absolute;
  right: 0;
  top: 0;
  height: 50px;
  width: 50px;
}
</style>
