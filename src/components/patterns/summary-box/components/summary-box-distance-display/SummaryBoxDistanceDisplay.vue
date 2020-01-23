<template>
    <div class="vs-summary-box-distance-display">
        <span v-if="isShowingMiles">{{ miles }}</span>
        <span v-else>{{ kilometres }}</span>
    </div>
</template>

<script>
import summaryBoxStore from "@components/patterns/summary-box/summaryBox.store"
/**
 * Summary Box Distance Display
 */
export default {
    name: "VsSummaryBoxDistanceDisplay",
    status: "prototype",
    release: "0.0.1",
    components: {},
    watch: {
        isShowingMiles(newValue, oldValue) {},
    },
    computed: {
        isShowingMiles() {
            return summaryBoxStore.getters["summaryBox/getShowMiles"]
        },
    },
    summaryBoxStore,
    props: {
        miles: {
            type: String,
        },
        kilometres: {
            type: String,
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
.vs-summary-box-distance-display ::v-deep {
    display: block;
    font-family: $headings-font-family;
    font-size: 1.625rem;
    position: absolute;
    text-align: center;
    top: 20%;
    width: 100%;
    @include media-breakpoint-up(lg) {
        font-size: 1.75rem;
    }
}
</style>

<docs>
  
  ```jsx
    <div class="py-5">
        <vs-summary-box-distance-display
            :miles=itineraries.sampleItinerary.totalMiles
            :kilometres=itineraries.sampleItinerary.totalKM
        >
        </vs-summary-box-distance-display>
    </div>
  ```
</docs>
