<template>
  <div class="vs-related-content__card card bg-dark">
    <slot />
  </div>
</template>

<script>
/**
 * TODO: Document usage
 */

export default {
  name: "VsRelatedContentCard",
  status: "prototype",
  release: "0.0.1",
  components: {},
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/card";

.vs-related-content__card {
  border: none;
  .card-body,
  .card-title a {
    color: $color-white;
  }
  .card-body {
    margin: 0 -1rem;
  }
}
</style>

<docs>
  ```jsx
    <vs-related-content-card>
      <img 
        :src="relatedcontent.sampleRelatedContent[0].image.imageSrc" 
        :alt="relatedcontent.sampleRelatedContent[0].image.imageAlt"
        class="card-img-top" 
      >
      <div class="card-body">
        <h3 class="card-title h5">
          <a class="stretched-link" :href="relatedcontent.sampleRelatedContent[0].href">
            {{relatedcontent.sampleRelatedContent[0].title}}
          </a>
        </h3>
        <div class="card-text">
          {{relatedcontent.sampleRelatedContent[0].description}}
        </div>
      </div>
    </vs-related-content-card>
  ```
</docs>
