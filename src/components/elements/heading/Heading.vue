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
      type: [String, Number],
      default: "1",
      validator: value => {
        return value.match(/(1|2|3|4|5|6)/)
      },
    },

    /**
     * Set the thin font
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
@import "~bootstrap/scss/utilities/spacing";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/type";

.heading {
  @at-root h1#{&}:not([class*="display"]) {
    letter-spacing: $h1-font-size * 0.1;
    @include media-breakpoint-up(lg) {
      letter-spacing: $letter-spacing-h1;
    }
  }

  @at-root h2#{&} {
    letter-spacing: $h2-font-size * 0.1;
  }

  @at-root h3#{&} {
    letter-spacing: $h3-font-size * 0.1;
  }

  @at-root h4#{&} {
    letter-spacing: $h4-font-size * 0.1;
  }

  @at-root h5#{&} {
    letter-spacing: $h5-font-size * 0.1;
  }

  @at-root h6#{&} {
    letter-spacing: $h6-font-size * 0.1;
  }

  &.heading--thin {
    font-family: $headings-font-family-thin;
  }
}
</style>

<docs>
  ```jsx
  <div>
    <vs-heading>The quick brown fox (level 1/default)</vs-heading>
    <br />
    <vs-heading level="2">The quick brown fox (level 2)</vs-heading>
    <br />
    <vs-heading level="3">The quick brown fox (level 3)</vs-heading>
    <br />
    <vs-heading level="4">The quick brown fox (level 4)</vs-heading>
    <br />
    <vs-heading level="5">The quick brown fox (level 5)</vs-heading>
    <br />
    <vs-heading level="6">The quick brown fox (level 6)</vs-heading>
    <br />
    <vs-heading thin>The quick brown fox (thin level 1/default)</vs-heading>
    <br />
    <vs-heading thin level="2">The quick brown fox (thin level 2/default)</vs-heading>
    <br />
    <vs-heading thin level="3">The quick brown fox (thin level 3/default)</vs-heading>
    <br />
    <vs-heading thin level="4">The quick brown fox (thin level 4/default)</vs-heading>
    <br />
    <vs-heading thin level="5">The quick brown fox (thin level 5/default)</vs-heading>
    <br />
    <vs-heading thin level="6">The quick brown fox (thin level 6/default)</vs-heading>
    <br />
  </div>
  ```
</docs>
