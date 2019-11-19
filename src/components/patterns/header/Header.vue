<template>
  <component :is="type" class="position-relative" ref="header">
    <div class="vs-header__top bg-primary position-relative">
      <vs-container>
        <vs-row>
          <vs-col cols="12" class="vs-header__top__main d-flex justify-content-between">
            <slot name="top-left" />
            <div class="d-inline-flex position-static justify-content-end">
              <slot name="top-right"></slot>
            </div>
          </vs-col>
        </vs-row>
      </vs-container>
      <vs-drawer class="bg-primary" drawer-key="header-top" :container="false">
        <slot name="top-drawer" />
      </vs-drawer>
    </div>

    <div class="vs-header__bottom position-relative bg-white">
      <vs-container>
        <vs-row>
          <vs-col cols="6" md="4" lg="3" class="d-flex">
            <slot name="logo" />
          </vs-col>
          <vs-col lg="7" cols="1" class="position-static">
            <vs-site-nav-list :level="1" :is-open="siteNavOpen" ref="siteNav" v-hand-down-focus>
              <slot name="site-navigation" />
            </vs-site-nav-list>
          </vs-col>

          <vs-col cols="5" md="7" lg="2" class="d-flex justify-content-end">
            <!-- <ul
              class="vs-desktop-nav__toggle-list d-none d-lg-flex justify-content-around list-unstyled m-0"
            >
              <slot name="desktop-nav-toggles" />
            </ul> -->
            <slot name="bottom-right" />
            <vs-site-nav-mobile-toggle-button
              :is-open="siteNavOpen"
              class="d-lg-none"
              @click.native="toggleMainNav"
            >
              Toggle menu
            </vs-site-nav-mobile-toggle-button>
            <!-- <div class="d-lg-none"><slot name="mobile-nav-button" /></div> -->
          </vs-col>
        </vs-row>
      </vs-container>
      <!-- <vs-site-nav :is-open="siteNavOpen" >
        <slot name="site-navigation" />
      </vs-site-nav> -->
      <vs-drawer drawer-key="header-bottom" class="py-4">
        <slot name="bottom-drawer" />
      </vs-drawer>
      <!-- <div class="d-none d-lg-block">
        <vs-desktop-nav name="Main navigation"> <slot name="desktop-submenu" /> </vs-desktop-nav>
      </div> -->

      <!-- <div>
        <vs-mobile-nav name="Main navigation" @setScrollOffset="setScrollOffset">
          <slot name="mobile-nav-items" />
        </vs-mobile-nav>
      </div> -->
    </div>
  </component>
</template>

<script>
import Vue from "vue"
import smoothscroll from "smoothscroll-polyfill"

import VsContainer from "@components/elements/layout/Container"
import VsIcon from "@components/elements/icon/Icon"
import VsRow from "@components/elements/layout/Row"
import VsCol from "@components/elements/layout/Col"
import VsDrawer from "../drawer/Drawer"
import VsDrawerContent from "../drawer/DrawerContent"
import { VsSiteNavMobileToggleButton, VsSiteNav } from "./components/site-navigation/"

import { BListGroup, BCollapse, VBToggle } from "bootstrap-vue"

import HandDownFocus from "@/directives/hand-down-focus"

export default {
  name: "VsHeader",
  status: "prototype",
  release: "0.1.0",
  components: {
    VsCol,
    VsContainer,
    VsRow,
    VsDrawer,
    VsDrawerContent,
    VsIcon,
    BListGroup,
    BCollapse,
    VsSiteNavMobileToggleButton,
    VsSiteNav,
  },
  directives: { "b-toggle": VBToggle },
  data() {
    return {
      siteNavOpen: false,
    }
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "header",
    },
  },
  directives: {
    HandDownFocus,
  },
  methods: {
    toggleMainNav() {
      this.siteNavOpen = !this.siteNavOpen
      console.log("toggle site nav")
      Vue.nextTick(() => {
        console.log("toggle site nav next tick")
        this.$refs.siteNav.$el.focus()
      })
    },
    resetMenus() {
      this.$emit("resetMenus")
    },
    setScrollOffset(offset) {
      this.$refs.header.scrollTo({
        behavior: "smooth",
        left: 0,
        top: offset > 0 ? offset + 68 : offset,
      })
    },
    collapseOtherMenus(openedId) {
      var allOpenedPanels = document.querySelectorAll(".collapse.show")
      allOpenedPanels.forEach(panel => {
        if (panel.id !== openedId) {
          this.$root.$emit("bv::toggle::collapse", panel.id)
        }
      })
      this.$emit("resetMenus")
    },
  },
  created() {
    smoothscroll.polyfill()
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "styles/placeholders";

.vs-header__top {
  z-index: $zindex-sticky;

  .vs-header__top__main {
    height: 28px;

    & ::v-deep * {
      font-size: $font-size-base;
      font-weight: 400;
    }

    @include media-breakpoint-up(md) {
      height: 35px;
    }

    @include media-breakpoint-up(lg) {
      & ::v-deep * {
        font-size: $font-size-sm;
      }
    }
  }
}

.vs-header__bottom {
  @extend %default-box-shadow;

  > .container > .row > *[class*="col"] {
    height: 40px;

    @include media-breakpoint-up(md) {
      height: 56px;
    }
  }
}
</style>

<docs>
  ```[import](./header.example.vue)
  ```
</docs>
