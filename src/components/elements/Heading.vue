<template>
  <component
    :is="type"
    class="heading"
    :class="{
      ['display-' + display]: display,
      'display-secondary': displaySecondary,
      'my-4': type === 'h1',
    }"
  >
    <small v-if="sub" class="sub-heading d-block">{{ sub }}</small> <slot />
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
     * Set display heading size
     */
    display: {
      type: [String, Number],
    },

    /**
     * Set display heading size
     */
    displaySecondary: {
      type: Boolean,
    },

    /**
     * The sub-heading text
     */
    sub: {
      type: String,
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
.heading {
  // @include reset;
  @extend .mb-3;
  font-family: $font-family-heading;
  line-height: $line-height-xs;
  color: $color-dreich-gray;

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
    color: $color-gorse-yellow;
    font-weight: $font-weight-semi-bold;
    font-size: 60%;
  }

  &[class*="display"] {
    font-family: $font-family-display;

    &.display-secondary {
      font-family: $font-family-display-secondary;
    }
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
    <vs-heading sub="Jumps over the lazy dog">The quick brown fox</vs-heading>
    <br />
    <vs-heading display="1">Display 1</vs-heading>
    <br />
    <vs-heading display="2">Display 2</vs-heading>
    <br />
    <vs-heading display="3">Display 3</vs-heading>
    <br />
    <vs-heading display="4">Display 4</vs-heading>
    <br />
    <vs-heading display="4" sub="With subheading">Display 4</vs-heading>
    <br />
    <vs-heading display="1" display-secondary>Display 1 secondary</vs-heading>
  </div>
  ```
</docs>
