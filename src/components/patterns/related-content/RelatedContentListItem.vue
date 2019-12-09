<template>
  <vs-col tag="li" cols="12" sm="6" lg="3" class="vs-related-content__list-item">
    <slot />
  </vs-col>
</template>

<script>
import { VsCol } from "@components/elements/layout"

/**
 * TODO: Document usage
 */

export default {
  name: "VsRelatedContentListItem",
  status: "prototype",
  release: "0.0.1",
  components: { VsCol },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx
<vs-related-content-list>
  <vs-related-content-list-item
    v-for="(item, index) in relatedcontent.sampleRelatedContent" 
    :key="index"
    slot="cards"
  >
    <vs-related-content-card>
      <img 
        :src="item.image.imageSrc" 
        :alt="item.image.imageAlt"
        class="card-img-top" 
      >
      <div class="card-body">
        <h3 class="card-title h5">
          <a class="stretched-link" :href="item.href">
            {{item.title}}
          </a>
        </h3>
        <div class="card-text">
          {{item.description}}
        </div>
      </div>
    </vs-related-content-card>
  </vs-related-content-list-item>
</vs-related-content-list>
  ```
</docs>
