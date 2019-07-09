<template>
  <vs-col-wrapper class="article" :tag="tag" v-bind="breakpointAttrs"> <slot /> </vs-col-wrapper>
</template>

<script>
import { VsColWrapper } from "../elements/layout"
import colsMixin from "@/mixins/cols.js"

/**
 * The Article component is a wrapper for article content, generally wrapped in content sections
 */
export default {
  name: "VsArticle",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsColWrapper,
  },
  mixins: [colsMixin],
  props: {
    /**
     * The html element name used for the component
     */
    tag: {
      type: String,
      default: "article",
    },
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx

  <vs-container>
    <vs-row>
      <vs-article sm="10" md="8" lg="6" xl="4" xxl="2">
        <vs-content-section title="First section" lead>
          <p>This is the lead section of the article. </p>
          
          <p>This article has breakpoint-specific column widths, meaning it's width is dynamic according to the xs, sm, md, lg, xl and xxl attributes applied to it and the browser width.</p>

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
