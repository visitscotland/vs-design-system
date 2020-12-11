<template>
    <VsList
        unstyled
        class="vs-mega-nav-top-menu"
        data-test="vs-mega-nav__top-menu"
    >
        <!-- @slot Default slot for top menu items -->
        <slot />
    </VsList>
</template>

<script>
import VsList from '@components/elements/list/List';

/**
 *  Mega nav top level menu list with a slot for top level menu items
 *
 * @displayName MegaNav Top Menu
 */
export default {
    name: 'VsMegaNavTopMenu',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsList,
    },
};
</script>

<style lang="scss">

.vs-mega-nav-top-menu {
    &.vs-list--unstyled{
        width: 100%;
        display: flex;
        margin-top: 0.75rem;
    }
}

 @include no-js {
    .vs-mega-nav__top-menu {
        &.vs-list.unstyled{
            display: block;
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
                            v-for="(item, index) in header.mainNav"
                            :key="index"
                            :href="item.href"
                            :cta-text="item.cta"
                        >
                            <span slot="buttonContent">
                                {{ item.title }}
                            </span>
                            <span slot="dropdownContent">
                                <ul>
                                    <li
                                        v-for="(navItem, navIndex) in item.dropdownNav"
                                        :key="navIndex"
                                    >
                                        {{ navItem.title }}
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
