<template>
    <section class="vs-itinerary position-sticky">
        <div class="fixed-bottom" v-show="!this.isDesktop && this.withinItineraryMain">
            <div class="d-flex justify-content-center pb-2">
                <vs-itinerary-mobile-map-toggle @click.native="toggleShowMap()" />
            </div>
        </div>
        <div class="vs-itinerary__map-container" v-show="this.isDesktop || this.showMap">
            <slot name="map" />
        </div>
        <vs-container>
            <vs-row>
                <vs-col cols="12" tag="ul" class="list-unstyled p-0">
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
        /* Design System wrapper affects page scroll detection, so temporary fix is to
        have a condition checking for design system wrapper. */
        window.addEventListener("resize", this.onResize)
        var designSystemWrapper = document.querySelector(".vds-example")
        if (designSystemWrapper === null) {
            window.addEventListener("scroll", this.onScroll)
        } else designSystemWrapper.addEventListener("scroll", this.onScroll)
    },
    destroyed() {
        window.removeEventListener("resize", this.onResize)
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-itinerary ::v-deep {
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
            margin-left: 6.25rem;
            position: -webkit-sticky;
            position: sticky;
            width: 45vw;
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
        slot="hero"
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
      <vs-container slot="upper" class="py-lg-4">
        <vs-row class="justify-content-md-between">
          <vs-col cols="12" lg="8" offset-lg="1">
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
          </vs-col>
        </vs-row>
        <vs-row>
          <vs-col cols="10" lg="8" offset-lg="1">
            <vs-heading level="1">
              {{itineraries.sampleItinerary.h1Heading}}
            </vs-heading>
          </vs-col>
          <vs-col cols="2">
            <div class="d-flex justify-content-center justify-content-sm-end">
              <!-- TODO - Below icon is FPO. Replace with icon with text component and a share component -->
              <vs-icon name="share" variant="dark" size="sm" />
            </div>
          </vs-col>
        </vs-row>
        <vs-row>
          <vs-col cols="12" md="6" lg="5" xl="6" offset-lg="1">
            <vs-lead-paragraph v-html="itineraries.sampleItinerary.introduction"></vs-lead-paragraph>
            <dl class="list-inline">
              <dt class="list-inline-item">Start / Finish</dt>
              <dd class="list-inline-item">{{itineraries.sampleItinerary.start}}/{{itineraries.sampleItinerary.finish}}</dd>
            </dl>
          </vs-col>
          <vs-col cols="12" md="6" lg="5" xl="4">
            <vs-summary-box-list>
                <vs-summary-box-list-item>
                    <vs-summary-box-display :text=itineraries.sampleItinerary.totalDays />
                    <vs-summary-box-label label="Days" />
                    </vs-summary-box-list-item>
                    <vs-summary-box-list-item>
                        <vs-summary-box-distance-display
                            :miles=itineraries.sampleItinerary.totalMiles
                            :kilometres=itineraries.sampleItinerary.totalKM
                            miles-label="miles"
                            kilometres-label="kilometres"
                        />
                        <vs-summary-box-distance-label
                            distance-label="Distance"
                            kilometres-abbr="km"
                            kilometres-label="kilometres"
                            miles-abbr="mi"
                            miles-label="miles"
                        />
                    </vs-summary-box-list-item>
                    <vs-summary-box-list-item>
                        <vs-summary-box-icon-with-label
                            :icon=itineraries.sampleItinerary.transport.key
                            :label=itineraries.sampleItinerary.transport.value
                        />
                        <vs-summary-box-label label="Transport" />
                    </vs-summary-box-list-item>
                    <vs-summary-box-list-item>
                        <vs-summary-box-icon-with-label
                            :icon=itineraries.sampleItinerary.theme.key
                            :label=itineraries.sampleItinerary.theme.value
                        />
                        <vs-summary-box-label label="Main theme" />
                    </vs-summary-box-list-item>
                </vs-summary-box-list>
          </vs-col>
        </vs-row>
      </vs-container>
      <vs-container slot="lower">
         <vs-row>
          <vs-col cols="12" lg="11" offset-lg="1">
            <vs-description-list class="mb-6">
                <vs-description-list-term>Highlights</vs-description-list-term>
                <vs-description-list-detail 
                    v-for="(highlight, index) in itineraries.sampleItinerary.highlights"
                >
                    {{highlight}}
                </vs-description-list-detail>
            </vs-description-list>
            <vs-description-list class="mb-8">
                <vs-description-list-term>Areas Covered</vs-description-list-term>
                    <vs-description-list-detail 
                        v-for="(areaCovered, index) in itineraries.sampleItinerary.areasCovered"
                        key="index"
                    >
                    {{areaCovered}}
                </vs-description-list-detail>
            </vs-description-list>
          </vs-col>
        </vs-row>
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
        <vs-description-list v-if="day.dayMiles && day.dayKM" slot="day-distance" class="list-inline text-center">
            <vs-description-list-term class="list-inline-item"><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></vs-description-list-term>
            <vs-description-list-detail class="list-inline-item">{{day.dayMiles}}/{{day.dayKM}}</vs-description-list-detail>
        </vs-description-list>

        <vs-description-list v-if="day.transport.length" class="text-center justify-content-center align-items-center" slot="day-transport" inline>
            <vs-description-list-term class="col-auto px-0">Transport</vs-description-list-term>
            <vs-description-list-detail class="col-auto m-0 px-0" v-for="(transportType, transportTypeIndex) in day.transport" :key="transportTypeIndex">
                <vs-tooltip :title="transportType.value">
                    <vs-icon :name="transportType.key" variant="dark" size="sm" />
                </vs-tooltip>
              <span class="sr-only">{{transportType.value}}</span>
            </vs-description-list-detail>
        </vs-description-list>
                    
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
                <vs-link external href="stop.href">
                    Find out more
                </vs-link>
                <vs-description-list class="my-4 mb-0 justify-content-start" inline>
                    <vs-description-list-term class="mb-0 mr-0 col-auto">Time to explore</vs-description-list-term>
                    <vs-description-list-detail class="mb-0 col-auto px-0">{{stop.timeToExplore}}</vs-description-list-detail>
                </vs-description-list>
                <vs-itinerary-tips v-if="stop.tips.tipsBody.length || stop.tips.tipsTitle.length">
                    <div slot="text">
                    <strong>{{stop.tips.tipsTitle}}</strong>
                    <div v-html="stop.tips.tipsBody"></div>
                    </div>
                    <vs-svg slot="svg" path="highland-cow" />
                </vs-itinerary-tips>
                <vs-icon-description-list v-if="stop.facilities.length">
                    <vs-icon-description-list-term>Key facilities</vs-icon-description-list-term>
                    <vs-icon-description-list-detail 
                        v-for="(facility, facilitiesIndex) in stop.facilities"
                        :key="facilitiesIndex"
                        :label="facility.value"
                        :icon="facility.key"
                        />
                </vs-icon-description-list>
              </div>
              <!-- mimic only showing these links on the last stop of the day -->
              <template v-if="stopIndex == day.stops.length - 1">
                <vs-itinerary-nearby-links-wrapper slot="nearby-links">
                    <vs-button-with-icon class="mb-3" variant="outline-primary" href="#" icon="food">
                        Nearby places to eat
                    </vs-button-with-icon>
                    <vs-button-with-icon variant="outline-primary" href="#" icon="product-accommodation">
                        Nearby places to stay
                    </vs-button-with-icon>
                </vs-itinerary-nearby-links-wrapper>
            </template>
        </vs-itinerary-stop>
    </vs-itinerary-day>
</vs-itinerary>
  ```
</docs>
