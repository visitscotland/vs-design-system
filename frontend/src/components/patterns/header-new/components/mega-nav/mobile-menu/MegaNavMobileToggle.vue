<template>
    <li
        class="vs-mega-nav__mobile-toggle"
        ref="mobileMenuToggle"
    >
        <VsButton
            class="vs-mega-nav__mobile-toggle__button"
            variant="transparent"
            aria-haspopup="true"
            :animate="false"
            :uppercase="false"
            :aria-expanded="isOpen ? 'true' : 'false'"
            @click.native="openMenu"
        >
            <slot />
        </VsButton>

        <VsMegaNavDropdown v-show="isOpen">
            <a :href="href">{{ ctaText }}</a>
            <hr>

            <slot name="dropdown-nav" />
        </VsMegaNavDropdown>
    </li>
</template>

<script>
/**
 *  Mega nav top level menu button
 */

import VsButton from '@components/elements/button/Button';
import VsMegaNavDropdown from '@components/patterns/header-new/components/mega-nav/MegaNavDropdown';

export default {
    name: 'VsMegaNavMobileToggle',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsButton,
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
    },
    data() {
        return {
            isOpen: false,
        };
    },
    created() {
        window.addEventListener('click', this.resetMenu);
    },
    beforeDestroy() {
        window.removeEventListener('click', this.resetMenu);
    },
    methods: {
        resetMenu(e) {
            // Close dropdown when user clicks elsewhere
            if (!this.$refs.mobileMenuToggle.contains(e.target)) {
                this.isOpen = false;
            }
        },
        openMenu() {
            this.isOpen = !this.isOpen;
        },
    },
};
</script>

<style lang="scss">
@import '~bootstrap/scss/type';

.vs-mega-nav__mobile-toggle {

    @include media-breakpoint-up(xl) {
        margin-right: $spacer-6;

        &:last-of-type{
            margin-right: 0;
        }
    }

    .vs-mega-nav__mobile-toggle__button.btn {
        letter-spacing: 0;
        font-weight: normal;
        line-height: 1.2;
        padding: $spacer-3 $spacer-2;
        border-radius: 0;
        border: 0;

        &::after {
            content: '';
            position: absolute;
            display: block;
            bottom: 0;
            left: 0;
            width: 0;
            height: 4px;
            background: $color-pink !important;
            transition: width 0.2s;
        }

        &:hover {
            color: $color-pink;

            &::after {
                width: 100%;
            }
        }

        &:focus {
            box-shadow: 0 0 0 0.1rem $color-pink inset;
        }
    }
}
</style>

<docs>
  ```jsx
  ```
</docs>
