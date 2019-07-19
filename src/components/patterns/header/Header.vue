<template>
  <component :is="type" class="vs-header">
    <div class="vs-universal-nav__wrapper">
      <vs-container>
        <div class="vs-universal-nav__wrapper--inner">
          <vs-dropdown
            name="Our sites"
            :dropdown-list="[
              {
                title: 'Business Events',
                href: 'https://businessevents.visitscotland.com',
                isExternal: true,
                isActive: false,
                trackingID: 1,
              },
              {
                title: 'Travel Trade',
                href: 'https://traveltrade.visitscotland.org',
                isExternal: true,
                isActive: false,
                trackingID: 1,
              },
              {
                title: 'Media Centre',
                href: 'http://mediacentre.visitscotland.org',
                isExternal: true,
                isActive: false,
                trackingID: 1,
              },
              {
                title: 'Corporate',
                href: 'https://www.visitscotland.org',
                isExternal: true,
                isActive: false,
                trackingID: 1,
              },
            ]"
          />
          <div class="d-flex">
            <vs-login />
            <vs-dropdown
              name="Language"
              :dropdown-list="[
                {
                  title: 'English',
                  href: 'https://www.visitscotland.com/',
                  abbreviation: 'EN',
                  trackingID: 1,
                  isActive: true,
                },
                {
                  title: 'Deutsch',
                  href: 'https://www.visitscotland.com/de-de',
                  abbreviation: 'DE',
                  trackingID: 1,
                  isActive: false,
                },
                {
                  title: 'Español',
                  href: 'https://www.visitscotland.com/es-es',
                  abbreviation: 'ES',
                  trackingID: 1,
                  isActive: false,
                },
                {
                  title: 'Français',
                  href: 'https://www.visitscotland.com/fr-fr',
                  abbreviation: 'FR',
                  trackingID: 1,
                  isActive: false,
                },
                {
                  title: 'Italiano',
                  href: 'https://www.visitscotland.com/it-it',
                  abbreviation: 'IT',
                  trackingID: 1,
                  isActive: false,
                },
                {
                  title: 'Nederlands',
                  href: 'https://www.visitscotland.com/nl-nl',
                  abbreviation: 'NL',
                  trackingID: 1,
                  isActive: false,
                },
              ]"
            />
          </div>
        </div>
      </vs-container>
    </div>

    <div class="vs-main-nav__wrapper">
      <vs-container>
        <div class="d-flex align-items-stretch justify-content-between">
          <vs-logo />
          <div class="vs-controls__wrapper">
            <vs-search />
            <vs-favourites />
            <vs-main-nav name="Main navigation" :main-navigation-list="sampleData" />
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

import json from "../../../assets/fixtures/mainNav.json"

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
    return {
      sampleData: json,
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
  <div>
    <vs-header
    />
  </div>
  ```
</docs>
