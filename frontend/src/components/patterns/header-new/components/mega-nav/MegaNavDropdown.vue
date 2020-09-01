<!-- eslint-disable -->
<template>
    <div class="vs-mega-nav__dropdown">
        <BDropdown 
            variant="transparent"
            ref="dropdown"
        >
            <template #button-content>
                <slot name="button-content" />
            </template>

            <slot name="cta-link" />
            <slot name="dropdown-content" />
        </BDropdown>
    </div>
</template>

<script>
/**
 *  Mega nav top level menu button
 */

import { BDropdown } from 'bootstrap-vue';

export default {
    name: 'VsMegaNavDropdown',
    status: 'prototype',
    release: '0.1.0',
    components: {
        BDropdown,
    },
    props: {
        controlId: {
            type: String,
            default: '',
        },
        href: {
            type: String,
            default: '',
        },
        ctaText: {
            type: String,
            default: '',
        },
    },
    mounted() {
        // Listen for dropdown opening and closing and emit event
        this.$root.$on('bv::dropdown::show', () => {
            this.$emit('menu-toggled');
        });

        this.$root.$on('bv::dropdown::hide', () => {
            this.$emit('menu-toggled');
        });

        // Close menu on resize screen to fix toggle btn issues
        window.addEventListener('resize', this.closeMenu);
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.closeMenu);
    },
    methods: {
        closeMenu() {
            this.$refs.dropdown.hide(true);
        },
    },
};
</script>

<style lang="scss">
@import '~bootstrap/scss/type';

.vs-mega-nav__dropdown {
    padding: 0;

    .dropdown {
        position: static;

        &.show {
            .btn.dropdown-toggle {
                color: $color-pink;

                &::after {
                    width: 100%;
                }
            }
        }
    }

    .btn.dropdown-toggle {
        position: relative;
        letter-spacing: 0;
        font-weight: normal;
        line-height: 1.2;
        border-radius: 0;
        border: 0;
        height: 32px;
        width: 32px;
        font-size: 0;
        padding: $spacer-1;

        &:focus {
            box-shadow: 0 0 0 0.1rem $color-pink inset;
        }

        &::after {
            display: none;
        }

        @include media-breakpoint-up(lg) {
            padding: $spacer-3 $spacer-2;
            height: auto;
            width: auto;
            font-size: 1rem;

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
                border: 0;
                margin: 0;
            }

            &:hover {
                color: $color-pink;

                &::after {
                    width: 100%;
                }
            }
        }
    }

    .dropdown-menu {
        background: $color-gray-tint-8;
        width: 100%;
        padding: 24px 0;
        margin: 0;
        min-height: 200px;
        border: 0;
        box-shadow: 0 3px 7px rgba(0, 0, 0, 0.16);
        transform: translate3d(0px, 55px, 0px) !important;
    }
}
</style>

<docs>
  ```jsx
  ```
</docs>
