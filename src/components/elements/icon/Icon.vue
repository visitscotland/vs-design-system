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
 * Our icons come in specific sizes - xxs, xs, sm, md, lg and xl - and can be any colour,
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
        return value.match(
          /(primary|secondary|success|danger|warning|info|light|dark|reverse-white)/
        )
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
        return value.match(/(xxs|xs|sm|md|lg|xl)/)
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
  xxs: $icon-size-xxs,
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
  reverse-white: $color-white,
);

.icon {
  fill: $color-black;
  overflow: visible;

  &.icon-reverse {
    background-color: $color-black;
    fill: $color-white;
  }

  @each $size in map-keys($sizes) {
    $padding: map-get($sizes, $size) / 4;
    $dimension: map-get($sizes, $size) + ($padding * 2);

    &.icon-#{$size} {
      height: $dimension;
      width: $dimension;
      padding: $padding;
    }

    &.icon-reverse {
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
    <div class="row">
      <div class="col" style="margin-bottom: 10px">
        <h3>Default</h3>
        <vs-icon name="search" />
      </div>
    </div>
    
    <div class="row">
      <div class="col" style="margin-bottom: 10px">
        <h3>Variant</h3>
        <vs-icon name="user" variant="primary" />
        <vs-icon name="user" variant="secondary" />
        <vs-icon name="user" variant="success" />
        <vs-icon name="user" variant="warning" />
        <vs-icon name="user" variant="danger" />
        <vs-icon name="user" variant="dark" />
        <vs-icon name="user" variant="light" />
      </div>
    </div>
    
    <div class="row">
      <div class="col" style="margin-bottom: 10px">
        <h3>Reverse</h3>
        <vs-icon name="favourite" reverse />
        <vs-icon name="favourite" reverse variant="primary" />
        <vs-icon name="favourite" reverse variant="secondary" />
        <vs-icon name="favourite" reverse variant="success" />
        <vs-icon name="favourite" reverse variant="warning" />
        <vs-icon name="favourite" reverse variant="danger" />
        <vs-icon name="favourite" reverse variant="dark" />
        <vs-icon name="favourite" reverse variant="light" />
      </div>
    </div>

    <div class="row">
      <div class="col" style="margin-bottom: 10px">
        <h3>Size</h3>
        <ul style="list-style-type: none; margin: 0; padding: 0;">
          <li>
            <h2>xxs</h2>
            <vs-icon name="chevron-down" size="xxs" />
            <vs-icon name="favourite" size="xxs" />
            <vs-icon name="search" size="xxs" />
            <vs-icon name="user" size="xxs" />
            <vs-icon name="external-link" size="xxs" />
          </li>
          <li>
            <h2>xs</h2>
            <vs-icon name="chevron-down" size="xs" />
            <vs-icon name="favourite" size="xs" />
            <vs-icon name="search" size="xs" />
            <vs-icon name="user" size="xs" />
            <vs-icon name="external-link" size="xs" />
          </li>
          <li>
            <h2>sm</h2>
            <vs-icon name="chevron-down" size="sm" />
            <vs-icon name="favourite" size="sm" />
            <vs-icon name="search" size="sm" />
            <vs-icon name="user" size="sm" />
            <vs-icon name="external-link" size="sm" />
          </li>
          <li>
            <h2>md</h2>
            <vs-icon name="chevron-down" size="md" />
            <vs-icon name="favourite" size="md" />
            <vs-icon name="search" size="md" />
            <vs-icon name="user" size="md" />
            <vs-icon name="external-link" size="md" />
          </li>
          <li>
            <h2>lg</h2>
            <vs-icon name="chevron-down" size="lg" />
            <vs-icon name="favourite" size="lg" />
            <vs-icon name="search" size="lg" />
            <vs-icon name="user" size="lg" />
            <vs-icon name="external-link" size="lg" />
          </li>
          <li>
            <h2>xl</h2>
            <vs-icon name="chevron-down" size="xl" />
            <vs-icon name="favourite" size="xl" />
            <vs-icon name="search" size="xl" />
            <vs-icon name="user" size="xl" />
            <vs-icon name="external-link" size="xl" />
          </li>
        </ul>      
      </div>
    </div>
  </div>
  ```
</docs>
