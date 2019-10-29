<template>
  <vs-button class="mr-lg-1" @click.native="isLoggedIn = !isLoggedIn" ref="trigger">
    <vs-svg class="mr-1" path="icons/user" height="10" fill="white" />

    <template v-if="isLoggedIn">
      <slot name="logged-in" />
    </template>
    <template v-else>
      <slot name="logged-out" />
    </template>
  </vs-button>
</template>

<script>
import VsSvg from "@components/elements/svg/Svg"
import VsButton from "@components/elements/button"

export default {
  name: "VsLoginButton",
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
</style>

<docs>
  ```jsx
    <bs-wrapper>
      <vs-login-button>
        <template v-slot:logged-in>
          <bs-wrapper class="d-lg-none">Log out</bs-wrapper>
          <bs-wrapper class="d-none d-lg-block text-nowrap">Hi Boudicca... (not you?)</bs-wrapper>
        </template>
        <template v-slot:logged-out>
          <span>Login</span>
        </template>
      </vs-login-button>
    </bs-wrapper>
  ```
</docs>
