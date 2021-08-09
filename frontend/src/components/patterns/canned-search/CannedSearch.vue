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
                        name="vsCannedSearchButtons"
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
                :img-src="prod.images ? prod.images[0].mediaUrl : ''"
                :img-alt="prod.name"
                :title="prod.name"
                :detail-link="{
                    link: prod.dmsLink.link,
                    label: prod.dmsLink.label,
                    type: prod.dmsLink.type.toLowerCase()
                }"
                :location="prod.address.city + ', ' + prod.address.county"
                :stars="prod.grading ? {
                    min:prod.grading.minStars,
                    max:prod.grading.maxStars,
                    gold:prod.grading.gold
                } : null"
                :description="prod.description"
                :website-link="{
                    link: prod.website,
                    label: 'Visit website',
                    type: 'external'
                }"
                :price-intro="prod.price ? prod.price.priceLabel : ''"
                :price="prod.price ? prod.price.price : ''"
                :price-outro="prod.price ? prod.price.priceBasis : ''"
                :badge-one="prod.category ? prod.category.name : ''"
                :badge-two="prod.offers"
                :badge-three="prod.covidInformation ?
                    prod.covidInformation.weAreOpen : ''"
                :good-to-go-logo="prod.covidInformation ?
                    prod.covidInformation.goodToGo : ''"
                :safe-travels-logo="prod.covidInformation ?
                    prod.covidInformation.safeTravels : ''"
                :awards="prod.awards"
                :categories="prod.locations"
            />
        </VsCarousel>
    </div>
</template>

<script>
import VsProductCard from '@components/patterns/canned-search/components/product-card/ProductCard';
import VsCarousel from '@components/patterns/carousel/Carousel';
import VsContainer from '@components/elements/layout/Container';
import VsRow from '@components/elements/layout/Row';
import VsCol from '@components/elements/layout/Col';

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
        VsContainer,
        VsRow,
        VsCol,
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
            <template slot="vsCannedSearchButtons">
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
