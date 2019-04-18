<template>
  <component :is="level" class="heading"> <slot /> </component>
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
  },
}
</script>

<style lang="scss" scoped>
.heading {
  @include reset;
  @include stack-space($space-m);
  font-family: $font-family-heading;
  line-height: $line-height-xs;
  color: $color-rich-black;

  h1#{&} {
    font-size: $h1-font-size;
    letter-spacing: $spacing-xs;
    @media #{$media-query-l} {
      font-size: $size-xxxl;
    }
  }

  h2#{&} {
    font-size: $h2-font-size;
    letter-spacing: $spacing-s;
  }

  h3#{&} {
    font-size: $h3-font-size;
  }

  h4#{&} {
    font-size: $h4-font-size;
  }

  h5#{&} {
    font-size: $h5-font-size;
  }

  h6#{&} {
    font-size: $h6-font-size;
  }

  h1#{&},
  h2#{&},
  h3#{&},
  h4#{&} {
    font-weight: $font-weight-semi-bold;
  }

  h5#{&},
  h6#{&} {
    font-weight: $font-weight-normal;
  }
}
</style>

<docs>
  ```jsx
  <div>
    <vs-heading>The quick brown fox</vs-heading>
    <vs-heading level="h2">The quick brown fox</vs-heading>
    <vs-heading level="h3">The quick brown fox</vs-heading>
    <vs-heading level="h4">The quick brown fox</vs-heading>
  </div>
  ```
</docs>
