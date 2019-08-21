<template>
  <component :is="type" class="vs-favourites">
    <b-collapse id="collapse-favourites" class="vs-favourites__wrapper">
      <vs-container>
        <vs-row>
          <vs-col> <h4>Favourites List</h4> </vs-col>
        </vs-row>
      </vs-container>
    </b-collapse>
  </component>
</template>

<script>
import { BCollapse } from "bootstrap-vue"

export default {
  name: "VsFavourites",
  status: "prototype",
  release: "0.0.1",
  components: {
    BCollapse,
  },
  data() {
    return {}
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "div",
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../styles/placeholders";

.vs-favourites__wrapper {
  background-color: $gray-tint-7;
  box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3), 0 8px 6px -6px rgba(0, 0, 0, 0.3);
  justify-content: space-between;
  padding: 1rem;
  left: 0;
  width: 100%;
  z-index: 1;
}
</style>

<docs>
  ```jsx
  <div style="position: relative; height: 100px;">
    <vs-favourites
    />
  </div>
  ```
</docs>
