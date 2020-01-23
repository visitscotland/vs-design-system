<template>
    <div class="text-center vs-summary-box-distance-label">
        <strong class="d-block">{{ distanceLabel }}</strong>
        <abbr :title="milesLabel">
            <button @click="handleClick(true)" type="button" :class="showMiles ? 'active' : ''">
                <span>{{ milesAbbr }}</span>
            </button>
        </abbr>
        <span class="separator">/</span>
        <abbr :title="kilometresLabel">
            <button @click="handleClick(false)" type="button" :class="showMiles ? '' : 'active'">
                <span>{{ kilometresAbbr }}</span>
            </button>
        </abbr>
    </div>
</template>

<script>
import summaryBoxStore from "@components/patterns/summary-box/summaryBox.store"
import VsTooltip from "@components/elements/tooltip/Tooltip"
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
    components: { VsTooltip },
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
        isShowingMiles(newValue, oldValue) {
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
    position: absolute;
    width: 100%;

    strong,
    abbr {
        font-size: 0.875rem;
        line-height: 1.125rem;
        @include media-breakpoint-up(lg) {
            font-size: 1.125rem;
        }
    }
    button {
        background: none;
        border: none;
        padding: 2px;
        &.active {
            font-weight: $font-weight-bold;
        }
        &:hover {
            cursor: pointer;
        }
        &:focus {
            box-shadow: 0 0 0 0.2rem rgba(0, 0, 0, 0.25);
            outline: none;
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
