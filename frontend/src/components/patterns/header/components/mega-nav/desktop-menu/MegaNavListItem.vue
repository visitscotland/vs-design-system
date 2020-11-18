<template>
    <li
        class="mega-nav-list-item"
        data-test="mega-nav-list-item"
    >
        <VsLink
            :href="href"
            :role="href === '#' ? 'heading' : ''"
            :class="{ 'mega-nav-list-item__heading': href === '#' }"
        >
            <slot />
        </VsLink>
    </li>
</template>

<script>
export default {
    name: 'VsMegaNavListItem',
    status: 'prototype',
    release: '0.1.0',
    props: {
        /**
         * The URL for the nav list link
         */
        href: {
            type: String,
            default: '#',
        },
    },
};
</script>

<style lang="scss">
.mega-nav-list-item{
    list-style-type: none;

    &__heading{
        &.vs-link.primary{
            text-decoration: none;
            color: #3A3638;
            border-bottom: 1px solid #d9d9d9;
            cursor: text;
        }
    }
}
</style>

<docs>
  ```jsx

  ```
</docs>
