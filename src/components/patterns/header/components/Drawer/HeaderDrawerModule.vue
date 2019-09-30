<template>
  <vs-row v-show="isVisible" @focus="focusOnContent" tabindex="-1">
    <vs-col cols="auto" order="2" v-if="showClose">
      <vs-close-button class="vs-header__drawer__close-button" @click.native="closeDrawer">
        Close the header drawer
      </vs-close-button>
    </vs-col>
    <vs-col>
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
    moduleName: {
      type: String,
      required: true,
    },
    showClose: {
      type: Boolean,
      default: false,
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
    focusOnContent() {
      if (isFunction(get(this.$slots, "default[0].componentInstance.$el.focus"))) {
        this.$slots.default[0].componentInstance.$el.focus()
      }
    },
  },
  watch: {
    isVisible(newVal, oldVal) {
      Vue.nextTick(this.focusOnContent)
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-header__drawer__close-button {
  right: 1em;
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
