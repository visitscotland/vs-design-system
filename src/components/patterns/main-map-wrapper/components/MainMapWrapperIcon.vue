<template>
    <div
        data-test="vs-main-map-marker-icon"
        class="vs-main-map-marker-icon"
        :class="parentClasses"
    >
        <VsIcon
            :name="mapMarkerType"
            class="vs-main-map-marker-icon__marker"
            :class="markerClasses"
        />

        <VsIcon
            class="vs-main-map-marker-icon__icon"
            :name="getIconDetails(id).name"
            size="xxs"
            variant="color-white"
        />
    </div>
</template>

<script>
import VsIcon from '@/components/elements/icon/Icon';
import mapIconMapping from '../../../../mixins/mapIconMapping';

export default {
    name: 'VsMainMapWrapperIcon',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsIcon,
    },
    mixins: [
        mapIconMapping,
    ],
    props: {
        id: {
            type: String,
            required: true,
        },
        isHovered: {
            type: Boolean,
            default: false,
        },
        isMapMarker: {
            type: Boolean,
            default: false,
        },
    },
    computed: {
        mapMarkerType() {
            let type = 'map-marker-filled';

            if (this.id === 'twnv') {
                type = 'places-map-pin';
            }

            return type;
        },
        parentClasses() {
            return this.isMapMarker ? 'vs-main-map-marker-icon--map-marker' : '';
        },
        markerClasses() {
            return [
                `vs-main-map-marker-icon__marker--${this.id}`,
                this.isHovered ? 'vs-main-map-marker-icon__marker--outline' : '',
            ];
        },
    },
};

</script>

<style lang="scss">
@include map-button-themes;

.vs-main-map-marker-icon {
    position: relative;
    display: inline-block;

    &--map-marker {
        &:hover {
            // transition: ease-in 0.1s;
            transform: scale(1.2) translate3d(0,0,0);
            transform-origin: bottom center;
            transition: ease-in-out 0.15s;
        }

        .vs-main-map-marker-icon__marker {
            -webkit-text-stroke-color: $color-white;;
            -webkit-text-stroke-width: 1px;
        }
    }

    &__marker {
        font-size: 30px !important;

        &--outline {
            -webkit-text-stroke-color: $color-white;;
            -webkit-text-stroke-width: 1px;
        }
    }

    &__icon.vs-icon {
        position: absolute;
        left: 50%;
        top: 5px;
        transform: translateX(-50%);
        font-size: $font-size-2 !important;
        color: $color-white;
    }

    .vs-icon {
        margin: 0;
    }
}

// override transparent button styles
.vs-button.btn-transparent .vs-main-map-marker-icon__icon {
    color: $color-white;
}
</style>
