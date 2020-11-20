<template>
    <li
        class="vs-mega-nav-top-menu-item"
        data-test="vs-mega-nav-top-menu-item"
        ref="menuToggle"
    >
        <VsMegaNavDropdown
            menu-toggle-alt-text="Toggle Menu"
        >
            <template #buttonContent>
                <!-- @slot For top menu item button content -->
                <slot name="buttonContent" />
            </template>

            <template #ctaLink>
                <VsLink
                    v-if="href && ctaText"
                    :href="href"
                    class="vs-mega-nav-top-menu-item__cta-link"
                >
                    {{ ctaText }}
                </VsLink>
                <hr class="vs-mega-nav-top-menu-item__divider">
            </template>

            <template #dropdownContent>
                <!-- @slot Slot for dropdown menu list content -->
                <div class="vs-mega-nav-top-menu-item__columns-wrapper">
                    <slot name="dropdownContent" />
                </div>
            </template>
        </VsMegaNavDropdown>
    </li>
</template>

<script>
/**
 *  Mega nav top level menu items with a slots for toggle button and dropdown content
 */
import VsMegaNavDropdown from '@components/patterns/header/components/mega-nav/MegaNavDropdown';
import VsLink from '@components/elements/link/Link';

export default {
    name: 'VsMegaNavTopMenuItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsMegaNavDropdown,
        VsLink,
    },
    props: {
        /**
         * The URL for the top level CTA link
         */
        href: {
            type: String,
            default: '',
        },
        /**
         * The text to display for the CTA link
         */
        ctaText: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">

.vs-mega-nav-top-menu-item{
    @include media-breakpoint-up(xl) {
        margin-right: $spacer-6;

        &:last-of-type {
            margin-right: 0;
        }
    }

    &__cta-link{
        text-decoration: none;
        padding: 0.12rem $spacer-5;
        transition: 0.2s color;

        &:hover{
            color: $color-secondary-gray-shade-3;
        }
    }

    &__divider{
        margin: $spacer-3 0 $spacer-8;
    }

    &__columns-wrapper{
        display: flex;
        width: 100%;
        flex-flow: column wrap;
        height: 470px;
        overflow: hidden;
        align-content: start;
    }
}

</style>

<docs>
  ```jsx
    <div class="bg-white">
        <VsContainer>
            <VsRow class="align-items-center">
                <VsCol cols="12">
                    <VsMegaNavTopMenu>
                        <VsMegaNavTopMenuItem
                            href="/"
                            cta-text="A Map of Scotland"
                        >
                            <span slot="buttonContent">
                                Places to Go
                            </span>
                            <span slot="dropdownContent">
                                <ul>
                                    <li>
                                        Submenu Item
                                    </li>
                                </ul>
                            </span>
                        </VsMegaNavTopMenuItem>
                    </VsMegaNavTopMenu>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
  ```
</docs>
