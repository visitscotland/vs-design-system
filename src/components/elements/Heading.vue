<template>
  <component :is="level" class="heading" :class="{ 'display-heading': display }">
    <slot />
  </component>
</template>

<script>
/**
 * Headings are used as the titles of each major section of a page in the
 * interface. For example, templates generally use headings as their title.
 * Heading element provides an option to change the level of the heading.
 */
export default {
  name: "VsHeading",
  status: "prototype",
  release: "1.0.0",
  props: {
    /**
     * The heading level used for the heading.
     * `h1, h2, h3, h4, h5, h6`
     */
    level: {
      type: String,
      default: "h1",
      validator: value => {
        return value.match(/(h1|h2|h3|h4|h5|h6)/)
      },
    },

    /**
     * Set to true for a display heading
     */
    display: {
      type: Boolean,
    },
  },
}
</script>

<style lang="scss" scoped>
.heading {
  // @include reset;
  @extend .mb-3;
  font-family: $font-family-heading;
  line-height: $line-height-xs;
  color: $color-rich-black;

  @at-root h1#{&} {
    font-size: $font-size-xxl;
    letter-spacing: $spacing-xs;
    @include media-breakpoint-up(lg) {
      font-size: $font-size-xxxl;
    }
  }

  @at-root h2#{&} {
    font-size: $h2-font-size;
    letter-spacing: $spacing-s;
  }

  @at-root h3#{&} {
    font-size: $h3-font-size;
  }

  @at-root h4#{&} {
    font-size: $h4-font-size;
  }

  @at-root h5#{&} {
    font-size: $h5-font-size;
  }

  @at-root h6#{&} {
    font-size: $h6-font-size;
  }

  @at-root h1#{&},
    h2#{&},
    h3#{&},
    h4#{&} {
    font-weight: $font-weight-semi-bold;
  }

  @at-root h5#{&},
    h6#{&} {
    font-weight: $font-weight-normal;
  }

  &.display-heading {
    font-family: $font-weight-bold;
    font-family: $font-family-display-secondary;
  }
}
</style>

<docs>
  ```jsx
  <div>
    <vs-heading display>The quick brown fox</vs-heading>
    <vs-heading>The quick brown fox</vs-heading>
    <vs-heading level="h2">The quick brown fox</vs-heading>
    <vs-heading level="h3">The quick brown fox</vs-heading>
    <vs-heading level="h4">The quick brown fox</vs-heading>
  </div>
  ```
</docs>
