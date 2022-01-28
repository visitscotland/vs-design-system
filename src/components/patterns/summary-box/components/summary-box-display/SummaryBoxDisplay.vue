<template>
    <div class="vs-summary-box-display position-absolute d-block text-center w-100">
        <span>{{ text }}</span>
    </div>
</template>

<script>
/**
 * Summary Box Display
 */
export default {
    name: "VsSummaryBoxDisplay",
    status: "prototype",
    release: "0.0.1",
    components: {},
    props: {
        text: {
            type: String,
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
.vs-summary-box-display {
    top: 20%;
    font-family: $headings-font-family;
    font-size: 1.625rem;
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
    <div class="py-2">
        <vs-summary-box-display
            :text=itineraries.sampleItinerary.totalDays
        />
    </div>
  ```
</docs>
