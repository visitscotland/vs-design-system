<template>
    <vs-row
        class="vs-summary-box__list list-unstyled p-3"
        :background="background"
        :text="text"
        :class="{
            [backgroundColorClass]: backgroundColorClass,
            [textColorClass]: textColorClass,
        }"
        tag="ul"
    >
        <slot />
    </vs-row>
</template>

<script>
import { VsRow } from "@components/elements/layout"
/**
 * Itinerary Stop image element
 */
export default {
    name: "VsSummaryBoxList",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsRow,
    },
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

<style lang="scss" scoped></style>
<docs>
  
  ```jsx
    <vs-summary-box-list background="warning" text="dark">
        <vs-summary-box-list-item border="dark">
            <strong>Days</strong>
            <span>{{itineraries.sampleItinerary.totalDays}}</span>
        </vs-summary-box-list-item>
        <vs-summary-box-list-item border="dark">
            <strong>Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></strong>
            <span>{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</span>
        </vs-summary-box-list-item>
        <vs-summary-box-list-item border="dark">
            <strong>Transport</strong>
            <div class="icon-wrapper">
                <vs-icon :name="itineraries.sampleItinerary.transport.key" variant="dark" size="sm" />
                {{itineraries.sampleItinerary.transport.value}}
            </div>
        </vs-summary-box-list-item>
        <vs-summary-box-list-item borderColor="dark">
            <strong>Main theme</strong>
            <div class="icon-wrapper">
                <vs-icon :name="itineraries.sampleItinerary.theme.key" variant="dark" size="sm" />
                {{itineraries.sampleItinerary.theme.value}}
            </div>
        </vs-summary-box-list-item>
    </vs-summary-box-list>
  ```
</docs>
