<template>
  <component :is="type" class="position-relative" ref="header">
    <div class="vs-header__top-wrapper bg-primary position-relative">
      <vs-container>
        <vs-row>
          <vs-col cols="12" class="d-flex justify-content-between">
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

    <div class="vs-header__bottom-wrapper position-relative bg-white">
      <vs-container>
        <vs-row>
          <vs-col>
            <div class="d-flex justify-content-between">
              <slot name="logo" />
              <ul
                class="vs-desktop-nav__toggle-list d-none d-lg-flex justify-content-around list-unstyled m-0"
              >
                <slot name="desktop-nav-toggles" />
              </ul>
              <div class="vs-controls__wrapper d-flex">
                <slot name="header-drawer-toggles" />
                <div class="d-lg-none"><slot name="mobile-nav-button" /></div>
              </div>
            </div>
          </vs-col>
        </vs-row>
      </vs-container>
      <vs-drawer drawer-key="header-bottom" class="py-4">
        <slot name="bottom-drawer" />
      </vs-drawer>
      <div class="d-none d-lg-block">
        <vs-desktop-nav name="Main navigation"> <slot name="desktop-submenu" /> </vs-desktop-nav>
      </div>
      <div class="d-lg-none">
        <vs-mobile-nav name="Main navigation" @setScrollOffset="setScrollOffset">
          <slot name="mobile-nav-items" />
        </vs-mobile-nav>
      </div>
    </div>
  </component>
</template>

<script>
import smoothscroll from "smoothscroll-polyfill"

import VsContainer from "@components/elements/layout/Container"
import VsIcon from "@components/elements/icon/Icon"
import VsRow from "@components/elements/layout/Row"
import VsCol from "@components/elements/layout/Col"
import VsDrawer from "../drawer/Drawer"
import VsDrawerContent from "../drawer/DrawerContent"

import { BListGroup, BCollapse, VBToggle } from "bootstrap-vue"

import handDownFocus from "@/directives/hand-down-focus"

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
  },
  directives: { "b-toggle": VBToggle },
  data() {
    return {
      languageDropdownOpen: false,
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
    handDownFocus,
  },
  methods: {
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

#header-language-list-dropdown ::v-deep {
  position: absolute;
  top: 42px;
  .vs-header--top--nav-item {
    background-color: $color-theme-primary;
    &:first-of-type {
      margin-left: 0;
    }
  }
}

.vs-desktop-nav__toggle-list {
  width: 100%;
  padding: 0 2rem;

  @include media-breakpoint-up(xl) {
    padding: 0 5rem;
  }
}

.vs-header__top-wrapper {
  z-index: $zindex-fixed;
  & ::v-deep * {
    font-size: $font-size-base;
    font-weight: 400;
  }

  @include media-breakpoint-up(lg) {
    & ::v-deep * {
      font-size: $font-size-sm;
    }
  }
}

.vs-header__bottom-wrapper {
  @extend %default-box-shadow;
}
</style>

<docs>
  ```[import](./header.example.vue)
  ```
</docs>
