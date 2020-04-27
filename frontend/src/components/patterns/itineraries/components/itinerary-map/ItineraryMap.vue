<template>
    <div
        class="vs-itinerary__map"
        ref="mapbox"
    />
</template>

<script>
import itinerariesStore from "@components/patterns/itineraries/itineraries.store"
import VsItineraryMapMarker from "@components/patterns/itineraries/components/itinerary-map/ItineraryMapMarker"
import Vue from "vue"
import mapboxgl from "mapbox-gl"
import geojsonExtent from "@mapbox/geojson-extent"

require("mapbox-gl/dist/mapbox-gl.css")

/**
 * TODO: Document usage
 */

export default {
    name: "VsItineraryMap",
    status: "prototype",
    release: "0.0.1",
    props: {
        accessToken: {
            type: String,
            required: true,
        },
        labels: {
            type: Object,
            required: true,
        },
        overviewMapLatitude: {
            type: String,
            required: true,
            default: "-4.13",
        },
        overviewMapLongitude: {
            type: String,
            required: true,
            default: "57.81",
        },
        overviewMapZoom: {
            type: String,
            required: false,
            default: "5",
        },
        stops: {
            type: Array,
            required: true,
        },
    },
    data() {
        return {
            isDesktop: false,
            geojsonData: {
                type: "FeatureCollection",
                features: [],
            },
            mapbox: {
                map: null,
                bounds: null,
                rotation: 0,
                config: {
                    container: this.$refs.mapbox,
                    style: "mapbox://styles/mapbox/outdoors-v11?optimize=true", // TODO: Ordinance Survey Map Tiles
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
        }
    },
    itinerariesStore,
    computed: {
        mapPadding() {
            if (this.isTablet) {
                return {
                    top: 100,
                    bottom: 100,
                    left: 100,
                    right: 100,
                }
            }
            return {
                top: 50,
                bottom: 50,
                left: 50,
                right: 50,
            }
        },
        highlightedStop() {
            return itinerariesStore.getters["itineraries/getHighlightedStop"]
        },
        highlightedStopCoordinates() {
            return itinerariesStore.getters["itineraries/getHighlightedStopCoordinates"]
        },
    },
    watch: {
        highlightedStopCoordinates() {
            this.addMapPopup()
        },
    },
    created() {
        // Store <body> style attribute, if one exists.
        this.bodyStyleAttribute = document.body.getAttribute("style") !== null ? document.body.getAttribute("style") : ""

        // Set access token.
        mapboxgl.accessToken = this.accessToken

        // Disable WebGL if its causing performance problems.
        mapboxgl.supported({
            failIfMajorPerformanceCaveat: true,
        })
    },
    mounted() {
        this.lazyloadMapComponent()
        this.isTablet = window.innerWidth >= 768
        window.addEventListener("resize", this.onResize)
    },
    destroyed() {
        window.removeEventListener("resize", this.onResize)
    },
    methods: {
        addMap() {
            this.mapbox.config.container = this.$refs.mapbox
            this.mapbox.map = new mapboxgl.Map(this.mapbox.config)
            this.mapbox.map.scrollZoom.disable()
            this.mapbox.map.on("rotate", () => {
                this.mapbox.rotation = this.mapbox.map.transform.angle
            })
        },
        addMapControls() {
            const nav = new mapboxgl.NavigationControl()
            // todo: add map control components or otherwise style the controls as per design
            // add tooltips to the map control icons
            this.mapbox.map.addControl(nav, "top-right")
            this.mapbox.map.addControl(new mapboxgl.FullscreenControl())
        },
        addMapFeatures() {
            this.stops.map((stop) => this.geojsonData.features.push({
                type: "Feature",
                geometry: {
                    type: "Point",
                    coordinates: [parseFloat(stop.longitude), parseFloat(stop.latitude)],
                },
                properties: {
                    title: stop.title,
                    stopCount: stop.stopCount,
                    imageSrc: stop.imageSrc,
                    altText: stop.altText,
                },
            }))
        },
        addMapMarkers() {
            this.geojsonData.features.forEach((feature) => {
                const markerComponent = new Vue({
                    ...VsItineraryMapMarker,
                    parent: this,
                    propsData: {
                        feature,
                    },
                })

                markerComponent.$mount()

                const mapboxMarker = new mapboxgl.Marker(markerComponent.$el)
                    .setLngLat(feature.geometry.coordinates)
                    .addTo(this.mapbox.map)

                this.markers.push(mapboxMarker)
            })
        },
        addMapPopup() {
            this.removeMapPopup()

            if (this.highlightedStopCoordinates !== null && this.highlightedStop !== null) {
                this.popup = new mapboxgl.Popup({
                    closeButton: false,
                    offset: {
                        top: [0, 20],
                        "top-left": [0, 20],
                        "top-right": [0, 20],
                        bottom: [0, -50],
                        "bottom-left": [0, -50],
                        "bottom-right": [0, -50],
                        left: [30, -20],
                        right: [-30, -20],
                    },
                })
                    .setLngLat(this.highlightedStopCoordinates)
                    .setHTML(
                        `
                            <img class="vs-itinerary__map-popup-image" src="${this.highlightedStop.properties.imageSrc}" alt="${this.highlightedStop.properties.altText}" />
                            <div>
                            <h4 class="vs-itinerary__map-popup-stop-number mb-0">${this.labels.stopLabel} ${this.highlightedStop.properties.stopCount}</h4>
                            <p class="vs-itinerary__map-popup-stop-name">${this.highlightedStop.properties.title}</p>
                            </div>
                        `,
                    )
                    .addTo(this.mapbox.map)
            }
        },
        fitToBounds() {
            this.mapbox.map.fitBounds(geojsonExtent(this.geojsonData), {
                padding: this.mapPadding,
            })
        },
        initialiseMapComponent() {
            this.addMap()
            this.addMapControls()

            if (this.stops.length) {
                this.addMapFeatures()
            }

            if (this.geojsonData.features.length) {
                this.addMapMarkers()
                this.fitToBounds()
            }
        },
        lazyloadMapComponent() {
            if (!("IntersectionObserver" in window)) {
                this.initialiseMapComponent()
                return
            }

            this.observer = new IntersectionObserver((entries) => {
                if (entries[0].intersectionRatio > 0) {
                    this.observer.unobserve(this.$el)
                    this.initialiseMapComponent()
                }
            })
            this.observer.observe(this.$el)
        },
        removeMapPopup() {
            if (this.popup !== null) {
                this.popup.remove()
                this.popup = null
            } else {
                this.popup = null
            }
        },
    },
    onResize() {
        this.isTablet = window.innerWidth >= 768
    },
}
</script>

<style lang="scss" scoped>
.vs-itinerary__map {
    height: 100vh;
    position: relative;

    & ::v-deep {
        .mapboxgl-popup {
            z-index: 999;
        }

        .mapboxgl-popup-content {
            display: flex;
            padding: 0.5rem;
        }

        .vs-itinerary__map-popup-stop-number {
            font-family: $font-family-base;
            font-size: $font-size-base;
            font-weight: $font-weight-bold;
        }

        .vs-itinerary__map-popup-stop-name {
            font-family: $font-family-base;
            font-size: $font-size-base;
            font-weight: $font-weight-normal;
        }

        .vs-itinerary__map-popup-image {
            width: 105px;
            margin-right: 1rem;
        }
    }
}
</style>

<docs>
  ```jsx

  const sampleItinerary = require("../../../../../assets/fixtures/itineraries/sampleItinerary.json")
  const stops = [];

  sampleItinerary.days.map(day => {
    day.stops.map(stop => {
      return stops.push({
        title: stop.title,
        latitude: stop.latitude,
        longitude: stop.longitude,
        stopCount: stop.stopCount,
        imageSrc: stop.image.imageSrc,
        altText: stop.image.altText
      });
    })
  })

    <vs-itinerary-map
      :access-token=keysList.keysList[0].mapToken
      overview-map-longitude="57.81"
      overview-map-latitude="-4.13"
      overview-map-zoom="5"
      :stops="stops"
      :labels='{
          "stopLabel": "Stop",
          "mapControlsFullscreenOpen": "Show fullscreen",
          "mapControlsFullscreenClose": "Exit fullscreen",
          "mapControlsCompass": "Reset angle",
          "mapControlsZoomIn": "Zoom in",
          "mapControlsZoomOut": "Zoom out",

      }'
    >
    </vs-itinerary-map>
  ```
</docs>
