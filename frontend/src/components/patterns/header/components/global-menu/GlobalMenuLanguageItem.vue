<template>
    <VsDropdownItem
        class="vs-global-menu__languages__item"
        :href="languageLink"
    >
        {{ languageName }}
    </VsDropdownItem>
</template>

<script>
import VsDropdownItem from '../../../dropdown/DropdownItem';

export default {
    name: 'VsGlobalMenuLanguageItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsDropdownItem,
    },
    props: {
        languageName: {
            type: String,
            default: null,
        },
        languageLink: {
            type: String,
            default: null,
        },
    },
};
</script>

<style lang="scss">
.vs-global-menu__languages {
    position: initial;

    @include media-breakpoint-up(lg) {
        position: relative;
    }
    .dropdown-menu {
        width: 100vw;
        background: $color-purple;
        font-size: $font-size-sm;
        transition: all ease-in-out 0.3s;
        margin: 0;
        max-height: 0;
        display: block;
        overflow: hidden;
        opacity: 0;
        transform: translate3d(0px, 0px, 0px) !important;

        .vs-global-menu__languages__item {
            &:not(:last-of-type) {
                border-bottom: 1px solid $color-purple-tint-3;
            }

            .dropdown-item {
                padding: 1rem;
                color: white;
                text-decoration: none;

                &:hover {
                    background: $color-purple-shade-2;
                }

                &:focus {
                    outline: 3px solid $color-purple-tint-5;
                    outline-offset: -3px;
                    background: $color-purple;
                }
            }
        }

        &.show {
            max-height: 700px;
            opacity: 1;
            transform: translate3d(0px, 28px, 0px) !important;
            border: none;
            padding: 0;

            @include media-breakpoint-up(lg) {
                transform: translate3d(0px, 34px, 0px) !important;
            }
        }

        @include media-breakpoint-up(lg) {
            width: 100%;
            transform: translate3d(0px, 0px, 0px) !important;
        }
    }
}
@include no-js {
    .vs-global-menu__languages {
        display: block;

        .dropdown-menu {
            @extend .show;
            position: initial;
            display: block;
            width: 100vw;
            border: none;
            opacity: 1;
            max-height: 700px;
            transform: translate3d(0px, 0px, 0px) !important;
            text-align: center;

            .vs-global-menu__languages__item {
                display: block;
            }
        }

        .dropdown-toggle {
            display: none;
        }

        .dropdown-item {
            color: white;

            &:hover {
                background: $color-purple-shade-2;
            }
        }

        .vs-global-menu__languages__label {
            display: inline;
            color: white;
            padding: 16px;
        }

        @include media-breakpoint-up(sm) {
            .dropdown-menu {
                display: inline-flex;
                flex-wrap: wrap;
                text-align: left;

                .vs-global-menu__languages__item {
                    display: inline;
                }
            }
        }
    }
}
</style>

<docs>
  ```
    <vs-global-menu-language-item languageName="English" languageLink="site/">
    </vs-global-menu-language-item>
  ```
</docs>
