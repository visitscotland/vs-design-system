<template>
  <component :is="type" class="vs-itinerary__stop-card">
    <h3>
      <slot name="stop-title" />
    </h3>
    <slot name="stop-image" />
    <slot name="stop-description" />
    <slot name="stop-time-to-explore" />
    <slot class="vs-itinerary__stop-href" name="stop-href" />
    <slot name="stop-facilities" />
  </component>
</template>
<script>
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"
import VsItineraryStopImage from "@components/patterns/itineraries/components/itinerary-stop-image/ItineraryStopImage"
import VsItineraryStopFacilityListItem from "@components/patterns/itineraries/components/itinerary-stop-facility-list-item/ItineraryStopFacilityListItem"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"

/**
 * TODO: Document usage.
 */

export default {
  name: "VsItineraryStopCard",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsContainer,
    VsRow,
    VsCol,
    VsItineraryStopImage,
    VsImageLocationMap,
    VsItineraryStopFacilityListItem,
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
h3 {
  font-weight: $font-weight-light;

  span {
    font-weight: $font-weight-bold;
  }
}
</style>

<docs>
```jsx
<vs-itinerary-stop-card 
  v-for="(stop, index) in itineraries.sampleItinerary.days[0].stops"
  :key="index"
>
  <template slot="stop-title">
      <span>Stop {{stop.stopCount}}</span>
      {{stop.title}}
  </template>
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
    <vs-itinerary-stop-facility-list-item
      v-for="(facility, facilitiesIndex) in stop.facilities"
      :key="facilitiesIndex"
      :facility="facility"
    >
      {{facility.value}}
    </vs-itinerary-stop-facility-list-item>
  </ul>
  </vs-itinerary-stop-card>
  ```
</docs>
