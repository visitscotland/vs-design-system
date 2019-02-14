<template>
  <component :is="type" v-html="svg" />
</template>

<script>
const req = require.context("@/assets/", true, /^\.\/.*\.svg$/)

/**
 * SVGs are used to display vector images
 */
export default {
  name: "Svg",
  status: "review",
  props: {
    /**
     * The name of the svg to display.
     */
    name: {
      required: true,
      default: "svgs/logo",
    },
    /**
     * The fill color of the SVG icon.
     */
    fill: {
      type: String,
      default: "black",
    },
    /**
     * The html element name used for the icon.
     */
    type: {
      type: String,
      default: "svg",
    },
    /**
     * The height of the svg, in pixels
     * `small, medium, large`
     */
    height: {
      type: String,
      default: "512",
      validator: value => {
        return value.match(/(^0-9)/)
      },
    },
    /**
     * The height of the svg, in pixels
     * `small, medium, large`
     */
    width: {
      type: String,
      default: "512",
      validator: value => {
        return value.match(/(^0-9)/)
      },
    },
  },
  data() {
    return {
      svg: req("./" + this.name + ".svg").replace(/^<svg /, `<svg style="fill: ${this.fill}" `),
    }
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
  &.large svg {
    width: $space-l;
    height: $space-l;
  }
  &.medium svg {
    width: $space-m;
    height: $space-m;
  }
  &.small svg {
    width: $space-s;
    height: $space-s;
  }
}
</style>

<docs>
  ```jsx
  <div>
    <Icon name="ready" aria-label="Component is ready" fill="#7cb518" />
    <Icon name="review" fill="rgb(255,186,10)" />
    <Icon name="deprecated" fill="rgb(235,59,36)" />
    <Icon name="prototype" fill="rgb(37,138,239)" />
  </div>
  ```
</docs>
