<template>
    <div class="vs-global-menu">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    class="vs-global-menu__wrapper"
                >
                    <!-- Small Screens Menu -->
                    <VsGlobalMenuDropdown
                        class="d-lg-none"
                        :active-site="activeSite"
                        :dropdown-label="dropdownLabel"
                        :websites="websites"
                    />

                    <!-- Large Screens Menu -->
                    <VsGlobalMenuList
                        class="d-none d-lg-flex"
                        :active-site="activeSite"
                        :websites="websites"
                    />

                    <!-- @slot The content you want to appear as the
                        second element on the global menu, after the websites list,
                        goes here. -->
                    <slot name="second-menu-item" />

                    <!-- @slot The content you want to appear as the
                        third element on the global menu, after the websites list,
                        goes here. -->
                    <slot name="third-menu-item" />
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import {
    VsContainer, VsRow, VsCol,
} from '@components/elements/layout';
import VsGlobalMenuDropdown from './GlobalMenuDropdown';
import VsGlobalMenuList from './GlobalMenuList';

/**
 * This component is the main Global Nav Wrapper for the top of the page.
 * It holds the Our Websites and slots for Login and Language Change functionalities.
 *
 * @displayName Global Menu
 */

export default {
    name: 'VsGlobalMenu',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsContainer,
        VsRow,
        VsCol,
        VsGlobalMenuDropdown,
        VsGlobalMenuList,
    },
    props: {
        /**
         * Determines the active website
         */
        activeSite: {
            type: String,
            required: true,
        },
        /**
         * Translation text for the "Our Websites" label
         */
        dropdownLabel: {
            type: String,
            default: 'Our Websites',
        },
    },
    data() {
        return {
            websites: [
                {
                    siteName: 'VisitScotland',
                    siteUrl: 'https://www.visitscotland.com/',
                },
                {
                    siteName: 'Business Events',
                    siteUrl: 'https://businessevents.visitscotland.com/',
                },
                {
                    siteName: 'Travel Trade',
                    siteUrl: 'https://traveltrade.visitscotland.org/',
                },
                {
                    siteName: 'Media Centre',
                    siteUrl: 'https://media.visitscotland.org/',
                },
                {
                    siteName: 'Corporate',
                    siteUrl: 'https://www.visitscotland.org/',
                },
            ],
        };
    },
};
</script>

<style lang="scss">
.vs-global-menu {
    width: 100%;
    background: $color-purple;
    color: white;
    position: relative;
    font-size: $font-size-sm;
    height: 28px;
    display: flex;
    align-items: center;

    @include media-breakpoint-up(lg) {
        height: 35px;
    }

    @include media-breakpoint-down(md) {
        .container {
            padding: 0;
            margin: 0;
            max-width: 100%;
        }

        .row {
            margin: 0 !important;
        }

        .col-12 {
            padding: 0 !important;
        }
    }

    &__wrapper {
        position: initial;
        display: flex;
        align-items: center;

        @include media-breakpoint-down(md) {
            width: 100%;
            padding: 0;
            margin: 0;
        }
    }
}

@include no-js {
    .vs-global-menu {
        height: auto;
        font-size: $font-size-base;

        &__wrapper {
            display: flex;
            flex-wrap: wrap;

            .vs-global-menu__websites {
                display: none;
            }

            .vs-list {
                display: flex !important;
            }
        }
    }
}
</style>

<docs>
  ```jsx
    <VsGlobalMenu
        dropdown-label="I nostri siti"
        active-site="https://www.visitscotland.com/"
    ><span
            slot="second-menu-item"
            style="min-width: 50px;"
        >
            User... (Not you?)
        </span>

        <span
            slot="third-menu-item"
        >
        <VsGlobalMenuLanguage>
            <VsGlobalMenuLanguageItem languageName="English">
            </VsGlobalMenuLanguageItem>
            <VsGlobalMenuLanguageItem languageName="Deutsch">
            </VsGlobalMenuLanguageItem>
            <VsGlobalMenuLanguageItem languageName="Español">
            </VsGlobalMenuLanguageItem>
            <VsGlobalMenuLanguageItem languageName="Français">
            </VsGlobalMenuLanguageItem>
            <VsGlobalMenuLanguageItem languageName="Italiano">
            </VsGlobalMenuLanguageItem>
            <VsGlobalMenuLanguageItem languageName="Nederlands">
            </VsGlobalMenuLanguageItem>
        </VsGlobalMenuLanguage>
        </span>
    </VsGlobalMenu>
  ```
</docs>
