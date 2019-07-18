<template>
  <component :is="type" class="vs-login" @click="toggleLogin()">
    <div class="vs-login__icon-wrapper"><vs-svg path="icons/user" height="10" fill="white" /></div>
    <span v-if="this.isLoggedIn">Log out</span> <span v-else>Login</span>
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"

export default {
  name: "VsLogin",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
  data() {
    return {
      isLoggedIn: false,
    }
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "button",
    },
  },
  methods: {
    toggleLogin() {
      this.$root.$emit("resetMenus")
      this.isLoggedIn = !this.isLoggedIn
      this.$el.blur()
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../styles/placeholders";

.vs-login {
  @extend %button-reset;
  @extend %uni-nav-button-style;

  &__icon-wrapper {
    display: flex;
    margin-right: 3px;
  }
}
</style>

<docs>
  ```jsx
    <vs-login

    />
  ```
</docs>
