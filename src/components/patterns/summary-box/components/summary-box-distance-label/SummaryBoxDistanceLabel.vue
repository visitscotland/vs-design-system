<template>
    <div class="text-center vs-summary-box-distance-label">
        <span class="sr-only">{{ distanceLabel }}</span>
        <button @click.native="toggleShowMiles(true)" type="button" class="btn btn-link">
            <abbr :title="milesLabel" :class="isShowingMiles ? 'active' : ''">
                {{ this.isShowingMiles }}{{ milesAbbr }}
            </abbr>
            <span class="sr-only">{{ milesLabel }}</span>
        </button>
        <span class="separator">/</span>
        <button @click.native="toggleShowMiles(false)" type="button" class="btn btn-link">
            <abbr :title="kilometresLabel" :class="isShowingMiles ? '' : 'active'">
                {{ kilometresAbbr }}
            </abbr>
            <span class="sr-only">{{ kilometresLabel }}</span>
        </button>
    </div>
</template>

<script>
import summaryBoxStore from "@components/patterns/summary-box/summaryBox.store"
/**
 * Summary Box Distance Label component includes toggles to change the distance type displayed within the VsSummaryBoxDistance component
 */
export default {
    name: "VsSummaryBoxDistanceLabel",
    status: "prototype",
    release: "0.0.1",
    components: {},
    props: {
        distanceLabel: {
            type: String,
            default: "Distance",
        },
        milesLabel: {
            type: String,
            default: "miles",
        },
        milesAbbr: {
            type: String,
            default: "mi",
        },
        kilometresLabel: {
            type: String,
            default: "kilometers",
        },
        kilometresAbbr: {
            type: String,
            default: "km",
        },
    },
    watch: {
        isShowingMiles() {
            this.toggleShowMiles()
        },
    },
    computed: {
        isShowingMiles() {
            console.log("computed")
            return summaryBoxStore.getters["summaryBox/getShowMiles"]
        },
    },
    summaryBoxStore,
    methods: {
        toggleShowMiles(value) {
            if (!this.isShowingMiles === value) {
                return summaryBoxStore.dispatch("summaryBox/setShowMiles", value)
            }
            return
        },
    },
    mounted() {
        console.log("mounted")
    },
}
</script>

<style lang="scss" scoped>
.vs-summary-box-distance-label {
    bottom: 5px;
    left: 0;
    position: absolute;
    width: 100%;

    button {
        padding: 0;
        color: $color-base-text;
    }

    abbr {
        display: block;
        font-size: 0.875rem;
        line-height: 1.125rem;
        &.active {
            font-weight: $font-weight-bold;
        }

        @include media-breakpoint-up(lg) {
            font-size: 1.125rem;
        }
    }
}
</style>

<docs>
  
  ```jsx
    <div class="position-relative p-5">
        <vs-summary-box-distance-label
            distance-label="Distance"
            miles-label="miles"
            miles-abbr="mi"
            kilometres-label="kilometres"
            kilometres-abbr="km"
        ></vs-summary-box-distance-label>
    </div>
  ```
</docs>
