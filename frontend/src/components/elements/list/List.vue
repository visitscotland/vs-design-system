<template>
    <Component
        :is="ordered ? 'ol' : 'ul'"
        class="vs-list"
        :class="{
            unstyled,
            inline,
        }"
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
    name: "VsList",
    status: "prototype",
    release: "0.0.1",
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
}
</script>

<style lang="scss" scoped>
.vs-list {
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
    <vs-list>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
    </vs-list>

    <h3>Inline</h3>
    <vs-list inline>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
    </vs-list>

    <h3>Inline Unstyled</h3>
    <vs-list unstyled inline>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
    </vs-list>

    <h3>Unstyled</h3>
    <vs-list unstyled>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test</li>
    </vs-list>

    <h3>Ordered List</h3>
    <vs-list ordered>
        <li>Test</li>
    </vs-list>
```
</docs>
