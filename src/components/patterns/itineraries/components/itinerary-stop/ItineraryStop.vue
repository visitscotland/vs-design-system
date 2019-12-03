<template>
  <component :is="type" class="vs-itinerary-stop__list-item">
    <div class="vs-itinerary-stop__header d-flex align-items-top">
      <vs-icon name="map-marker-solid" variant="secondary-teal" size="md" padding="0" />
      <slot name="stop-title" />
    </div>
    <slot name="stop-image" />
    <slot name="stop-description" />
    <slot name="stop-time-to-explore" />
    <slot class="vs-itinerary-stop-href" name="stop-href" />
    <slot name="stop-facilities" />
  </component>
</template>
<script>
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"
import VsItineraryStopImage from "@components/patterns/itineraries/components/itinerary-stop-image/ItineraryStopImage"
import VsItineraryStopFacility from "@components/patterns/itineraries/components/itinerary-stop-facility/ItineraryStopFacility"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"

/**
 * TODO: Document usage.
 */

export default {
  name: "VsItineraryStop",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsContainer,
    VsRow,
    VsCol,
    VsItineraryStopImage,
    VsImageLocationMap,
    VsItineraryStopFacility,
    VsButton,
    VsIcon,
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "li",
    },
  },
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-itinerary-stop__list-item {
  border: 1px solid $color-secondary-gray;
  padding: $spacer-4;
}

.vs-itinerary-stop__title ::v-deep > span {
  font-family: $headings-font-family;
  display: block;
}
</style>

<docs>
```jsx
<ul style="list-style-type: none; padding: 0;">
<vs-itinerary-stop 
  v-for="(stop, index) in itineraries.sampleItinerary.days[0].stops"
  :key="index"
>
<vs-heading 
  slot="stop-title"
  level="3" 
  thin 
  class="vs-itinerary-stop__title ml-1">
  <span>Stop {{stop.stopCount}}</span>
  {{stop.title}}
</vs-heading>
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
  <dl slot="stop-time-to-explore">
    <dt>Time to explore:</dt>
    <dd>{{stop.timeToExplore}}</dd>
  </dl>
  <a slot="stop-href"
    :href="stop.href"
  >Find out more</a>
  <ul slot="stop-facilities" class="list-unstyled">
    <vs-itinerary-stop-facility
      v-for="(facility, facilitiesIndex) in stop.facilities"
      :key="facilitiesIndex"
      :facility="facility"
    >
      {{facility.value}}
    </vs-itinerary-stop-facility>
  </ul>
  </vs-itinerary-stop>
  </ul>
  ```
</docs>
