<template>
  <vs-drawer-toggle
    v-bind="$attrs"
    class="vs-header__drawer-toggle"
    :class="{ 'vs-header__drawer-toggle--top': isTop }"
  >
    <slot />
  </vs-drawer-toggle>
</template>

<script>
import VsDrawerToggle from "@components/patterns/drawer/DrawerToggle"

/**
 * This component is used to generate a VsDrawerToggle component with
 * the relevant styles for inclusion in VsHeader.
 */
export default {
  name: "VsHeaderDrawerToggle",
  components: {
    VsDrawerToggle,
  },
  props: {
    section: {
      type: String,
      default: "bottom",
      validator: value => {
        return value.match(/(top|bottom)/)
      },
    },
  },
  computed: {
    isTop() {
      return this.section === "top"
    },
  },
}
</script>

<style lang="scss" scoped>
@import "../../styles/placeholders";

.vs-header__drawer-toggle.vs-drawer-toggle {
  @extend %header-nav-item;

  &.vs-header__drawer-toggle--top {
    @extend %header-nav-item-top;
  }
}
</style>
