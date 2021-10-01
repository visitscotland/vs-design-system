<template>
    <VsHeading
        level="4"
        class="vs-canned-search-address"
        data-test="vs-canned-search-address"
    >
        {{ address }}
    </VsHeading>
</template>

<script>
import VsHeading from '@components/elements/heading/Heading';

/**
* Shows a formatted address for a canned search product card
*
* @displayName Canned Search Address
*/

export default {
    name: 'VsCannedSearchAddress',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsHeading,
    },
    props: {
        /**
        * The address string to display
        */
        address: {
            type: String,
            default: '',
        },
    },
};

</script>

<style lang="scss">
    h4.vs-canned-search-address {
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
    <VsCannedSearchAddress
        address="Callander, Loch Lomond, The Trossachs, Stirling & Forth Valley"
    />
```
</docs>
