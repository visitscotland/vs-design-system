<template>
    <div
        class="vs-mega-nav__dropdown"
        data-test="vs-mega-nav__dropdown"
    >
        <BDropdown
            variant="transparent"
            ref="dropdown"
        >
            <template #button-content>
                <!-- @slot For dropdown toggle button content  -->
                <slot name="buttonContent" />
            </template>

            <VsContainer>
                <VsRow>
                    <VsCol cols="12">
                        <!-- @slot Used to display the top menu link
                        at the top of the dropdown menu  -->
                        <slot name="ctaLink" />
                        <!-- @slot The rest of the mega nav links put here in the dropdown menu  -->
                        <slot name="dropdownContent" />

                        <VsButton
                            class="vs-mega-nav__dropdown__close-btn d-lg-none position-absolute"
                            icon="close"
                            icon-only
                            variant="transparent"
                            @click.native="closeMenu"
                        >
                            <span class="sr-only">
                                {{ menuToggleAltText }}
                            </span>
                        </VsButton>
                    </VsCol>
                </VsRow>
            </VsContainer>
        </BDropdown>
    </div>
</template>

<script>
import {
    VsCol, VsRow, VsContainer,
} from '@components/elements/layout';
import { BDropdown } from 'bootstrap-vue';
import VsButton from '@components/elements/button/Button';

/**
 *  This component includes a slot for toggle button content
 *  and slots for the mega nav dropdown menu content
 *
 * @displayName MegaNav Dropdown
 */
export default {
    name: 'VsMegaNavDropdown',
    status: 'prototype',
    release: '0.1.0',
    components: {
        BDropdown,
        VsCol,
        VsRow,
        VsContainer,
        VsButton,
    },
    props: {
        /**
         * Accessiblity alt text for the menu button
         */
        menuToggleAltText: {
            type: String,
            required: true,
        },
    },
    mounted() {
        // Listen for dropdown opening and closing and emit event
        this.$root.$on('bv::dropdown::show', () => {
            /**
             * Triggers when the dropdown is about to show
             */
            this.$emit('menuToggled');
        });

        this.$root.$on('bv::dropdown::hide', () => {
            /**
             * Triggers when the dropdown is about to close
             */
            this.$emit('menuToggled');
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

    .vs-mega-nav__dropdown__close-btn{
        @extend .dropdown-toggle;
        right: 4px;
        top: -67px;

        @include media-breakpoint-up(sm) {
            right: 12px;
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

        &:hover {
            .icon.icon-dark[data-v-196177e7] {
                fill: $color-pink;
            }
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
                background: $color-pink;
                transition: width 0.2s linear;
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
        box-shadow: 0px 9px 5px -7px rgba(0,0,0,0.1), inset 0px 10px 6px -8px rgba(0, 0, 0, 0.16);
        transform: translate3d(0px, 55px, 0px) !important;
    }

}

@include no-js {
    .vs-mega-nav__dropdown{
        .btn.dropdown-toggle {
            padding: $spacer-2 $spacer-2;
            font-size: 1rem;

            &.disabled {
                opacity: 1;
            }

            &:focus {
                box-shadow: 0 0 0 0.1rem $color-pink inset;
            }

            &:hover {
                color: $color-base-text;
            }

            &::after {
                content: '';
                position: absolute;
                display: block;
                bottom: 0;
                left: 0;
                width: 100%;
                height: 4px;
                background: $color-base-text;
                transition: width 0.2s linear;
                border: 0;
                margin: 0;
            }
        }

        .dropdown-menu{
            display: block;
            position: relative;
            border: 0;
            box-shadow: none;
            transform: none!important;
        }
    }
}
</style>

<docs>
   ```[import](./__examples__/meganav.dropdown.example.vue)
    ```
</docs>
