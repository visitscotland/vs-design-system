<template>
  <vs-button
    class="vs-mobile-nav__button p-1"
    id="mobilenavbutton"
    variant="transparent"
    focus-style="underline"
    focus-colour="pink"
  >
    <span class="sr-only"><slot /></span>
    <vs-icon v-if="isOpen" name="close" size="sm" variant="dark" />
    <vs-icon v-else name="menu" size="sm" variant="dark" />
  </vs-button>
</template>

<script>
import VsIcon from "@components/elements/icon"
import VsButton from "@components/elements/button"

export default {
  name: "VsSiteNavMobileToggleButton",
  status: "prototype",
  release: "0.1.0",
  components: {
    VsIcon,
    VsButton,
  },
  props: {
    isOpen: {
      type: Boolean,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/utilities/spacing";
</style>

<docs>
  ```jsx

  <div>
    <vs-site-nav-mobile-toggle-button>
      Toggle Main Navigation
    </vs-site-nav-mobile-toggle-button>
  </div>
  ```
</docs>
