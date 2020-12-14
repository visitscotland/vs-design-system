<template>
    <div
        class="vs-page-intro position-relative"
        :class="backgroundClass"
        data-test="vs-page-intro"
    >
        <slot name="hero" />
        <div class="vs-page-intro__wrapper--outer">
            <div class="vs-page-intro__wrapper--inner">
                <div
                    class="vs-page-intro__wrapper--inner-top"
                    data-test="vs-page-intro__upper"
                >
                    <slot name="upper" />
                </div>
                <div
                    class="vs-page-intro__wrapper--inner-bottom py-9"
                    v-if="!!this.$slots['lower']"
                    data-test="vs-page-intro__lower"
                >
                    <slot name="lower" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
/**
 * Component is just a wrapper for the page hero and introduction.
 * Note that everything within is just a slot.
 *
 * @displayName Page Intro
 */
export default {
    name: 'VsPageIntro',
    status: 'prototype',
    release: '0.0.1',
    props: {
        background: {
            type: String,
            default: 'light',
            validator: (value) => value.match(/(light|dark)/),
        },
    },
    computed: {
        backgroundClass() {
            return `vs-page-intro--${this.background}`;
        },
    },
};
</script>

<style lang="scss">
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

.vs-page-intro .vs-page-intro__wrapper--inner {
    @include media-breakpoint-up(lg) {
        background: $color-white;
        margin: -250px 0 0;
        position: relative;
    }

    @include media-breakpoint-up(xl) {
        margin: -250px 0 0;
    }
}

.vs-page-intro .vs-hero figcaption {
    @include media-breakpoint-up(lg) {
        bottom: 200px;
    }

    // IE11 - force min width of hero caption
    @media screen and (-ms-high-contrast: active), screen and (-ms-high-contrast: none) {
        min-width: 200px;
    }
}

.vs-page-intro--dark {
    background: $color-secondary-gray-shade-4;
}
</style>

<docs>

  ```jsx
    const sampleItinerary = require("../../../assets/fixtures/itineraries/sample-itinerary.json")
    <vs-page-intro background="dark">
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
              <!-- TODO - Below icon is FPO. Replace with icon
              with text component and a share component -->
              <vs-icon name="share" variant="dark" size="md" />
            </div>
          </vs-col>
        </vs-row>
        <vs-row>
          <vs-col cols="12" md="6" lg="5" xl="6" offset-lg="1">
            <vs-rich-text-wrapper
                variant="lead"
                v-html="itineraries.sampleItinerary.introduction">
            </vs-rich-text-wrapper>
            <dl class="list-inline">
                <dt class="list-inline-item">Start / Finish</dt>
                <dd class="list-inline-item">
                    {{itineraries.sampleItinerary.start}}/{{itineraries.sampleItinerary.finish}}
                </dd>
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
                <vs-description-list-item title>Highlights</vs-description-list-item>
                <vs-description-list-item
                    v-for="(highlight, index) in itineraries.sampleItinerary.highlights"
                >
                    {{highlight}}
                </vs-description-list-item>
            </vs-description-list>
            <vs-description-list class="mb-8">
                <vs-description-list-item title>Areas Covered</vs-description-list-item>
                    <vs-description-list-item
                        v-for="(areaCovered, index) in itineraries.sampleItinerary.areasCovered"
                        key="index"
                    >
                    {{areaCovered}}
                </vs-description-list-item>
            </vs-description-list>
          </vs-col>
        </vs-row>
      </vs-container>

      <vs-container slot="lower">
       <vs-row>
        <vs-col cols="12" lg="11" offset-lg="1">
          <vs-description-list class="mb-6">
              <vs-description-list-item title>Highlights</vs-description-list-item>
              <vs-description-list-item
                  v-for="(highlight, index) in itineraries.sampleItinerary.highlights"
              >
                  {{highlight}}
              </vs-description-list-item>
          </vs-description-list>
          <vs-description-list class="mb-8">
              <vs-description-list-item title>Areas Covered</vs-description-list-item>
                  <vs-description-list-item
                      v-for="(areaCovered, index) in itineraries.sampleItinerary.areasCovered"
                      key="index"
                  >
                  {{areaCovered}}
              </vs-description-list-item>
          </vs-description-list>
        </vs-col>
      </vs-row>
    </vs-container>
  </vs-page-intro>

  <vs-page-intro class="mt-12">
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
            <!-- TODO - Below icon is FPO. Replace with icon
            with text component and a share component -->
            <vs-icon name="share" variant="dark" size="md" />
          </div>
        </vs-col>
      </vs-row>
      <vs-row>
        <vs-col cols="12" md="6" lg="5" xl="6" offset-lg="1">
          <vs-rich-text-wrapper
              variant="lead"
              v-html="itineraries.sampleItinerary.introduction">
          </vs-rich-text-wrapper>
          <dl class="list-inline">
              <dt class="list-inline-item">Start / Finish</dt>
              <dd class="list-inline-item">
                  {{itineraries.sampleItinerary.start}}/{{itineraries.sampleItinerary.finish}}
              </dd>
          </dl>
        </vs-col>
      </vs-row>
    </vs-container>
    </vs-page-intro>
  ```
</docs>
