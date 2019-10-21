<template>
  <vs-row class="position-relative" v-show="isVisible" @focus="focusOnContent" tabindex="-1">
    <vs-col
      class="d-none d-md-flex col-md-1 col-xl-2 justify-content-end"
      order="2"
      v-if="showClose"
    >
      <vs-close-button
        @click.native="closeDrawer"
        @keydown.native="checkKeydown($event)"
        ref="closeButton"
      >
        Close the header drawer
      </vs-close-button>
    </vs-col>

    <vs-col order="1" md="10" xl="8" offset-md="1" offset-xl="2">
      <slot />
      <button @focus="closeDrawer" class="catch-focus__button" />
    </vs-col>
  </vs-row>
</template>

<script>
import Vue from "vue"
import headerStore from "../../header.store"
import { isFunction, get } from "lodash"
import VsCloseButton from "@components/patterns/close-button/CloseButton"
import VsRow from "@components/elements/layout/Row"
import VsCol from "@components/elements/layout/Col"

export default {
  name: "HeaderDrawerModule",
  components: {
    VsCloseButton,
    VsRow,
    VsCol,
  },
  props: {
    /**
     * Name of the module - used to discover when the module should
     * be shown according to the drawer header VueX store.
     */
    moduleName: {
      type: String,
      required: true,
    },
    /**
     * Whether or not the close drawer button is shown
     */
    showClose: {
      type: Boolean,
      default: false,
    },
    /**
     * Determines whether and what gains focus when this module
     * is opened, i.e. becomes visible.
     * `false, "content", "close"`
     */
    focusOnOpen: {
      type: [String, Boolean],
      default: false,
      validator: value => {
        return value === false || value.match(/(content|close)/)
      },
    },
  },
  computed: {
    isVisible() {
      return headerStore.getters["header/drawer/isCurrentModule"](this.moduleName)
    },
    hasDefaultSlot() {
      return isFunction(get(this.$scopedSlots, "default"))
    },
  },
  methods: {
    closeDrawer() {
      headerStore.dispatch("header/drawer/close")
    },
    onBecomeVisible() {
      if (this.focusOnOpen === "close" && this.showClose) {
        this.$refs.closeButton.$el.focus()
      } else if (this.focusOnOpen === "content") {
        this.focusOnContent()
      }
    },
    focusOnContent() {
      if (isFunction(get(this.$slots, "default[0].componentInstance.$el.focus"))) {
        this.$slots.default[0].componentInstance.$el.focus()
      }
    },
    checkKeydown($event) {
      if ($event.key === "Tab" && $event.shiftKey) {
        this.closeDrawer()
      }
    },
  },
  watch: {
    isVisible(newVal, oldVal) {
      if (!oldVal && newVal) {
        Vue.nextTick(this.onBecomeVisible)
      }
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/position";

.catch-focus__button {
  width: 0;
  height: 0;
  margin: 0;
  padding: 0;
  border: none;
  display: block;
}
</style>
