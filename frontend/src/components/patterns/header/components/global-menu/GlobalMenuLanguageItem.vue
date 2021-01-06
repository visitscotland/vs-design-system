<template>
    <VsDropdownItem
        class="vs-global-menu__languages__item"
        :href="languageLink"
    >
        {{ languageName }}
    </VsDropdownItem>
</template>

<script>
import VsDropdownItem from '../../../dropdown/components/DropdownItem';

/**
 * TODO: Document usage
 *
 * @displayName Global Menu Language Item
 */
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
.vs-global-menu__languages__item {
    &:not(:last-of-type) {
        border-bottom: 1px solid $color-purple-tint-3;
    }

    .dropdown-item {
        padding: $spacer-4;
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

@include no-js {
    .vs-global-menu__languages__item {
        display: block;
    }

    @include media-breakpoint-up(sm) {
        display: inline;
    }
}
</style>

<docs>
  ```
    <VsGlobalMenuLanguageItem language-name="English" languageLink="site/"/>
  ```
</docs>
