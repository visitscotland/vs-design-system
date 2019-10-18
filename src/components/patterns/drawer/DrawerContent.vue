<template>
  <vs-row class="position-relative" v-show="isVisible" @focus="focusOnContent" tabindex="-1">
    <div v-if="showClose" class="d-none d-md-block position-absolute close-button-wrapper">
      <vs-close-button
        @click.native="closeDrawer"
        @keydown.native="checkKeydown($event)"
        ref="closeButton"
      >
        Close this drawer
      </vs-close-button>
    </div>
    <vs-col md="10" xl="8" offset-md="1" offset-xl="2">
      <slot />
      <button @focus="closeDrawer" class="catch-focus__button" />
    </vs-col>
  </vs-row>
</template>

<script>
import Vue from "vue"
import logger from "@/utils/logger"
import { isFunction, get, hasIn } from "lodash"

import VsCloseButton from "@components/patterns/close-button/CloseButton"
import VsRow from "@components/elements/layout/Row"
import VsCol from "@components/elements/layout/Col"
import VsDrawer from "./Drawer"

import drawerStore from "./drawer.store"
import { IS_ACTIVE_CONTENT } from "./drawer.store.getter-types"
import { CLOSE_DRAWER } from "./drawer.store.action-types"

export default {
  name: "VsDrawerContent",
  components: {
    VsCloseButton,
    VsRow,
    VsCol,
  },
  props: {
    /**
     * unique key for this content - used to discover when to show
     * the content according to the drawer VueX store.
     */
    contentKey: {
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
     * Determines whether and what gains focus when this content
     * becomes visible.
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
  data() {
    return { drawerKey: null }
  },
  computed: {
    isVisible() {
      return drawerStore.getters["drawer/" + IS_ACTIVE_CONTENT](this.contentKey, this.drawerKey)
    },
    hasDefaultSlot() {
      return isFunction(get(this.$scopedSlots, "default"))
    },
  },
  methods: {
    closeDrawer() {
      drawerStore
        .dispatch("drawer/" + CLOSE_DRAWER, {
          drawerKey: this.drawerKey,
        })
        .then(returnFocusElement => {
          if (isFunction(get(returnFocusElement, "$el.focus"))) {
            returnFocusElement.$el.focus()
          }
        })
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
    getParentDrawerKey() {
      let $current = this.$parent
      let $parentEl = this.$el.closest("#vs-header__drawer-wrapper")
      let i = 0

      // TODO: Replace with a non-hacky solution ASAP or if one
      // doesn't exist, use a prop instead
      while (i < 500) {
        if ($current.$parent === undefined) {
          $current = null
          break
        }

        $current = $current.$parent

        if ($current.$options.name === VsDrawer.name) {
          break
        }

        i++
      }

      return get($current, "$options.propsData.drawerKey")
    },
  },
  watch: {
    isVisible(newVal, oldVal) {
      if (!oldVal && newVal) {
        Vue.nextTick(this.onBecomeVisible)
      }
    },
  },
  mounted() {
    let parentDrawerKey = this.getParentDrawerKey()

    if (!parentDrawerKey) {
      logger.error("VsDrawerContent is not inside a parent VsDrawer")
    }

    this.drawerKey = parentDrawerKey
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/position";

.close-button-wrapper {
  right: 0;
  top: 0;
}

.catch-focus__button {
  width: 0;
  height: 0;
  margin: 0;
  padding: 0;
  border: none;
  display: block;
}
</style>
