<template>
  <vs-col class="sidebar" :tag="tag" v-bind="$attrs"> <slot /> </vs-col>
</template>

<script>
import { VsCol } from "../elements/layout/"

/**
 * Sidebar component: a wrapper component for sidebar content, generally
 * wrapped in content sections
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
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx
  <vs-container>
    <vs-row>
      <vs-sidebar>
        <vs-content-section tag="aside" heading="Heading">
          <p style="margin-bottom:8px">Aside section content. Sidebar container has:</p>
          
          <pre style="white-space:normal">no cols setting - auto flex fit</pre>
        </vs-content-section>
      </vs-sidebar>
    </vs-row>
    <vs-row>
      <vs-sidebar sm="10" md="8" lg="6" xl="4" xxl="2">
        <vs-content-section tag="aside" heading="Heading">
          <p style="margin-bottom:8px">Aside section content. Sidebar container has:</p>
          
          <pre style="white-space:normal">sm="10" md="8" lg="6" xl="4" xxl="2"</pre>
        </vs-content-section>
      </vs-sidebar>
    </vs-row>
    <vs-row>
      <vs-sidebar cols="6">
        <vs-content-section tag="aside" heading="Heading">
          <p style="margin-bottom:8px">Aside section content. Sidebar container has:</p>

          <pre style="white-space:normal">cols="6"</pre>
        </vs-content-section>
      </vs-sidebar>
    </vs-row>
  </vs-container>
  ```
</docs>
