<template>
  <component :is="type" class="vs-main-nav" :aria-label="name">
    <button
      class="vs-main-nav__button"
      data-toggle-trigger
      @click="triggerToggle()"
      aria-haspopup="true"
      :aria-expanded="show ? 'true' : 'false'"
    >
      <span class="switch"> <span class="sr-only">Toggle Main Navigation</span> </span>
    </button>
    <transition name="slide-fade">
      <ul
        class="vs-main-nav__list vs-main-nav__list--level1"
        :class="{ expanded: show }"
        data-toggle-pane
        v-if="show"
      >
        <VsMainNavListItem
          v-for="(link, index) in this.mainNavigationList"
          :level="1"
          :item="link"
          :key="index"
        />
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
    mainNavigationList: {
      type: Array,
      required: true,
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
      this.$el.querySelector("[data-toggle-trigger]").blur()
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
@import "~bootstrap/scss/utilities/screenreaders";
@import "../styles/placeholders";
@import "../styles/animations";

.vs-main-nav {
  display: flex;

  &__button {
    @extend %button-reset;
    @extend %main-nav-button-style;

    .switch {
      display: block;
      width: 16px;
      height: 2px;
      left: 0.5rem;
      background-color: $color-mid-granite;
      position: relative;

      &::before {
        content: "";
        display: block;
        width: 16px;
        height: 2px;
        background-color: $color-mid-granite;
        position: absolute;
        top: -6px;
        transition: transform 250ms ease-in-out;
      }

      &::after {
        content: "";
        display: block;
        width: 16px;
        height: 2px;
        background-color: $color-mid-granite;
        position: absolute;
        top: 6px;
        transition: transform 250ms ease-in-out;
      }
    }

    &[aria-expanded="true"] {
      .switch {
        background-color: $color-white;

        &::before {
          transform: rotate(45deg);
          top: 0;
          left: -2px;
          width: 20px;
        }

        &::after {
          transform: rotate(135deg);
          top: 0;
          left: -2px;
          width: 20px;
        }
      }
    }

    &:focus {
      @extend %focus-pink-inset;
    }
  }

  &__list {
    @extend %list-reset;
    left: 0;
    position: absolute;
    top: 40px;
    height: 100vh;
    width: 100%;
    z-index: 2;

    &--level1 {
      box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
      background-color: $color-white;
    }
  }
}
</style>

<docs>
  ```jsx
  const mainNav = require("../../../../assets/fixtures/mainNav.json")

  <div style="position: relative; height: 100vh;">
    <vs-main-nav
        name="Main navigation"
        :main-navigation-list="mainNav"
    />
  </div>
  ```
</docs>
