<template>
  <component :is="theTag" :tag="isVsCol ? tag : false" v-bind="breakpointAttrs">
    <slot />
  </component>
</template>

<script>
import colsMixin from "../../mixins/cols.js"
import VsCol from "../elements/Col"

/**
 * The ColWrapper component is used by components to wrap a the VsCol component
 * and includes some shared functionality for that scenario
 */
export default {
  name: "VsColWrapper",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsCol,
  },
  mixins: [colsMixin],
  props: {
    /**
     * The html element name used for the component
     */
    tag: {
      type: String,
      default: "div",
    },
  },
  computed: {
    /**
     * Returns the tag name for the component
     *
     * If isVsCol returns true then the tag is vs-col,
     * otherwise it's the value of the tag prop
     */
    theTag() {
      return this.isVsCol ? "vs-col" : this.tag
    },
    /**
     * Returns true if one of the breakpoint props is specified
     */
    isVsCol() {
      return this.cols | this.xs | this.sm | this.md | this.lg | this.xl | this.xxl
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
