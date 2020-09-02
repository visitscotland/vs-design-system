<template>
    <VsList
        unstyled
        class="vs-mega-nav-top-menu"
    >
        <slot />
    </VsList>
</template>

<script>
/**
 *  Mega nav top level menu list with a slot for top level menu items
 */

import VsList from '@components/elements/list/List';

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
@import "~bootstrap/scss/type";

.vs-mega-nav-top-menu {
    &.vs-list.unstyled{
        width: 100%;
        display: flex;
        margin-top: 0.75rem;
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
                            <span slot="button-content">
                                {{ item.title }}
                            </span>
                            <span slot="dropdown-content">
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
