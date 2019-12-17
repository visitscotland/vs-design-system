<template>
  <div class="vs-itinerary__map" ref="mapbox"></div>
</template>

<script>
import itinerariesStore from "@components/patterns/itineraries/itineraries.store"
import mapboxgl from "mapbox-gl"
import geojsonExtent from "@mapbox/geojson-extent"
import VsItineraryMapMarker from "@components/patterns/itineraries/components/itinerary-map/ItineraryMapMarker"
import Vue from "vue"
require("mapbox-gl/dist/mapbox-gl.css")

/**
 * TODO: Document usage
 */

export default {
  name: "VsItineraryMap",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsItineraryMapMarker,
  },
  data() {
    return {
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
          style: "mapbox://styles/mapbox/outdoors-v11?optimize=true",
          center: [parseFloat(this.overviewMapLatitude), parseFloat(this.overviewMapLongitude)],
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
  itinerariesStore,
  watch: {
    highlightedStopCoordinates() {
      this.addMapPopup()
    },
  },
  computed: {
    mapPadding: () => {
      return {
        top: 100,
        bottom: 100,
        left: 100,
        right: 100,
      }
    },
    activeStop: () => {
      return itinerariesStore.getters["itineraries/getActiveStop"]
    },
    highlightedStop: () => {
      return itinerariesStore.getters["itineraries/getHighlightedStop"]
    },
    highlightedStopCoordinates() {
      return itinerariesStore.getters["itineraries/getHighlightedStopCoordinates"]
    },
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
      var nav = new mapboxgl.NavigationControl()
      this.mapbox.map.addControl(nav, "top-right")
    },
    addMapFeatures() {
      this.stops.map(stop => {
        return this.geojsonData.features.push({
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
        })
      })
    },
    addMapMarkers() {
      this.geojsonData.features.forEach(feature => {
        let markerComponent = new Vue({
          ...VsItineraryMapMarker,
          parent: this,
          propsData: {
            feature: feature,
          },
        })

        markerComponent.$mount()

        let mapboxMarker = new mapboxgl.Marker(markerComponent.$el)
          .setLngLat(feature.geometry.coordinates)
          .addTo(this.mapbox.map)

        this.markers.push(mapboxMarker)
      })
    },
    addMapPopup() {
      this.removeMapPopup()
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
            <h4 class="vs-itinerary__map-popup-heading">${this.highlightedStop.properties.title}</h4>
        `
        )
        .addTo(this.mapbox.map)
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
    isElementOnScreen(stopCount) {
      var element = document.querySelector("[data-stop='" + stopCount + "']")
      var bounds = element.getBoundingClientRect()
      return bounds.top < window.innerHeight && bounds.top > 0
    },
    lazyloadMapComponent() {
      if (!("IntersectionObserver" in window)) {
        this.initialiseMapComponent()
        return
      }

      this.observer = new IntersectionObserver(entries => {
        if (entries[0].intersectionRatio > 0) {
          this.observer.unobserve(this.$el)
          this.initialiseMapComponent()
        }
      })
      this.observer.observe(this.$el)
    },
    onScroll() {
      this.stops.map(stop => {
        if (this.isElementOnScreen(stop.stopCount)) {
          this.setActiveStop(stop.stopCount)
        }
      })
    },
    removeMapPopup() {
      this.popup !== null ? this.popup.remove() : null
      this.popup = null
    },
    setActiveStop(stopCount) {
      if (this.activeStop === stopCount) {
        return
      } else {
        itinerariesStore.dispatch("itineraries/setStopActive", stopCount)
      }
    },
  },
  created() {
    // Store <body> style attribute, if one exists.
    this.bodyStyleAttribute =
      document.body.getAttribute("style") !== null ? document.body.getAttribute("style") : ""
    // Set access token.
    mapboxgl.accessToken = this.accessToken

    // Disable WebGL if its causing performance problems.
    mapboxgl.supported({
      failIfMajorPerformanceCaveat: true,
    })
  },
  mounted() {
    this.lazyloadMapComponent()
    var designSystemWrapper = document.querySelector(".vds-example")
    if (designSystemWrapper === null) {
      window.addEventListener("scroll", this.onScroll)
    } else designSystemWrapper.addEventListener("scroll", this.onScroll)
  },
  destroyed() {
    window.removeEventListener("scroll", this.onScroll)
  },
}
</script>

<style lang="scss" scoped>
.vs-itinerary__map {
  height: 100vh;
  position: relative;

  & ::v-deep {
    .mapboxgl-popup-content {
      display: flex;
      padding: 0.5rem;
    }

    .vs-itinerary__map-popup-heading {
      font-family: $font-family-base;
      font-size: $font-size-base;
      font-weight: $font-weight-bold;
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
      access-token="pk.eyJ1IjoidmlzaXRzY290bGFuZC1kZXYiLCJhIjoiY2p4MGZwcmtjMDBlczN5bTBnY3pjeHNubCJ9.d3CJWPvX9FfjfSNAW98Q6w"
      overview-map-longitude="57.81"
      overview-map-latitude="-4.13"
      overview-map-zoom="5"
      :stops="stops"
      :labels='{
          "mapControlsFullscreenOpen": "Show fullscreen",
          "mapControlsFullscreenClose": "Exit fullscreen",
          "mapControlsCompass": "Reset angle",
          "mapControlsZoomIn": "Zoom in",
          "mapControlsZoomOut": "Zoom out"
      }'
    >
    </vs-itinerary-map>
  ``` 
</docs>
