<template>
  <component
    :is="type"
    class="vs-main-nav__button"
    id="mobilenavbutton"
    v-b-toggle.collapse-mobile-nav
    @click="handleClick()"
  >
    <span class="sr-only">Toggle Main Navigation</span>
    <vs-icon v-if="show" name="menu-close" size="sm" variant="dark" />
    <vs-icon v-else name="menu" size="sm" variant="dark" />
  </component>
</template>

<script>
import VsIcon from "../../../elements/icon/Icon"
import { VBToggle } from "bootstrap-vue"

export default {
  name: "VsMobileNavButton",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
  },
  data() {
    return {
      show: false,
    }
  },
  directives: { "b-toggle": VBToggle },
  methods: {
    handleClick() {
      this.show = !this.show
    },
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "button",
    },
    name: {
      type: String,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../styles/placeholders";
@import "../styles/animations";

.vs-main-nav__button {
  @extend %button-reset;
  @extend %main-nav-button-style;

  &:focus {
    @extend %focus-pink-inset;
  }
}
</style>

<docs>
  ```jsx

  <div>
    <vs-mobile-nav-button name="Mobile navigation"></vs-mobile-nav-button>
  </div>
  ```
</docs>
