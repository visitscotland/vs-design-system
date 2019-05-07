<template>
  <component :is="theTag" class="article" :tag="cols ? tag : false" :cols="cols">
    <slot />
  </component>
</template>

<script>
import VsCol from "../elements/Col"

/**
 * Article component's contain sections of article content
 */
export default {
  name: "VsArticle",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsCol,
  },
  props: {
    /**
     * The html element name used for the component
     */
    tag: {
      type: String,
      default: "article",
    },

    /**
     * The number of cols the sidebar should take up
     */
    cols: {
      type: String,
    },
  },
  computed: {
    theTag() {
      return this.cols ? "vs-col" : this.tag
    },
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx

  <vs-container>
    <vs-row>
      <vs-article cols="6">
        <vs-content-section title="First section" lead>
          <p>This is the lead section of the article. </p>
          
          <p>Design traveling WordPress design blogger colorful fun. Clean traveling adventure theme wanderlust wanderlust simple darn wanderlust, adventure adventure webdesign expedition. Traveling simple traveling organized adventure traveling cute organized, colorful fun Travel website.</p>
        </vs-content-section>
        <vs-content-section title="Second section">
          <p>This is the second section of the article.</p>

          <p>Theme whimsical whimsical colorful simple organized darn cute, fun adventure simple organized. Traveler wanderlust modern blogger fun, website wanderlust theme design fun. Design darn colorful whimsical traveling wanderlust whimsical traveler clean, colorful website pretty excursion simple.</p>

          <p>Travel Travel wanderlust adventure colorful traveling modern Travel expedition. Theme Travel expedition expedition cute traveler expedition. Excursion traveling traveling travelblogger darn clean fun. Adventure website pretty WordPress design, design whimsical webdesign expedition whimsical. Design traveling darn website simple website pretty traveler.</p>
        </vs-content-section>
      </vs-article>
    </vs-row>
  </vs-container>

  ```
</docs>
