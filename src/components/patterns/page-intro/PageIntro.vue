<template>
  <component :is="type" class="vs-page-intro bg-light position-relative">
    <div class="vs-page-intro__wrapper--outer">
      <slot />
    </div>
  </component>
</template>

<script>
/**
 * Itinerary Stop image element
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
    <vs-page-intro>
      
    </vs-page-intro>
  ```
</docs>
