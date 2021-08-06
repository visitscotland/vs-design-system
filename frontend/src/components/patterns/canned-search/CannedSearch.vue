<template>
    <div
        class="vs-canned-search"
        data-test="vs-canned-search"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="10"
                    offset="1"
                    md="8"
                    offset-md="2"
                    lg="6"
                    offset-lg="3"
                    class="vs-canned-search__buttons"
                >
                    <!-- @slot Holds one or more navigation buttons  -->
                    <slot
                        name="cannedSearchButtons"
                    />
                </VsCol>
            </VsRow>
        </VsContainer>
        <VsCarousel
            next-text="next page"
            prev-text="previous page"
            slides-xs="1"
            slides-md="2"
            slides-lg="3"
            v-if="products.length"
        >
            <VsProductCard
                v-for="(prod, index) in products"
                :key="index"
                :slide-index="'' + index"
                :img-src="prod.images[0].mediaUrl"
                :img-alt="prod.name"
                :title="prod.name"
                :detail-link="{
                    link: prod.dmsLink.link,
                    label: prod.dmsLink.label,
                    type: prod.dmsLink.type.toLowerCase()
                }"
            />
        </VsCarousel>
    </div>
</template>

<script>
import VsProductCard from '@components/patterns/canned-search/components/product-card/ProductCard';
import VsCarousel from '@components/patterns/carousel/Carousel';

const axios = require('axios');

/**
* Wrapper for canned search, invokes api call to provided endpoint and
* creates cards for products
*
* @displayName Canned Search
*/

export default {
    name: 'VsCannedSearch',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsProductCard,
        VsCarousel,
    },
    props: {
        /**
        * The url that products should be retrieved from for display
        */
        apiUrl: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            products: [],
        };
    },
    mounted() {
        if (this.apiUrl) {
            this.retrieveProducts();
        }
    },
    methods: {
        retrieveProducts() {
            axios.get(this.apiUrl)
                .then((response) => {
                    this.products = response.data.data.products;
                })
                .catch(() => {
                    this.products = [];
                });
        },
    },
};

</script>

<style lang="scss">
    .vs-canned-search__buttons {
        margin-bottom: $spacer-9;
    }
</style>

<docs>
```jsx
    <VsModuleWrapper>
        <template slot="vsModuleWrapperHeading">
            B&Bs, guesthouses and hostels in Loch Lomond and The Trossachs national park
        </template>
        <template slot="vsModuleWrapperIntro">
            Find your perfect place to stay from a wide range of rural, coastal and mountain
            locations...
        </template>

        <VsCannedSearch
            apiUrl="http://172.28.81.65:8089/data/search/productsearch?areaproxdist=10&loc=Scotland&locplace=&locprox=1&prodtypes=acco&locale="
        >
            <template slot="cannedSearchButtons">
                <VsButton
                    class="mx-2 mb-2"
                    href="https://www.visitscotland.com"
                >
                    View All B&Bs
                </VsButton>
                <VsButton
                    class="mx-2 mb-2"
                    href="https://www.visitscotland.com"
                    icon="map"
                >
                    View on Map
                </VsButton>
            </template>
        </VsCannedSearch>
    </VsModuleWrapper>
```
</docs>
