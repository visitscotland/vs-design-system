<template>
  <b-collapse
    v-model="isOpen"
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

import drawerStore from "./drawer.store"
import { REGISTER_DRAWER } from "./drawer.store.action-types"
import { GET_ACTIVE_CONTENT } from "./drawer.store.getter-types"

import { BCollapse } from "bootstrap-vue"
import VsContainer from "@components/elements/layout/Container"

export default {
  name: "VsDrawer",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsContainer,
    BCollapse,
  },
  data() {
    return {
      isOpen: Boolean(this.activeContent),
    }
  },
  props: {
    /**
     * The unique identifier for the Drawer instance
     */
    drawerKey: {
      type: String,
      required: true,
    },
  },
  computed: {
    activeContent() {
      return drawerStore.getters["drawer/" + GET_ACTIVE_CONTENT](this.drawerKey)
    },
  },
  watch: {
    activeContent(newValue) {
      this.isOpen = Boolean(newValue)
    },
  },
  beforeCreate() {
    /**
     * We do this on beforeCreate, otherwise the keys in the store will not be
     * reactive for the computed prop above
     */
    drawerStore.dispatch("drawer/" + REGISTER_DRAWER, {
      drawerKey: this.$options.propsData.drawerKey,
    })
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
