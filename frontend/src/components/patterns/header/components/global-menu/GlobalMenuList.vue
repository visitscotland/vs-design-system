<template>
    <VsList
        unstyled
        inline
        class="
            vs-global-menu__list
        "
    >
        <li
            v-for="site in websites"
            :key="site.siteName"
            class="vs-global-menu__list_item"
            :class="[
                (site.siteUrl == activeSite)
                    ? 'vs-global-menu__list_item--active d-none d-lg-block'
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
</template>

<script>
import VsList from '@components/elements/list';

/**
 * This component is the list used inside the Global Menu component.
 * It holds the list of websites to be shown on bigger devices.
 *
 * @displayName Global Menu List
 */
export default {
    name: 'VsGlobalMenuList',
    status: 'prototype',
    release: '0.1.0',
    components: {
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
         * List of websites to be rendered
         */
        websites: {
            type: Array,
            required: true,
        },
    },
};
</script>

<style lang="scss">
.vs-global-menu__list {
    position: initial;
    flex: auto;

    a {
        color: white;
        text-decoration: none;

        &:hover {
            background: $color-purple-shade-2;
        }
    }

    &_item {
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
    .vs-global-menu__list {
        flex-wrap: wrap;

        .vs-list {
            display: flex !important;
        }

        &_item {
            display: flex;
            flex: auto;
            flex-basis: 100%;

            border-bottom: 1px solid $color-purple-tint-3;

            @include media-breakpoint-up(md) {
                flex-basis: auto;
                border: none;
                margin: 0 $spacer-4;
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

</style>

<docs>
  ```jsx
    <VsGlobalMenu
        dropdown-label="Our websites"
        active-site="https://www.visitscotland.com/"
    >
        <span
            slot="third-menu-item"
            style="min-width: 50px;"
        >
            EN
        </span>
    </VsGlobalMenu>
  ```
</docs>
