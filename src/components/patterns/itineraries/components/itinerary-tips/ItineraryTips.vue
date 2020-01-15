<template>
  <div class="itinerary-tips position-relative my-7">
    <div class="itinerary-tips__text">
      <slot name="text" />
    </div>
    <div class="itinerary-tips__svg position-absolute">
      <slot name="svg" />
    </div>
  </div>
</template>

<script>
/**
 * Itinerary tips that optionally display within an itinerary stop
 */

export default {
  name: "VsItineraryTips",
  status: "prototype",
  release: "0.0.1",
  components: {},
  props: {},
}
</script>

<style lang="scss" scoped>
.itinerary-tips {
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
  padding: $spacer-4 $spacer-4 $spacer-4 $spacer-9;
}
.itinerary-tips__text ::v-deep {
  p:last-of-type {
    margin-bottom: 0;
  }
}
.itinerary-tips__svg {
  left: -10px;
  bottom: -5px;
}
</style>

<docs>
  ```jsx
    <vs-itinerary-tips>
      <div slot="text">
        <strong>Don't miss:</strong>
        <p>Make sure you visit one of the many lovely cafés and restaurants that the city has to offer, or venture out to experience Edinburgh’s lively nightlife in the many bars, clubs and restaurants across the city.</p>
      </div>
      <vs-svg slot="svg" path="highland-cow" />
    </vs-itinerary-tips>
  ``` 
</docs>
