<template>
    <div
        class="vs-canned-search-cuisines"
        data-test="vs-canned-search-cuisines"
    >
        {{ transformedCuisines }}
    </div>
</template>

<script>

/**
* Component that displays a , separated list of cuisines for a product
* card in canned search
*
* @displayName Canned Search Cuisines
*/

export default {
    name: 'VsCannedSearchCuisines',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
        * A list of cuisines that describe the product, should contain
        * objects, each of which has a `name` prop
        */
        cuisines: {
            type: Array,
            default: null,
        },
    },
    computed: {
        /**
        * Returns the first 3 provided cuisines as a comma separated string for
        * display
        */
        transformedCuisines() {
            if (this.cuisines) {
                return this.cuisines
                    .slice(0, 3)
                    .map((cuisine) => cuisine.name)
                    .join(', ');
            }

            return '';
        },
    },
};

</script>

<style lang="scss">
    .vs-canned-search-cuisines {
        font-size: $font-size-base;
        line-height: $line-height-s;
        font-weight: $font-weight-semi-bold;
        margin-bottom: $spacer-1;
    }
</style>

<docs>
```jsx
    <VsCannedSearchCuisines
        :cuisines="[
            {'id':'scottish', 'name':'Scottish'},
            {'id':'vege', 'name':'Vegetarian/Vegan'},
            {'id':'+more', 'name':'+ More'}]"
    />
</docs>
