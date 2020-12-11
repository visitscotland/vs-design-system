<template>
    <li
        class="vs-mega-nav-list-item"
        data-test="vs-mega-nav-list-item"
        :class="navItemClass"
    >
        <VsLink
            :href="href"
            :role="href === '#' ? 'heading' : ''"
        >
            <!-- @slot Default slot for nav link content  -->
            <slot />
        </VsLink>
    </li>
</template>

<script>
import VsLink from '@components/elements/link/Link';

/**
 *  Meganav list items with link and slot for link content
 *
 * @displayName MegaNav List Item
 */
export default {
    name: 'VsMegaNavListItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsLink,
    },
    props: {
        /**
         * The URL for the nav list link
         */
        href: {
            type: String,
            default: '#',
        },
        /**
         * Check if link is for subheading
         */
        subheadingLink: {
            type: Boolean,
            default: false,
        },
    },
    computed: {
        navItemClass() {
            return {
                'vs-mega-nav-list-item__heading': this.href === '#',
                'vs-mega-nav-list-item__link': this.href !== '#',
                'vs-mega-nav-list-item__subheading-link': this.subheadingLink,
            };
        },
    },
};
</script>

<style lang="scss">
.vs-mega-nav-list-item{
    position: relative;
    list-style-type: none;
    line-height: $line-height-s;
    font-size: $h4-font-size;

    .vs-link--variant-primary{
        text-decoration: none;
        display: block;
        padding: 0.125rem $spacer-5;
        border: 2px solid $color-white;

        &:focus{
            outline: 0;
            border: 2px solid $color-pink;
        }
    }

    &__heading{
        border-bottom: 1px solid #d9d9d9;
        margin-bottom: $spacer-1;
        line-height: $standard-line-height;

        .vs-link--variant-primary{
            color: $color-secondary-gray-shade-3;
            font-size: $h3-font-size;
            cursor: text;
        }
    }

    &__link{
        .vs-link--variant-primary{
            color: $color-secondary-gray;
            transition: 0.2s color;

            &:hover{
                color: $color-pink;

                &:after{
                   height: 100%;
                }
            }

            &:after{
                content: "";
                position: absolute;
                display: block;
                top: 0;
                left: 0;
                width: 6px;
                height: 0;
                background: $color-pink;
                transition: height 0.15s linear;
            }
        }
    }

    &__subheading-link{
        .vs-link--variant-primary{
            color: $color-pink;
        }
    }
}

@include no-js {
    .vs-mega-nav-list-item{
        &__heading{
            .vs-link--variant-primary{
                font-size: $h2-font-size;
            }
        }
    }
}
</style>

<docs>
  ```jsx
    <VsMegaNavList style="background-color: white;">
        <VsMegaNavListItem slot="navListHeading">
            Attractions
        </VsMegaNavListItem>

        <VsMegaNavListItem slot="navListItems" href="http://visitscotland.com">
            20 Top Attractions
        </VsMegaNavListItem>

        <VsMegaNavListItem slot="navListItems" href="http://visitscotland.com">
            Arts & Culture
        </VsMegaNavListItem>

        <VsMegaNavListItem slot="navListItems" href="http://visitscotland.com">
            Castles
        </VsMegaNavListItem>

        <VsMegaNavListItem slot="navListItems" href="http://visitscotland.com">
            Gardens & Parks
        </VsMegaNavListItem>

        <VsMegaNavListItem slot="navListItems" href="http://visitscotland.com">
            Museums
        </VsMegaNavListItem>

        <VsMegaNavListItem
            href="http://visitscotland.com"
            subheading-link
            slot="navHeadingCtaLink"
        >
            All Attractions
        </VsMegaNavListItem>
    </VsMegaNavList>
  ```
</docs>
