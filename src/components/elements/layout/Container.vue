<template>
  <b-container tag="div" v-bind="$attrs"><slot /></b-container>
</template>

<script>
import { BContainer } from "bootstrap-vue"
/**
 * Containers are the primary structural element used to organise page content. Our containers utilise
 * <a href="https://bootstrap-vue.js.org/docs/components/layout">Bootstrap Vue's b-container component</a>,
 * and can be fixed (default) or fluid.
 */

export default {
  name: "VsContainer",
  status: "prototype",
  release: "0.1.0",
  components: {
    BContainer,
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/grid";
</style>

<docs>
  ```jsx
    <div>
      <vs-container style="background:#ccc">
        <vs-row>
          <vs-col>This is a fixed-width container</vs-col>
        </vs-row>
      </vs-container>
      <br />
      <vs-container fluid style="background:#ccc">
        <vs-row>
          <vs-col>This is a fluid container</vs-col>
        </vs-row>
      </vs-container>
      <br />
      *Note: the fixed width container appears fluid here because it is inside a parent element that is not full-page width.
    </div>

  ```
</docs>
