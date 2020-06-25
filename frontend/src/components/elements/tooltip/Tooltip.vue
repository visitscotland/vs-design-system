<template>
    <span
        v-b-tooltip.hover
        class="vs-tooltip d-inline-block position-relative"
        :title="title"
    >
        <slot />
    </span>
</template>

<script>
import { VBTooltip } from "bootstrap-vue"
/**
 * TODO: Document usage
 */

export default {
    name: "VsTooltip",
    status: "prototype",
    release: "0.0.1",
    directives: {
        "b-tooltip": VBTooltip,
    },
    props: {
        title: {
            type: String,
            default: "",
        },
    },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx

    <vs-tooltip title="This is a search icon">
      <vs-icon :name="bus" :variant="primary" :size="lg" />
    </vs-tooltip>
  ```
</docs>
