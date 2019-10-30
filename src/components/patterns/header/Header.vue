<template>
  <component :is="type" class="vs-header" ref="header">
    <div class="vs-header__wrapper--top position-relative">
      <vs-container>
        <vs-row>
          <vs-col cols="12" class="d-flex justify-content-between">
            <b-list-group class="d-lg-inline-flex d-none">
              <slot name="universal-nav" :dummy="{}"></slot>
            </b-list-group>

            <vs-drawer-toggle
              class="vs-universal-nav--toggle d-lg-none"
              content-key="universal-nav"
              drawer-key="header-upper"
            >
              <slot name="universal-nav-toggle">Our sites</slot>
            </vs-drawer-toggle>

            <div class="d-inline-flex vs-header__top__right justify-content-end">
              <slot name="login"></slot>

              <vs-drawer-toggle
                class="vs-universal-nav--toggle d-lg-none"
                content-key="language-list"
                drawer-key="header-upper"
              >
                <slot name="language-list-toggle-label">LANG</slot>
                <vs-icon
                  v-if="external"
                  class="d-lg-none ml-1"
                  name="chevron-down"
                  size="xxs"
                  variant="reverse-white"
                />
              </vs-drawer-toggle>

              <vs-button
                class="vs-language-list--toggle"
                @click.native="languageDropdownOpen = !languageDropdownOpen"
              >
                <slot name="language-list-toggle-label" :dummy="{}">LANG</slot>
              </vs-button>

              <b-collapse v-model="languageDropdownOpen" id="header-language-list-dropdown">
                <b-list-group :horizontal="false">
                  <slot name="language-list"></slot>
                </b-list-group>
              </b-collapse>
            </div>
          </vs-col>
          <vs-col cols="12"> </vs-col>
        </vs-row>
      </vs-container>
      <vs-drawer class="bg-primary" drawer-key="header-upper" :container="false">
        <vs-drawer-content content-key="universal-nav" focus-on-open="content">
          <b-list-group class="d-lg-none" v-hand-down-focus tabindex="-1">
            <slot name="universal-nav" :dummy="{}"></slot>
          </b-list-group>
        </vs-drawer-content>
        <vs-drawer-content content-key="language-list" focus-on-open="content">
          <b-list-group class="d-lg-none" v-hand-down-focus tabindex="-1">
            <slot name="language-list" :dummy="{}"></slot>
          </b-list-group>
        </vs-drawer-content>
      </vs-drawer>
    </div>

    <div class="vs-header__wrapper--bottom position-relative">
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
      <vs-drawer drawer-key="header-lower" class="py-4">
        <slot name="header-drawer-modules" />
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
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/spacing";
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities/position";
@import "~bootstrap/scss/utilities/screenreaders";
@import "styles/placeholders";

.vs-universal-nav--toggle {
  margin-left: -#{$input-btn-padding-x};
}

.vs-language-list--toggle {
  @include media-breakpoint-down(md) {
    display: none !important;
  }
}

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

.vs-header {
  position: relative;
}

.vs-header__wrapper--top {
  background-color: $color-theme-primary;
  z-index: 4;

  @include media-breakpoint-up(lg) {
    & ::v-deep * {
      font-size: $font-size-sm;
    }
  }
}

.vs-header__wrapper--bottom {
  background-color: $white;
  @extend %default-box-shadow;
}

.vs-header__universal-nav-col {
  position: static;

  @include media-breakpoint-up(md) {
    position: relative;
  }
}

.vs-header__top__right {
  position: static;

  @include media-breakpoint-up(md) {
    position: relative;
  }
}
</style>

<docs>
  ```vue

  <template>
  
    <div style="overflow-y: scroll; min-height: 600px;">
      <vs-skip-to
        :target="contentContainer"
      >
        Skip to Content
      </vs-skip-to>

      <vs-drawer-toggle
        content-key="site-search"
        drawer-key="header-lower"
        type="vs-skip-to-button"
      >
        Skip to Search
      </vs-drawer-toggle>

      <vs-header>

        <template #universal-nav>
          <vs-header-top-nav-item
            v-for="(site, i) in header.ourSites"
            :key="i"
            :href="site.href"
            :external="site.isExternal"
            :active="site.isActive"
            :tracking-id="site.trackingId"
          >
            {{ site.title }}
          </vs-header-top-nav-item>
        </template>

        <template #login>
          <vs-login-button>
            <template v-slot:logged-in>
              <span class="d-lg-none">Log out</span>
              <span>Hi Boudicca... (not you?)</span>
            </template>
            <template v-slot:logged-out>
              <span>Login</span>
            </template>
          </vs-login-button>
        </template>

        <template #language-list-toggle-label>
          EN
        </template>

        <template #language-list>
          <vs-header-top-nav-item
            v-for="(lang, i) in header.languages"
            :key="i"
            :href="lang.href"
            :active="lang.isActive"
            :tracking-id="lang.trackingId"
          >
            <span>{{ lang.title }}</span>
          </vs-header-top-nav-item>
        </template>        

        <template #language>
          <vs-language
            name="Language"
            class="vs-dropdown--language"
            :dropdown-list="header.languages"
          />
        </template>

        <template #logo>
          <vs-logo />
        </template>

        <template #mobile-nav-button>
          <vs-mobile-nav-button />
        </template>

        <template #header-drawer-toggles>

          <vs-drawer-toggle
            drawer-key="header-lower"
            content-key="site-search"
            type="vs-site-search-toggle-button"
          >
            Search
          </vs-drawer-toggle>

          <vs-drawer-toggle
            drawer-key="header-lower"
            content-key="favourites-list"
            :href="favourite.href"
            :title="favourite.title"
            type="vs-favourites-button"
          />

        </template>

        <template #header-drawer-modules>
          <vs-drawer-content 
            content-key="site-search" 
            ref="siteSearch" 
            focus-on-open="content"
          >
            <vs-site-search />
          </vs-drawer-content>
          <vs-drawer-content 
            content-key="favourites-list" 
            focus-on-open="close"
          >
            <vs-favourites-list/>
          </vs-drawer-content>
        </template>

        <template #desktop-nav-toggles>
          <vs-desktop-nav-toggles
            v-for="(item, index) in header.mainNav"
            :level="1"
            :href="item.href"
            :is-external="item.isExternal"
            :title="item.title"
            :subnav="item.subnav"
            :promo-list="item.promoList"
            :promo-item="item.promoItem"
            :chart-widgets="item.chartWidgets"
            :key="index"
            :toggleId="index+1"
          />
        </template>

        <vs-desktop-nav-submenu
          slot="desktop-submenu"
          v-for="(item, index) in header.mainNav"
          :level="1"
          :href="item.href"
          :is-external="item.isExternal"
          :tracking-id="item.trackingId"
          :title="item.title"
          :subnav="item.subnav"
          :promo-list="item.promoList"
          :promo-item="item.promoItem"
          :chart-widgets="item.chartWidgets"
          :key="index"
          :subnavId="index+1"
        >
          <vs-desktop-nav-list-item
            slot="subnav"
            v-for="(level2, index2) in item.subnav"
            :level="2"
            :href="level2.href"
            :is-external="level2.isExternal"
            :tracking-id="level2.trackingId"
            :title="level2.title"
            :subnav="level2.subnav"
            :key="index2"
          >
            <vs-desktop-nav-list-item
              slot="subnav"
              v-for="(level3, index3) in level2.subnav"
              :level="3"
              :href="level3.href"
              :is-external="level3.isExternal"
              :title="level3.title"
              :tracking-id="level3.trackingId"
              :subnav="level3.subnav"
              :key="index3"
            >
            </vs-desktop-nav-list-item>
          </vs-desktop-nav-list-item>
        </vs-desktop-nav-submenu>

        <vs-mobile-nav-list-item
          slot="mobile-nav-items"
          v-for="(item, index) in header.mainNav"
          :level="1"
          :href="item.href"
          :is-external="item.isExternal"
          :tracking-id="item.trackingId"
          :title="item.title"
          :subnav="item.subnav"
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
            :tracking-id="level2.trackingId"
            :title="level2.title"
            :subnav="level2.subnav"
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
              :tracking-id="level3.trackingId"
              :subnav="level3.subnav"
              :promo-list="level3.promoList"
              :promo-item="level3.promoItem"
              :key="index3"
            >
            </vs-mobile-nav-list-item>
          </vs-mobile-nav-list-item>
        </vs-mobile-nav-list-item>

      </vs-header>

      <bs-wrapper class="container">
        <bs-wrapper class="row">
          <bs-wrapper id="content-container" class="col" tabindex="3000" style="height:1000">
            Dummy page content
          </bs-wrapper>
        </bs-wrapper>
      </bs-wrapper>
    </div>
  </template>

  <script>

  export default {
    data() {
      return {
        contentContainer: null,
        siteSearchModule: null
      }
    },
    mounted() {
      this.contentContainer = document.getElementById("content-container")
      this.siteSearchModule = this.$refs.siteSearch
    },
  }
  </script>


  ```
</docs>
