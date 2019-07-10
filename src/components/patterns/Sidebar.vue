<template>
  <vs-col-wrapper class="sidebar" :tag="tag" v-bind="breakpointAttrs"> <slot /> </vs-col-wrapper>
</template>

<script>
import { VsColWrapper } from "../elements/layout/"
import colsMixin from "@/mixins/cols.js"

/**
 * Sidebar component: a wrapper component for sidebar content, generally
 * wrapped in content sections
 */
export default {
  name: "VsSidebar",
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
      default: "aside",
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
      <vs-sidebar sm="10" md="8" lg="6" xl="4" xxl="2">
        <vs-content-section tag="aside" heading="Heading">
          Aside section content - sidebar component has some cols attributes, therefore rendered as a vs-col
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
