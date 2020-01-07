<template>
  <vs-button
    class="vs-mobile-nav__button p-0 p-sm-1"
    id="mobilenavbutton"
    v-b-toggle.collapse-mobile-nav
    variant="transparent"
    focus-style="underline"
    focus-colour="pink"
    @click.native="handleClick()"
  >
    <span class="sr-only"><slot /></span>
    <vs-icon v-if="show" name="close" size="sm" variant="dark" />
    <vs-icon v-else name="menu" size="sm" variant="dark" />
  </vs-button>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"
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
    name: {
      type: String,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "../../styles/placeholders";
@import "../../styles/animations";
</style>

<docs>
  ```jsx

  <div>
    <vs-mobile-nav-button name="Mobile navigation">Toggle Main Navigation</vs-mobile-nav-button>
  </div>
  ```
</docs>
