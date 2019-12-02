<template> </template>
<script>
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"
import VsHero from "@components/patterns/hero/Hero"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"

/**
 * TODO: Document usage.
 */

export default {
  name: "VsItinerary",
  status: "prototype",
  release: "0.0.1",
  components: { VsContainer, VsRow, VsCol, VsHero, VsImageLocationMap, VsButton, VsIcon },
  props: {},
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped></style>

<docs>
```jsx
    <vs-hero
      v-for="(item, index) in hero.imageExamples"
      :altText="item.altText"
      :credit="item.credit"
      :description="item.description"
      :image-src="item.imageSrc"
      :key="index"
      :latitude="item.latitude"
      :longitude="item.longitude"
    >
    <img 
      class="lazyload" 
      :src="item.imageSrc"
      srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
      :data-srcset="item.imageSrc" 
      :alt="item.altText"
      data-sizes="auto"
      slot="image" />
      <noscript>
        <img class="img-fluid" :src="item.imageSrc" alt="item.altText" />
      </noscript>
      <span slot="overlayText" class="vs-hero__overlay-text text-light">Scotland</span>
    </vs-hero>
  ```
</docs>
