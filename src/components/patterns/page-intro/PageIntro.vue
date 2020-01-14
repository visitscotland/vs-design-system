<template>
  <component :is="type" class="vs-page-intro bg-light position-relative">
    <div class="vs-page-intro__wrapper--outer">
      <slot />
    </div>
  </component>
</template>

<script>
/**
 * Component is just a wrapper for the page hero and introduction. Note that everything within is just a slot.
 */
export default {
  name: "VsPageIntro",
  status: "prototype",
  release: "0.0.1",
  components: {},
  props: {
    /**
     * The image alt text for screen readers
     */
    type: {
      type: String,
      default: "div",
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-page-intro__wrapper--outer {
  background: $color-white;
  margin-top: -1rem;
  @include media-breakpoint-up(lg) {
    margin: 0;
    background: none;
  }
}
.vs-page-intro ::v-deep .vs-page-intro__wrapper--inner {
  @include media-breakpoint-up(lg) {
    padding: 2rem 4rem;
    background: $color-white;
    box-shadow: 0 0 1rem rgba(0, 0, 0, 0.2);
    margin: -150px 0 0;
    position: relative;
  }

  @include media-breakpoint-up(xl) {
    margin: -200px 0 0;
  }
}
</style>

<docs>
  
  ```jsx
    const sampleItinerary = require("../../../assets/fixtures/itineraries/sampleItinerary.json")
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
            </vs-col>
          </vs-row>
          <vs-row class="justify-content-md-between">
            <vs-col cols="12" sm="6" lg="7">
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
  ```
</docs>
