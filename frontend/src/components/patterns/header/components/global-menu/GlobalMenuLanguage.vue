<template>
    <VsDropdown
        class="vs-global-menu__languages"
    >
        <template
            v-slot:button-content
        >
            <VsIcon
                class="d-none d-lg-inline"
                name="information"
                variant="light"
                size="xxs"
            />
            <span class="d-lg-none">
                {{ selectedLanguage }}
            </span>
            <!-- Tablet/Desktop -->
            <span class="d-none d-lg-inline">
                Language: {{ selectedLanguage }}
            </span>
        </template>
        <VsDropdownItem
            v-for="lang in websiteLanguages"
            :key="lang.languageTag"
        >
            <!-- Mobile Text -->
            <div class="d-lg-none">
                {{ lang.languageTag }}
            </div>
            <!-- Tablet/Desktop -->
            <div class="d-none d-lg-block">
                {{ lang.languageName }}
            </div>
        </VsDropdownItem>
    </VsDropdown>
</template>

<script>
import VsDropdown from '../../../dropdown/Dropdown';
import VsDropdownItem from '../../../dropdown/DropdownItem';

export default {
    name: 'VsGlobalMenuLanguage',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsDropdown,
        VsDropdownItem,
    },
    data() {
        return {
            selectedLanguage: 'EN',
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
.vs-global-menu__languages .btn {
    padding: 0;
    background: $color-purple;
    border: none;
    max-width: 130px;

    &:hover {
        background: $color-purple-shade-2;
    }
}

.vs-global-menu__languages {
    position: initial;

    & .btn {
        padding: 0.5rem;
        font-size: $font-size-sm;

        @include media-breakpoint-up(lg) {
            font-size: $small-font-size;
        }

        &-secondary:not(:disabled):not(.disabled):active {
            background: $color-purple-shade-2;
        }

        &:focus {
            outline: 3px solid $color-purple-tint-5;
            outline-offset: -3px;
            box-shadow: none;
        }
    }

    &.show .btn,
    &.show .btn:active,
    &.show .btn:focus {
        background: $color-purple-shade-2;
    }

    ul:focus {
        outline: 3px solid $color-purple-tint-5;
        outline-offset: -3px;
    }

    .dropdown-menu {
        min-width: auto;
        width: 100%;
        background: $color-purple;

        &.show {
            transform: translate3d(0px, 32px, 0px) !important;
            border: none;
            padding: 0;

            li {
                border-bottom: 1px solid $color-purple-tint-3;

                &:last-of-type {
                    border: none;
                }

                a {
                    padding: 1rem;
                    color: white;
                    text-decoration: none;

                    &:hover, &:focus {
                        background: $color-purple-shade-2;
                    }

                    &:focus {
                        outline: 3px solid $color-purple-tint-5;
                        outline-offset: -3px;
                    }
                }
            }
        }
    }
}

.vs-global-menu__languages {
    @include media-breakpoint-up(lg) {
        position: relative;
    }
}
</style>

<docs>
  ```
    <vs-global-menu-language></vs-global-menu-language>
  ```
</docs>
