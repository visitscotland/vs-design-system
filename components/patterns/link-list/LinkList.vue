<template>
    <ul class="link-list">
        <!-- @slot Slot for link list items -->
        <slot />
    </ul>
</template>

<script>
/**
 * This component is a list of links.
 *
 * @displayName Link List
 */

export default {
    name: 'VsLinkList',
    status: 'prototype',
    release: '0.0.1',
};
</script>

<style lang="scss">
    .link-list {
        list-style: none;
        padding: 0;
        margin-bottom: $spacer-7;
    }
</style>
