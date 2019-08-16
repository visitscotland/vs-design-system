<template>
  <b-button :variant="variant || 'primary'" :href="href"> <slot /> </b-button>
</template>

<script>
import { BButton } from "bootstrap-vue"
/**
 * Buttons are generally used for interface actions. Suitable for all-purpose use.
 * Defaults to appearance that has white background with grey border.
 * Primary style should be used only once per view for main call-to-action.
 */

export default {
  name: "VsButton",
  status: "prototype",
  release: "0.0.1",
  components: {
    BButton,
  },
  props: {
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
    <bs-wrapper class="mb-4">
      <h4>Variants</h4>
      <vs-button variant="primary" class="mr-2">Primary</vs-button>
      <vs-button variant="secondary" class="mr-2">Secondary</vs-button>
      <vs-button variant="success" class="mr-2">Success</vs-button>
      <vs-button variant="danger" class="mr-2">Danger</vs-button>
      <vs-button variant="warning" class="mr-2">Warning</vs-button>
      <vs-button variant="info" class="mr-2">Info</vs-button>
      <vs-button variant="light" class="mr-2">Light</vs-button>
      <vs-button variant="dark" class="mr-2">Dark</vs-button>
    </bs-wrapper>
    <bs-wrapper class="mb-4">
      <h4>Link</h4>
      <vs-button href="https://www.visitscotland.com">Link</vs-button>
      <vs-button href="https://www.google.com">Another link</vs-button>
    </bs-wrapper>
  </div>
  ```
</docs>
