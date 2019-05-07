<template>
  <component :is="theTag" class="sidebar" :tag="cols ? tag : false" :cols="cols">
    <slot />
  </component>
</template>

<script>
import VsCol from "../elements/Col"

/**
 * Sidebar component
 */
export default {
  name: "VsSidebar",
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
      default: "aside",
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
      <vs-sidebar cols="6">
        <vs-content-section tag="aside" heading="Heading">
          Aside section content - sidebar component has cols attribute, therefore rendered as a vs-col
        </vs-content-section>
      </vs-sidebar>
    </vs-row>
    <vs-row>
      <vs-col cols="6">
        <vs-sidebar>
          <vs-content-section tag="aside" heading="Heading">
            Aside section content - sidebar component has no cols attribute, therefore not rendered as a vs-col
          </vs-content-section>
        </vs-sidebar>
      </vs-col>
    </vs-row>
  </vs-container>
  ```
</docs>
