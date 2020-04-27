<template>
    <div class="vs-summary-box-distance-display position-absolute d-block text-center w-100">
        <span
            v-if="isShowingMiles"
            id="display_miles"
        >{{ miles }}</span>
        <span
            v-else
            id="display_kilometres"
        >{{ kilometres }}</span>
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
    components: {
    },
    props: {
        miles: {
            type: String,
            default: "",
        },
        kilometres: {
            type: String,
            default: "",
        },
        milesLabel: {
            type: String,
            default: "",
        },
        kilometresLabel: {
            type: String,
            default: "",
        },
    },
    computed: {
        isShowingMiles() {
            return summaryBoxStore.getters["summaryBox/getShowMiles"]
        },
    },
    watch: {
        isShowingMiles() {},
    },
    summaryBoxStore,
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
.vs-summary-box-distance-display {
    font-family: $headings-font-family;
    font-size: 1.625rem;
    top: 20%;
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
            miles-label="miles"
            kilometres-label="kilometres"
        >
        </vs-summary-box-distance-display>
    </div>
  ```
</docs>
