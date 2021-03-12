<template>
    <Component
        :is="ordered ? 'ol' : 'ul'"
        class="vs-list"
        :class="{
            'vs-list--unstyled': unstyled,
            'vs-list--inline': inline,
            'vs-list--ordered': ordered,
        }"
        v-bind="$attrs"
    >
        <slot />
    </Component>
</template>

<script>
/**
 * Generic List Component that can be used for any kind of list needed given the right properties.
 *
 * @displayName List
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

<style lang="scss">
.vs-list {
    margin: 0 0 $spacer-5 $spacer-6;
    padding: 0;
    list-style: none;

    li{
        margin-bottom: $spacer-2;

        &::before{
            content: "• ";
            color: $color-pink;
            padding-right: 0.7rem;
            margin-left: -$spacer-6;
            font-size: $h2-font-size;
            line-height: $line-height-xs;
        }
    }

    &.vs-list--unstyled,
    &.vs-list--inline,
    &.vs-list--ordered {
        margin: 0;

        li{
            margin-bottom: 0;

            &::before{
                display: none;
            }
        }
    }

    &.vs-list--inline {
        display: flex;
    }

    &.vs-list--ordered {
        margin: 0 0 $spacer-5 $spacer-6;
        list-style-position: inside;
        list-style: decimal;

    }
}
</style>

<docs>
```jsx
    <h3>Styled</h3>
    <VsList>
        <li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. </li>
            <VsList>
                <li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. </li>
                <li>Maecenas nec massa placerat, bibendum ex sit amet, blandit dolor.</li>
                <li>Curabitur tempus quam et dolor venenatis blandit. Nulla et erat orci.
                Suspendisse consequat ipsum et ex molestie viverra.
                In faucibus eget nisi eu lobortis.</li>
                <li>Integer efficitur lacus id mi scelerisque, porta feugiat leo egestas. </li>
            </VsList>
        <li>Curabitur tempus quam et dolor venenatis blandit. Nulla et erat orci.
        Suspendisse consequat ipsum et ex molestie viverra. In faucibus eget nisi eu lobortis.</li>
        <li>Integer efficitur lacus id mi scelerisque, porta feugiat leo egestas. </li>
    </VsList>

    <h3 class="mt-8">Unstyled</h3>
    <VsList unstyled>
        <li >Test</li>
        <li >Test</li>
        <li >Test</li>
    </VsList>

    <h3 class="mt-8">Inline Unstyled </h3>
    <VsList unstyled inline>
        <li class="mr-7">Test</li>
        <li class="mr-7">Test
        </li>
        <li class="mr-7">Test</li>
    </VsList>

    <h3 class="mt-8">Ordered List</h3>
    <VsList ordered>
        <li>Test</li>
        <li>Test</li>
        <li>Test</li>
    </VsList>
```
</docs>
