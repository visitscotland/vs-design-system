<template>
    <BTooltip
        :target="target"
        :title="title"
        data-test="vs-tooltip"
        custom-class="vs-tooltip"
    />
</template>

<script>
import { BTooltip } from 'bootstrap-vue';
/**
 * A tootlip to be used with a focusable element
 * e.g a button, link or input
 *
 * @displayName Tooltip
 */

export default {
    name: 'VsTooltip',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BTooltip,
    },
    props: {
        title: {
            type: String,
            default: '',
        },
        target: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss"></style>

<docs>
  ```jsx
    <VsButton
        id="link-button"
        tabindex="0"
        href="#"
        icon="bus"
        size="lg"
        icon-only
        icon-variant-override="dark"
        class="p-0"
        variant="transparent"
      />
    <VsTooltip target="link-button" title="Bus"></VsTooltip>
  ```
</docs>
