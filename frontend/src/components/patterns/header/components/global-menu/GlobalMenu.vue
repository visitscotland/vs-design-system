<template>
    <div class="vs-global-menu">
        <VsContainer
            fluid="lg"
            class="px-0 px-sm-3"
        >
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

    &__wrapper {
        position: initial;
        display: flex;
        align-items: center;

        @include media-breakpoint-down(md) {
            padding-left: 0;
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
  ```
    <vs-global-menu
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
        </span>
    </vs-global-menu>
  ```
</docs>
