<template>
    <VsButton
        v-bind="$attrs"
        v-b-tooltip.hover
        class="vs-tooltip d-inline-block position-relative"
        :title="title"
    >
        <slot />
    </VsButton>
</template>

<script>
import { VBTooltip } from 'bootstrap-vue';
/**
 * A button that renders a tooltip on hover or focus
 * of the element
 *
 * @displayName Tooltip
 */

export default {
    name: 'VsTooltip',
    status: 'prototype',
    release: '0.0.1',
<<<<<<< HEAD
    components: {
        VsButton,
    },
=======
>>>>>>> d528b0ebf615aaee3e3cd0c677defd6c34d85709
    directives: {
        'b-tooltip': VBTooltip,
    },
    props: {
        title: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss"></style>

<docs>
  ```jsx
    <VsTooltip
        title="This is a bus icon"
        href="#"
        icon="bus"
        size="lg"
        icon-only
        icon-variant-override="dark"
        class="p-0"
        variant="transparent"
    />
  ```
</docs>
