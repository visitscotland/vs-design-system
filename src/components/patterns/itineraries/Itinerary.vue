<template>
  <component :is="type" class="vs-itinerary position-sticky">
    <div class="fixed-bottom" v-show="!this.isDesktop && this.withinItineraryMain">
      <div class="vs-itinerary__map-toggle-container d-flex justify-content-center pb-2">
        <vs-itinerary-mobile-map-toggle @click.native="toggleShowMap()" />
      </div>
    </div>
    <div class="vs-itinerary__map-container" v-show="this.isDesktop || this.showMap">
      <slot name="map" />
    </div>
    <div class="vs-itinerary__list-container">
      <slot name="list" />
    </div>
  </component>
</template>
<script>
import itinerariesStore from "@components/patterns/itineraries/itineraries.store"
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import VsHeading from "@components/elements/heading/Heading"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"
import VsImageWithCaption from "@components/patterns/image-with-caption/ImageWithCaption"
import VsItineraryMobileMapToggle from "@components/patterns/itineraries/components/itinerary-mobile-map-toggle/ItineraryMobileMapToggle"

/**
 * TODO: Document usage.
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
  props: {
    type: {
      type: String,
      default: "section",
    },
  },
  computed: {
    currentActiveStop: () => {
      return itinerariesStore.getters["itineraries/getActiveStop"]
    },
  },
  methods: {
    onResize() {
      this.isDesktop = window.innerWidth >= 1200 ? true : false
      this.showMap = window.innerWidth >= 1200 ? true : false
    },
    onScroll() {
      var bounding = this.$el.getBoundingClientRect()
      var insideStartOfItineraryMain =
        bounding.top <= (window.innerHeight || document.documentElement.clientHeight) ? true : false
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
    // TODO: move the resize and scroll event listeners and functions to the parent Itinerary.vue component
    // add watchers to listen for changes
    window.addEventListener("resize", this.onResize)
    var designSystemWrapper = document.querySelector(".vds-example")
    if (designSystemWrapper === null) {
      window.addEventListener("scroll", this.onScroll)
    } else designSystemWrapper.addEventListener("scroll", this.onScroll)
  },
  destroyed() {
    window.removeEventListener("resize", this.onResize)
    window.removeEventListener("scroll", this.onScroll)
  },
}
</script>

<style lang="scss" scoped>
.vs-itinerary ::v-deep {
  .vs-itinerary__map-toggle-container {
    background: linear-gradient(to bottom, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 1) 100%);
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

  <vs-page-intro>
    <vs-hero
      :altText="itineraries.sampleItinerary.image.altText"
      :credit="itineraries.sampleItinerary.image.credit"
      :caption="itineraries.sampleItinerary.image.caption"
      :image-src="itineraries.sampleItinerary.image.imageSrc"
      :latitude="itineraries.sampleItinerary.image.latitude"
      :longitude="itineraries.sampleItinerary.image.longitude"
    >
    <img 
      class="lazyload" 
      :src="itineraries.sampleItinerary.image.imageSrc"
      srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
      :data-srcset="itineraries.sampleItinerary.image.imageSrc" 
      :alt="itineraries.sampleItinerary.image.altText"
      data-sizes="auto"
       />
    </vs-hero>
    <vs-container>
      <div class="vs-page-intro__wrapper--inner">
        <vs-breadcrumb>
          <vs-breadcrumb-item 
            v-for="(item, index) in breadcrumb.breadcrumb"
            :key="index"
            :href="item.href"
            :active="item.active"
            :text="item.name"
            >
          </vs-breadcrumb-item>
        </vs-breadcrumb>
        <vs-row class="justify-content-md-between">
          <vs-col cols="12" sm="6" lg="7">
            <vs-heading level="1">
              {{itineraries.sampleItinerary.h1Heading}}
            </vs-heading>
            <div class="lead" v-html="itineraries.sampleItinerary.introduction"></div>
            <dl class="list-inline">
              <dt class="list-inline-item">Start / Finish</dt>
              <dd class="list-inline-item">{{itineraries.sampleItinerary.start}}/{{itineraries.sampleItinerary.finish}}</dd>
            </dl>
          </vs-col>
          <vs-col cols="12" sm="6" md="5" lg="4">
            <vs-summary-box-list class="bg-warning">
              <vs-summary-box-list-item>
                <strong>Days</strong>
                <span>{{itineraries.sampleItinerary.totalDays}}</span>
              </vs-summary-box-list-item>
              <vs-summary-box-list-item>
                <strong>Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></strong>
                <span>{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</span>
              </vs-summary-box-list-item>
              <vs-summary-box-list-item>
                <strong>Transport</strong>
                <div class="icon-wrapper">
                  <vs-icon :name="itineraries.sampleItinerary.transport.key" variant="dark" size="sm" />
                  {{itineraries.sampleItinerary.transport.value}}
                </div>
              </vs-summary-box-list-item>
              <vs-summary-box-list-item>
                <strong>Main theme</strong>
                <div class="icon-wrapper">
                  <vs-icon :name="itineraries.sampleItinerary.theme.key" variant="dark" size="sm" />
                  {{itineraries.sampleItinerary.theme.value}}
                </div>
              </vs-summary-box-list-item>
            </vs-summary-box-list>
          </vs-col>
        </vs-row>
      </div>
     <vs-itinerary-highlights-list>
      <dt>Highlights</dt>
      <dd 
        v-for="(highlight, index) in itineraries.sampleItinerary.highlights"
        class="mb-0"
      >
        {{highlight}}
      </dd>
      <dt class="mt-6">Areas Covered</dt>
      <dd 
        v-for="(areaCovered, index) in itineraries.sampleItinerary.areasCovered"
        class="mb-0"
      >
        {{areaCovered}}
      </dd>
    </vs-itinerary-highlights-list>
  </vs-container>
</vs-page-intro>
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
        "mapControlsFullscreenOpen": "Show fullscreen",
        "mapControlsFullscreenClose": "Exit fullscreen",
        "mapControlsCompass": "Reset angle",
        "mapControlsZoomIn": "Zoom in",
        "mapControlsZoomOut": "Zoom out"
    }'
    >
  </vs-itinerary-map>
  <vs-container slot="list">
    <vs-row>
      <vs-col cols="12" tag="ul" class="list-unstyled">
        <vs-itinerary-day 
          v-for="(day, index) in itineraries.sampleItinerary.days"
          :defaultShow="(day.dayCount < 3) ? true : false"
          :key="index"
          >
          <vs-heading 
            slot="day-title"
            level="2" 
            thin
            class="vs-itinerary-day__title">
            <span>Day {{day.dayCount}}</span>
            {{day.title}}
          </vs-heading>

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
                    
          <div slot="day-introduction" v-html="day.introduction"></div>
          <ul slot="stops" class="mt-9 list-unstyled">
            <li 
              v-for="(stop, index) in day.stops" 
              class="vs-itinerary-stop__list-item" 
              :data-stop="stop.stopCount">
              <div class="d-flex justify-content-between align-items-top">
                <vs-icon name="map-marker-filled" variant="secondary-teal" size="md" :padding="0" />
                <vs-heading 
                  level="3" 
                  thin 
                  class="vs-itinerary-stop__title ml-4">
                  <span 
                  >Stop {{stop.stopCount}}</span>
                  {{stop.title}}
                </vs-heading>

                <vs-favourites-toggle-button
                  :href="stop.href"
                  :title="stop.title"
                />
              </div>
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
              <div slot="stop-description" v-html="stop.description"></div>
              <dl slot="stop-time-to-explore" class="list-inline my-4 mb-0">
                <dt class="list-inline-item mb-0">Time to explore:</dt>
                <dd class="list-inline-item mb-0">{{stop.timeToExplore}}</dd>
              </dl>
              <vs-itinerary-stop-pullout slot="stop-pullout" v-if="stop.pullOut.description.length">
                <div>
                  <strong>{{stop.pullOut.title}}</strong>
                  <div v-html="stop.pullOut.description"></div>
                </div>
                <vs-svg path="highland-cow" />
              </vs-itinerary-stop-pullout>
              <a 
                class="vs-itinerary__stop-link text-uppercase font-weight-bold d-inline-flex align-items-center"
                :href="stop.href"
              >
                Find out more
                <vs-icon name="play-filled" variant="primary" size="xxs" :padding=3 />
              </a>
              <dl v-if="stop.facilities.length" class="itinerary-stop__facilities">
                <dt>Key facilities</dt>
                <dd v-for="(facility, facilitiesIndex) in stop.facilities"
                  :key="facilitiesIndex">
                  <vs-icon :name="facility.key" variant="dark" size="sm" />
                  {{facility.value}}
                </dd>
              </dl>
            </li>
          </ul>
        </vs-itinerary-day>
      </vs-col>
    </vs-row>
  </vs-container>
</vs-itinerary>
<vs-related-content-list slot="related-content">
  <h2 slot="header" class="text-warning text-center py-7 m-0">Extend Your Trip</h2>
  <vs-related-content-list-item
    v-for="(item, index) in relatedContent.relatedContent" 
    :key="index"
    slot="cards"
  >
    <vs-related-content-card>
      <img 
        :src="item.image.imageSrc" 
        :alt="item.image.imageAlt"
        class="card-img-top" 
      >
      <div class="card-body">
        <h3 class="card-title h5">
          <a class="stretched-link" :href="item.href">
            {{item.title}}
          </a>
        </h3>
        <div class="card-text">
          {{item.description}}
        </div>
      </div>
    </vs-related-content-card>
  </vs-related-content-list-item>
</vs-related-content-list>
  ```
</docs>
