<template>
  <b-row tag="div">
    <!-- @slot The row's contents go here -->
    <slot />
  </b-row>
</template>

<script>
import BRow from "bootstrap-vue/es/components/layout/row"
/**
 * Rows are used to organise content into rows. Their parent must be a container or column
 * element and they must always have column elements as their direct children. Our row component
 * utilises the <a href="https://bootstrap-vue.js.org/docs/components/layout">b-row Bootstrap Vue component</a>.
 */

export default {
  name: "VsRow",
  status: "prototype",
  release: "0.0.1",
  components: {
    BRow,
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx
  <div>
    <vs-container>
      <vs-row>
        <vs-col>
          This is a row with a single column
        </vs-col>
      </vs-row>
    </vs-container>
  </div>
  ```
</docs>
