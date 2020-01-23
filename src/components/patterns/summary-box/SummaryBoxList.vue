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
 * Itinerary Stop image element
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
    @include media-breakpoint-up(lg) {
        max-width: 400px;
    }
}
.position-wrapper {
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
    <vs-summary-box-list textColor="light">
      <vs-summary-box-list-item>
        <vs-summary-box-text-only slot="summary-box-text-only">{{itineraries.sampleItinerary.totalDays}}</vs-summary-box-text-only>
        <vs-summary-box-label slot="summary-box-label">Days</vs-summary-box-label>
      </vs-summary-box-list-item>
      <vs-summary-box-list-item>
        <vs-summary-box-text-only slot="summary-box-text-only">{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</vs-summary-box-text-only>
        <vs-summary-box-label slot="summary-box-label">Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></vs-summary-box-label>
      </vs-summary-box-list-item>
      <vs-summary-box-list-item>
        <vs-summary-box-icon-with-label slot="icon-with-label">
            <vs-icon slot="icon" :name="itineraries.sampleItinerary.transport.key" variant="dark" size="md" :padding="0" />
            <span slot="label">{{itineraries.sampleItinerary.transport.value}}</span>
        </vs-summary-box-icon-with-label>
        <vs-summary-box-label slot="summary-box-label">Transport</vs-summary-box-label>
      </vs-summary-box-list-item>
      <vs-summary-box-list-item>
        <vs-summary-box-icon-with-label slot="icon-with-label">
            <vs-icon slot="icon" :name="itineraries.sampleItinerary.theme.key" variant="dark" size="md" :padding="0" />
            <span slot="label">{{itineraries.sampleItinerary.theme.value}}</span>
        </vs-summary-box-icon-with-label>
        <vs-summary-box-label slot="summary-box-label">Main theme</vs-summary-box-label>
      </vs-summary-box-list-item>
    </vs-summary-box-list>
  ```
</docs>
