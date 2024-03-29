<template>
    <div
        class="vs-map"
        data-test="vs-map"
    >
        <div
            v-if="showMapMessage"
            class="vs-map__message"
            :class="showZoomMessage ? '' : 'vs-map__message--with-overlay'"
            data-test="vs-map__message"
        >
            <div class="vs-map__message-box">
                <VsLoading
                    v-if="isLoading"
                    class="vs-map__loading"
                />
                <p class="vs-map__message-text">
                    <template v-if="isLoading">
                        <!-- @slot Message to show when map is loading  -->
                        <slot name="mapLoadingText" />
                    </template>
                    <template v-else-if="showInfoMessage">
                        <!-- @slot Generic message slot -->
                        <slot name="infoMessage" />
                    </template>
                    <template v-else-if="showZoomMessage === 'too-close'">
                        <!-- @slot Message for zoom level too close -->
                        <slot name="zoomTooClose" />
                    </template>
                    <template v-else-if="showZoomMessage === 'too-far'">
                        <!-- @slot Message for zoom level too far -->
                        <slot name="zoomTooFar" />
                    </template>
                </p>
            </div>
        </div>
        <div
            :id="mapId"
            class="vs-map__map"
            ref="mapbox"
        />
        <VsWarning class="vs-map__no-js">
            <!-- @slot Message to show when JS is disabled  -->
            <slot name="noJs" />
        </VsWarning>
    </div>
</template>

<script>
import Vue from 'vue';
import VsWarning from '@components/patterns/warning/Warning';
import VsLoading from '@components/elements/loading-spinner/LoadingSpinner';
import osBranding from '@/utils/os-branding';
import VsMapMarker from './components/MapMarker';
import mapStore from '../../../stores/map.store';

let mapboxgl = null;
let geojsonExtent = null;

/**
 * Renders a MapBox map
 *
 * @displayName Map
 */

export default {
    name: 'VsMap',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsWarning,
        VsLoading,
    },
    props: {
        /**
         * Starting latitude for map view
         */
        overviewMapLatitude: {
            type: String,
            default: '57.81',
        },
        /**
         * Starting longitude for map view
         */
        overviewMapLongitude: {
            type: String,
            default: '-5.51748',
        },
        /**
         * Starting zoom level for map view
         */
        overviewMapZoom: {
            type: String,
            default: '5',
        },
        /**
         * Unique ID for the map
         */
        mapId: {
            type: String,
            required: true,
        },
        /**
         * Whether or not the map is visible
         * used for triggering resize
         */
        isVisible: {
            type: Boolean,
            required: true,
        },
        /**
         * Place data for markers
         */
        places: {
            type: Array,
            required: true,
        },
        /**
         * Whether the map should fit to marker bounds
         */
        fitToMarkers: {
            type: Boolean,
            default: false,
        },
        /**
         * Whether the map should show regions polygons
         */
        showPolygons: {
            type: Boolean,
            default: false,
        },
        /**
         * The current selectdd item
         */
        selectedItem: {
            type: String,
            default: null,
        },
        /**
         * Data to set map bounds
         */
        boundsData: {
            type: Object,
            default: () => {},
        },
        /** If the map should show an info message defined
         * a slot
         */
        showInfoMessage: {
            type: Boolean,
            default: null,
        },
        /**
         * Allows parent component to fire a reset zoom event
        */
        resetZoom: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            isDesktop: false,
            geojsonData: {
                type: 'FeatureCollection',
                features: [],
            },
            polygons: {
                type: 'FeatureCollection',
                features: [],
            },
            multiPolygons: [],
            mapbox: {
                map: null,
                bounds: null,
                rotation: 0,
                config: {
                    container: this.$refs.mapbox,
                    style: 'https://api.visitscotland.com/maps/vector/v1/vts/resources/styles',
                    pitchWithRotate: false,
                    dragRotate: false,
                },
            },
            markers: [],
            popup: null,
            hoveredStateId: null,
            activeStateId: null,
            showMapMessage: true,
            isLoading: true,
            showZoomMessage: null,
        };
    },
    computed: {
        getZoom() {
            if (this.mapbox.map) {
                return this.mapbox.map.getZoom();
            }

            return null;
        },
        highlightedPlace() {
            if (this.mapbox.map) {
                return mapStore.getters.getHoveredStop(this.mapId);
            }

            return '';
        },
        activeMarkerPostion() {
            if (this.mapbox.map) {
                return mapStore.getters.getActiveMarkerPosition;
            }

            return null;
        },
    },
    watch: {
        isVisible(newVal) {
            if (newVal && this.mapbox.map !== null) {
                this.mapbox.map.resize();
            }
        },
        places() {
            this.geojsonData.features = [];
            // this.geojsonData.features.splice(0, this.geojsonData.features.length);
            this.addMapFeatures();
            this.addMapMarkers();
        },
        showPolygons(val) {
            if (val === true) {
                this.showMapPolygons();
            } else {
                this.hideMapPolygons();
            }
        },
        highlightedPlace(newVal) {
            if (newVal.length === 0) {
                this.removeHoveredPolygon();
            } else {
                this.addHoveredPolygon(newVal);
            }
        },
        selectedItem(newVal) {
            const isPolygon = this.polygons.features
                .filter((feature) => feature.properties.id === newVal);

            if (!newVal) {
                this.removeActivePolygon();
            } else if (isPolygon.length > 0) {
                this.addActivePolygon(newVal);
            }
        },
        activeMarkerPostion(coords) {
            if (!this.checkPointIsVisible(coords)) {
                this.centreMapOnPoint(coords);
            }
        },
        showInfoMessage(newVal) {
            if (newVal) {
                this.showMapMessage = true;
            } else {
                this.showMapMessage = false;
            }
        },
        resetZoom(newVal) {
            if (newVal) {
                this.mapbox.map.fitBounds(this.calculateBoundingBox());
                this.$emit('zoom-reset');

                if (this.showInfoMessage) {
                    this.showMapMessage = true;
                }
            };
        },
    },
    mounted() {
        this.lazyloadMapComponent();
        this.isTablet = window.innerWidth >= 768;
        window.addEventListener('resize', this.onResize);

        /**
         * Initialise branding options when DOM loads
         */
        window.addEventListener('DOMContentLoaded', () => {
            osBranding.init({
                div: this.mapId,
            });
        });
    },
    methods: {
        /**
         * Adds a map to the page
         */
        addMap() {
            const boundingBox = this.calculateBoundingBox();

            this.mapbox.config.container = this.$refs.mapbox;
            this.mapbox.map = new mapboxgl.Map({
                container: this.$refs.mapbox,
                style: 'https://api.visitscotland.com/maps/vector/v1/vts/resources/styles',
                bounds: boundingBox,
                maxZoom: 18,
                minZoom: 4,
            });
            this.mapbox.map.scrollZoom.disable();
            this.mapbox.map.on('rotate', () => {
                this.mapbox.rotation = this.mapbox.map.transform.angle;
            });

            this.mapbox.map.resize();

            this.mapbox.map.on('load', () => {
                this.addMapPolygons();
                this.showMapMessage = false;
                this.isLoading = false;
                this.$emit('map-ready', true);
                this.mapbox.map.boxZoom.enable();
            });

            this.mapbox.map.on('zoomend', () => {
                if (this.mapbox.map.getZoom() === 4) {
                    this.showZoomMessage = 'too-far';
                    this.showMapMessage = true;
                } else if (this.mapbox.map.getZoom() === 18) {
                    this.showZoomMessage = 'too-close';
                    this.showMapMessage = true;
                } else if (!this.showInfoMessage) {
                    this.showZoomMessage = null;
                    this.showMapMessage = false;
                }
            });
        },
        /**
         * Adds map to controls
         */
        addMapControls() {
            const nav = new mapboxgl.NavigationControl();
            this.mapbox.map.addControl(nav, 'top-right');
            this.mapbox.map.addControl(new mapboxgl.FullscreenControl());
        },
        /**
         * Adds map features
         */
        addMapFeatures() {
            this.places.map((place) => {
                if (typeof place.geometry !== 'undefined') {
                    let coordinateArray = [
                        place.geometry.coordinates[0],
                        place.geometry.coordinates[1],
                    ];

                    if (place.geometry.type === 'Polygon') {
                        coordinateArray = [
                            place.geometry.coordinates[0],
                        ];
                    }

                    if (place.geometry.type === 'MultiPolygon') {
                        coordinateArray = [
                            place.geometry.coordinates,
                        ];
                    }

                    return this.geojsonData.features.push({
                        type: 'Feature',
                        geometry: {
                            type: place.geometry.type,
                            coordinates: coordinateArray,
                        },
                        properties: {
                            title: place.properties.title,
                            imageSrc: place.image,
                            type: typeof place.properties.category !== 'undefined' ? place.properties.category.id : '',
                            id: place.properties.id,
                        },
                        id: place.properties.id,
                    });
                }

                return false;
            });
        },
        /**
         * Adds map markers
         */
        addMapMarkers() {
            // timeout needed to give the store a chance to load
            // so that watchers update
            let timeout = 0;

            if (this.initialLoad) {
                timeout = 1000;
            }
            setTimeout(() => {
                if (this.markers !== null) {
                    for (let i = this.markers.length - 1; i >= 0; i--) {
                        this.markers[i].remove();
                    }
                }

                for (let child = this.$children.length - 1; child >= 0; child--) {
                    this.$children[child].$destroy();
                }

                this.geojsonData.features.forEach((feature) => {
                    if (feature.geometry.type === 'Point') {
                        const markerComponent = new Vue({
                            ...VsMapMarker,
                            parent: this,
                            propsData: {
                                feature,
                                mapId: this.mapId,
                            },
                        });

                        markerComponent.$mount();

                        const mapboxMarker = new mapboxgl.Marker(markerComponent.$el)
                            .setLngLat(feature.geometry.coordinates)
                            .addTo(this.mapbox.map);

                        this.markers.push(mapboxMarker);
                    }
                });
            }, timeout);
        },
        /**
         * Hide all polygons
         */
        hideMapPolygons() {
            this.mapbox.map.setLayoutProperty('regions-fills', 'visibility', 'none');
            this.mapbox.map.setLayoutProperty('regions-borders', 'visibility', 'none');
        },
        /**
         * Show all polygons
         */
        showMapPolygons() {
            this.mapbox.map.setLayoutProperty('regions-fills', 'visibility', 'visible');
            this.mapbox.map.setLayoutProperty('regions-borders', 'visibility', 'visible');
        },
        /**
         * Add polygons from data into map
         */
        addMapPolygons() {
            this.geojsonData.features.forEach((feature) => {
                if (feature.geometry.type === 'Polygon') {
                    if (!this.polygons.features.includes(feature.properties.id)) {
                        this.polygons.features.push(feature);
                    }
                } else if (feature.geometry.type === 'MultiPolygon') {
                    if (!this.polygons.features.includes(feature.properties.id)) {
                        this.multiPolygons.push(feature);
                    }
                }
            });

            this.multiPolygons.forEach((poly) => {
                const polyProps = poly.properties;
                poly.geometry.coordinates[0].forEach((coord) => {
                    this.polygons.features.push({
                        geometry: {
                            type: 'Polygon',
                            coordinates: coord,
                        },
                        id: polyProps.id,
                        properties: {
                            id: polyProps.id,
                            imageSrc: polyProps.imageSrc,
                            title: polyProps.title,
                            type: polyProps.type,
                        },
                        type: 'Feature',
                    });
                });
            });

            this.mapbox.map.addSource('regions', {
                type: 'geojson',
                data: this.polygons,
                promoteId: 'id',
            });

            this.mapbox.map.addLayer({
                id: 'regions-fills',
                type: 'fill',
                source: 'regions',
                layout: {
                },
                paint: {
                    'fill-color': [
                        'case',
                        ['==', ['feature-state', 'interaction-state'], 'hover'],
                        'rgba(173,14,110,0.698)',
                        ['==', ['feature-state', 'interaction-state'], 'active'],
                        'rgba(173,14,110,0.533)',
                        '#A5A5A5',
                    ],
                    'fill-opacity': 0.8,
                },
            });

            this.mapbox.map.addLayer({
                id: 'regions-borders',
                type: 'line',
                source: 'regions',
                layout: {
                },
                paint: {
                    'line-color': '#fff',
                    'line-width': 1,
                },
            });

            // Hide map polygons by default
            this.hideMapPolygons();

            // When the user moves their mouse over the state-fill layer,
            // we'll update the feature state for the feature under the mouse.
            this.mapbox.map.on('mousemove', 'regions-fills', (e) => {
                if (e.features.length > 0) {
                    this.addMapPopup(e);
                    this.removeHoveredPolygon();
                    this.addHoveredPolygon(e.features[0].id);
                }
            });

            // When the mouse leaves the state-fill layer, update the
            // feature state of the previously hovered feature.
            this.mapbox.map.on('mouseleave', 'regions-fills', () => {
                this.removeMapPopup();
                this.removeHoveredPolygon();
            });

            // When the clicks the the state-fill layer, update the
            // feature state of the active feature.
            this.mapbox.map.on('click', 'regions-fills', (e) => {
                this.removeActivePolygon();
                this.addActivePolygon(e.features[0].id);
            });
        },
        /**
         * Remove the current active polygon
         */
        removeActivePolygon() {
            this.mapbox.map.setFeatureState(
                {
                    source: 'regions',
                    id: this.activeStateId,
                },
                {
                    'interaction-state': '',
                },
            );

            this.activeStateId = null;
        },
        /**
         * Add a new active polygon
         */
        addActivePolygon(polyId) {
            this.activeStateId = polyId;

            this.mapbox.map.setFeatureState(
                {
                    source: 'regions',
                    id: this.activeStateId,
                },
                {
                    'interaction-state': 'active',
                },
            );

            mapStore.dispatch('setActivePlace', {
                mapId: this.mapId,
                placeId: polyId,
            });
            this.$emit('show-detail', polyId);

            this.$emit('set-category', 'regions');
        },
        /**
         * Remove the current hovered polygon
         */
        removeHoveredPolygon() {
            if (this.hoveredStateId) {
                let state = '';
                if (this.hoveredStateId === this.activeStateId) {
                    state = 'active';
                }

                this.mapbox.map.setFeatureState(
                    {
                        source: 'regions',
                        id: this.hoveredStateId,
                    },
                    {
                        'interaction-state': state,
                    },
                );
            }

            mapStore.dispatch('setHoveredPlace', {
                mapId: this.mapId,
                hoveredId: '',
            });

            this.hoveredStateId = null;
        },
        /**
         * Add a hovered polygon
         */
        addHoveredPolygon(polyId) {
            this.hoveredStateId = polyId;
            this.mapbox.map.setFeatureState(
                {
                    source: 'regions',
                    id: this.hoveredStateId,
                },
                {
                    'interaction-state': 'hover',
                },
            );

            mapStore.dispatch('setHoveredPlace', {
                mapId: this.mapId,
                hoveredId: polyId,
            });
        },
        /**
         * Adds map pop ups
         */
        addMapPopup(e) {
            function findCenter(markers) {
                const lats = [];
                const lngs = [];
                markers[0].forEach((m) => {
                    lats.push(m[1]);
                    lngs.push(m[0]);
                });
                return {
                    lat: (Math.min(...lats) + Math.max(...lats)) / 2,
                    lng: (Math.min(...lngs) + Math.max(...lngs)) / 2,
                };
            };

            const detectEsc = (keyEvent) => {
                if (keyEvent.key === 'Escape') {
                    this.removeMapPopup();
                    document.body.removeEventListener('keyup', detectEsc);
                }
            };

            const centerPoint = findCenter(e.features[0].geometry.coordinates);

            if (e.features[0].id !== this.hoveredStateId
                && e.features[0].id !== this.activeStateId) {
                this.removeMapPopup();
                document.body.removeEventListener('keyup', detectEsc);

                this.popup = new mapboxgl.Popup()
                    .setLngLat(centerPoint)
                    .setHTML(e.features[0].properties.title)
                    .addTo(this.mapbox.map);
            }

            document.body.addEventListener('keyup', detectEsc);
        },
        /**
         * Remove the popup from the map
         */
        removeMapPopup() {
            if (this.popup) {
                this.popup.remove();
                this.popup = null;
            }
        },
        /**
         * Ensures map fits to bounds
         */
        fitToBounds() {
            this.mapbox.map.fitBounds(geojsonExtent(this.geojsonData), {
                padding: {
                    top: 100,
                    bottom: 100,
                    left: 100,
                    right: 100,
                },
            });
        },
        /**
         * Initialises the map component
         */
        initialiseMapComponent() {
            this.addMap();
            this.addMapControls();

            if (this.places.length) {
                this.addMapFeatures();
            }

            if (this.geojsonData.features.length) {
                this.addMapMarkers();
                if (this.fitToMarkers) {
                    this.fitToBounds();
                }
            }
        },
        /**
         * Initialises lazy loading
         */
        lazyloadMapComponent() {
            // ALL Mapbox dependency import and init must be done only in the mounted
            // lifecycle event so it doesn't break SSR

            mapboxgl = require('mapbox-gl'); // eslint-disable-line global-require
            geojsonExtent = require('@mapbox/geojson-extent'); // eslint-disable-line global-require

            // Disable WebGL if its causing performance problems.
            mapboxgl.supported({
                failIfMajorPerformanceCaveat: true,
            });

            if (!('IntersectionObserver' in window)) {
                this.initialiseMapComponent();
                return;
            }

            this.observer = new IntersectionObserver((entries) => {
                if (entries[0].intersectionRatio > 0) {
                    this.observer.unobserve(this.$el);
                    this.initialiseMapComponent();
                }
            });
            this.observer.observe(this.$el);
        },
        /**
         * Checks for window size on resize
         */
        onResize() {
            this.isTablet = window.innerWidth >= 768;
        },
        /**
         * Creates a map bounding object from polygon data
         */
        getBoundsFromPolygon() {
            const coordinates = this.boundsData.coordinates[0];
            /* eslint-disable arrow-body-style */
            return coordinates.reduce((bounds, coord) => {
                return bounds.extend(coord);
            }, new mapboxgl.LngLatBounds(coordinates[0], coordinates[0]));
            /* eslint-enable arrow-body-style */
        },
        /**
         * Check a marker is visible on the map
         */
        checkPointIsVisible() {
            const isInBounds = this.mapbox.map.getBounds()
                .contains(this.activeMarkerPostion);

            return isInBounds;
        },
        /**
         * Reposition map to centre on point
         */
        centreMapOnPoint(coords) {
            this.mapbox.map.flyTo({
                center: coords,
            });
        },
        /**
         * Calculate bounding box depending on data provided
        */
        calculateBoundingBox() {
            let boundingBox;

            if (typeof this.boundsData === 'undefined'
                || this.boundsData.type === 'undefined') {
                boundingBox = [
                    [-7.555827, 55.308836], // south-west point.
                    [-0.778285, 60.830894], // north-east point.
                ];
            } else if (this.boundsData.type === 'bounds') {
                const southWest = new mapboxgl
                    .LngLat(
                        this.boundsData.coordinates[1][0],
                        this.boundsData.coordinates[1][1]
                    );
                const northEast = new mapboxgl
                    .LngLat(
                        this.boundsData.coordinates[0][0],
                        this.boundsData.coordinates[0][1]
                    );
                boundingBox = new mapboxgl.LngLatBounds(southWest, northEast);
            } else if (this.boundsData.type === 'Polygon') {
                boundingBox = this.getBoundsFromPolygon();
            } else {
                boundingBox = [
                    [-7.555827, 55.308836], // south-west point.
                    [-0.778285, 60.830894], // north-east point.
                ];
            }

            return boundingBox;
        },
    },
};
</script>

<style lang="scss">
@import "mapbox-gl/dist/mapbox-gl.css";
@import "../../../styles/_os-branding.scss";

.os-api-branding.logo {
    margin: $spacer-2;
}

.vs-map {
    height: 100%;
    position: relative;

    &__map {
        height: 100%;
        position: relative;
    }

    &__message {
        position: absolute;
        z-index: 20;
        height: 100%;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        pointer-events: none;

        &--with-overlay {
            pointer-events: all;
            background: rgba(255, 255, 255, 0.4);
        }
    }

    &__message-box {
        border: 1px solid $color-pink;
        border-radius: $border-radius-default;
        height: 142px;
        width: 200px;
        background: $color-white;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        padding: $spacer-6;
    }

    &__loading {
        margin-bottom: $spacer-4;
    }

    &__message-text {
        font-size: $font-size-3;
        margin-bottom: 0;
        text-align: center;
    }

    &__no-js {
        display: none;
    }

    .mapboxgl-ctrl-top-right .mapboxgl-ctrl {
        margin: $spacer-2;

        & > button {
            border: 2px solid $color-theme-primary;
            background-position: center center;
            background-repeat: no-repeat;
            background-size: 1rem 1rem;
        }
    }

    .mapboxgl-ctrl-group:not(:empty) {
        background: transparent;
        border: none;
        box-shadow: none;

        & > button {
            width: 32px;
            height: 32px;
            background-color: $color-white;
            border-radius: 0 !important;

            &:active {
                background-color: $color-white;
            }

            &:focus {
                @extend %primary-button-focus;
            }

            &:not(:disabled):hover {
                background-color: $color-theme-primary;
            }

            &.mapboxgl-ctrl-zoom-in {
                margin-bottom: $spacer-2;
            }

            @include media-breakpoint-up(lg) {
                width: 36px;
                height: 36px;
            }
        }
    }

    .mapboxgl-ctrl-compass {
        display: none;
    }

    .mapboxgl-ctrl-fullscreen {
        &::after {
            font-family: "Font Awesome Kit";
            content: "\e017";
            display: inline-block;
            color: $color-theme-primary;
        }

        &:hover,
        &:focus {
            &::after {
                color: $color-white;
            }
        }
    }

    .mapboxgl-ctrl-shrink {
        &::after {
            font-family: "Font Awesome Kit";
            content: "\e07b";
            display: inline-block;
            color: $color-theme-primary;
        }

        &:hover,
        &:focus {
            &::after {
                color: $color-white;
            }
        }
    }

    .mapboxgl-ctrl-zoom-in {
        &::after {
            font-family: "Font Awesome Kit";
            content: "\e05c";
            display: inline-block;
            color: $color-theme-primary;
        }

        &:hover,
        &:focus {
            &::after {
                color: $color-white;
            }
        }
    }

    .mapboxgl-ctrl-zoom-out {
        &::after {
            font-family: "Font Awesome Kit";
            content: "\e034";
            display: inline-block;
            color: $color-theme-primary;
        }

        &:hover,
        &:focus {
            &::after {
                color: $color-white;
            }
        }
    }

    .mapboxgl-ctrl-zoom-in,
    .mapboxgl-ctrl-zoom-out {
        position: relative;

        &:disabled {
            &::after {
                position: absolute;
                top: -2px;
                left: -2px;
                display: flex;
                justify-content: center;
                align-items: center;
                width: calc(100% + 4px);
                height: calc(100% + 4px);
                background-color: #c3c1c2;
                color: white;
            }
        }
    }

    .mapboxgl-ctrl {
        & > button {
            &:hover,
            &:focus {
                background-color: $color-theme-primary;
            }
        }

        .mapboxgl-ctrl-icon {
            display: none;
        }
    }
}

@include no-js {
    .vs-map {
        &__map {
            display: none;
        }

        &__no-js {
            display: flex;
        }
    }
}
</style>
