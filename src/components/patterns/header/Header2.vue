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
                <vs-button
                  class="vs-header__site-search__toggle-button"
                  ref="siteSearchButton"
                  @click.native="toggleSearch"
                  :aria-expanded="siteSearchIsVisible"
                >
                  <span class="d-xl-none sr-only">Toggle Search</span>
                  <vs-icon name="search" size="sm" variant="reverse-white" />
                  <span class="d-none d-xl-flex vs-site-search__search-button-text">
                    <span class="sr-only">Toggle</span> Search
                  </span>
                </vs-button>
                <slot name="favourites-button" />
                <div class="d-lg-none"><slot name="mobile-nav-button" /></div>
              </div>
            </div>
          </vs-col>
        </vs-row>
      </vs-container>
      <b-collapse
        v-model="drawer.isOpen"
        class="vs-header__drawer-wrapper"
        id="vs-header__drawer-wrapper"
      >
        <vs-container>
          <vs-row>
            <vs-col>
              <slot name="site-search" v-if="siteSearchIsVisible" />
              <slot name="favourites-list" v-if="favouritesListIsVisible" />
            </vs-col>
          </vs-row>
        </vs-container>
      </b-collapse>
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
import VsContainer from "../../elements/layout/Container"
import VsSvg from "../../elements/svg/Svg"
import VsRow from "../../elements/layout/Row"
import VsCol from "../../elements/layout/Col"
import smoothscroll from "smoothscroll-polyfill"
import { BCollapse } from "bootstrap-vue"
import store, { names as storeNames } from "./header.store"
import { mapState } from "vuex"

export default {
  name: "VsHeader2",
  status: "prototype",
  release: "0.0.1",
  store,
  components: {
    VsCol,
    VsContainer,
    VsRow,
    VsSvg,
    BCollapse,
  },
  data() {
    return {
      drawer: {
        moduleNames: {
          siteSearch: storeNames.drawer.moduleNames.SITE_SEARCH,
          favouritesList: storeNames.drawer.moduleNames.FAVOURITES_LIST,
        },
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
  },
  computed: {
    drawerModule() {
      return store.getters["header/drawer/module"]
    },
    siteSearchIsVisible() {
      return store.getters["header/drawer/module"] === this.siteSearchModuleName
    },
    siteSearchModuleName() {
      return this.drawer.moduleNames.siteSearch
    },
    favouritesListIsVisible() {
      return store.getters["header/drawer/module"] === this.favouritesListModuleName
    },
    favouritesListModuleName() {
      return this.drawer.moduleNames.favouritesList
    },
  },
  watch: {
    drawerModule(newValue) {
      this.drawer.isOpen = !!newValue

      // if (newValue === this.siteSearchModuleName) {
      //   setTimeout(() => {
      //     this.$refs.searchInput.focus()
      //   })
      // }
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
    toggleSearch() {
      if (this.siteSearchIsVisible) {
        this.closeDrawer()
      } else {
        this.showDrawerModule(this.siteSearchModuleName)
      }
    },
    showDrawerModule(moduleName) {
      store.dispatch("header/drawer/showModule", moduleName)
    },
    closeDrawer() {
      store.dispatch("header/drawer/close")
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

.vs-header__drawer-wrapper {
  @extend %default-inset-box-shadow;
  background-color: $gray-tint-7;
  padding: 1rem;
  width: 100%;
}

.vs-header__site-search__toggle-button {
  background-color: $color-pink;
  color: $color-white;
  z-index: 1;

  &:hover,
  &:focus {
    outline: none;
    box-shadow: inset 0 -3px 0 0 $color-white;

    @include media-breakpoint-up(xl) {
      box-shadow: 0 3px 0 0 $purple-shade-2;
    }
  }

  @include media-breakpoint-up(sm) {
    box-shadow: 0 5px 0 0 $color-pink;
  }

  @include media-breakpoint-up(xl) {
    align-items: center;
    box-shadow: 0 5px 0 0 $color-pink;
    display: flex;
    font-size: 1.125rem;
    font-weight: $font-weight-semi-bold;
    justify-content: center;
    padding: 0 1.25rem 0 0.5rem;
    width: auto;
  }
}
</style>

<docs>
  ```vue

  <template>
    <div style="overflow-y: scroll; min-height: 600px;">
      <vs-skip-to
        tabindex="1000"
        :target="contentContainer"
      >
        Skip to Content
      </vs-skip-to>

      <vs-skip-to
        tabindex="1001"
        @activated="skipToSearch"
      >
        Skip to Search
      </vs-skip-to>

      <vs-header2>
        <vs-desktop-universal-nav
          slot="desktop-universal-nav"
          name="Our sites"
          :dropdown-list="ourSites"
        />
        <vs-mobile-universal-nav
          slot="mobile-universal-nav"
          name="Our sites"
          class="vs-dropdown--mobile-universal-nav"
          :dropdown-list="ourSites"
        />
        <vs-login
          slot="login" 
          username=""
          />
        <vs-language
          slot="language"
          name="Language"
          class="vs-dropdown--language"
          :dropdown-list="language"
        />
        <vs-logo 
          slot="logo" />

        <vs-mobile-nav-button
          slot="mobile-nav-button" />

        <vs-favourites-button
          slot="favourites-button"
          :href="favourite.href"
          :title="favourite.title" />

        <vs-site-search slot="site-search" />
        <vs-favourites-list slot="favourites-list" />
        
        <vs-desktop-nav-toggles
          slot="desktop-nav-toggles"
          v-for="(item, index) in mainNav"
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
        ></vs-desktop-nav-toggles>

      </vs-header2>

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

  const ourSites = require("../../../assets/fixtures/ourSites.json")
  const language = require("../../../assets/fixtures/languages.json")
  const mainNav = require("../../../assets/fixtures/mainNav.json")
  const favourite = require("../../../assets/fixtures/favourite.json")

  import siteSearchStore from "./components/Search/site-search.store"

  export default {
    data() {
      return {
        ourSites,
        language,
        mainNav,
        favourite,
        contentContainer: null
      }
    },
    mounted() {
      this.contentContainer = document.getElementById("content-container")
    },
    methods: {
      skipToSearch() {
        siteSearchStore.dispatch("siteSearch/open")
      }
    }

  }
  </script>


  ```
</docs>
