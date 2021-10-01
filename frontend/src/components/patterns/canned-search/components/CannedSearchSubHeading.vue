<template>
    <VsHeading
        level="4"
        class="vs-canned-search-sub-heading"
        data-test="vs-canned-search-sub-heading"
    >
        {{ subHeading }}
    </VsHeading>
</template>

<script>
import VsHeading from '@components/elements/heading/Heading';

/**
* Shows a formatted sub-heading for a canned search product card,
* usually contains an address or list of themes
*
* @displayName Canned Search Sub Heading
*/

export default {
    name: 'VsCannedSearchSubHeading',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsHeading,
    },
    props: {
        /**
        * The string to display
        */
        subHeading: {
            type: String,
            default: '',
        },
    },
};

</script>

<style lang="scss">
    h4.vs-canned-search-sub-heading {
        font-family: $font-family-base;
        font-size: $font-size-base;
        line-height: 1;
        color: $color-secondary-teal-shade-2;
        letter-spacing: normal;
        margin: $spacer-0;
        margin-bottom: $spacer-3;
    }
</style>

<docs>
```jsx
    <VsCannedSearchSubHeading
        sub-heading="Callander, Loch Lomond, The Trossachs, Stirling & Forth Valley"
    />
```
</docs>
