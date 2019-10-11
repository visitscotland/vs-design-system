<template>
  <component
    :is="type"
    @click.native.prevent="toggleModule"
    ref="self"
    :aria-expanded="moduleIsVisible"
    :is-on="moduleIsVisible"
    v-bind="$attrs"
  >
    <slot />
  </component>
</template>

<script>
import store from "../../header.store"

export default {
  name: "VsHeaderDrawerToggle",
  props: {
    type: {
      type: String,
      default: "vs-button",
    },
    moduleName: {
      type: String,
      required: true,
    },
  },
  computed: {
    moduleIsVisible() {
      return store.getters["header/drawer/isCurrentModule"](this.moduleName)
    },
  },
  methods: {
    closeDrawer() {
      store.dispatch("header/drawer/close")
    },
    showModule() {
      store.dispatch("header/drawer/showModule", {
        moduleName: this.moduleName,
        returnFocusElement: this.$refs.self,
      })
    },
    toggleModule() {
      if (this.moduleIsVisible) {
        this.closeDrawer()
      } else {
        this.showModule()
      }
    },
  },
}
</script>
