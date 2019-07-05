<template>
  <vs-svg
    :path="path"
    :class="{
      icon: true,
      ['icon-' + size]: true,
      ['icon-' + name]: true,
      ['icon-' + variant]: variant,
      'icon-reverse': reverse,
    }"
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
      default: null,
      validator: value => {
        return value.match(/(primary|secondary|success|danger|warning|info|light|dark)/)
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
    /**
     * Whether to reverse the icon's background and
     * fill colours
     */
    reverse: {
      type: Boolean,
    },
  },
  computed: {
    path() {
      return iconPath + this.name
    },
    dimension() {
      return get(designTokens, "props.icon_size_" + this.size + ".value", "40px")
    },
  },
}
</script>

<style lang="scss" scoped>
// @include reset;

$sizes: (
  xs: $icon-size-xs,
  sm: $icon-size-sm,
  md: $icon-size-md,
  lg: $icon-size-lg,
  xl: $icon-size-xl,
);

$variants: (
  primary: $color-theme-primary,
  secondary: $color-theme-secondary,
  success: $color-theme-success,
  danger: $color-theme-danger,
  warning: $color-theme-warning,
  info: $color-theme-info,
  light: $color-theme-light,
  dark: $color-theme-dark,
);

.icon {
  fill: $color-black;
  overflow: visible;

  &.icon-reverse {
    background-color: $color-black;
    fill: $color-white;
  }

  @each $size in map-keys($sizes) {
    &.icon-#{$size} {
      $padding: map-get($sizes, $size) / 4;
      $dimension: map-get($sizes, $size) + ($padding * 2);
      height: $dimension;
      width: $dimension;
      padding: $padding;
      border-radius: $dimension / 2;
    }
  }

  @each $variant in map-keys($variants) {
    &.icon-#{$variant} {
      fill: map-get($variants, $variant);

      &.icon-reverse {
        fill: $color-white;
        background: map-get($variants, $variant);
      }
    }
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
