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
    .dropdown-menu {
        width: 100%;
        background: $color-purple;
        font-size: $font-size-sm;

        &.show {
            transform: translate3d(0px, 32px, 0px) !important;
            border: none;
            padding: 0;

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
