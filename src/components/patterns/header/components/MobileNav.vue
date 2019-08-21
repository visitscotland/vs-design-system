<template>
  <component :is="type">
    <b-collapse id="collapse-mobile-nav">
      <nav class="vs-main-nav" :aria-label="name">
        <ul class="vs-main-nav__list vs-main-nav__list--level1 list-unstyled">
          <slot />
        </ul>
      </nav>
    </b-collapse>
  </component>
</template>

<script>
import VsMainNavListItem from "./MainNavListItem"
import { BCollapse } from "bootstrap-vue"

export default {
  name: "VsMobileNav",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsMainNavListItem,
    BCollapse,
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
      default: "div",
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

.vs-main-nav__list {
  height: 100vh;
  width: 100%;
  z-index: 2;

  @include media-breakpoint-up(md) {
    top: 50px;
  }

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
    <vs-mobile-nav name="Mobile navigation"> 
      <vs-main-nav-list-item
        v-for="(item, index) in mainNav"
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :subnav="item.subnav"
        :tracking-id="item.trackingId"
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
          :subnav="level2.subnav"
          :tracking-id="item.trackingId"
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
            :subnav="level3.subnav"
            :tracking-id="item.trackingId"
            :promo-list="level3.promoList"
            :promo-item="level3.promoItem"
            :key="index3"
          >
          </vs-main-nav-list-item>
        </vs-main-nav-list-item>
      </vs-main-nav-list-item>
    </vs-mobile-nav>
  </div>
  ```
</docs>
