<template>
    <div
        class="vs-canned-search-price"
    >
        <div
            v-if="priceIntro"
            class="vs-canned-search-price__intro"
        >
            {{ priceIntro }}
        </div>
        <div
            v-if="price"
            class="vs-canned-search-price__main"
        >
            {{ price }}
        </div>
        <div
            v-if="priceOutro"
            class="vs-canned-search-price__outro"
        >
            {{ priceOutro }}
        </div>
    </div>
</template>

<script>

/**
* Shows a formatted price with intro and outro
*
* @displayName Canned Search Price
*/

export default {
    name: 'VsCannedSearchPrice',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
        * The price of the product. Setting this will cause the price to appear in the
        * left half of the summary box
        */
        price: {
            type: String,
            default: '',
        },
        /**
        * A line of text that precedes the product price, not rendered if price
        * not provided
        */
        priceIntro: {
            type: String,
            default: '',
        },
        /**
        * A line of text that appears after the product price, not rendered if price
        * not provided
        */
        priceOutro: {
            type: String,
            default: '',
        },
    },
};

</script>

<style lang="scss">
    .vs-canned-search-price {
        &__main {
            font-weight: $font-weight-bold;;
        }

        &__outro {
            font-size: $small-font-size;
        }
    }
</style>

<docs>
```jsx
    <VsCannedSearchPrice
        priceIntro="Price from"
        price="£110"
        priceOutro="Per room per night (breakfast inc.)"
    />
```
</docs>
