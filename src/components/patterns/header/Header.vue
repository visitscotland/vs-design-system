<template>
  <component :is="type" class="vs-header">
    <div class="vs-universal-nav__wrapper">
      <vs-container>
        <div class="vs-universal-nav__wrapper--inner">
          <slot name="ourSites"></slot>
          <div class="d-flex"><slot name="login"></slot> <slot name="language"></slot></div>
        </div>
      </vs-container>
    </div>

    <div class="vs-main-nav__wrapper">
      <vs-container>
        <div class="d-flex align-items-stretch justify-content-between">
          <slot name="logo"></slot>
          <div class="vs-controls__wrapper">
            <slot name="search"></slot> <slot name="favourites"></slot> <slot name="mainNav"></slot>
          </div>
        </div>
      </vs-container>
    </div>
  </component>
</template>

<script>
import VsContainer from "../../elements/layout/Container"
import VsHeading from "../../elements/heading/Heading"
import VsRow from "../../elements/layout/Row"
import VsCol from "../../elements/layout/Col"
import VsSvg from "../../elements/svg/Svg"
import VsDropdown from "./components/Dropdown"
import VsFavourites from "./components/Favourites"
import VsLogin from "./components/Login"
import VsLogo from "./components/Logo"
import VsMainNav from "./components/MainNav"
import VsMainNavListItem from "./components/MainNavListItem"
import VsMainNavPromoItem from "./components/MainNavPromoItem"
import VsMainNavPromoList from "./components/MainNavPromoList"
import VsSearch from "./components/Search"

export default {
  name: "VsHeader",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsCol,
    VsContainer,
    VsDropdown,
    VsFavourites,
    VsLogin,
    VsLogo,
    VsMainNav,
    VsMainNavListItem,
    VsMainNavPromoItem,
    VsRow,
    VsSearch,
    VsSvg,
  },
  data() {
    return {}
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
  methods: {
    resetMenus() {
      this.$emit("resetMenus")
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "styles/placeholders";

.vs-header {
  min-height: 600px; // temporary -
  overflow-x: hidden;
  overflow-y: scroll;
  position: relative;
}

.vs-universal-nav {
  &__wrapper {
    background-color: $color-heather-purple;
    position: relative;
    width: 100%;
    z-index: 4;

    &--inner {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin: 0 -5px;
    }
  }
}

.vs-main-nav {
  &__wrapper {
    background-color: $white;
    box-shadow: 0 8px 6px -6px rgba(0, 0, 0, 0.3);
    position: relative;
    z-index: 3;
  }
}

.vs-controls {
  &__wrapper {
    display: flex;
    align-items: stretch;
    margin: 0 -1rem 0 1rem;
  }
}
</style>

<docs>
  ```jsx

  const ourSites = require("../../../assets/fixtures/ourSites.json")
  const language = require("../../../assets/fixtures/languages.json")
  const mainNav = require("../../../assets/fixtures/mainNav.json")

  <div>
    <vs-header>
      <vs-dropdown
        slot="ourSites"
        name="Our sites"
        :dropdown-list="ourSites"
      />
      <vs-login
        slot="login" />
      <vs-dropdown
        slot="language"
        name="Language"
        :dropdown-list="language"
      />
      <vs-logo 
        slot="logo" />
      <vs-search
        slot="search" />
      <vs-favourites
        slot="favourites" />
      <vs-main-nav 
        slot="mainNav"
        name="Main navigation" 
        :main-navigation-list="mainNav" />
    </vs-header>
  </div>
  ```
</docs>
