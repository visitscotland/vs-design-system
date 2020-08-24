<template>
    <BNavbar
        class="vs-mega-nav"
        toggleable="lg"
    >
        <VsContainer>
            <VsSvgLink
                class="vs-mega-nav__logo mr-lg-6 mr-xl-12"
                logo-alt-text="VisitScotland Home"
                href="/"
                svg-fill="700e57"
                svg-path="visitscotland"
            />

            <BNavbarToggle
                class="vs-mega-nav__toggle"
                target="vs-mega-nav__collapse"
                @click.native="toggleCollapse"
            >
                <VsIcon
                    v-if="isCollapsed"
                    name="close"
                    variant="dark"
                    size="sm"
                />
                <VsIcon
                    v-else
                    name="bars-mobile-menu"
                    variant="dark"
                    size="md"
                />
            </BNavbarToggle>

            <BCollapse
                id="vs-mega-nav__collapse"
                is-nav
            >
                <VsMegaNavMobileList class="d-lg-none">
                    <slot name="mega-nav-mobile-items" />
                </VsMegaNavMobileList>

                <VsMegaNavTopMenu
                    class="d-none d-lg-flex"
                >
                    <slot name="mega-nav-top-menu-items" />
                </VsMegaNavTopMenu>
            </BCollapse>
        </VsContainer>
    </BNavbar>
</template>

<script>
import { VsContainer } from '@components/elements/layout';
import VsSvgLink from '@components/patterns/svg-link/SvgLink';
import {
    BNavbar, BNavbarToggle, BCollapse,
} from 'bootstrap-vue';

export default {
    name: 'VsMegaNav',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsContainer,
        VsSvgLink,
        BNavbar,
        BNavbarToggle,
        BCollapse,
    },
    data() {
        return {
            isCollapsed: false,
        };
    },
    methods: {
        toggleCollapse() {
            this.isCollapsed = !this.isCollapsed;
        },
    },
};
</script>

<style lang="scss">

@import "~bootstrap/scss/navbar";

.vs-mega-nav {
    position: relative;
    background: $color-white;
    @extend %default-box-shadow;

    @include media-breakpoint-up(lg) {
        padding: 0;
    }

    &__logo {
        padding: $spacer-1 0;

        svg{
            width: 184px;
        }
    }

    &__menu {
        position: static;
    }

    &__toggle {
        border: 0;
        font-size: 0;
        padding: $spacer-1;
        height: 32px;
        width: 32px;

        &:focus{
            outline-color: $color-pink;
        }
    }
}
</style>

<docs>
  ```jsx
  ```
</docs>
