<template>
  <b-col tag="div" :cols="cols"> <slot /> </b-col>
</template>

<script>
import BCol from "bootstrap-vue/es/components/layout/col"
/**
 * Column components are used to organise, align and space sibling blocks of content within row
 * elements. They are always direct children of row elements/components. Our column component
 * utilises <a href="https://bootstrap-vue.js.org/docs/components/layout">the Bootstrap Vue b-col component</a>.
 */

export default {
  name: "VsCol",
  status: "prototype",
  release: "0.0.1",
  components: {
    BCol,
  },
  props: {
    /**
     * The number of columns this component should take up
     */
    cols: {
      type: [String, Number],
    },
  },
}
</script>

<style lang="scss" scoped></style>

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
