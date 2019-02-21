<template>
  <component
    :is="type"
    :href="href"
    :type="typeString"
    :class="['button', state, variationClass, iconAlignClass]"
    :disabled="disabled"
  >
    <slot /> <i v-if="icon" :class="['icon', 'icon-' + icon]"></i>
    <i v-if="variation === 'dropdown'" class="icon icon-chevron-down"></i>
  </component>
</template>

<script>
/**
 * Buttons are generally used for interface actions. Suitable for all-purpose use.
 * Defaults to appearance that has white background with grey border.
 * Primary style should be used only once per view for main call-to-action.
 */
export default {
  name: "VsButton",
  status: "prototype",
  release: "3.5.0",
  props: {
    /**
     * The html element used for the button.
     * `button, a`
     */
    type: {
      type: String,
      default: "button",
      validator: value => {
        return value.match(/(button|a)/)
      },
    },

    /**
     * When setting the button’s type to a link, use this option to give a href.
     */
    href: {
      type: String,
      default: null,
    },
    /**
     * Set the button’s type to “submit”.
     */
    submit: {
      type: Boolean,
    },
    /**
     * Manually trigger various states of the button.
     * `hover, active, focus`
     */
    state: {
      type: String,
      default: null,
      validator: value => {
        return value.match(/(hover|active|focus)/)
      },
    },
    /**
     * Style variation to give additional meaning.
     * `primary, secondary`
     */
    variation: {
      type: String,
      default: null,
      validator: value => {
        return value.match(/(primary|dropdown|tag|keyline|keyline-white)/)
      },
    },
    /**
     * Whether button is disabled.
     */
    disabled: {
      type: Boolean,
    },
    /**
     * Icon class.
     *
     */
    icon: {
      type: String,
      default: null,
    },
    /**
     * Icon left or right.
     * `left, right`
     */
    iconAlign: {
      type: String,
      default: null,
      validator: value => {
        return value.match(/(left|right)/)
      },
    },
  },
  computed: {
    variationClass: function() {
      return "button--" + (this.variation || "primary")
    },
    iconAlignClass: function() {
      if (this.icon) {
        return "button--icon-" + (this.iconAlign || "left")
      }

      return null
    },
    typeString: function() {
      if (this.type === "button") {
        return this.submit ? "submit" : "button"
      }

      return null
    },
  },
}
</script>

<style lang="scss" scoped>
%button,
a.button,
button.button {
  display: inline-block;
  position: relative;
  padding: 0.5em 1em;
  border: 2px solid $color_heather_pink;
  border-radius: 1000px;
  background: $color_heather_pink;
  color: white;
  font-weight: $font-weight-bold;
  cursor: pointer;
  user-select: none;

  %button:hover,
  &:focus,
  &:hover,
  &.hover,
  &.focus {
    background: darken($color_heather_pink, 8%);
    border-color: darken($color_heather_pink, 8%);
    color: white;
  }

  &:focus {
    outline: none;
  }

  &[disabled] {
    opacity: 0.5;
    cursor: default;
    pointer-events: none;
    filter: grayscale(100%);
  }

  %button--keyline,
  &--keyline {
    background: transparent;
    color: $color_heather_pink;
    border-color: $color_heather_pink;

    &:focus,
    &:hover {
      background: transparent;
      color: $color_total_eclipse;
      border-color: $color_total_eclipse;
    }
  }

  %button--keyline-white,
  &--keyline-white {
    background: transparent;
    color: white;
    border-color: white;

    &:focus,
    &:hover {
      background: white;
      color: $color_dreich_gray;
      border-color: white;
    }
  }

  %button--icon-left,
  &--icon-left {
    padding-left: 2.25em;

    .icon {
      position: absolute;
      top: 50%;
      margin-top: -0.5em;
      left: 0.75em;
      line-height: 1;
    }
  }

  %button--icon-right,
  &--icon-right {
    padding-right: 2.25em;

    .icon {
      position: absolute;
      top: 50%;
      margin-top: -0.5em;
      right: 0.75em;
      line-height: 1;
    }
  }

  &--dropdown {
    @extend %button--icon-right;

    border-radius: $border-radius-lg;
    border-width: 1px;
    border-color: $color_mid_granite;
    color: $color_dark_granite;
    background: rgba($color_white, 0.8);

    &:focus,
    &:hover {
      border-color: $color_heather_pink;
      color: $color_heather_pink;
      background: rgba($color_white, 1);
    }
  }

  &--tag {
    border: none;
    background: $color_light_granite;
    color: $color_total_eclipse;
    border-radius: $border-radius;

    &:focus,
    &:hover {
      background: darken($color_light_granite, 5%);
      color: $color_total_eclipse;
    }
  }
}

.button-menu {
  @extend %button;

  border-radius: 0;
  border: 1px solid $color_thistle_purple;
  background: $color_white;
  color: $color_thistle_purple;
  cursor: pointer;

  &:hover,
  &:focus {
    border: 1px solid $color_thistle_purple;
    background: $color_white;
    color: $color_thistle_purple;
    box-shadow: none;
  }

  &--active,
  &--active:hover,
  &--active:focus {
    border: 1px solid $color_thistle_purple;
    background: $color_thistle_purple;
    color: $color_white;
    box-shadow: none;
    padding-bottom: vs-padding(md);
    right: 0;
  }
}
</style>

<docs>
  ```jsx
  <div>
    <div class="row mb-4">
      <vs-button variation="primary" class="mr-4">Default</vs-button>

      <vs-button variation="dropdown" class="mr-4">variation="dropdown"</vs-button>

      <vs-button variation="tag" class="mr-4">variation="tag"</vs-button>

      <vs-button variation="keyline" class="mr-4">variation="keyline"</vs-button>

      <div class="card bg-dark p-2 mt-2 mb-0">
        <vs-button variation="keyline-white" class="mr-4">variation="keyline-white"</vs-button>
      </div>
    </div>
    <div class="row mb-4">
      <vs-button icon="arrow-left" class="mr-4">icon="x"</vs-button>

      <vs-button icon="arrow-right" icon-align="right" class="mr-4">icon="x" icon-align="right"</vs-button>
    </div>
    <div class="row mb-4">
      <vs-button state="hover" class="mr-4">state="hover"</vs-button>

      <vs-button state="focus">state="focus"</vs-button>
    </div>
    <div class="row mb-4">
      <vs-button disabled class="mr-4">disabled</vs-button>
    </div>
    <div class="row mb-4">
      <vs-button type="button" class="mr-4">type="button"</vs-button>
      <vs-button type="a" class="mr-4" href="https://visitscotland.com">type="a"</vs-button>
    </div>
    <div class="row mb-4">
      <vs-button submit class="mr-4">submit</vs-button>
    </div>
  </div>
  ```
</docs>
