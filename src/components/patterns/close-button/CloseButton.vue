<template>
  <vs-button
    class="vs-close-button p-2"
    variant="transparent"
    focus-style="underline"
    focus-colour="pink"
  >
    <span class="sr-only"><slot /></span>
    <vs-icon name="close" size="xs" :padding="0" variant="dark" />
  </vs-button>
</template>

<script>
export default {
  name: "VsCloseButton",
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/utilities/spacing";

.vs-close-button {
  position: absolute;
  right: 0;
  top: 0;
}
</style>
