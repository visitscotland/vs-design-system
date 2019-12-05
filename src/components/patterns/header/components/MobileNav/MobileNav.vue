<template>
  <component :is="type">
    <b-collapse id="collapse-mobile-nav">
      <div class="vs-mobile-nav__wrapper">
        <vs-container>
          <vs-row no-gutters>
            <vs-col>
              <nav class="vs-mobile-nav" :aria-label="name">
                <ul class="vs-mobile-nav__list vs-mobile-nav__list--level1 list-unstyled">
                  <slot />
                </ul>
              </nav>
            </vs-col>
          </vs-row>
        </vs-container>
      </div>
    </b-collapse>
  </component>
</template>

<script>
import VsMobileNavListItem from "./MobileNavListItem"
import { BCollapse } from "bootstrap-vue"

export default {
  name: "VsMobileNav",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsMobileNavListItem,
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
@import "../../styles/placeholders";

.collapse:not(.show) {
  display: none;
  opacity: 0;
}

.collapsing {
  position: relative;
  height: 0;
  overflow: hidden;
  opacity: 1;
  transition: all 50ms ease-in-out;
}

.vs-mobile-nav__wrapper {
  background-color: $color-white;
  box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
}

.vs-mobile-nav {
  display: flex;
  margin: 0 -1rem;
}

.vs-mobile-nav__button {
  @extend %button-reset;
  @extend %main-nav-button-style;

  &:focus {
    @extend %focus-pink-inset;
  }
}

.vs-mobile-nav__list {
  height: 100vh;
  width: 100%;
  z-index: 2;

  @include media-breakpoint-up(md) {
    top: 50px;
  }
}
</style>

<docs>
  ```jsx

  <div style="position: relative; height: 600px; overflow-y: scroll; max-width: 400px;">
    <vs-mobile-nav-button name="Mobile navigation"></vs-mobile-nav-button>
    <vs-mobile-nav name="Mobile navigation"> 
      <vs-mobile-nav-list-item
        v-for="(item, index) in header.mainNav"
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
      <vs-mobile-nav-list-item
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
          <vs-mobile-nav-list-item
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
          </vs-mobile-nav-list-item>
        </vs-mobile-nav-list-item>
      </vs-mobile-nav-list-item>
    </vs-mobile-nav>
  </div>
  ```
</docs>
