<template>
  <component :is="level" class="heading" :class="displayClass">
    <slot /> <small v-if="sub" class="sub-heading d-block">{{ sub }}</small>
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
     * Set display heading size
     */
    display: {
      type: [String, Number],
    },

    /**
     * The sub-heading text
     */
    sub: {
      type: String,
    },
  },
  computed: {
    displayClass() {
      return this.display ? "display-" + this.display : null
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

  @at-root h1#{&}:not([class*="display"]) {
    font-size: $font-size-xxl;
    letter-spacing: $spacing-xs;
    @include media-breakpoint-up(lg) {
      font-size: $font-size-xxxl;
    }
  }

  @at-root h2#{&} {
    letter-spacing: $spacing-s;
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

  .sub-heading {
    font-weight: $font-weight-semi-bold;
  }

  &[class*="display"] {
    font-family: $font-family-display;

    .sub-heading {
      font-family: $font-family-display-secondary;
    }
  }
}
</style>

<docs>
  ```jsx
  <div>
    <vs-heading>The quick brown fox (h1/default)</vs-heading>
    <br />
    <vs-heading level="h2">The quick brown fox (h2)</vs-heading>
    <br />
    <vs-heading level="h3">The quick brown fox (h3)</vs-heading>
    <br />
    <vs-heading level="h4">The quick brown fox (h4)</vs-heading>
    <br />
    <vs-heading sub="jumps over the lazy dog">The quick brown fox</vs-heading>
    <br />
    <vs-heading display="1">Display 1</vs-heading>
    <br />
    <vs-heading display="2">Display 2</vs-heading>
    <br />
    <vs-heading display="3">Display 3</vs-heading>
    <br />
    <vs-heading display="4">Display 4</vs-heading>
    <br />
    <vs-heading display="4" sub="with subheading">Display 4</vs-heading>
  </div>
  ```
</docs>
