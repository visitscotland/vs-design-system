<template>
    <div class="vs-prototype p-0 m-0">
        <slot />
    </div>
</template>

<script>
/**
 * Prototypes are wrappers used to visually communicate parts
 * of the product or actions that are still unavailable.
 *
 * This is a development only tool to help visualise
 * components/functionalities to be finished.
 *
 * @displayName Prototype
 */

export default {
    name: 'VsPrototype',
    status: 'prototype',
    release: '0.0.1',
};
</script>

<style lang="scss">
.vs-prototype {
    opacity: $opacity-50;

    & > * {
        color: darken(grey, 70%);
        border-color: darken(grey, 70%);
        background: lighten(grey, 30%);
    }

    :hover,
    &:hover {
        cursor: not-allowed;
    }
}
</style>