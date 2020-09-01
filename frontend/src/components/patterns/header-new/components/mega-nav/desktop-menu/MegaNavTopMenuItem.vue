<template>
    <li
        class="vs-mega-nav__item"
        ref="menuToggle"
    >
        <VsMegaNavDropdown :control-id="controlId">
            <template #button-content>
                <slot name="button-content" />
            </template>

            <template #cta-link>
                <a :href="href">{{ ctaText }}</a>
                <hr>
            </template>

            <template #dropdown-content>
                <slot name="dropdown-content" />
            </template>
        </VsMegaNavDropdown>
    </li>
</template>

<script>
/**
 *  Mega nav top level menu button
 */
import VsMegaNavDropdown from '@components/patterns/header-new/components/mega-nav/MegaNavDropdown';

export default {
    name: 'VsMegaNavTopMenuItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsMegaNavDropdown,
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
        controlId: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
@import '~bootstrap/scss/type';

.vs-mega-nav__item {
    @include media-breakpoint-up(xl) {
        margin-right: $spacer-6;

        &:last-of-type {
            margin-right: 0;
        }
    }
}
</style>

<docs>
  ```jsx
  ```
</docs>
