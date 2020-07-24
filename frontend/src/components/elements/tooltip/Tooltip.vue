<template>
    <span
        class="d-inline-block position-relative"
        v-b-tooltip.hover
        :title="title"
    >
        <slot />
    </span>
</template>

<script>
import { VBTooltip } from 'bootstrap-vue';
/**
 * Renders a text span with tooltip displayed on hover
 */

export default {
    name: 'VsTooltip',
    status: 'prototype',
    release: '0.0.1',
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

<docs>
  ```jsx

    <vs-tooltip title="This is a search icon">
      <vs-icon :name="bus" :variant="primary" :size="lg" />
    </vs-tooltip>
  ```
</docs>
