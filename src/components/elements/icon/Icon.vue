<template>
  <vs-svg
    :path="path"
    :class="['icon', 'icon-' + size, 'icon-' + name, 'icon-' + variant]"
    :height="dimension"
    :width="dimension"
    :fill="fill"
  />
</template>

<script>
import VsSvg from "../svg"
import designTokens from "@/assets/tokens/tokens.raw.json"
import { get } from "lodash"

const iconPath = "icons/"

/**
 * Icons are used to visually communicate core parts of the product and
 * available actions. They can act as wayfinding tools to help users more
 * easily understand where they are in the product.
 *
 * Our icons come in specific sizes - xs, sm, md, lg and xl - and can be any colour,
 * including any of the theme colours.
 */
export default {
  name: "VsIcon",
  status: "prototype",
  release: "0.1.0",
  components: { VsSvg },
  props: {
    /**
     * The name of the icon to display.
     */
    name: {
      required: true,
      default: "search",
    },
    /**
     * The fill color of the SVG icon.
     */
    variant: {
      type: String,
      default: "default",
      validator: value => {
        return value.match(/(default|primary|reverse)/)
      },
    },
    /**
     * The size of the icon. Defaults to medium.
     * `small, medium, large`
     */
    size: {
      type: String,
      default: "md",
      validator: value => {
        return value.match(/(xs|sm|md|lg|xl)/)
      },
    },
  },
  computed: {
    path() {
      return iconPath + this.name
    },
    dimension() {
      return get(designTokens, "props.icon_size_" + this.size + ".value", "32px")
    },
    fill() {
      if (!this.variant || this.variant === "default") {
        return
      }

      if (this.variant === "primary") {
      }
    },
  },
}
</script>

<style lang="scss" scoped>
// We don’t want to use scoped since these styles need to cascade down to SVGs.
// We also want to be able to style .icon inside buttons etc.
.icon {
  // @include reset;
  &.icon-primary {
    fill: $color-theme-primary;
  }
}
</style>

<docs>
  ```jsx
  <div>
    <vs-icon name="search" aria-label="Look for something" fill="#7cb518" />
    <vs-icon name="favourite" fill="rgb(255,186,10)" />
    <vs-icon name="user" fill="rgb(235,59,36)" />
  </div>
  ```
</docs>
