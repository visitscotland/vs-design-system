<template>
    <div
        class="mega-nav-mobile-menu"
        @blur="handleBlur"
    >
        <slot />
    </div>
</template>

<script>
export default {
    name: 'VsMegaNavDropdown',
    status: 'prototype',
    release: '0.1.0',
    methods: {
        handleBlur() {
            console.log('blur');
        },
    },
};
</script>

<style lang="scss">
    .mega-nav-mobile-menu {
        position: absolute;
        top: 100%;
        left: 0;
        width: 100%;
    }
</style>
