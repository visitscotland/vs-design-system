<template>
    <button
        class="vs-map-marker"
        :class="isHighlighted ? 'active' : ''"
        data-test="vs-map-marker"
        variant="transparent"
        @click="handleClick(feature.properties.id)"
        @keydown="handleClick(feature.properties.id)"
    >
        <VsSvg
            class="vs-map-marker__icon"
            :class="isHighlighted ? 'active' : ''"
            :variant="isHighlighted ? 'dark' : 'secondary-teal'"
            slot="svg"
            :path="`marker-${feature.properties.type}`"
        />
    </button>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';
// import mapStore from '../../../../stores/map.store';

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
    // TO DO - refactor as part of VS-4087
    // computed: {
    //     highlightedStop() {
    //         return itinerariesStore.getters['itineraries/getHighlightedStop'];
    //     },
    // },
    // watch: {
    //     highlightedStop() {
    //         this.toggleHighlighted();
    //     },
    // },
    // methods: {
    //     handleClick() {
    //         if (this.highlightedStop === this.feature) {
    //             return itinerariesStore.dispatch('itineraries/setStopHighlighted', null);
    //         }
    //         return itinerariesStore.dispatch('itineraries/setStopHighlighted', this.feature);
    //     },
    //     handleMouseEnter() {
    //         return itinerariesStore.dispatch('itineraries/setStopHighlighted', this.feature);
    //     },
    //     handleMouseLeave() {
    //         return itinerariesStore.dispatch('itineraries/setStopHighlighted', null);
    //     },
    //     toggleHighlighted() {
    //         this.isHighlighted = this.highlightedStop === this.feature;
    //     },
    // },
    methods: {
        handleClick(id) {
            this.$parent.$emit('show-detail', id);
        },
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

        .vs-map-marker__icon {
            transform: scale(1.2, 1.2) translateY(-10px);
        }
    }

    &__icon {
        transition: $transition-base;
    }

    // &.active {
    //     .vs-map-marker__wrapper {
    //         transform: scale(1.2, 1.2) translateY(-10px);
    //     }

    //     svg {
    //         fill: $color-secondary-teal-shade-3 !important;
    //     }
    // }

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

    .fade-enter-active,
    .fade-leave-active {
        transition: opacity 2s ease;
    }

    .fade-enter-from,
    .fade-leave-to {
        opacity: 0;
    }
}
</style>
