<template>
    <b-col v-bind="$attrs"> <slot /> </b-col>
</template>

<script>
import { BCol } from "bootstrap-vue"

/**
 * Column components are used to organise, align and space sibling blocks of content within row
 * elements. They are always direct children of row elements/components. Our column component
 * utilises <a href="https://bootstrap-vue.js.org/docs/components/layout">the Bootstrap Vue BCol component</a>.
 *
 * NOTE: this component is a wrapper for the BCol component so any BCol prop can be applied to it.
 */

export default {
    name: "VsCol",
    status: "prototype",
    release: "0.1.0",
    components: {
        BCol,
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
        <vs-col cols="6" sm="12">
          <pre>cols="6" sm="12"</pre>
        </vs-col>
      </vs-row>
      <vs-row>
        <vs-col cols="8" lg="3">
          <pre>cols="8" lg="3"</pre>
        </vs-col>
        <vs-col>
          <pre>no cols props</pre>
        </vs-col>
      </vs-row>
    </vs-container>
  </div>

  ```
</docs>
