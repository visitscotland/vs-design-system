<template>
  <dl class="vs-itinerary__highlights-list py-7">
    <slot />
  </dl>
</template>

<script>
/**
 * TODO: Document usage
 */

export default {
  name: "VsItineraryHighlightsList",
  status: "prototype",
  release: "0.0.1",
}
</script>

<style lang="scss" scoped>
.vs-itinerary__highlights-list {
  dt {
    font-family: $headings-font-family;
    border-bottom: 1px solid $color-gray-tint-5;
    margin-bottom: $spacer-3;
    padding: $spacer-1 0;
  }
}
</style>

<docs>
  ```jsx
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
  ```
</docs>
