<template>
    <VsDropdownItem
        class="vs-global-menu__languages__item"
        :href="languageLink"
        @click.native="setLanguage()"
    >
        {{ languageName }}
    </VsDropdownItem>
</template>

<script>
import cookieMixin from '@mixins/cookieMixin';
import VsDropdownItem from '../../../dropdown/DropdownItem';

export default {
    name: 'VsGlobalMenuLanguageItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsDropdownItem,
    },
    mixins: [
        cookieMixin,
    ],
    props: {
        /**
         * Language name to be shown.
         * Example: "English"
         */
        languageName: {
            type: String,
            default: null,
        },
        /**
         * Link for the translated page version
         */
        languageLink: {
            type: String,
            default: null,
        },
        /**
         * Language locale string.
         * Example: "en_GB"
         */
        language: {
            type: String,
            default: null,
        },
    },
    methods: {
        setLanguage() {
            console.log('setLanguage called on item');
            const localeExists = this.cookieExists('vs_locale');
            const googleExists = this.cookieExists('googtrans');

            this.setCookie('vs_locale', this.localeCookie, !localeExists);
            this.setCookie('googtrans', this.translationCookie, !googleExists);
            console.log(`vs_locale, ${this.localeCookie}, ${!localeExists}`);
            console.log(`googtrans, ${this.translationCookie}, ${!googleExists}`);
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
