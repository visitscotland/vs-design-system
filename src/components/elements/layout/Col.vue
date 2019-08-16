<template>
  <b-col :tag="tag" v-bind="breakpointAttrs"> <slot /> </b-col>
</template>

<script>
import { BCol } from "bootstrap-vue"
import colsMixin from "@/mixins/cols.js"

/**
 * Column components are used to organise, align and space sibling blocks of content within row
 * elements. They are always direct children of row elements/components. Our column component
 * utilises <a href="https://bootstrap-vue.js.org/docs/components/layout">the Bootstrap Vue b-col component</a>.
 *
 * NOTE: this component uses the cols mixin, which means that it has the following props for defining
 *
 */

export default {
  name: "VsCol",
  status: "prototype",
  release: "0.0.1",
  components: {
    BCol,
  },
  mixins: [colsMixin],
  props: {
    /**
     * The number of columns this component should take up
     */
    cols: {
      type: [String, Number],
    },

    /**
     * The html element name used for the component
     */
    tag: {
      type: String,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/grid";
</style>

<docs>
  ```jsx

  <div class="style-cols">
    <vs-container>
      <vs-row>
        <vs-col>
          1 of 1
        </vs-col>
      </vs-row>
      <vs-row>
        <vs-col>
          1 of 2
        </vs-col>
        <vs-col>
          2 of 2
        </vs-col>
      </vs-row>
      <vs-row>
        <vs-col>
          1 of 3
        </vs-col>
        <vs-col>
          2 of 3
        </vs-col>
        <vs-col>
          3 of 3
        </vs-col>
      </vs-row>
    </vs-container>
  </div>

  ```
</docs>
