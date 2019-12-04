<template>
  <div class="vs-itinerary">
    <slot />
  </div>
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
  props: {},
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
</style>

<docs>
```jsx
    <vs-hero
      :altText="itineraries.sampleItinerary.image.altText"
      :credit="itineraries.sampleItinerary.image.credit"
      :description="itineraries.sampleItinerary.image.description"
      :image-src="itineraries.sampleItinerary.image.imageSrc"
      :latitude="itineraries.sampleItinerary.image.latitude"
      :longitude="itineraries.sampleItinerary.image.longitude"
      slot="hero"
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
    </vs-container>
    <vs-container>
      <vs-heading
        level="1"
      >
        {{itineraries.sampleItinerary.h1Heading}}
      </vs-heading>
      <div v-html="itineraries.sampleItinerary.introduction"></div>
      <dl class="list-inline">
        <dt class="list-inline-item">Start / Finish</dt>
        <dd class="list-inline-item">{{itineraries.sampleItinerary.start}}/{{itineraries.sampleItinerary.finish}}</dd>
      </dl>
    </vs-container>
    <div class="bg-light py-4">
      <vs-container>
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
      </vs-container>
     
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
    <ul style="list-style-type: none; padding: 0px;">
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
        
        <dl v-if="day.dayMiles && day.dayKM" slot="day-distance" class="list-inline mb-0 text-center">
          <dt class="list-inline-item mb-0"><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr>:</dt>
          <dd class="list-inline-item mb-0">{{day.dayMiles}}/{{day.dayKM}}</dd>
        </dl>

        <dl v-if="day.transport.length" class="list-inline text-center" slot="day-transport">
          <dt class="list-inline-item">Transport:</dt>
          <dl class="list-inline-item" v-for="(transportType, transportTypeIndex) in day.transport">
            <vs-icon :name="transportType.key" variant="dark" size="sm" />
            <span class="sr-only">{{transportType.value}}</span>
          </dl>
        </dl>

        <div slot="day-introduction" v-html="day.introduction"></div>
        <ul slot="stops" class="mt-9" style="list-style-type: none; padding: 0px;">
          <vs-itinerary-stop 
            v-for="(stop, index) in day.stops"
            :key="index"
          >
          <vs-heading 
            slot="stop-title"
            level="3" 
            thin 
            class="vs-itinerary-stop__title ml-4">
            <span>Stop {{stop.stopCount}}</span>
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
          <a slot="stop-href" class="vs-itinerary__stop-link text-uppercase font-weight-bold d-inline-flex align-items-center"
            :href="stop.href"
          >
            Find out more
            <vs-icon name="play" variant="primary" size="xxs" :padding=3 />
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
          
  ```
</docs>
