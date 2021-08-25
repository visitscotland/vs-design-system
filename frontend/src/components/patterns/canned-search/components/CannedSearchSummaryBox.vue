<template>
    <div class="vs-canned-search-summary-box">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="6"
                    class="vs-canned-search-summary-box__summary-item"
                    v-if="!!this.$slots['vsCannedSearchSummaryLeft']"
                >
                    <!--
                        @slot Holds the content for the second optional item in the grey
                        summary box  at the bottom of the card, usually a
                        vsCannedSearchPrice

                        Expects html
                    -->
                    <slot
                        name="vsCannedSearchSummaryLeft"
                    />
                </VsCol>
                <VsCol
                    cols="6"
                    class="vs-canned-search-summary-box__summary-item"
                    v-if="!!this.$slots['vsCannedSearchSummaryRight']"
                >
                    <!--
                        @slot Holds the content for the second optional item in the grey
                        summary box  at the bottom of the card, usually a link

                        Expects html
                    -->
                    <slot
                        name="vsCannedSearchSummaryRight"
                    />
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsContainer from '@components/elements/layout/Container';
import VsRow from '@components/elements/layout/Row';
import VsCol from '@components/elements/layout/Col';

/**
* Component that displays a grey summary box for product cards within canned
* search. Contains a series of summary items
*
* @displayName Canned Search Summary Box
*/

export default {
    name: 'VsCannedSearchSummaryBox',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsContainer,
        VsRow,
        VsCol,
    },
};

</script>

<style lang="scss">
    .vs-canned-search-summary-box {
        padding: $spacer-2;
        background-color: $color-gray-tint-7;
        min-height: 4.5rem;

        .row {
            align-items: center;
            justify-content: center;
            min-height: $spacer-10;
        }
    }

    .vs-canned-search-summary-box__summary-item {
        font-size: $font-size-base;
        line-height: $line-height-s;
        text-align: center;

        &:not(:last-child) {
            border-right: 1px solid $color-gray-tint-1;
        }
    }
</style>

<docs>
```jsx
    const sampleAccom = require("../../../../assets/fixtures/canned-search/sample-accom.json");

    <VsCannedSearchSummaryBox>
        <VsCannedSearchPrice
            v-if="sampleAccom.price"
            slot="vsCannedSearchSummaryLeft"
            :priceIntro="sampleAccom.price.priceLabel"
            :price="'£' + sampleAccom.price.price"
            :priceOutro="sampleAccom.price.priceBasis"
        />
        <VsLink
            :href="sampleAccom.website"
            type="external"
            slot="vsCannedSearchSummaryRight"
        >
            Visit Website
        </VsLink>
    </VsCannedSearchSummaryBox>
```
</docs>
