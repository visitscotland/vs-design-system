<template>
    <div class="vs-global-menu">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    class="vs-global-menu__wrapper"
                >
                    <!-- Small Screens Menu -->
                    <VsDropdown
                        :text="dropdownLabel"
                        class="
                            vs-global-menu__websites
                            d-lg-none
                        "
                    >
                        <VsDropdownItem
                            v-for="site in ourWebsites"
                            :key="site.siteName"
                            :href="site.siteUrl"
                            :target="
                                (site.siteUrl == activeSite)
                                    ? '_self'
                                    : '_blank'
                            "
                        >
                            {{ site.siteName }}
                        </VsDropdownItem>
                    </VsDropdown>

                    <!-- Large Screens Menu -->
                    <VsList
                        unstyled
                        inline
                        class="
                            vs-global-menu__websites
                            d-none
                            d-lg-flex
                        "
                    >
                        <li
                            v-for="site in ourWebsites"
                            :key="site.siteName"
                            class="vs-global-menu__websites__item"
                            :class="[
                                (site.siteUrl == activeSite)
                                    ? 'vs-global-menu__websites__item--active'
                                    : ''
                            ]"
                        >
                            <a
                                :target="
                                    (site.siteUrl == activeSite)
                                        ? '_self'
                                        : '_blank'
                                "
                                :href="site.siteUrl"
                            >{{ site.siteName }}</a>
                        </li>
                    </VsList>

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
import VsList from '@components/elements/list';
import VsDropdown from '../../../dropdown/Dropdown';
import VsDropdownItem from '../../../dropdown/DropdownItem';

/**
 * This component is the main Global Nav Wrapper for the top of the page.
 * It holds the Our Websites and slots for Login and Language Change functionalities.
 */

export default {
    name: 'VsGlobalMenu',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsContainer,
        VsRow,
        VsCol,
        VsDropdown,
        VsDropdownItem,
        VsList,
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
            ourWebsites: [
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

.vs-global-menu__websites,
.vs-global-menu__websites .btn {
    padding: 0;
    background: transparent;
    border: none;
}

.vs-global-menu__websites {
    position: initial;
    flex: auto;

    a {
        color: white;
        text-decoration: none;

        &:hover {
            background: $color-purple-shade-2;
        }
    }

    .btn {
        padding: 0.3rem $spacer-5;
        font-size: $font-size-sm;
        display: flex;
        align-items: center;

        &-secondary:not(:disabled):not(.disabled):active {
            background: $color-purple-shade-2;
        }

        &:focus {
            outline: 3px solid $color-pink-tint-5;
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
        outline: 3px solid $color-pink-tint-5;
        outline-offset: -3px;
    }


    .dropdown-menu {
        min-width: auto;
        width: 100vw;
        background: $color-purple;
        transition: all ease-in-out 0.3s;
        max-height: 0;
        display: block;
        overflow: hidden;
        opacity: 0;
        transform: translate3d(0px, 0px, 0px);

        li {
            border-bottom: 1px solid $color-purple-tint-3;

            &:last-of-type {
                border: none;
            }

            a {
                padding: 0.7rem $spacer-5;
                color: white;
                text-decoration: none;
                font-size: $font-size-sm;

                &:hover, &:focus {
                    background: $color-purple-shade-2;
                }

                &:focus {
                    outline: 3px solid $color-pink-tint-5;
                    outline-offset: -3px;
                }
            }
        }

        &.show {
            max-height: 500px;
            opacity: 1;
            transform: translate3d(0px, 26px, 0px) !important;
            border: none;
            padding: 0;
        }
    }

    &__item {
        a {
            padding: 0.65rem $spacer_5;
            max-height: 35px;

            &:focus {
                outline: 3px solid $color-pink-tint-5;
                outline-offset: -3px;
            }

            &:active {
                background: white;
                color: $color-purple-shade-2;
                outline: none;
            }
        }

        &--active {
            a {
            background: $color-white;
            color: $color-purple-shade-2;

                &:hover {
                    color: white;
                }
                &:active {
                    background: $color-purple-shade-2;
                }
            }
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

            .vs-dropdown {
                display: none;
            }

            .vs-list {
                display: flex !important;
            }
        }

        &__websites {
            flex-wrap: wrap;

            &__item {
                display: flex;
                flex: auto;
                flex-basis: 100%;

                border-bottom: 1px solid $color-purple-tint-3;

                @include media-breakpoint-up(md) {
                    flex-basis: auto;
                    border: none;
                }

                a {
                    display: flex;
                    align-items: center;
                    flex: auto;
                    justify-content: center;
                    padding: 1.9rem $spacer-5;
                }
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
    >
        <span
            slot="second-menu-item"
            style="min-width: 50px;"
        >
            User... (Not you?)
        </span>

        <span
            slot="third-menu-item"
            style="min-width: 50px;"
        >
            EN
        </span>
    </vs-global-menu>
  ```
</docs>
