<template>
    <section class="vs-itinerary position-sticky">
        <div class="fixed-bottom" v-show="!this.isDesktop && this.withinItineraryMain">
            <div class="vs-itinerary__map-toggle-container d-flex justify-content-center pb-2">
                <vs-itinerary-mobile-map-toggle @click.native="toggleShowMap()" />
            </div>
        </div>
        <div class="vs-itinerary__map-container" v-show="this.isDesktop || this.showMap">
            <slot name="map" />
        </div>
        <vs-container>
            <vs-row>
                <vs-col cols="12" tag="ul" class="list-unstyled">
                    <slot name="list" />
                </vs-col>
            </vs-row>
        </vs-container>
    </section>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import VsHeading from "@components/elements/heading/Heading"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"
import VsImageWithCaption from "@components/patterns/image-with-caption/ImageWithCaption"
import VsItineraryMobileMapToggle from "@components/patterns/itineraries/components/itinerary-mobile-map-toggle/ItineraryMobileMapToggle"

/**
 * A wrapper component that wraps the itinerary map and list.
 * It controls display of the mobile map toggle on smaller screens.
 */

export default {
    name: "VsItinerary",
    status: "prototype",
    release: "0.0.1",
    data() {
        return {
            showMap: window.innerWidth >= 1200 ? true : false,
            isDesktop: window.innerWidth >= 1200 ? true : false,
            withinItineraryMain: false,
        }
    },
    components: {
        VsContainer,
        VsRow,
        VsCol,
        VsHeading,
        VsImageLocationMap,
        VsImageWithCaption,
        VsItineraryMobileMapToggle,
        VsButton,
        VsIcon,
    },
    props: {},
    methods: {
        onResize() {
            this.isDesktop = window.innerWidth >= 1200 ? true : false
            this.showMap = window.innerWidth >= 1200 ? true : false
        },
        onScroll() {
            var bounding = this.$el.getBoundingClientRect()
            var insideStartOfItineraryMain =
                bounding.top <= (window.innerHeight || document.documentElement.clientHeight)
                    ? true
                    : false
            var outsideEndOfItineraryMain =
                bounding.bottom <= (window.innerHeight || document.documentElement.clientHeight)
                    ? true
                    : false
            this.withinItineraryMain =
                insideStartOfItineraryMain && !outsideEndOfItineraryMain ? true : false
        },
        toggleShowMap() {
            this.showMap = !this.showMap
        },
    },
    mounted() {
        window.addEventListener("resize", this.onResize)
    },
    destroyed() {
        window.removeEventListener("resize", this.onResize)
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-itinerary ::v-deep {
    .vs-itinerary__map-toggle-container {
        background: linear-gradient(
            to bottom,
            rgba(255, 255, 255, 0) 0%,
            rgba(255, 255, 255, 1) 100%
        );
    }

    .vs-itinerary__map-container {
        height: 100vh;
        position: fixed;
        top: 0;
        width: 100vw;
        z-index: 1020;

        @include media-breakpoint-down(lg) {
            bottom: 0;
            left: 0;
            right: 0;
        }

        @include media-breakpoint-up(lg) {
            float: right;
            position: -webkit-sticky;
            position: sticky;
            width: 50vw;
        }
    }
}
</style>

<docs>
```jsx
  const sampleItinerary = require("../../../assets/fixtures/itineraries/sampleItinerary.json")
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

<vs-itinerary>
  <!-- TODO: move mapbox prod and dev keys to an environment variable -->
  <vs-itinerary-map
    slot="map"
    access-token="pk.eyJ1IjoidmlzaXRzY290bGFuZC1kZXYiLCJhIjoiY2p4MGZwcmtjMDBlczN5bTBnY3pjeHNubCJ9.d3CJWPvX9FfjfSNAW98Q6w"
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
        "mapControlsZoomOut": "Zoom out"
    }'
    >
  </vs-itinerary-map>

        <vs-itinerary-day 
            slot="list"
            v-for="(day, index) in itineraries.sampleItinerary.days"
                :defaultShow="(day.dayCount < 3) ? true : false"
                :key="index"
                :dayNumber="day.dayCount"
                dayLabel="Day"
                :dayTitle="day.title"
            >

          <dl v-if="day.dayMiles && day.dayKM" slot="day-distance" class="list-inline text-center">
            <dt class="list-inline-item"><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr>:</dt>
            <dd class="list-inline-item">{{day.dayMiles}}/{{day.dayKM}}</dd>
          </dl>

          <dl v-if="day.transport.length" class="list-inline text-center" slot="day-transport">
            <dt class="list-inline-item">Transport:</dt>
            <dd class="list-inline-item" v-for="(transportType, transportTypeIndex) in day.transport" :key="transportTypeIndex">
                <vs-tooltip :title="transportType.value">
                    <vs-icon :name="transportType.key" variant="dark" size="sm" />
                </vs-tooltip>
              <span class="sr-only">{{transportType.value}}</span>
            </dd>
          </dl>

          <div class="mb-5" slot="day-introduction" v-html="day.introduction"></div>
            <vs-itinerary-stop 
              slot="stops"
              v-for="(stop, stopIndex) in day.stops"
                :key="stopIndex"
                :stopNumber="stop.stopCount"
                stopLabel="Stop"
                :stopTitle="stop.title"
              >
              <div slot="stop-details">
                <vs-image-with-caption
                    :altText="stop.image.altText"
                    :credit="stop.image.credit"
                    :caption="stop.image.caption"
                    :image-src="stop.image.imageSrc"
                    :latitude="stop.image.latitude"
                    :longitude="stop.image.longitude"
                    >
                    <img 
                    class="lazyload" 
                    :src="stop.image.imageSrc"
                    srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                    :data-srcset="stop.image.imageSrc" 
                    :alt="stop.image.altText"
                    data-sizes="auto" />
                </vs-image-with-caption>
                <div v-html="stop.description"></div>
                <dl class="list-inline my-4 mb-0">
                    <dt class="list-inline-item mb-0">Time to explore:</dt>
                    <dd class="list-inline-item mb-0">{{stop.timeToExplore}}</dd>
                </dl>
                <vs-itinerary-tips v-if="stop.tips.tipsBody.length || stop.tips.tipsTitle.length">
                    <div slot="text">
                    <strong>{{stop.tips.tipsTitle}}</strong>
                    <div v-html="stop.tips.tipsBody"></div>
                    </div>
                    <vs-svg slot="svg" path="highland-cow" />
                </vs-itinerary-tips>
                <a href="stop.href">Find out more</a>
                <dl v-if="stop.facilities.length" class="itinerary-stop__facilities">
                    <dt>Key facilities</dt>
                    <dd v-for="(facility, facilitiesIndex) in stop.facilities"
                    :key="facilitiesIndex">
                    <vs-icon :name="facility.key" variant="dark" size="sm" />
                    {{facility.value}}
                    </dd>
                </dl>
              </div>
              <!-- mimic only showing these links on the last stop of the day -->
              <template v-if="stopIndex == day.stops.length - 1">
                <vs-itinerary-nearby-links slot="nearby-links">
                    <vs-button class="d-inline-flex mb-4" variant="outline-primary" href="#">
                        <vs-icon name="food" variant="primary" size="sm"></vs-icon>
                        Nearby places to eat
                    </vs-button>
                    <br />
                    <vs-button class="d-inline-flex" variant="outline-primary" href="#">
                        <vs-icon name="product-accommodation" variant="primary" size="sm"></vs-icon>
                        Nearby places to stay
                    </vs-button>
                </vs-itinerary-nearby-links>
            </template>
        </vs-itinerary-stop>
    </vs-itinerary-day>
</vs-itinerary>
  ```
</docs>
