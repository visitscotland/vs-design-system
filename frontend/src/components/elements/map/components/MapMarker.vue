<template>
    <button
        class="vs-map-marker"
        :class="isHighlighted ? 'active' : ''"
        data-test="vs-map-marker"
        variant="transparent"
        @mouseenter="handleMouseEnter()"
        @mouseleave="handleMouseLeave()"
        @focus="handleClick()"
        @blur="handleClick()"
        @click="handleClick()"
        @keydown="handleClick()"
    >
        <div class="vs-map-marker__wrapper">
            <VsSvg
                :class="isHighlighted ? 'active' : ''"
                :variant="isHighlighted ? 'dark' : 'secondary-teal'"
                slot="svg"
                :path="`marker-${feature.properties.type}`"
            />
        </div>
    </button>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';

/**
 * A marker for a map compenent
 *
 * @displayName Map Marker
 */

export default {
    name: 'VsMapMarker',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsSvg,
    },
    props: {
        feature: {
            type: Object,
            required: true,
        },
    },
    data() {
        return {
            isHighlighted: false,
        };
    },
    // computed: {
    //     highlightedStop() {
    //         return itinerariesStore.getters['itineraries/getHighlightedStop'];
    //     },
    // },
    watch: {
        highlightedStop() {
            this.toggleHighlighted();
        },
    },
    methods: {
        // handleClick() {
        //     if (this.highlightedStop === this.feature) {
        //         return itinerariesStore.dispatch('itineraries/setStopHighlighted', null);
        //     }
        //     return itinerariesStore.dispatch('itineraries/setStopHighlighted', this.feature);
        // },
        // handleMouseEnter() {
        //     return itinerariesStore.dispatch('itineraries/setStopHighlighted', this.feature);
        // },
        // handleMouseLeave() {
        //     return itinerariesStore.dispatch('itineraries/setStopHighlighted', null);
        // },
        // toggleHighlighted() {
        //     this.isHighlighted = this.highlightedStop === this.feature;
        // },
    },
};
</script>

<style lang="scss">
.vs-map-marker {
    background: transparent;
    border: none;
    display: block;
    font-weight: $font-weight-bold;
    padding: 0;
    position: absolute;

    &:hover,
    &:focus,
    &.active {
        z-index: 1 !important;
    }

    svg {
        transition: $transition-base;
    }

    &.active {
        .vs-map-marker__wrapper {
            transform: scale(1.2, 1.2) translateY(-10px);
        }

        svg {
            fill: $color-secondary-teal-shade-3 !important;
        }
    }

    &__count {
        color: $color-white;
        display: block;
        font-size: $font-size-4;
        font-family: $headings-font-family;
        position: absolute;
        top: 4px;
        left: 0;
        transition: $transition-base;
        text-align: center;
        width: 100%;
    }
}
</style>
