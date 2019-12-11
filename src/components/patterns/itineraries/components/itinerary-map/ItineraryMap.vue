<template>
  <div class="vs-itinerary__map sticky-top" ref="mapbox"></div>
</template>

<script>
import itinerariesStore from "@components/patterns/itineraries/itineraries.store"
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
  components: {},
  data() {
    return {
      activeStop: null,
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
          center: [this.overviewMapLatitude, this.overviewMapLongitude],
          zoom: this.overviewMapZoom,
        },
      },
      markers: {},
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
      default: -4.13,
    },
    overviewMapLongitude: {
      type: String,
      required: true,
      default: 57.81,
    },
    overviewMapZoom: {
      type: String,
      required: false,
      default: 5,
    },
    stops: {
      type: Array,
      required: true,
    },
  },
  itinerariesStore,
  computed: {
    mapPadding: function() {
      return {
        top: 80,
        bottom: 80,
        left: 80,
        right: 80,
      }
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
      this.geojsonData.features.forEach(marker => {
        var el = document.createElement("button")
        el.innerHTML = '<span class="sr-only">Stop</span>' + marker.properties.stopCount
        el.className = "vs-itinerary__map-marker"
        el.addEventListener("mouseenter", e => {
          this.addMapPopup(marker)
        })
        el.addEventListener("mouseleave", e => {
          this.removeMapPopup()
        })
        new mapboxgl.Marker(el).setLngLat(marker.geometry.coordinates).addTo(this.mapbox.map)
      })
    },
    addMapPopup(marker) {
      this.removeMapPopup()
      if (marker.properties.title) {
        this.popup = new mapboxgl.Popup({
          closeButton: false,
          offset: {
            top: [0, 20],
            "top-left": [0, 20],
            "top-right": [0, 20],
            bottom: [0, -20],
            "bottom-left": [20, 0],
            "bottom-right": [20, 0],
            left: [20, 0],
            right: [-20, 0],
          },
        })
          .setLngLat(marker.geometry.coordinates)
          .setHTML(
            `
          <img class="vs-itinerary__map-popup-image" src="${marker.properties.imageSrc}" alt="${marker.properties.altText}" />
          <h4 class="vs-itinerary__map-popup-heading">${marker.properties.title}</h4>
        `
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

      this.observer = new IntersectionObserver(entries => {
        if (entries[0].intersectionRatio > 0) {
          this.observer.unobserve(this.$el)
          this.initialiseMapComponent()
        }
      })
      this.observer.observe(this.$el)
    },
    removeMapPopup() {
      this.popup !== null ? this.popup.remove() : null
      this.popup = null
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
  },
}
</script>

<style lang="scss" scoped>
.vs-itinerary__map {
  height: 100vh;

  @include media-breakpoint-up(lg) {
    width: 50vw;
    float: right;
  }

  & ::v-deep {
    .vs-itinerary__map-marker.mapboxgl-marker.mapboxgl-marker-anchor-center {
      background-color: $color-theme-secondary-teal;
      border-radius: 50%;
      border: 1px solid $color-white;
      color: $color-white;
      width: 40px;
      height: 40px;

      &:hover {
        z-index: 1;
      }
    }

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
