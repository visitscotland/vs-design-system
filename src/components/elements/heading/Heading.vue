<template>
  <component
    :is="type"
    class="heading"
    :class="{
      'heading--thin': thin,
    }"
  >
    <slot />
  </component>
</template>

<script>
import { isNumber } from "lodash"
/**
 * Headings are used as the titles of each major section of a page in the
 * interface. For example, templates generally use headings as their title.
 * Heading element provides an option to change the level of the heading.
 */
export default {
  name: "VsHeading",
  status: "prototype",
  release: "0.1.0",
  props: {
    /**
     * The heading level used for the heading.
     * `1, 2, 3, 4, 5, 6`
     */
    level: {
      type: [String, Number],
      default: "1",
      validator: value => {
        return isNumber(value) ? value > 0 && value < 7 : value.match(/(1|2|3|4|5|6)/)
      },
    },

    /**
     * Use the thin font
     */
    thin: {
      type: Boolean,
    },
  },
  computed: {
    type() {
      return "h" + this.level
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "../../../assets/fonts/fonts.css";

$font-sizes: (
  1: $h1-font-size,
  2: $h2-font-size,
  3: $h3-font-size,
  4: $h4-font-size,
  5: $h5-font-size,
  6: $h6-font-size,
);

.heading {
  @each $level, $size in $font-sizes {
    @at-root h#{$level}#{&} {
      letter-spacing: $size * 0.1;
      @if $level == 1 {
        @include media-breakpoint-up(lg) {
          letter-spacing: $letter-spacing-h1;
        }
      }
    }
  }

  &.heading--thin {
    font-family: $headings-font-family-thin;
  }
}
</style>

<docs>
  ```jsx
  <div>
    <vs-heading>H1 Heading</vs-heading>
    <vs-heading thin>H1 Heading</vs-heading>
    <br />
    <vs-heading level="2">H2 Heading</vs-heading>
    <vs-heading thin level="2">H2 Heading</vs-heading>
    <br />
    <vs-heading level="3">H3 Heading</vs-heading>
    <vs-heading thin level="3">H3 Heading</vs-heading>
    <br />
    <vs-heading level="4">H4 Heading</vs-heading>
    <vs-heading thin level="4">H4 Heading</vs-heading>
    <br />
    <vs-heading level="5">H5 Heading</vs-heading>
    <vs-heading thin level="5">H5 Heading</vs-heading>
    <br />
    <vs-heading level="6">H6 Heading</vs-heading>
    <vs-heading thin level="6">H6 Heading</vs-heading>
  </div>
  ```
</docs>
