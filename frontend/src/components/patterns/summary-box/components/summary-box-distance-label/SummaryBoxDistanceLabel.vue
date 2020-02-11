<template>
    <div class="vs-summary-box-distance-label text-center d-block position-absolute w-100">
        <strong class="d-block">{{ distanceLabel }}</strong>
        <div class="d-flex justify-content-center align-items-center">
            <vs-button
                @click.native="handleClick(true)"
                :class="showMiles ? 'active' : ''"
                :aria-expanded="showMiles ? 'true' : 'false'"
                variant="transparent"
                aria-controls="display_miles"
            >
                <abbr :title="milesLabel">{{ milesAbbr }}</abbr>
            </vs-button>
            <span class="separator">/</span>
            <vs-button
                @click.native="handleClick(false)"
                :class="showMiles ? '' : 'active'"
                :aria-expanded="showMiles ? 'false' : 'true'"
                variant="transparent"
                aria-controls="display_kilometres"
            >
                <abbr :title="kilometresLabel">{{ kilometresAbbr }}</abbr>
            </vs-button>
        </div>
    </div>
</template>

<script>
import summaryBoxStore from "@components/patterns/summary-box/summaryBox.store"
import VsButton from "@components/elements/button/Button"
/**
 * Summary Box Distance Label component includes toggles to change the distance type displayed within the VsSummaryBoxDistance component
 */
export default {
    name: "VsSummaryBoxDistanceLabel",
    status: "prototype",
    release: "0.0.1",
    data() {
        return {
            showMiles: true,
        }
    },
    components: {
        VsButton,
    },
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
            return summaryBoxStore.getters["summaryBox/getShowMiles"]
        },
    },
    summaryBoxStore,
    methods: {
        handleClick(value) {
            return summaryBoxStore.dispatch("summaryBox/setShowMiles", value)
        },
        toggleShowMiles() {
            this.showMiles = this.isShowingMiles
        },
    },
}
</script>
<style lang="scss" scoped>
.vs-summary-box-distance-label {
    bottom: 5px;
    left: 0;
    line-height: 1rem;
    @include media-breakpoint-up(lg) {
        line-height: 1.125rem;
    }

    strong,
    .btn {
        font-size: 0.875rem;
        @include media-breakpoint-up(lg) {
            font-size: 1.125rem;
        }
    }
    .btn {
        padding: 0 2px;
        text-transform: initial !important;
        letter-spacing: initial !important;
        text-decoration: underline;

        &.active {
            font-weight: $font-weight-bold;
            text-decoration: underline;
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
