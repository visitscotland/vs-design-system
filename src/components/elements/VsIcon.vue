<template>
  <vs-svg
    :path="path"
    :container="type"
    :aria-label="ariaLabel"
    :fill="fill"
    :class="['icon', 'icon-' + size]"
  />
</template>

<script>
import VsSvg from "./VsSvg"

const iconPath = "icons/"

/**
 * Icons are used to visually communicate core parts of the product and
 * available actions. They can act as wayfinding tools to help users more
 * easily understand where they are in the product.
 */
export default {
  name: "VsIcon",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
  props: {
    /**
     * The name of the icon to display.
     */
    name: {
      required: true,
      default: "settings",
    },
    /**
     * The fill color of the SVG icon.
     */
    fill: {
      type: String,
    },
    /**
     * Descriptive text to be read to screenreaders.
     */
    ariaLabel: {
      type: String,
      default: "icon",
    },
    /**
     * The html element name used for the icon.
     */
    type: {
      type: String,
      default: "span",
    },
    /**
     * The size of the icon. Defaults to medium.
     * `small, medium, large`
     */
    size: {
      type: String,
      default: "medium",
      validator: value => {
        return value.match(/(small|medium|large)/)
      },
    },
  },
  computed: {
    path() {
      return iconPath + this.name
    },
  },
}
</script>

<style lang="scss">
// We don’t want to use scoped since these styles need to cascade down to SVGs.
// We also want to be able to style .icon inside buttons etc.
.icon {
  // @include reset;
  &.icon-large svg {
    width: $spacer-3 * 1.5;
    height: $spacer-3 * 1.5;
  }
  &.icon-medium svg {
    width: $spacer-3;
    height: $spacer-3;
  }
  &.icon-small svg {
    width: $spacer-2;
    height: $spacer-2;
  }
}
</style>

<docs>
  ```jsx
  <div>
    <vs-icon name="search" aria-label="Look for something" fill="#7cb518" />
    <vs-icon name="picture" fill="rgb(255,186,10)" />
    <vs-icon name="youtube" fill="rgb(235,59,36)" />
    <vs-icon name="email" fill="rgb(37,138,239)" />


  </div>
  ```
</docs>
