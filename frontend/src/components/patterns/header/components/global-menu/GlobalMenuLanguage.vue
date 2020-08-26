<template>
    <VsDropdown
        class="vs-global-menu__languages"
    >
        <template
            v-slot:button-content
        >
            <VsIcon
                class="vs-global-menu__languages__icon"
                name="globe"
                variant="light"
                size="xxs"
            />
            <!-- Tablet/Desktop -->
            <span class="vs-global-menu__languages__text">
                Language:
            </span>
            {{ selectedLanguage }}
        </template>
        <slot />
    </VsDropdown>
</template>

<script>
import VsDropdown from '../../../dropdown/Dropdown';

export default {
    name: 'VsGlobalMenuLanguage',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsDropdown,
    },
    data() {
        return {
            selectedLanguage: ' EN',
            websiteLanguages: [
                {
                    languageTag: 'EN',
                    languageName: 'English',
                },
                {
                    languageTag: 'DE',
                    languageName: 'Deutsch',
                },
                {
                    languageTag: 'ES',
                    languageName: 'Español',
                },
                {
                    languageTag: 'FR',
                    languageName: 'Français',
                },
                {
                    languageTag: 'IT',
                    languageName: 'Italiano',
                },
                {
                    languageTag: 'NE',
                    languageName: 'Nederlands',
                },
            ],
        };
    },
};
</script>

<style lang="scss">
.vs-global-menu__languages {
    position: initial;

    .dropdown-toggle {
        padding: 0.5rem;
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
            box-shadow: none;
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
