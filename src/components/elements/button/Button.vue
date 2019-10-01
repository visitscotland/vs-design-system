<template>
  <b-button
    :variant="variant"
    :href="href"
    :tabindex="tabindex"
    class="d-flex align-items-center justify-content-center"
    :class="{
      [focusStyleClass]: focusStyleClass,
      [focusColourClass]: focusColourClass,
    }"
  >
    <slot />
  </b-button>
</template>

<script>
import { BButton } from "bootstrap-vue"
import focusStylesMixin from "@/mixins/focusStyles.js"

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
  mixins: [focusStylesMixin],
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
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/buttons";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";

.btn {
  -webkit-appearance: none;
  border: none;

  @include vs-focus;

  &.btn-transparent {
    background-color: transparent;
    &:focus,
    &:hover,
    &:active {
      background-color: transparent;
    }
  }
}

.btn-dark {
  &:hover {
    background-color: $gray-shade-5;
  }
}
</style>

<docs>
```jsx
<bs-wrapper class="p-4" style="background-color:rgb(210, 210, 210)">

  <bs-wrapper class="mb-4">
    <h4>Primary variant (default)</h4>
    <bs-wrapper class="d-flex">
      <vs-button class="mr-2">Default</vs-button>
      <vs-button focus-style="underline" class="mr-2">Focus style: underline</vs-button>
      <vs-button focus-colour="pink" class="mr-2">Focus colour: pink</vs-button>
      <vs-button focus-colour="white" class="mr-2">Focus colour: white</vs-button>
      <vs-button focus-colour="black" class="mr-2">Focus colour: black</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>Secondary variant</h4>
    <bs-wrapper class="d-flex">
      <vs-button variant="secondary" class="mr-2">Default</vs-button>
      <vs-button variant="secondary" focus-style="underline" class="mr-2">Focus style: underline</vs-button>
      <vs-button variant="secondary" focus-colour="pink" class="mr-2">Focus colour: pink</vs-button>
      <vs-button variant="secondary" focus-colour="white" class="mr-2">Focus colour: white</vs-button>
      <vs-button variant="secondary" focus-colour="black" class="mr-2">Focus colour: black</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>Success variant</h4>
    <bs-wrapper class="d-flex">
      <vs-button variant="success" class="mr-2">Default</vs-button>
      <vs-button variant="success" focus-style="underline" class="mr-2">Focus style: underline</vs-button>
      <vs-button variant="success" focus-colour="pink" class="mr-2">Focus colour: pink</vs-button>
      <vs-button variant="success" focus-colour="white" class="mr-2">Focus colour: white</vs-button>
      <vs-button variant="success" focus-colour="black" class="mr-2">Focus colour: black</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>Danger variant</h4>
    <bs-wrapper class="d-flex">
      <vs-button variant="danger" class="mr-2">Default</vs-button>
      <vs-button variant="danger" focus-style="underline" class="mr-2">Focus style: underline</vs-button>
      <vs-button variant="danger" focus-colour="pink" class="mr-2">Focus colour: pink</vs-button>
      <vs-button variant="danger" focus-colour="white" class="mr-2">Focus colour: white</vs-button>
      <vs-button variant="danger" focus-colour="black" class="mr-2">Focus colour: black</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>Warning variant</h4>
    <bs-wrapper class="d-flex">
      <vs-button variant="warning" class="mr-2">Default</vs-button>
      <vs-button variant="warning" focus-style="underline" class="mr-2">Focus style: underline</vs-button>
      <vs-button variant="warning" focus-colour="pink" class="mr-2">Focus colour: pink</vs-button>
      <vs-button variant="warning" focus-colour="white" class="mr-2">Focus colour: white</vs-button>
      <vs-button variant="warning" focus-colour="black" class="mr-2">Focus colour: black</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>Info variant</h4>
    <bs-wrapper class="d-flex">
      <vs-button variant="info" class="mr-2">Default</vs-button>
      <vs-button variant="info" focus-style="underline" class="mr-2">Focus style: underline</vs-button>
      <vs-button variant="info" focus-colour="pink" class="mr-2">Focus colour: pink</vs-button>
      <vs-button variant="info" focus-colour="white" class="mr-2">Focus colour: white</vs-button>
      <vs-button variant="info" focus-colour="black" class="mr-2">Focus colour: black</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>Light variant</h4>
    <bs-wrapper class="d-flex">
      <vs-button variant="light" class="mr-2">Default</vs-button>
      <vs-button variant="light" focus-style="underline" class="mr-2">Focus style: underline</vs-button>
      <vs-button variant="light" focus-colour="pink" class="mr-2">Focus colour: pink</vs-button>
      <vs-button variant="light" focus-colour="white" class="mr-2">Focus colour: white</vs-button>
      <vs-button variant="light" focus-colour="black" class="mr-2">Focus colour: black</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>Dark variant</h4>
    <bs-wrapper class="d-flex">
      <vs-button variant="dark" class="mr-2">Default</vs-button>
      <vs-button variant="dark" focus-style="underline" class="mr-2">Focus style: underline</vs-button>
      <vs-button variant="dark" focus-colour="pink" class="mr-2">Focus colour: pink</vs-button>
      <vs-button variant="dark" focus-colour="white" class="mr-2">Focus colour: white</vs-button>
      <vs-button variant="dark" focus-colour="black" class="mr-2">Focus colour: black</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>Transparent variant</h4>
    <bs-wrapper class="d-flex">
      <vs-button variant="transparent" class="mr-2">Default</vs-button>
      <vs-button variant="transparent" focus-style="underline" class="mr-2">Focus style: underline</vs-button>
      <vs-button variant="transparent" focus-colour="pink" class="mr-2">Focus colour: pink</vs-button>
      <vs-button variant="transparent" focus-colour="white" class="mr-2">Focus colour: white</vs-button>
      <vs-button variant="transparent" focus-colour="black" class="mr-2">Focus colour: black</vs-button>
    </bs-wrapper>
  </bs-wrapper>

  <bs-wrapper class="mb-4">
    <h4>Links</h4>
    <bs-wrapper class="d-flex">
      <vs-button href="https://www.visitscotland.com" class="mr-2">Link</vs-button>
      <vs-button href="https://www.google.com" class="mr-2">Another link</vs-button>
    </bs-wrapper>
  </bs-wrapper>

</bs-wrapper>
```
</docs>
