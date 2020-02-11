<template>
    <b-list-group v-bind="$attrs" v-hand-down-focus><slot /></b-list-group>
</template>

<script>
import { BListGroup } from "bootstrap-vue"
import HandDownFocus from "@/directives/hand-down-focus"

/**
 * Wrapper for the BListGroup component.
 */
export default {
    name: "VsListGroup",
    components: {
        BListGroup,
    },
    directives: {
        HandDownFocus,
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/list-group";
</style>

<docs>
  ```js
  ```
</docs>
