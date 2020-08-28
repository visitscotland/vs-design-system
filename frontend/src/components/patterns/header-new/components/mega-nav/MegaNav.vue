<!-- eslint-disable -->
<template>
    <div class="vs-mega-nav bg-white">
        <VsContainer>
            <VsRow class="align-items-center justify-content-sm-end">
                <VsCol
                    cols="8"
                    md="4"
                    lg="3"
                >
                    <VsSvgLink
                        class="vs-mega-nav__logo"
                        logo-alt-text="VisitScotland Home"
                        href="/"
                        svg-fill="700e57"
                        svg-path="visitscotland"
                    />
                </VsCol>
                <VsCol
                    cols="4"
                    md="8"
                    lg="9"
                    class="vs-mega-nav__menu d-none d-lg-block"
                >
                    <VsMegaNavTopMenu>
                        <!-- @slot for top menu list items  -->
                        <slot name="mega-nav-top-menu-items" />
                    </VsMegaNavTopMenu>
                </VsCol>

                <VsCol
                    cols="4"
                    md="8"
                    lg="9"
                    class="d-flex d-lg-none justify-content-end position-static"
                >
                    <VsMegaNavDropdownToggle>
                        <template slot="toggle-btn">
                            <VsIcon
                                name="bars-mobile-menu"
                                variant="dark"
                                size="md"
                            />
                        </template>
                    </VsMegaNavDropdownToggle>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import {
    VsCol, VsRow, VsContainer,
} from '@components/elements/layout';
import VsSvgLink from '@components/patterns/svg-link/SvgLink';
import VsMegaNavDropdownToggle from '@components/patterns/header-new/components/mega-nav/MegaNavDropdownToggle';

export default {
    name: 'VsMegaNav',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsCol,
        VsRow,
        VsContainer,
        VsSvgLink,
        VsMegaNavDropdownToggle,
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
@import '~bootstrap/scss/navbar';

// .vs-mega-nav {
//     position: relative;
//     background: $color-white;
//     @extend %default-box-shadow;
//     padding: 0.25rem 0;

//     @include media-breakpoint-up(lg) {
//         padding: 0;
//     }

//     &__logo {
//         padding: $spacer-1 0;

//         svg {
//             width: 184px;
//         }
//     }

//     &__menu {
//         position: static;
//     }

//     &__toggle {
//         border: 0;
//         font-size: 0;
//         padding: $spacer-1;
//         height: 32px;
//         width: 32px;

//         &:focus {
//             outline-color: $color-pink;
//         }
//     }
// }

.vs-mega-nav {
    position: relative;
    display: flex;
    align-items: center;
    height: 55px;
    @extend %default-box-shadow;

    &__logo svg {
        max-width: 184px;
    }

    .vs-mega-nav__menu {
        position: static;
    }
}
</style>

<docs>
  ```jsx
  ```
</docs>
