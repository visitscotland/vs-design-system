<template>
    <div
        class="vs-warning"
        :class="{
            [`vs-warning--${theme}`]: true,
            [`vs-warning--${size}`]: true,
            [`vs-warning--${align}`]: true,
        }"
        data-test="vs-warning"
    >
        <div class="vs-warning__content">
            <VsIcon
                class="vs-warning__icon"
                :name="icon"
                v-bind="iconAttrs"
            />

            <div>
                <p>
                    <slot />
                </p>

                <p
                    v-if="!!this.$slots['extra-content']"
                >
                    <slot name="extra-content" />
                </p>
                <VsButton
                    v-bind="btnAttrs"
                    v-if="!!this.$slots['button-text']"
                    variant="secondary"
                    class="vs-warning__button"
                >
                    <slot name="button-text" />
                </VsButton>
            </div>
        </div>
    </div>
</template>
<script>

import VsIcon from '@components/elements/icon';
import VsButton from '@components/elements/button';

/**
 * A generic warning component that expands to cover whatever component
 * contains it
 *
 * @displayName Warning
 */
export default {
    name: 'VsWarning',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsIcon,
        VsButton,
    },
    props: {
        /**
        * The warning message to display to the user
        */
        icon: {
            type: String,
            default: 'review',
        },
        /**
        * Type of warning
        */
        type: {
            type: String,
            default: 'normal',
            validator: (value) => value.match(
                /(normal|cookie)/,
            ),
        },
        /**
        * Color theme - can be `primary` or `secondary`.
        * The default theme is secondary
        */
        theme: {
            type: String,
            default: 'dark',
            validator: (value) => value.match(
                /(light|dark)/,
            ),
        },
        /**
        * Message size - can be `small` or `normal`.
        * The default size is normal
        */
        size: {
            type: String,
            default: 'normal',
            validator: (value) => value.match(
                /(small|normal)/,
            ),
        },
        /**
        * Alignment of message - can be `left` or `right`.
        * The default alignment is left
        */
        align: {
            type: String,
            default: 'left',
            validator: (value) => value.match(
                /(left|right)/,
            ),
        },
    },
    computed: {
        btnAttrs() {
            const attrsObj = {
            };
            if (this.type === 'cookie') {
                attrsObj.class = 'ot-sdk-show-settings vs-warning__cookie-trigger';
                attrsObj.id = 'ot-sdk-btn';
            }
            if (this.theme === 'dark') {
                attrsObj.onDark = '';
            }
            if (this.size === 'small') {
                attrsObj.size = 'sm';
            }

            return attrsObj;
        },
        iconAttrs() {
            const iconAttrs = {
            };

            if (this.theme === 'dark') {
                iconAttrs.customColour = '#FCCA1B';
            } else {
                iconAttrs.variant = 'primary';
            }

            if (this.size === 'small') {
                iconAttrs.size = 'md';
            } else {
                iconAttrs.size = 'lg';
            }

            return iconAttrs;
        },
    },
};
</script>

<style lang="scss">
    .vs-warning {
        position: relative;
        display: flex;
        align-items: flex-end;
        justify-content: flex-start;
        text-align: left;
        padding: $spacer-9;
        background: $color-gray-tint-7;
        height: 100%;

        &--small {
            padding: $spacer-5;

            .vs-warning__content {
                max-width: 100%;
            }

            .vs-warning__button {
                margin-top: $spacer-3;
            }
        }

        &--dark {
            color: $color-white;
            background: rgba(0,0,0,0.8);
        }

        &--right {
            justify-content: flex-end;

            .vs-warning__button {
                display: flex;
                margin-left: auto;
            }
        }

        &__icon {
            margin-right: $spacer-3;
        }

        &__button {
            margin-top: $spacer-9;
        }

        &__content {
            display: flex;
            // flex-direction: row;
            align-items: flex-start;
            justify-content: flex-start;
            max-width: 80%;

            & > div p:last-of-type {
                margin-bottom: 0;
            }
        }

        /** override OneTrust styles **/
        // &__cookie-trigger {
        //     color: $color-yellow !important;
        //     font-weight: $font-weight-normal !important;
        //     background: transparent;
        //     border: none;
        //     box-shadow: none;

        //     &:focus {
        //         @extend %outline-link-focus-dark;
        //     }
        // }

        // @include media-breakpoint-up(sm) {
        //     &--row {
        //         padding: $spacer-5;
        //     }
        // }

        // @include media-breakpoint-up(md) {
        //     &--row {
        //         .vs-warning__icon {
        //             margin: 0 $spacer-5 0 0;
        //         }
        //     }
        // }
    }
</style>
