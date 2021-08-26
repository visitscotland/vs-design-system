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
            <VsCannedSearchProductCard
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
                :location="fetchAddress(prod.address)"
                :description="prod.description"
                :search-type="searchType"
            >
                <VsCannedSearchStars
                    v-if="prod.grading"
                    slot="vsCannedSearchStarRating"
                    :min="prod.grading.minStars"
                    :max="prod.grading.maxStars"
                    :gold="prod.grading.gold"
                />
                <VsCannedSearchCategories
                    slot="vsCannedSearchCategories"
                    v-if="prod.locations"
                    :categories="prod.locations"
                />
                <VsCannedSearchLogos
                    slot="vsCannedSearchLogos"
                    :good-to-go-logo="prod.covidInformation && prod.covidInformation.goodToGo ?
                        prod.covidInformation.goodToGo : null"
                    :safe-travels-logo="prod.covidInformation && prod.covidInformation.safeTravels ?
                        prod.covidInformation.safeTravels : null"
                    :access-guide="prod.accessGuide || null"
                    :awards="prod.awards"
                />
                <VsCannedSearchBadges
                    slot="vsCannedSearchBadges"
                    :badge-one="prod.category ? prod.category.name : ''"
                    :badge-two="prod.offers"
                    :badge-three="fetchBadgeThree(prod)"
                />
                <VsCannedSearchSummaryBox
                    slot="vsCannedSearchSummary"
                >
                    <VsCannedSearchDates
                        v-if="prod.opening"
                        slot="vsCannedSearchSummaryTop"
                        :period="prod.opening.period"
                        :label="prod.opening.period.label"
                    />
                    <VsCannedSearchPrice
                        v-if="prod.price"
                        slot="vsCannedSearchSummaryLeft"
                        :price-intro="prod.price.priceLabel"
                        :price="prod.price.price"
                        :price-outro="prod.price.priceBasis"
                    />
                    <VsLink
                        v-if="prod.website"
                        :href="prod.website.link"
                        :type="prod.website.type.toLowerCase()"
                        slot="vsCannedSearchSummaryRight"
                    >
                        {{ prod.website.label }}
                    </VsLink>
                </VsCannedSearchSummaryBox>
            </VsCannedSearchProductCard>
        </VsCarousel>
        <VsContainer
            v-if="!!this.$slots['vsCannedSearchCredit']"
            class="vs-canned-search__credit-container"
        >
            <!--
                @slot Holds credit info for search data from third parties

                Expects html
            -->
            <slot
                name="vsCannedSearchCredit"
            />
        </VsContainer>
    </div>
</template>

<script>
import VsCannedSearchProductCard from '@components/patterns/canned-search/components/CannedSearchProductCard';
import VsCannedSearchStars from '@components/patterns/canned-search/components/CannedSearchStars';
import VsCannedSearchLogos from '@components/patterns/canned-search/components/CannedSearchLogos';
import VsCannedSearchCategories from '@components/patterns/canned-search/components/CannedSearchCategories';
import VsCannedSearchPrice from '@components/patterns/canned-search/components/CannedSearchPrice';
import VsCannedSearchSummaryBox from '@components/patterns/canned-search/components/CannedSearchSummaryBox';
import VsCannedSearchDates from '@components/patterns/canned-search/components/CannedSearchDates';
import VsCarousel from '@components/patterns/carousel/Carousel';
import VsContainer from '@components/elements/layout/Container';
import VsRow from '@components/elements/layout/Row';
import VsCol from '@components/elements/layout/Col';
import VsLink from '@components/elements/link/Link';

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
        VsCannedSearchProductCard,
        VsCannedSearchStars,
        VsCannedSearchLogos,
        VsCannedSearchCategories,
        VsCannedSearchPrice,
        VsCannedSearchSummaryBox,
        VsCannedSearchDates,
        VsCarousel,
        VsContainer,
        VsRow,
        VsCol,
        VsLink,
    },
    props: {
        /**
        * The url that products should be retrieved from for display
        */
        apiUrl: {
            type: String,
            default: '',
        },
        /**
        * The type of product that is being search for, determines how product
        * card addresses are displayed.
        */
        searchType: {
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
        /**
         * Invoked when mounted, retrieves products from the dms and sets them
         * to the component
         */
        retrieveProducts() {
            axios.get(this.apiUrl)
                .then((response) => {
                    this.products = response.data.data.products;
                })
                .catch(() => {
                    this.products = [];
                });
        },
        fetchAddress(address) {
            if (this.searchType === 'even') {
                return `${address.line1}, ${address.city}`;
            }

            return `${address.city}, ${address.county}`;
        },
        fetchBadgeThree(product) {
            if (product.covidInformation && product.covidInformation.weAreOpen) {
                return product.covidInformation.weAreOpen;
            }

            if (product.opening && product.opening.nowOn) {
                return product.opening.nowOn;
            }

            return '';
        },
    },
};

</script>

<style lang="scss">
    .vs-canned-search {
        .vs-carousel .row > div {
            @include media-breakpoint-down(sm) {
                margin-left: 0;
                max-width: 100%;
                flex: 0 0 100%;
            }
        }
    }

    .vs-canned-search__buttons {
        margin-bottom: $spacer-9;

        .vs-button {
            margin: $spacer-0 $spacer-2 $spacer-2;
        }
    }

    .vs-canned-search__credit-container {
        text-align: right;
        font-size: $font-size-sm;
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
            apiUrl="http://172.28.81.65:8089/data/component/cannedsearch?prodtypes=acco&avail=off&locplace=4751&locprox=10.0&loc=Glasgow&fac_id=accessguide"
        >
            <template slot="vsCannedSearchButtons">
                <VsButton
                    href="https://www.visitscotland.com"
                >
                    View All B&Bs
                </VsButton>
                <VsButton
                    href="https://www.visitscotland.com"
                    icon="map"
                >
                    View on Map
                </VsButton>
            </template>
        </VsCannedSearch>
    </VsModuleWrapper>
    <VsModuleWrapper>
        <template slot="vsModuleWrapperHeading">
            An events search example
        </template>

        <VsCannedSearch
            apiUrl="http://172.28.81.65:8089/data/component/cannedsearch?prodtypes=even&locplace=&locprox=10.0&loc=Scotland&fac_id=goodtogo"
            searchType="even"
        >
            <template slot="vsCannedSearchButtons">
                <VsButton
                    href="https://www.visitscotland.com"
                >
                    View All Events
                </VsButton>
                <VsButton
                    href="https://www.visitscotland.com"
                    icon="map"
                >
                    View on Map
                </VsButton>
            </template>

            <template slot="vsCannedSearchCredit">
                These are some credits for a third party search
            </template>
        </VsCannedSearch>
    </VsModuleWrapper>
```
</docs>
