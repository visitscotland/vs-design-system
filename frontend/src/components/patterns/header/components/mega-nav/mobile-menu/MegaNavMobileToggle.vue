<template>
    <li
        class="vs-mega-nav-mobile-toggle"
        ref="mobileMenuToggle"
    >
        <a :href="href">
            <slot />
        </a>
    </li>
</template>

<script>
/**
 * WORK IN PROGRESS
 *
 * @displayName MegaNav Mobile Togge
 */

export default {
    name: 'VsMegaNavMobileToggle',
    status: 'prototype',
    release: '0.1.0',
    components: {
    },
    props: {
        href: {
            type: String,
            default: '',
        },
        ctaText: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
</style>

<docs>
  ```jsx
  ```
</docs>
