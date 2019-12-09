<template>
  <aside class="vs-related-content bg-dark my-9 p-4">
    <vs-container>
      <slot name="header" />
      <vs-row tag="ul" class="list-unstyled">
        <slot name="cards" />
      </vs-row>
    </vs-container>
  </aside>
</template>

<script>
import { VsContainer, VsRow } from "@components/elements/layout"

/**
 * TODO: Document usage
 */

export default {
  name: "VsRelatedContentList",
  status: "prototype",
  release: "0.0.1",
  components: { VsContainer, VsRow },
}
</script>

<style lang="scss" scoped>
.vs-related-content {
  display: block;
}
</style>
<docs>
  ```jsx
<vs-related-content-list>
  <h2 slot="header" class="text-warning text-center py-7 m-0">Extend Your Trip</h2>
  <vs-related-content-list-item
    v-for="(item, index) in relatedContent.relatedContent" 
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
