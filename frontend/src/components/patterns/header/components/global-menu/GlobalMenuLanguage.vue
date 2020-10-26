<template>
    <VsDropdown class="vs-global-menu__languages">
        <template v-slot:button-content>
            <VsIcon
                class="vs-global-menu__languages__icon"
                name="globe"
                variant="light"
                size="xxs"
                focusable="false"
            />
            <!-- Tablet/Desktop -->
            <span class="vs-global-menu__languages__text">{{ languageLabel }}</span>
            <span class="vs-global-menu__languages__selected">{{ selectedLanguage }}</span>
        </template>

        <!-- No JS Version -->
        <span class="vs-global-menu__languages__label">
            <VsIcon
                name="globe"
                variant="light"
                size="xxs"
                focusable="false"
            />
            {{ languageLabel }}
        </span>
        <slot />
    </VsDropdown>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
import VsDropdown from '../../../dropdown/Dropdown';

export default {
    name: 'VsGlobalMenuLanguage',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsDropdown,
        VsIcon,
    },
    props: {
        languageLabel: {
            type: String,
            default: 'Language',
        },
        currentLocale: {
            type: String,
            default: 'en_GB',
        },
    },
    computed: {
        selectedLanguage() {
            return this.currentLocale.substr(0, 2).toUpperCase();
        },
        localeCookie() {
            return this.currentLocale.replace('_', '-').toLowerCase();
        },
        translationCookie() {
            return `/en/${this.currentLocale.substr(0, 2).toLowerCase()}`;
        },
    },
    mounted() {
        this.setCookie('vs_locale', this.localeCookie);
        this.setCookie('googtrans', this.translationCookie);
    },
    methods: {
        cookieExists(cookie) {
            return (document.cookie.indexOf(`${cookie}=`) >= 0);
        },
        setCookie(name, value) {
            const expiryDate = new Date();
            expiryDate.setFullYear(expiryDate.getFullYear() + 1);

            let cookieString = '';
            cookieString = `${name}=${value};`;
            cookieString += 'path=/;';

            if (!this.cookieExists(name)) {
                cookieString += `expires=${expiryDate};`;
            };

            document.cookie = cookieString;
        },
    },
};
</script>

<style lang="scss">
.vs-global-menu__languages {
    position: static;

    .btn {
        @include media-breakpoint-up(lg) {
            height: 35px;
        }
    }

    .dropdown-menu {
        width: 100%;
        background: $color-purple;
        font-size: $font-size-sm;
        transition: all ease-in-out 0.3s;
        margin: 0;
        max-height: 0;
        overflow: hidden;
        opacity: 0;
        transform: translate3d(0px, 0px, 0px) !important;

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
            min-width: 130px;
            transform: translate3d(0px, 0px, 0px) !important;
        }
    }

    .dropdown-toggle {
        padding: 0.3rem $spacer-2;
        font-size: $font-size-sm;
        background: $color-purple;
        border: none;

        &-secondary:not(:disabled):not(.disabled):active {
            background: $color-purple-shade-2;
        }

        &:focus {
            outline: 3px solid $color-purple-tint-5;
            outline-offset: -3px;
            box-shadow: none !important;
        }

        &:hover {
            background: $color-purple-shade-2;
        }
    }

    &.show {
        .dropdown-toggle,
        .dropdown-toggle:active,
        .dropdown-toggle:focus {
            background: $color-purple-shade-2;
        }
    }
}

.vs-global-menu__languages {
    @include media-breakpoint-up(lg) {
        position: relative;
    }

    .dropdown-toggle {
        @include media-breakpoint-up(lg) {
            padding: $spacer-2 $spacer-4;
        }
    }
}

.vs-global-menu__languages__icon {
    display: none;

    @include media-breakpoint-up(lg) {
        display: inline;
    }
}

.vs-global-menu__languages__text {
    padding-left: $spacer-2;
    display: none;

    @include media-breakpoint-up(lg) {
        display: inline;
    }
}

.vs-global-menu__languages__label {
    display: none;
}

@include no-js {
    .vs-global-menu__languages {
        display: block;

        .dropdown-menu {
            @extend .show;
            position: initial;
            display: block;
            border: none;
            opacity: 1;
            max-height: 700px;
            transform: translate3d(0px, 0px, 0px) !important;
            text-align: center;

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
            padding: $spacer-4;
        }

        @include media-breakpoint-up(sm) {
            .dropdown-menu {
                display: inline-flex;
                flex-wrap: wrap;
                text-align: left;
            }
        }
    }
}
</style>

<docs>
  ```
    <VsGlobalMenuLanguage>
        <VsGlobalMenuLanguageItem language-name="English" />
        <VsGlobalMenuLanguageItem language-name="Deutsch" />
        <VsGlobalMenuLanguageItem language-name="Español" />
        <VsGlobalMenuLanguageItem language-name="Français" />
        <VsGlobalMenuLanguageItem language-name="Italiano" />
        <VsGlobalMenuLanguageItem language-name="Nederlands" />
    </VsGlobalMenuLanguage>
  ```
</docs>
