<template>
  <b-button :variant="variant" :type="type" :href="href" :disabled="disabled"> <slot /> </b-button>
</template>

<script>
import BButton from "bootstrap-vue/es/components/button/button"
/**
 * Buttons are generally used for interface actions. Suitable for all-purpose use.
 * Defaults to appearance that has white background with grey border.
 * Primary style should be used only once per view for main call-to-action.
 */

export default {
  name: "VsButton",
  status: "prototype",
  release: "3.5.0",
  components: {
    BButton,
  },
  props: {
    /**
     * The type applied to the button.
     * `button, submit, reset`
     */
    type: {
      type: String,
      default: "button",
      validator: value => {
        return value.match(/(button|submit|reset)/)
      },
    },
    /**
     * Use this option to render the button as an anchor element with the given href.
     */
    href: {
      type: String,
      default: null,
    },
    /**
     * Style variation to give additional meaning.
     * `primary, secondary, success, danger, warning, info, light, dark`
     */
    variant: {
      type: String,
      default: null,
      validator: value => {
        return value.match(/(primary|secondary|success|danger|warning|info|light|dark)/)
      },
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
      <div class="col">
        <h4>Variants</h4>
        <vs-button variant="primary" class="mr-2">Primary</vs-button>
        <vs-button variant="secondary" class="mr-2">Secondary</vs-button>
        <vs-button variant="success" class="mr-2">Success</vs-button>
        <vs-button variant="danger" class="mr-2">Danger</vs-button>
        <vs-button variant="warning" class="mr-2">Warning</vs-button>
        <vs-button variant="info" class="mr-2">Info</vs-button>
        <vs-button variant="light" class="mr-2">Light</vs-button>
        <vs-button variant="dark" class="mr-2">Dark</vs-button>
      </div>
    </div>
    <div class="row mb-4">
      <div class="col">
        <h4>Button type</h4>
        <vs-button type="button" class="mr-2">Button</vs-button>
        <vs-button type="reset" class="mr-2">Reset</vs-button>
        <vs-button type="submit" class="mr-2">Submit</vs-button>
      </div>
    </div>
    <div class="row mb-4">
      <div class="col">
        <h4>Link</h4>
        <vs-button href="https://www.visitscotland.com">Link</vs-button>
      </div>
    </div>
  </div>
  ```
</docs>
