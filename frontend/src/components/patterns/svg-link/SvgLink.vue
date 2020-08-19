<template>
    <a
        class="vs-svg-link"
        :href="href"
    >
        <span class="sr-only">{{ logoAltText }}</span>
        <VsSvg
            :path="svgPath"
            :fill="svgFill"
        />
    </a>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg.vue';

/**
 * A component for an SVG wrapped in a link with accessible sr-only text
 */
export default {
    name: 'VsSvgLink',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsSvg,
    },
    props: {
        /**
         * Accessiblity alt text for the svg link
         */
        logoAltText: {
            type: String,
            required: true,
        },
        /**
         * URL for the svg link
         */
        href: {
            type: String,
            required: true,
        },
        /**
         * Fill colour for svg
         */
        svgFill: {
            type: String,
            default: '',
        },
        /**
         * Path to svg to display on the svg link
         */
        svgPath: {
            type: String,
            required: true,
        },
    },
};
</script>

<docs>
```jsx
    <vs-svg-link
        logo-alt-text="VisitScotland Home"
        href="/"
        svg-fill="700e57"
        svg-path="visitscotland"
    />
```
</docs>
