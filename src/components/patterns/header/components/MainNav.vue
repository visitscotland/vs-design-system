<template>
  <component :is="type" class="vs-main-nav" :aria-label="name">
    <button
      class="vs-main-nav__button"
      ref="trigger"
      data-test-trigger
      @click="triggerToggle()"
      aria-haspopup="true"
      :aria-expanded="show ? 'true' : 'false'"
    >
      <span class="vs-main-nav__switch"> <span class="sr-only">Toggle Main Navigation</span> </span>
    </button>
    <transition name="slide-fade">
      <ul
        class="vs-main-nav__list vs-main-nav__list--level1 list-unstyled"
        :class="{ expanded: show }"
        v-if="show"
      >
        <slot />
      </ul>
    </transition>
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"
import VsMainNavListItem from "./MainNavListItem"

export default {
  name: "VsMainNav",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsSvg,
    VsMainNavListItem,
  },
  data() {
    return {
      show: false,
    }
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "nav",
    },
    name: {
      type: String,
    },
  },
  methods: {
    triggerToggle() {
      let currentState = this.show
      this.$root.$emit("resetMenus")
      if (currentState === false) {
        this.show = true
      }
      this.$refs.trigger.blur()
    },
    reset() {
      this.show = false
    },
  },
  mounted() {
    this.$root.$on("resetMenus", this.reset)
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

.vs-main-nav {
  display: flex;
}

.vs-main-nav__button {
  @extend %button-reset;
  @extend %main-nav-button-style;

  &:focus {
    @extend %focus-pink-inset;
  }
}

.vs-main-nav__switch {
  background-color: $color-mid-granite;
  display: block;
  height: 2px;
  left: 11px;
  position: relative;
  width: 16px;

  .vs-main-nav__button[aria-expanded="true"] & {
    background-color: $color-white;
  }

  &::before {
    background-color: $color-mid-granite;
    content: "";
    display: block;
    height: 2px;
    position: absolute;
    top: -6px;
    transition: transform 250ms ease-in-out;
    width: 16px;

    .vs-main-nav__button[aria-expanded="true"] & {
      left: -2px;
      top: 0;
      transform: rotate(45deg);
      width: 20px;
    }
  }

  &::after {
    background-color: $color-mid-granite;
    content: "";
    display: block;
    height: 2px;
    position: absolute;
    top: 6px;
    transition: transform 250ms ease-in-out;
    width: 16px;

    .vs-main-nav__button[aria-expanded="true"] & {
      left: -2px;
      top: 0;
      transform: rotate(135deg);
      width: 20px;
    }
  }
}

.vs-main-nav__list {
  height: 100vh;
  left: 0;
  position: absolute;
  top: 40px;
  width: 100%;
  z-index: 2;

  &--level1 {
    box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
    background-color: $color-white;
  }
}
</style>

<docs>
  ```jsx

  <div style="position: relative; height: 100vh;">
    <vs-main-nav
        name="Main navigation"
    />
  </div>
  ```
</docs>
