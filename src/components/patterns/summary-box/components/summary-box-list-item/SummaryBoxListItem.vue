<template>
    <li
        class="box"
        :text="border"
        :class="{
            [widthClass]: widthClass,
        }"
    >
        <slot name="icon-with-label" />
        <slot name="summary-box-text-only" />
        <slot name="summary-box-distance" />
        <slot name="summary-box-label" />
        <slot name="summary-box-distance-label" />
    </li>
</template>

<script>
/**
 * Summary Box List Item
 */
export default {
    name: "VsSummaryBoxListItem",
    status: "prototype",
    release: "0.0.1",
    components: {},
    props: {
        /**
         * Style the border color.
         * `primary, secondary, success, danger, warning, info, light, dark, transparent`
         */
        border: {
            type: String,
            default: "dark",
            validator: value => {
                return value.match(
                    /(primary|secondary|success|danger|warning|info|light|dark|transparent)/
                )
            },
        },
        width: {
            type: String,
            default: "50",
            validator: value => {
                return value.match(/(20|25|33|50|100)/)
            },
        },
    },
    computed: {
        borderColorClass() {
            return "border border-" + this.border
        },
        widthClass() {
            return "box-" + this.width
        },
    },
    methods: {
        toggleShowMiles() {},
    },
}
</script>

<style lang="scss" scoped>
.box {
    margin: 0.5rem;
    position: relative;
    overflow: hidden;

    @include media-breakpoint-up(md) {
        margin: 0.75rem;
    }

    &-20 {
        flex: 1 0 calc(20% - 1rem);
        @include media-breakpoint-up(md) {
            flex: 1 0 calc(20% - 1.5rem);
        }
    }

    &-25 {
        flex: 1 0 calc(25% - 1rem);
        @include media-breakpoint-up(md) {
            flex: 1 0 calc(25% - 1.5rem);
        }
    }

    &-33 {
        flex: 1 0 calc(33% - 1rem);
        @include media-breakpoint-up(md) {
            flex: 1 0 calc(33% - 1.5rem);
        }
    }

    &-50 {
        flex: 1 0 calc(50% - 1rem);
        @include media-breakpoint-up(md) {
            flex: 1 0 calc(50% - 1.5rem);
        }
    }

    &-100 {
        flex: 1 0 calc(100% - 1rem);
        @include media-breakpoint-up(md) {
            flex: 1 0 calc(100% - 2rem);
        }
    }

    &::after {
        content: "";
        display: block;
        padding-bottom: 100%;
        border: 1px solid black;
    }
}
</style>

<docs>
  
  ```jsx

    <vs-summary-box-list textColor="light">
      <vs-summary-box-list-item width="50">
        <vs-summary-box-text-only slot="summary-box-text-only">{{itineraries.sampleItinerary.totalDays}}</vs-summary-box-text-only>
        <vs-summary-box-label slot="summary-box-label">Days</vs-summary-box-label>
      </vs-summary-box-list-item>
      <vs-summary-box-list-item width="50">
        <vs-summary-box-text-only slot="summary-box-text-only">{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</vs-summary-box-text-only>
        <vs-summary-box-label slot="summary-box-label">Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></vs-summary-box-label>
      </vs-summary-box-list-item>
      <vs-summary-box-list-item width="50">
         <vs-summary-box-icon-with-label slot="icon-with-label">
            <vs-icon slot="icon" name="cycle" variant="dark" size="md" :padding="0" />
            <span slot="label">das Fahrrad</span>
        </vs-summary-box-icon-with-label>
        <vs-summary-box-label slot="summary-box-label">Transport</vs-summary-box-label>
      </vs-summary-box-list-item>
      <vs-summary-box-list-item width="50">
        <vs-summary-box-icon-with-label slot="icon-with-label">
            <vs-icon slot="icon" name="castle" variant="dark" size="md" :padding="0" />
            <span slot="label">das beste von Schottland</span>
        </vs-summary-box-icon-with-label>
        <vs-summary-box-label slot="summary-box-label">Main theme</vs-summary-box-label>
      </vs-summary-box-list-item>
    </vs-summary-box-list>
  ```
</docs>
