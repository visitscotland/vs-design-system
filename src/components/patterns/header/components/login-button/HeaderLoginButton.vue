<template>
  <vs-button @click.native="isLoggedIn = !isLoggedIn" class="vs-header__login-button">
    <vs-svg class="mr-1" path="icons/user" height="10" fill="white" />

    <template v-if="isLoggedIn">
      <slot name="logged-in-content" />
    </template>
    <template v-else>
      <slot name="logged-out-content" />
    </template>
  </vs-button>
</template>

<script>
import VsSvg from "@components/elements/svg/Svg"
import VsButton from "@components/elements/button"

/**
 *  Placeholder component for header login component
 */
export default {
  name: "VsHeaderLoginButton",
  status: "prototype",
  release: "0.1.0",
  components: { VsSvg, VsButton },
  data() {
    return {
      isLoggedIn: false,
    }
  },
  methods: {
    toggleLogin() {
      this.isLoggedIn = !this.isLoggedIn
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/spacing";
@import "../../styles/placeholders";

.vs-header__login-button {
  @extend %header-nav-item, %header-nav-item-top;
}
</style>

<docs>
  ```jsx
    <vs-header-login-button>
      <template v-slot:logged-in-content>
        <bs-wrapper class="d-lg-none">Log out</bs-wrapper>
        <bs-wrapper class="d-none d-lg-block text-nowrap">Hi Boudicca... (not you?)</bs-wrapper>
      </template>
      <template v-slot:logged-out-content>
        <span>Login</span>
      </template>
    </vs-header-login-button>
  ```
</docs>
