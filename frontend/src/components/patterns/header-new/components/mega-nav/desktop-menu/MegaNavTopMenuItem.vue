<template>
    <li
        class="vs-mega-nav__item"
        ref="menuToggle"
    >
        <VsMegaNavDropdown>
            <template #button-content>
                <!-- @slot For top menu item button content -->
                <slot name="button-content" />
            </template>

            <template #cta-link>
                <a :href="href">{{ ctaText }}</a>
                <hr>
            </template>

            <template #dropdown-content>
                <!-- @slot Slot for dropdown menu list content -->
                <slot name="dropdown-content" />
            </template>
        </VsMegaNavDropdown>
    </li>
</template>

<script>
/**
 *  Mega nav top level menu items with a slots for toggle button and dropdown content
 */
import VsMegaNavDropdown from '@components/patterns/header-new/components/mega-nav/MegaNavDropdown';

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
@import '~bootstrap/scss/type';

.vs-mega-nav__item {
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
                            <span slot="button-content">
                                Places to Go
                            </span>
                            <span slot="dropdown-content">
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
