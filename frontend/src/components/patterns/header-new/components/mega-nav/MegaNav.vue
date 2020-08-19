<!-- eslint-disable -->
<template>
    <div class="vs-mega-nav bg-white">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="8"
                    md="4"
                    lg="3"
                >
                    <VsSvgLink
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
                    class="vs-mega-nav__menu"
                >
                    <VsMegaNavTopMenu>
                        <!-- @slot for top menu list items  -->
                        <slot name="mega-nav-top-menu-items" />
                    </VsMegaNavTopMenu>
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

export default {
    name: 'VsMegaNav',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsCol,
        VsRow,
        VsContainer,
        VsSvgLink,
    },
};
</script>

<style lang="scss">
.vs-mega-nav {
    position: relative;
    display: flex;
    align-items: center;
    height: 55px;
    @extend %default-box-shadow;

    &__logo {
        display: inline-block;
    }

    .vs-mega-nav__menu{
        position: initial;
    }
}

</style>

<docs>
  ```jsx
  ```
</docs>
