<template>
  <component :is="type" class="vs-itinerary-stop__list-item">
    <div class="vs-itinerary-stop__header d-flex align-items-top">
      <vs-icon name="map-marker-solid" variant="secondary-teal" size="md" :padding="0" />
      <slot name="stop-title" />
      <slot name="stop-favourite" />
    </div>
    <slot name="stop-image" />
    <slot name="stop-description" />
    <slot name="stop-time-to-explore" />
    <slot name="stop-pullout" />
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
import VsItineraryStopPullout from "@components/patterns/itineraries/components/itinerary-stop-pullout/ItineraryStopPullout"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"
import VsFavouritesToggleButton from "@components/patterns/favourites/FavouritesToggleButton"

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
    VsItineraryStopPullout,
    VsImageLocationMap,
    VsItineraryStopFacility,
    VsFavouritesToggleButton,
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
.vs-itinerary-stop__list-item {
  background-color: $color-white;
  border: 1px solid $color-gray-tint-5;
  padding: $spacer-4;
  margin-bottom: $spacer-4;
}

.vs-itinerary-stop__title ::v-deep > span {
  font-family: $headings-font-family;
  display: block;
}

.itinerary-stop__facilities {
  border-top: 1px solid $color-gray-tint-5;
  margin: $spacer-9 -1rem -1rem;
  padding: 1rem;
  text-align: center;

  dt {
    margin-bottom: 1rem;
    flex-basis: 1;
    display: block;
    width: 100%;
  }
}
</style>

<docs>
```jsx
<ul style="list-style-type: none; padding: 0px;">
<vs-itinerary-stop 
  v-for="(stop, index) in itineraries.sampleItinerary.days[3].stops"
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
  <vs-itinerary-stop-pullout slot="stop-pullout" v-if="stop.pullOut.description.length">
    <div slot="text">
      <strong>{{stop.pullOut.title}}</strong>
      <div v-html="stop.pullOut.description"></div>
    </div>
    <vs-svg slot="svg" path="highland-cow" />
  </vs-itinerary-stop-pullout>
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
  ```
</docs>
