<template>
    <div class="vs-page-intro position-relative">
        <slot name="hero" />
        <div class="vs-page-intro__wrapper--outer">
            <div class="vs-page-intro__wrapper--inner">
                <div class="vs-page-intro__wrapper--inner-top">
                    <slot name="upper" />
                </div>
                <div class="vs-page-intro__wrapper--inner-bottom">
                    <slot name="lower" />
                </div>
            </div>
        </div>
    </div>
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
    props: {},
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
.vs-page-intro__wrapper--outer {
    background: $color-white;
    margin-top: -1rem;
    @include media-breakpoint-up(lg) {
        margin: 0;
        background: none;
        padding: 0 1rem;
        width: 100%;
        max-width: 960px;
        margin-right: auto;
        margin-left: auto;
    }
    @include media-breakpoint-up(xl) {
        max-width: 1140px;
    }
    @include media-breakpoint-up(xxl) {
        max-width: 1340px;
    }
}
.vs-page-intro__wrapper--inner-bottom {
    background-color: $color-theme-light;
}

.vs-page-intro ::v-deep .vs-page-intro__wrapper--inner {
    @include media-breakpoint-up(lg) {
        background: $color-white;
        box-shadow: 0 0 1rem rgba(0, 0, 0, 0.2);
        margin: -250px 0 0;
        position: relative;
    }

    @include media-breakpoint-up(xl) {
        margin: -250px 0 0;
    }
}
.vs-page-intro ::v-deep figcaption {
    @include media-breakpoint-up(lg) {
        bottom: 200px;
    }
}
</style>

<docs>
  
  ```jsx
    const sampleItinerary = require("../../../assets/fixtures/itineraries/sampleItinerary.json")
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
            <vs-summary-box-list textColor="light">
            <vs-summary-box-list-item>
                <vs-summary-box-text-only slot="summary-box-text-only">{{itineraries.sampleItinerary.totalDays}}</vs-summary-box-text-only>
                <vs-summary-box-label slot="summary-box-label">Days</vs-summary-box-label>
            </vs-summary-box-list-item>
            <vs-summary-box-list-item>
                <vs-summary-box-text-only slot="summary-box-text-only">{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</vs-summary-box-text-only>
                <vs-summary-box-label slot="summary-box-label">Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></vs-summary-box-label>
            </vs-summary-box-list-item>
            <vs-summary-box-list-item>
                <vs-summary-box-icon-with-label slot="icon-with-label">
                    <vs-icon slot="icon" name="cycle" variant="dark" size="md" :padding="0" />
                    <span slot="label">das Fahrrad</span>
                </vs-summary-box-icon-with-label>
                <vs-summary-box-label slot="summary-box-label">Transport</vs-summary-box-label>
            </vs-summary-box-list-item>
            <vs-summary-box-list-item>
                <vs-summary-box-icon-with-label slot="icon-with-label">
                    <vs-icon slot="icon" name="castle" variant="dark" size="md" :padding="0" />
                    <span slot="label">das beste von Schottland</span>
                </vs-summary-box-icon-with-label>
                <vs-summary-box-label slot="summary-box-label">Main theme</vs-summary-box-label>
            </vs-summary-box-list-item>
            </vs-summary-box-list>
          </vs-col>
        </vs-row>
      </vs-container>
      <vs-container slot="lower" class="py-lg-4">
        <vs-row>
          <vs-col cols="12" sm="6" lg="7" offset-lg="1">
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
          </vs-col>
        </vs-row>
      </vs-container>
    </vs-page-intro>
  ```
</docs>
