<template>
    <li
        class="vs-mega-nav-top-menu-item"
        data-test="vs-mega-nav__top-menu__item"
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
                <a
                    v-if="href && ctaText"
                    :href="href"
                    class="vs-mega-nav__cta-link"
                    data-test="vs-mega-nav__cta-link"
                >
                    {{ ctaText }}
                </a>
                <hr>
            </template>

            <template #dropdownContent>
                <!-- @slot Slot for dropdown menu list content -->
                <slot name="dropdownContent" />
            </template>
        </VsMegaNavDropdown>
    </li>
</template>

<script>
import VsMegaNavDropdown from '@components/patterns/header/components/mega-nav/MegaNavDropdown';

/**
 *  Mega nav top level menu items with a slots for toggle button and dropdown content
 *
 * @displayName MegaNav Top Menu Item
 */
export default {
    name: 'VsMegaNavTopMenuItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsMegaNavDropdown,
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

.vs-mega-nav-top-menu-item {
    @include media-breakpoint-up(xl) {
        margin-right: $spacer-6;

        &:last-of-type {
            margin-right: 0;
        }
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
