<template>
    <div class="vs-summary-box-text-only">
        <slot />
    </div>
</template>

<script>
/**
 * Summary Box Text Only
 */
export default {
    name: "VsSummaryBoxTextOnly",
    status: "prototype",
    release: "0.0.1",
    components: {},
    props: {},
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
.vs-summary-box-text-only ::v-deep {
    top: 20%;
    display: block;
    font-family: $headings-font-family;
    font-size: 1.625rem;
    position: absolute;
    text-align: center;
    width: 100%;
    .divider {
        font-family: $headings-font-family-thin;
    }
    @include media-breakpoint-up(lg) {
        font-size: 1.75rem;
    }
}
</style>

<docs>
  
  ```jsx
    <vs-summary-box-text-only>
        <span>{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</span>
    </vs-summary-box-text-only>
  ```
</docs>
