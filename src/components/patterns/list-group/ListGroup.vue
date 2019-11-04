<template>
  <b-list-group v-bind="$attrs"><slot /></b-list-group>
</template>

<script>
import { BListGroup } from "bootstrap-vue"

/**
 * Wrapper for the BListGroup component.
 */
export default {
  name: "VsListGroup",
  components: {
    BListGroup,
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/list-group";
</style>

<docs>
  ```js

</docs>
