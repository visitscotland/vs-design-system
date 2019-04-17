<template>
  <component
    :is="type"
    :href="href"
    :type="typeString"
    :class="['btn', variationClass, iconAlignClass]"
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
     * Style variation to give additional meaning.
     * `primary, secondary`
     */
    variation: {
      type: String,
      default: null,
      validator: value => {
        return value.match(/(primary|secondary|success|danger|warning|info|light|dark)/)
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
      return "btn-" + (this.variation || "primary")
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
@import "~bootstrap/scss/buttons";
</style>

<docs>
  ```jsx
  <div>
    <div class="row mb-4">
      <vs-button variation="primary" class="mr-2">Primary</vs-button>
      <vs-button variation="secondary" class="mr-2">Secondary</vs-button>
      <vs-button variation="success" class="mr-2">Success</vs-button>
      <vs-button variation="danger" class="mr-2">Danger</vs-button>
      <vs-button variation="warning" class="mr-2">Warning</vs-button>
      <vs-button variation="info" class="mr-2">Info</vs-button>
      <vs-button variation="light" class="mr-2">Light</vs-button>
      <vs-button variation="dark" class="mr-2">Dark</vs-button>
    </div>
    <div class="row mb-4">
      <vs-button variation="primary" disabled class="mr-2">Disabled</vs-button>
      <vs-button type="a" href="www.visitscotland.com" target="_blank">Link</vs-button>
    </div>
    <div class="row mb-4">

    </div>
    <div class="row mb-4">

    </div>
    <div class="row mb-4">

    </div>
    <div class="row mb-4">

    </div>
  </div>
  ```
</docs>
