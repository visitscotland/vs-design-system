<template>
  <component :is="type" class="vs-itinerary">
    <slot />
  </component>
</template>
<script>
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import VsHeading from "@components/elements/heading/Heading"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"
import VsHero from "@components/patterns/hero/Hero"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"

/**
 * TODO: Document usage.
 */

export default {
  name: "VsItinerary",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsContainer,
    VsRow,
    VsCol,
    VsHeading,
    VsHero,
    VsImageLocationMap,
    VsButton,
    VsIcon,
  },
  props: {
    type: {
      type: String,
      default: "article",
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-itinerary ::v-deep {
  figcaption {
    @include media-breakpoint-up(lg) {
      bottom: 9rem;
    }

    @include media-breakpoint-up(xl) {
      bottom: 160px;
    }
  }

  .vs-itinerary__map {
    @include media-breakpoint-up(lg) {
      float: right;
      position: -webkit-sticky;
      position: sticky;
      top: 0;
      width: 50vw;
      z-index: 1020;
    }
  }

  .vs-itineraries__intro-wrapper {
    @include media-breakpoint-up(lg) {
      padding: 2.5rem 4rem;
      background: $color-white;
      box-shadow: 0 0 1rem rgba(0, 0, 0, 0.2);
      margin: -12rem 0;
      position: relative;
    }

    @include media-breakpoint-up(xl) {
      margin: -200px 0;
    }
  }
  .vs-itineraries__highlights-wrapper {
    padding: 2rem 0;

    @include media-breakpoint-up(lg) {
      padding: 15rem 0 2rem 0;
    }

    @include media-breakpoint-up(xl) {
      padding: 21rem 0 2rem 0;
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
    <vs-hero
      :altText="itineraries.sampleItinerary.image.altText"
      :credit="itineraries.sampleItinerary.image.credit"
      :description="itineraries.sampleItinerary.image.description"
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
      slot="image" />
      <noscript>
        <img class="img-fluid" :src="itineraries.sampleItinerary.image.imageSrc" alt="item.altText" />
      </noscript>
    </vs-hero>
    <vs-container>
      <div class="vs-itineraries__intro-wrapper">
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
            <vs-itinerary-summary-list>
              <vs-itinerary-summary-list-item>
                <strong>Days</strong>
                <span>{{itineraries.sampleItinerary.totalDays}}</span>
              </vs-itinerary-summary-list-item>
              <vs-itinerary-summary-list-item>
                <strong>Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></strong>
                <span>{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</span>
              </vs-itinerary-summary-list-item>
              <vs-itinerary-summary-list-item>
                <strong>Transport</strong>
                <div class="icon-wrapper">
                  <vs-icon :name="itineraries.sampleItinerary.transport.key" variant="dark" size="sm" />
                  {{itineraries.sampleItinerary.transport.value}}
                </div>
              </vs-itinerary-summary-list-item>
              <vs-itinerary-summary-list-item>
                <strong>Main theme</strong>
                <div class="icon-wrapper">
                  <vs-icon :name="itineraries.sampleItinerary.theme.key" variant="dark" size="sm" />
                  {{itineraries.sampleItinerary.theme.value}}
                </div>
              </vs-itinerary-summary-list-item>
            </vs-itinerary-summary-list>
          </vs-col>
        </vs-row>
      </div>
    </vs-container>
    <div class="bg-light vs-itineraries__highlights-wrapper">
      <vs-container>
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
    </div>
    <div class="position-sticky">
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
      <vs-container>
        <vs-row>
          <vs-col cols="12">
            <ul class="list-unstyled">
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
                <dl class="list-inline-item" v-for="(transportType, transportTypeIndex) in day.transport">
                  <vs-itinerary-transport-type :transportType="transportType">
                    <span class="sr-only">{{transportType.value}}</span>
                  </vs-itinerary-transport-type>
                </dl>
              </dl>
                          
                <div slot="day-introduction" v-html="day.introduction"></div>
                <ul slot="stops" class="mt-9 list-unstyled">
                  <vs-itinerary-stop 
                    v-for="(stop, index) in day.stops"
                    :stop="stop"
                    :key="index"
                  >
                  <vs-heading 
                    slot="stop-title"
                    level="3" 
                    thin 
                    class="vs-itinerary-stop__title ml-4">
                    <span 
                    >Stop {{stop.stopCount}}</span>
                    {{stop.title}}
                  </vs-heading>

                  <vs-favourites-toggle-button
                    slot="stop-favourite"
                    :href="stop.href"
                    :title="stop.title"
                  />
                  <vs-itinerary-stop-image
                      :altText="stop.image.altText"
                      :credit="stop.image.credit"
                      :description="stop.image.description"
                      :image-src="stop.image.imageSrc"
                      :latitude="stop.image.latitude"
                      :longitude="stop.image.longitude"
                      slot="stop-image"
                    >
                  <img 
                    class="lazyload" 
                    :src="stop.image.imageSrc"
                    srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                    :data-srcset="stop.image.imageSrc" 
                    :alt="stop.image.altText"
                    data-sizes="auto"
                    slot="image" />
                    <noscript>
                      <img class="img-fluid" :src="stop.image.imageSrc" alt="item.image.altText" />
                    </noscript>
                  </vs-itinerary-stop-image>
                  <div slot="stop-description" v-html="stop.description"></div>
                  <dl slot="stop-time-to-explore" class="list-inline my-4 mb-0">
                    <dt class="list-inline-item mb-0">Time to explore:</dt>
                    <dd class="list-inline-item mb-0">{{stop.timeToExplore}}</dd>
                  </dl>
                  <vs-itinerary-stop-pullout slot="stop-pullout" v-if="stop.pullOut.description.length">
                    <div slot="text">
                      <strong>{{stop.pullOut.title}}</strong>
                      <div v-html="stop.pullOut.description"></div>
                    </div>
                    <vs-svg slot="svg" path="highland-cow" />
                  </vs-itinerary-stop-pullout>
                  <a slot="stop-href" class="vs-itinerary__stop-link text-uppercase font-weight-bold d-inline-flex align-items-center"
                    :href="stop.href"
                  >
                    Find out more
                    <vs-icon name="play-filled" variant="primary" size="xxs" :padding=3 />
                  </a>
                  <dl v-if="stop.facilities.length" class="itinerary-stop__facilities" slot="stop-facilities">
                    <dt>Key facilities</dt>
                    <vs-itinerary-stop-facility
                      v-for="(facility, facilitiesIndex) in stop.facilities"
                      :key="facilitiesIndex"
                      :facility="facility"
                    >
                      {{facility.value}}
                    </vs-itinerary-stop-facility>
                  </dl>
                  </vs-itinerary-stop>
                </ul>
            </vs-itinerary-day>
            </ul>
          </vs-col>    
        </vs-row>
      </vs-container>
    </div>
  <vs-related-content-list>
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
</vs-itinerary>
          
  ```
</docs>
