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
  status: "review",
  release: "1.0.0",
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
// This is here just to provide defaults if the original tokens are removed.
// Can be removed once you’re ready to start defining your own sizes.
@import "../../docs/docs.tokens.scss";

// We don’t want to use scoped since these styles need to cascade down to SVGs.
// We also want to be able to style .icon inside buttons etc.
.icon {
  @include reset;
  &.icon-large svg {
    width: $space-l;
    height: $space-l;
  }
  &.icon-medium svg {
    width: $space-m;
    height: $space-m;
  }
  &.icon-small svg {
    width: $space-s;
    height: $space-s;
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
