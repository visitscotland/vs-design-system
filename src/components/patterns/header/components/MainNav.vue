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
    background-color: $color-white;
    box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
  }
}
</style>

<docs>
  ```jsx
  const mainNav = require("../../../../assets/fixtures/mainNav.json")

  <div class="vs-header" style="position: relative; height: 100vh;">
    <vs-main-nav name="Main navigation"> 
      <vs-main-nav-list-item
        v-for="(item, index) in mainNav"
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :tracking-id="item.trackingID"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
        :key="index"
      >
      <vs-main-nav-list-item
          slot="subnav"
          v-for="(level2, index2) in item.subnav"
          :level="2"
          :href="level2.href"
          :is-external="level2.isExternal"
          :title="level2.title"
          :tracking-id="level2.trackingID"
          :subnav="level2.subnav"
          :promo-list="level2.promoList"
          :promo-item="level2.promoItem"
          :key="index2"
        >
          <vs-main-nav-list-item
            slot="subnav"
            v-for="(level3, index3) in level2.subnav"
            :level="3"
            :href="level3.href"
            :is-external="level3.isExternal"
            :title="level3.title"
            :tracking-id="level3.trackingID"
            :subnav="level3.subnav"
            :promo-list="level3.promoList"
            :promo-item="level3.promoItem"
            :key="index3"
          >
          </vs-main-nav-list-item>
        </vs-main-nav-list-item>
      </vs-main-nav-list-item>
    </vs-main-nav>
  </div>
  ```
</docs>
