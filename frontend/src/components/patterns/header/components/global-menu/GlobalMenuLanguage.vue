<template>
    <VsDropdown class="vs-global-menu__languages">
        <template v-slot:button-content>
            <VsIcon
                class="vs-global-menu__languages__icon"
                name="globe"
                variant="light"
                size="xxs"
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
    created() {
        document.cookie = `vs_locale=${this.localeCookie}`;
        document.cookie = `googtrans=${this.translationCookie}`;
    },
};
</script>

<style lang="scss">
.vs-global-menu__languages {
    .dropdown-toggle {
        padding: 0.3rem $spacer-1;
        font-size: $font-size-sm;
        background: $color-purple;
        border: none;
        max-width: 130px;

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
            padding: $spacer-2 $spacer-1;
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
    padding-left: 0.5rem;
    display: none;

    @include media-breakpoint-up(lg) {
        display: inline;
    }
}

.vs-global-menu__languages__label {
    display: none;
}
</style>

<docs>
  ```
    <vs-global-menu-language>
        <vs-global-menu-language-item languageName="English">
        </vs-global-menu-language-item>
        <vs-global-menu-language-item languageName="Deutsch">
        </vs-global-menu-language-item>
        <vs-global-menu-language-item languageName="Español">
        </vs-global-menu-language-item>
        <vs-global-menu-language-item languageName="Français">
        </vs-global-menu-language-item>
        <vs-global-menu-language-item languageName="Italiano">
        </vs-global-menu-language-item>
        <vs-global-menu-language-item languageName="Nederlands">
        </vs-global-menu-language-item>
    </vs-global-menu-language>
  ```
</docs>
