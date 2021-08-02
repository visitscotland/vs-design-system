<template>
    <div
        class="vs-tag mr-2 mb-4"
        data-test="vs-tag"
    >
        <VsLink
            :href="href"
        >
            <!-- @slot Holds the tag text (text expected) -->
            <slot />
        </VsLink>
    </div>
</template>

<script>
import VsLink from '@components/elements/link/Link';

/**
 * A styled tag with a link
 *
 * @displayName Tag
 */
export default {
    name: 'VsTag',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsLink,
    },
    props: {
        /**
         * The URL the tag will link to
         */
        href: {
            type: String,
            required: true,
        },
    },
};
</script>

<style lang="scss">
.vs-tag {
    display: inline-block;;
    border: 2px solid $color-pink;
    border-radius: $border-radius-pill;
    transition: $transition-base;

    .vs-link {
        display: block;
        text-decoration: none;
        padding: $spacer-1 $spacer-2;
    }

    &:hover {
        background-color: $color-pink;

        .vs-link {
            color: $color-white;
        }
    }
}
</style>

<docs>
```jsx
    <VsTag
        href="https://visitscotland.com"
    >
        Activities
    </VsTag>
    <VsTag
        href="#"
    >
        Castles
    </VsTag>
    <VsTag
        href="#"
    >
        Things to do
    </VsTag>
```
</docs>
