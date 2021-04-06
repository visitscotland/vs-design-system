<template>
    <li
        class="vs-summary-box-distance-list-item position-relative"
        data-test="vs-summary-box-distance-list-item"
    >
        <div
            class="vs-summary-box-distance-display position-absolute d-block text-center w-100"
            data-test="vs-summary-box-distance-display"
        >
            <span
                v-if="isShowingMiles"
                id="display_miles"
            >{{ miles }}</span>
            <span
                v-else
                id="display_kilometres"
            >{{ kilometres }}</span>
        </div>
        <div class="vs-summary-box-distance-label text-center d-block position-absolute w-100">
            <strong class="d-block">{{ distanceLabel }}</strong>
            <div class="d-flex justify-content-center align-items-center">
                <VsButton
                    @click.native="handleClick(true)"
                    :class="showMiles ? 'active' : ''"
                    :aria-expanded="showMiles ? 'true' : 'false'"
                    variant="transparent"
                    aria-controls="display_miles"
                >
                    <abbr :title="milesLabel">{{ milesAbbr }}</abbr>
                </VsButton>
                <span class="separator">/</span>
                <VsButton
                    @click.native="handleClick(false)"
                    :class="showMiles ? '' : 'active'"
                    :aria-expanded="showMiles ? 'false' : 'true'"
                    variant="transparent"
                    aria-controls="display_kilometres"
                >
                    <abbr :title="kilometresLabel">{{ kilometresAbbr }}</abbr>
                </VsButton>
            </div>
        </div>
    </li>
</template>

<script>
import summaryBoxStore from '@components/patterns/summary-box/summaryBox.store';
import VsButton from '@components/elements/button/Button';

/**
 * Summary Box Distance List Item
 *
 * @displayName Summary Box Distance List Item
 */
export default {
    name: 'VsSummaryBoxDistanceListItem',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsButton,
    },
    props: {
        miles: {
            type: String,
            default: '',
        },
        kilometres: {
            type: String,
            default: '',
        },
        distanceLabel: {
            type: String,
            default: 'Distance',
        },
        milesLabel: {
            type: String,
            default: 'miles',
        },
        milesAbbr: {
            type: String,
            default: 'mi',
        },
        kilometresLabel: {
            type: String,
            default: 'kilometers',
        },
        kilometresAbbr: {
            type: String,
            default: 'km',
        },
    },
    data() {
        return {
            showMiles: true,
        };
    },
    computed: {
        isShowingMiles() {
            return summaryBoxStore.getters['summaryBox/getShowMiles'];
        },
    },
    watch: {
        isShowingMiles() {
            this.toggleShowMiles();
        },
    },
    summaryBoxStore,
    methods: {
        handleClick(value) {
            return summaryBoxStore.dispatch('summaryBox/setShowMiles', value);
        },
        toggleShowMiles() {
            this.showMiles = this.isShowingMiles;
        },
    },
};
</script>

<style lang="scss" scoped>
.vs-summary-box-distance-list-item {
    flex-grow: 1;
    flex-basis: calc(50% - 1rem);
    margin: 0.5rem;
    @include media-breakpoint-up(md) {
        flex-grow: 1;
        flex-basis: calc(50% - 1.5rem);
        margin: 0.75rem;
    }

    &::after {
        border: 1px solid $color-theme-dark;
        content: "";
        display: block;
        padding-bottom: 100%;
    }
}

.vs-summary-box-distance-display {
    font-family: $headings-font-family;
    font-size: 1.625rem;
    top: 20%;
    @include media-breakpoint-up(lg) {
        font-size: 1.75rem;
    }

    span {
        display: block;
        padding: 0 .25em;
        word-break: break-word;
        line-height: 1;
    }
}

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
    <ul style="width: 200px; list-style-type: none;">
       <VsSummaryBoxDistanceListItem
            :miles=itineraries.sampleItinerary.totalMiles
            :kilometres=itineraries.sampleItinerary.totalKM
            distance-label="Distance"
            miles-label="miles"
            miles-abbr="mi"
            kilometres-label="kilometres"
            kilometres-abbr="km"
        >
        </VsSummaryBoxDistanceListItem>
    </ul>
  ```
</docs>
