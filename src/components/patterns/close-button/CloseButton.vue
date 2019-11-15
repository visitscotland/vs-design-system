<template>
  <vs-button class="px-3" variant="transparent" :animate="false">
    <span class="sr-only"><slot /></span>
    <vs-icon name="close" size="xs" variant="secondary" />
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
</style>
