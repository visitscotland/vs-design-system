<template>
  <component :is="type" class="vs-login">
    <button class="vs-login__button" @click="toggleLogin()" ref="trigger">
      <div class="vs-login__icon-wrapper">
        <vs-svg path="icons/user" height="10" fill="white" />
      </div>
      <span v-if="this.isLoggedIn">
        <span class="d-lg-none">Log out</span>
        <span class="d-none d-lg-block vs-login__greeting">Hi {{ username }}... (not you?)</span>
      </span>
      <span v-else>Login</span>
    </button>
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
      default: "div",
    },
    username: {
      type: String,
    },
  },
  methods: {
    toggleLogin() {
      this.$root.$emit("resetMenus")
      this.isLoggedIn = !this.isLoggedIn
      this.$refs.trigger.blur()
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

.vs-login__button {
  @extend %button-reset;
  @extend %uni-nav-button-style;

  @include media-breakpoint-up(sm) {
    margin-right: 1rem;
  }
}
.vs-login__icon-wrapper {
  display: flex;
  margin-right: 3px;
}

.vs-login__greeting {
  white-space: nowrap;
}
</style>

<docs>
  ```jsx
    <vs-login

    />
  ```
</docs>
