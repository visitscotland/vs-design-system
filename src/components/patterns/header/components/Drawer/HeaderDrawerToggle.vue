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
import drawerStore from "./drawer.store"

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
      return drawerStore.getters["drawer/isCurrentModule"](this.moduleName)
    },
  },
  methods: {
    closeDrawer() {
      drawerStore.dispatch("drawer/close")
    },
    showModule() {
      drawerStore.dispatch("drawer/showModule", {
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
