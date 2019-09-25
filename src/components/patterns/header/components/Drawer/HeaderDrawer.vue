<template>
  <b-collapse
    v-model="drawer.isOpen"
    class="vs-header__drawer-wrapper py-4"
    id="vs-header__drawer-wrapper"
  >
    <vs-container>
      <slot />
    </vs-container>
  </b-collapse>
</template>

<script>
import smoothscroll from "smoothscroll-polyfill"
import { BCollapse } from "bootstrap-vue"
import VsContainer from "@components/elements/layout/Container"
import store, { names as storeNames } from "../../header.store"

export default {
  name: "VsHeaderDrawer",
  status: "prototype",
  release: "0.0.1",
  store,
  components: {
    VsContainer,
    BCollapse,
  },
  data() {
    return {
      drawer: {
        isOpen: !!store.getters["header/drawer/module"],
      },
    }
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "header",
    },
    favouriteHref: {
      type: String,
    },
    favouriteTitle: {
      type: String,
    },
  },
  computed: {
    drawerModule() {
      return store.getters["header/drawer/module"]
    },
  },
  watch: {
    drawerModule(newValue) {
      this.drawer.isOpen = !!newValue
    },
  },
  created() {
    smoothscroll.polyfill()
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/spacing";

.vs-header__drawer-wrapper {
  @extend %default-inset-box-shadow;
  background-color: $gray-tint-7;
  width: 100%;
}

.collapse:not(.show) {
  display: none;
}

.collapsing {
  position: relative;
  height: 0;
  overflow: hidden;
  transition: height 100ms ease;
}
</style>

<docs>
  ```vue

  ```
</docs>
