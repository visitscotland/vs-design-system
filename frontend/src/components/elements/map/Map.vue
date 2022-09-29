<template>
    <div
        class="vs-map"
        ref="mapbox"
        id="vs-map"
        data-test="vs-map"
    />
</template>

<script>
// import Vue from 'vue';
import osBranding from '@/utils/os-branding';

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
    props: {
        labels: {
            type: Object,
            required: true,
        },
        overviewMapLatitude: {
            type: String,
            default: '-4.13',
        },
        overviewMapLongitude: {
            type: String,
            default: '57.81',
        },
        overviewMapZoom: {
            type: String,
            default: '5',
        },
        pins: {
            type: Array,
            required: true,
        },
    },
    data() {
        return {
            isDesktop: false,
            geojsonData: {
                type: 'FeatureCollection',
                features: [],
            },
            mapbox: {
                map: null,
                bounds: null,
                rotation: 0,
                config: {
                    container: this.$refs.mapbox,
                    style: 'https://api.visitscotland.com/maps/vector/v1/vts/resources/styles',
                    center: [
                        parseFloat(this.overviewMapLatitude),
                        parseFloat(this.overviewMapLongitude),
                    ],
                    zoom: parseInt(this.overviewMapZoom, 10),
                    maxBounds: [
                        [-11.697414, 52.801395], // south-west point.
                        [0.651219, 61.395636], // north-east point.
                    ],
                    pitchWithRotate: false,
                    dragRotate: false,
                },
            },
            markers: [],
            popup: null,
        };
    },
    mounted() {
        this.lazyloadMapComponent();
        this.isTablet = window.innerWidth >= 768;
        window.addEventListener('resize', this.onResize);

        window.addEventListener('DOMContentLoaded', () => {
            osBranding.init({
                div: 'vs-map',
            });
        });
    },
    methods: {
        /**
         * Adds a map to the page
         */
        addMap() {
            this.mapbox.config.container = this.$refs.mapbox;
            this.mapbox.map = new mapboxgl.Map(this.mapbox.config);
            this.mapbox.map.scrollZoom.disable();
            this.mapbox.map.on('rotate', () => {
                this.mapbox.rotation = this.mapbox.map.transform.angle;
            });
        },
        /**
         * Adds map to controls
         */
        addMapControls() {
            const nav = new mapboxgl.NavigationControl();
            // todo: add map control components or otherwise style the controls as per design
            // add tooltips to the map control icons
            this.mapbox.map.addControl(nav, 'top-right');
            this.mapbox.map.addControl(new mapboxgl.FullscreenControl());
        },
        /**
         * Adds map features
         */
        // addMapFeatures() {
        //     this.stops.map((stop) => this.geojsonData.features.push({
        //         type: 'Feature',
        //         geometry: {
        //             type: 'Point',
        //             coordinates: [parseFloat(stop.longitude), parseFloat(stop.latitude)],
        //         },
        //         properties: {
        //             title: stop.title,
        //             stopCount: stop.stopCount,
        //             imageSrc: stop.imageSrc,
        //             altText: stop.altText,
        //         },
        //     }));
        // },
        /**
         * Adds map markers
         */
        // addMapMarkers() {
        //     this.geojsonData.features.forEach((feature) => {
        //         const markerComponent = new Vue({
        //             ...VsItineraryMapMarker,
        //             parent: this,
        //             propsData: {
        //                 feature,
        //             },
        //         });

        //         markerComponent.$mount();

        //         const mapboxMarker = new mapboxgl.Marker(markerComponent.$el)
        //             .setLngLat(feature.geometry.coordinates)
        //             .addTo(this.mapbox.map);

        //         this.markers.push(mapboxMarker);
        //     });
        // },
        /**
         * Adds map pop ups
         */
        addMapPopup() {
            this.removeMapPopup();

            if (this.highlightedStopCoordinates !== null && this.highlightedStop !== null) {
                this.popup = new mapboxgl.Popup({
                    closeButton: false,
                    offset: {
                        top: [0, 20],
                        'top-left': [0, 20],
                        'top-right': [0, 20],
                        bottom: [0, -50],
                        'bottom-left': [0, -50],
                        'bottom-right': [0, -50],
                        left: [30, -20],
                        right: [-30, -20],
                    },
                })
                    .setLngLat(this.highlightedStopCoordinates)
                    .setHTML(
                        `
                            <img class="vs-itinerary-map__popup-image" src="${this.highlightedStop.properties.imageSrc}" alt="${this.highlightedStop.properties.altText}" />
                            <div>
                            <h4 class="vs-itinerary-map__popup-stop-number mb-0">${this.labels.stopLabel} ${this.highlightedStop.properties.stopCount}</h4>
                            <p class="vs-itinerary-map__popup-stop-name">${this.highlightedStop.properties.title}</p>
                            </div>
                        `,
                    )
                    .addTo(this.mapbox.map);
            }
        },
        /**
         * Ensures map fits to bounds
         */
        fitToBounds() {
            this.mapbox.map.fitBounds(geojsonExtent(this.geojsonData), {
                padding: this.mapPadding,
            });
        },
        /**
         * Initialises the map component
         */
        initialiseMapComponent() {
            this.addMap();
            this.addMapControls();

            if (this.pins.length) {
                this.addMapFeatures();
            }

            if (this.geojsonData.features.length) {
                this.addMapMarkers();
                this.fitToBounds();
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
         * Removes a map pop up
         */
        removeMapPopup() {
            if (this.popup !== null) {
                this.popup.remove();
                this.popup = null;
            } else {
                this.popup = null;
            }
        },
        /**
         * Checks for window size on resize
         */
        onResize() {
            this.isTablet = window.innerWidth >= 768;
        },
    },
};
</script>

<style>
@import "mapbox-gl/dist/mapbox-gl.css";

@import "https://labs.os.uk/public/os-api-branding/v0.2.0/os-api-branding.css";

.os-api-branding.logo {
    margin: $spacer-2;
}

.vs-map {
    height: 100%;
    position: relative;
}
</style>
