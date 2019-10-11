<template>
  <b-button
    :variant="variant"
    :href="href"
    :tabindex="tabindex"
    class="d-flex align-items-center justify-content-center"
    :size="size"
  >
    <slot />
  </b-button>
</template>

<script>
import { BButton } from "bootstrap-vue"

/**
 * Buttons are generally used for interface actions. Suitable for all-purpose use.
 * Defaults to appearance that has white background with grey border.
 * Primary style should be used only once per view for main call-to-action.
 *
 * NOTE: When listening for the @click event you need to use the `native` modifier
 * in order to listen for the underlying `button`s native event. e.g.
 *
 * <vs-button @click.native="handler">Click me</vs-button>
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
     * Tab index value - this is needed as tabindex attribute is sometimes stripped
     * from the button on first update with nested components
     */
    tabindex: {
      type: String,
      default: null,
    },
    /**
     * Style variation to give additional meaning.
     * `primary, primary-pink, secondary, success, danger, warning, info, light, dark, transparent`
     */
    variant: {
      type: String,
      default: "primary",
      validator: value => {
        return value.match(
          /(primary|primary-pink|secondary|success|danger|warning|info|light|dark|transparent)/
        )
      },
    },
    size: {
      type: String,
      default: "md",
      validator: value => {
        return value.match(/(sm|md|lg)/)
      },
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/buttons";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";

.btn {
  transition: $transition-base;
}
</style>

<docs>
```jsx

  <bs-wrapper class="mb-4">
    <h4>Variants</h4>
    <bs-wrapper class="d-flex mb-4">
      <vs-button class="mr-2">Primary (default)</vs-button>
      <vs-button variant="secondary" class="mr-2">Secondary</vs-button>
      <vs-button variant="success" class="mr-2">Success</vs-button>
      <vs-button variant="danger" class="mr-2">Danger</vs-button>
      <vs-button variant="warning" class="mr-2">Warning</vs-button>
      <vs-button variant="info" class="mr-2">Info</vs-button>
      <vs-button variant="light" class="mr-2">Light</vs-button>
      <vs-button variant="dark" class="mr-2">Dark</vs-button>
      <vs-button variant="transparent" class="mr-2">Transparent</vs-button>
    </bs-wrapper>
    <h4>Sizes</h4>
    <bs-wrapper>
      <vs-button class="mb-2" size="sm">Small</vs-button>
      <vs-button class="mb-2" size="md">Medium</vs-button>
      <vs-button class="mb-2" size="lg">Large</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>CTA Links</h4>
    <bs-wrapper class="d-flex">
      <vs-button href="https://www.visitscotland.com" class="mr-2">Link</vs-button>
      <vs-button href="https://www.google.com" class="mr-2">Another link</vs-button>
    </bs-wrapper>
  </bs-wrapper>

```
</docs>
