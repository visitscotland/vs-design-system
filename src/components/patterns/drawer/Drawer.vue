<template>
  <b-collapse v-model="isOpen" class="vs-header__drawer-wrapper" id="vs-header__drawer-wrapper">
    <vs-container v-if="container">
      <slot />
    </vs-container>

    <slot v-else />
  </b-collapse>
</template>

<script>
import smoothscroll from "smoothscroll-polyfill"

import drawerStore from "./drawer.store"
import { REGISTER_DRAWER } from "./drawer.store.action-types"
import { GET_ACTIVE_CONTENT } from "./drawer.store.getter-types"

import { BCollapse } from "bootstrap-vue"
import VsContainer from "@components/elements/layout/Container"

/**
 * VsDrawer provides a collapsible container that can contain
 * multiple sets of content wrapped in VsDrawerContent. Matched
 * VsToggle components provide the ability to switch between
 * the sets of content and open/close the drawer.
 */
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
    /**
     * Whether the content is wrapped in a container or not
     */
    container: {
      type: Boolean,
      default: true,
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
@import "~bootstrap/scss/utilities/background";

.vs-header__drawer-wrapper {
  @extend %default-inset-box-shadow;
  background-color: $color-gray-tint-7;
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
```jsx
<div>
  <bs-wrapper class="container">
    <bs-wrapper class="row mb-4">
      <bs-wrapper class="col-6 d-flex justify-content-center">
        <vs-drawer-toggle class="mr-2" drawer-key="drawer-1" content-key="content-1">
          Toggle<br />drawer 1<br />content 1</vs-drawer-toggle>
        <vs-drawer-toggle drawer-key="drawer-1" content-key="content-2">
          Toggle<br />drawer 1<br />content 2</vs-drawer-toggle>
      </bs-wrapper>
      <bs-wrapper class="col-6 d-flex justify-content-center">
        <vs-drawer-toggle class="mr-2" drawer-key="drawer-2" content-key="content-1">
            Toggle<br />drawer 2<br />content 1</vs-drawer-toggle>
        <vs-drawer-toggle drawer-key="drawer-2" content-key="content-2">
          Toggle<br />drawer 2<br />content 2</vs-drawer-toggle>
      </bs-wrapper>

    </bs-wrapper>

    <bs-wrapper class="row">
      <bs-wrapper class="col-6">
        <vs-drawer drawer-key="drawer-1">
          <vs-drawer-content content-key="content-1">
            DRAWER 1 CONTENT 1
          </vs-drawer-content>
          <vs-drawer-content content-key="content-2">
            DRAWER 1 CONTENT 2
          </vs-drawer-content>
        </vs-drawer>
      </bs-wrapper>

      <bs-wrapper class="col-6">
        <vs-drawer drawer-key="drawer-2">
          <vs-drawer-content content-key="content-1">
            DRAWER 2 CONTENT 1
          </vs-drawer-content>
          <vs-drawer-content content-key="content-2">
            DRAWER 2 CONTENT 2
          </vs-drawer-content>
        </vs-drawer>
      </bs-wrapper>

    </bs-wrapper>
  </bs-wrapper>
</div>
```
</docs>
