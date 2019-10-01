<template>
  <component :is="type" class="vs-header" ref="header">
    <div class="vs-header__wrapper--top position-relative">
      <vs-container>
        <vs-row>
          <vs-col class="d-flex justify-content-between">
            <div class="d-inline-flex d-lg-none"><slot name="mobile-universal-nav"></slot></div>
            <div class="d-none d-lg-flex"><slot name="desktop-universal-nav"></slot></div>
            <div class="d-inline-flex vs-header__site-controls-col justify-content-end">
              <slot name="login"></slot> <slot name="language"></slot>
            </div>
          </vs-col>
        </vs-row>
      </vs-container>
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
      <vs-header-drawer>
        <slot name="header-drawer-modules" />
      </vs-header-drawer>
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
import VsContainer from "@components/elements/layout/Container"
import VsSvg from "@components/elements/svg/Svg"
import VsRow from "@components/elements/layout/Row"
import VsCol from "@components/elements/layout/Col"
import smoothscroll from "smoothscroll-polyfill"
import store, { names as storeNames } from "./header.store"
import VsHeaderDrawer from "./components/Drawer/HeaderDrawer"
import VsHeaderDrawerToggle from "./components/Drawer/HeaderDrawerToggle"

export default {
  name: "VsHeader",
  status: "prototype",
  release: "0.0.1",
  store,
  components: {
    VsCol,
    VsContainer,
    VsRow,
    VsSvg,
    VsHeaderDrawer,
    VsHeaderDrawerToggle,
  },
  data() {
    return {
      drawer: {
        isOpen: !!store.getters["header/drawer/module"],
      },
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
    favouriteHref: {
      type: String,
    },
    favouriteTitle: {
      type: String,
    },
  },
  computed: {
    drawerModule() {
      return store.getters["header/drawer/module"]
    },
  },
  watch: {
    drawerModule(newValue) {
      this.drawer.isOpen = !!newValue
    },
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

.vs-header__site-controls-col {
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

      <vs-header-drawer-toggle
        module-name="site-search"
        type="vs-skip-to-button"
      >
        Skip to Search
      </vs-header-drawer-toggle>

      <vs-header
        :favourite-href="favourite.href"
        :favourite-title="favourite.title"
      >

        <template #desktop-universal-nav>
          <vs-desktop-universal-nav
            name="Our sites"
            :dropdown-list="header.ourSites"
          />
        </template>

        <template #mobile-universal-nav>
          <vs-mobile-universal-nav
            name="Our sites"
            class="vs-dropdown--mobile-universal-nav"
            :dropdown-list="header.ourSites"
          />
        </template>

        <template #login>
          <vs-login
            username=""
          />
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

          <vs-header-drawer-toggle
            module-name="site-search"
            type="vs-site-search-toggle-button"
          />

          <vs-header-drawer-toggle
            module-name="favourites-list"
            :show-close="true"
            :href="favourite.href"
            :title="favourite.title"
            type="vs-favourites-button"
          />

        </template>

        <template #header-drawer-modules>
          <header-drawer-module module-name="site-search" ref="siteSearch" :show-close="true">
            <vs-site-search />
          </header-drawer-module>
          <header-drawer-module module-name="favourites-list" :show-close="true">
            <vs-favourites-list/>
          </header-drawer-module>
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
    methods: {
      skipToSearch() {
      }
    }

  }
  </script>


  ```
</docs>
