<template>
    <li class="box" :text="border">
        <slot />
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
    flex: 1 0 calc(50% - 1rem);
    @include media-breakpoint-up(md) {
        margin: 0.75rem;
        flex: 1 0 calc(50% - 1.5rem);
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
    <ul class="list-unstyled" style="width: 200px;">
       <vs-summary-box-list-item>
            <vs-summary-box-display
                :text=itineraries.sampleItinerary.totalDays
            />
            <vs-summary-box-label
                label="Days"
            />
        </vs-summary-box-list-item>
    </ul>
  ```
</docs>
