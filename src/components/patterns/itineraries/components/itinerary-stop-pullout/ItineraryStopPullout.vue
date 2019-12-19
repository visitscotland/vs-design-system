<template>
  <div class="itinerary-stop-pullout position-relative my-7">
    <div class="itinerary-stop-pullout__text">
      <slot name="text" />
    </div>
    <div class="itinerary-stop-pullout__svg position-absolute">
      <slot name="svg" />
    </div>
  </div>
</template>

<script>
/**
 * TODO: Document usage
 */

export default {
  name: "VsItineraryStopPullout",
  status: "prototype",
  release: "0.0.1",
  components: {},
  props: {},
}
</script>

<style lang="scss" scoped>
.itinerary-stop-pullout {
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
  padding: $spacer-4 $spacer-4 $spacer-4 $spacer-9;
}
.itinerary-stop-pullout__text ::v-deep {
  p:last-of-type {
    margin-bottom: 0;
  }
}
.itinerary-stop-pullout__svg {
  left: -10px;
  bottom: -5px;
}
</style>

<docs>
  ```jsx
    <vs-itinerary-stop-pullout>
      <div slot="text">
        <strong>Don't miss:</strong>
        <p>Make sure you visit one of the many lovely cafés and restaurants that the city has to offer, or venture out to experience Edinburgh’s lively nightlife in the many bars, clubs and restaurants across the city.</p>
      </div>
      <vs-svg slot="svg" path="highland-cow" />
    </vs-itinerary-stop-pullout>
  ``` 
</docs>
