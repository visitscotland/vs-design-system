<template>
    <Component
        :is="ordered ? 'ol' : 'ul'"
        :class="[
            $style.root,
            {
                [$style.unstyled]: unstyled,
                [$style.inline]: inline,
            }
        ]"
    >
        <slot />
    </Component>
</template>

<script>
/**
 * Generic List Component that can be used for any kind of list needed given the right properties.
 *
 */
export default {
    name: 'VsList',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * Option to choose the kind of list needed (ordered | unordered)
         */
        ordered: {
            type: Boolean,
            default: false,
        },
        /**
         * Option to add/remove bullet points
         */
        unstyled: {
            type: Boolean,
            default: false,
        },
        /**
         * Option to set the list as inline or normal
         */
        inline: {
            type: Boolean,
            default: false,
        },
    },
};
</script>

<style lang="scss" module>
.root {
    &.unstyled {
        padding: 0;
        margin: 0;
        list-style: none;
    }

    &.inline {
        display: flex;
    }
}
</style>

<docs>
```jsx
    <h3>Default</h3>
    <vs-list class="mb-8">
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
    </vs-list>

    <h3>Inline</h3>
    <vs-list inline class="mb-8">
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
    </vs-list>

    <h3>Inline Unstyled</h3>
    <vs-list unstyled inline class="mb-8">
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
    </vs-list>

    <h3>Unstyled</h3>
    <vs-list unstyled class="mb-8">
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
    </vs-list>

    <h3>Ordered</h3>
    <vs-list ordered class="mb-8">
        <li>Test</li>
        <li>Test</li>
        <li>Test</li>
    </vs-list>

    <h3>Ordered Unstyled</h3>
    <vs-list ordered unstyled class="mb-8">
        <li>Test</li>
        <li>Test</li>
        <li>Test</li>
    </vs-list>
```
</docs>
