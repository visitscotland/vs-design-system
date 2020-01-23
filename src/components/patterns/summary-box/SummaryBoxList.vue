<template>
    <div class="position-wrapper">
        <ul
            class="vs-summary-box-list list-unstyled d-flex flex-wrap p-2 p-sm-3"
            :background="background"
            :text="text"
            :class="{
                [backgroundColorClass]: backgroundColorClass,
                [textColorClass]: textColorClass,
            }"
        >
            <slot />
        </ul>
    </div>
</template>

<script>
/**
 * Summary Box List - Wraps a number of VsSummaryBoxListItem components
 */
export default {
    name: "VsSummaryBoxList",
    status: "prototype",
    release: "0.0.1",
    components: {},
    props: {
        /**
         * Style the background color.
         * `primary, secondary, success, danger, warning, info, light, dark, transparent`
         */
        background: {
            type: String,
            default: "warning",
            validator: value => {
                return value.match(
                    /(primary|secondary|success|danger|warning|info|light|dark|transparent)/
                )
            },
        },
        /**
         * Style the text color.
         * `primary, secondary, success, danger, warning, info, light, dark, transparent`
         */
        text: {
            type: String,
            default: "dark",
            validator: value => {
                return value.match(
                    /(primary|secondary|success|danger|warning|info|light|dark|transparent)/
                )
            },
        },
    },
    computed: {
        backgroundColorClass() {
            return "bg-" + this.background
        },
        textColorClass() {
            return "text-" + this.text
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
.vs-summary-box-list {
    width: 350px;
    @include media-breakpoint-up(lg) {
        width: 400px;
    }
}
.position-wrapper {
    display: flex;
    justify-content: center;
    @include media-breakpoint-up(lg) {
        position: absolute;
        top: 0;
        width: calc(100% - 2rem);
        z-index: 1000;
    }
}
</style>
<docs>
  
  ```jsx
  <div class="position-relative" style="height: 400px;">
    <vs-summary-box-list>
       <vs-summary-box-list-item>
            <vs-summary-box-display
                :text=itineraries.sampleItinerary.totalDays
            />
            <vs-summary-box-label
                label="Days"
            />
        </vs-summary-box-list-item>
        <vs-summary-box-list-item>
            <vs-summary-box-distance-display
                :miles=itineraries.sampleItinerary.totalMiles
                :kilometres=itineraries.sampleItinerary.totalKM
            />
            <vs-summary-box-distance-label
                distance-label="Distance"
                kilometres-abbr="km"
                kilometres-label="kilometres"
                miles-abbr="mi"
                miles-label="miles"
            />
        </vs-summary-box-list-item>
        <vs-summary-box-list-item>
            <vs-summary-box-icon-with-label
                :icon=itineraries.sampleItinerary.transport.key
                :label=itineraries.sampleItinerary.transport.value
            />
            <vs-summary-box-label
                label="Transport"
            />
        </vs-summary-box-list-item>
        <vs-summary-box-list-item>
            <vs-summary-box-icon-with-label
                :icon=itineraries.sampleItinerary.theme.key
                :label=itineraries.sampleItinerary.theme.value
            />
            <vs-summary-box-label
                label="Main Theme"
            />
        </vs-summary-box-list-item>
    </vs-summary-box-list>
    </div>
  ```
</docs>
