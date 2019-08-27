<template>
  <component :is="type" class="vs-header" ref="header">
    <vs-container>
      <vs-row>
        <vs-col class="d-inline-flex justify-content-end justify-content-lg-center">
          <slot name="skip-to-content"></slot> <slot name="skip-to-search"></slot>
        </vs-col>
      </vs-row>
    </vs-container>

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
                <slot name="search-button" /> <slot name="favourites-button" />
                <div class="d-lg-none"><slot name="mobile-nav-button" /></div>
              </div>
            </div>
          </vs-col>
        </vs-row>
      </vs-container>
    </div>
    <slot name="search" /> <slot name="favourites-list" />
    <div class="d-none d-lg-block">
      <vs-desktop-nav name="Main navigation"> <slot name="desktop-submenu" /> </vs-desktop-nav>
    </div>
    <div class="d-lg-none">
      <vs-mobile-nav name="Main navigation" @setScrollOffset="setScrollOffset">
        <slot name="mobile-nav-items" />
      </vs-mobile-nav>
    </div>
  </component>
</template>

<script>
import VsContainer from "../../elements/layout/Container"
import VsSvg from "../../elements/svg/Svg"
import VsRow from "../../elements/layout/Row"
import VsCol from "../../elements/layout/Col"
import smoothscroll from "smoothscroll-polyfill"

export default {
  name: "VsHeader",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsCol,
    VsContainer,
    VsRow,
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
    setScrollOffset(offset) {
      this.$refs.header.scrollTo({
        behavior: "smooth",
        left: 0,
        top: offset > 0 ? offset + 68 : offset,
      })
    },
    collapseOtherMenus(openedId) {
      var allPaneIds = [
        "collapseSubnav1",
        "collapseSubnav2",
        "collapseSubnav3",
        "collapseSubnav4",
        "collapseSubnav5",
        "collapse-search",
        "collapse-favourites",
      ]
      allPaneIds.map(otherId => {
        if (otherId !== openedId) {
          var collapseElement = document.getElementById(otherId)
          console.log(collapseElement)
          //console.log(collapseElement.attr('aria-expanded'));
        }
      })

      //this.$root.$emit('bv::hide::collapse', menuId)
    },
  },
  created() {
    smoothscroll.polyfill()
  },
  mounted() {
    this.$root.$on("bv::collapse::state", (openedId, isOpen) => {
      if (isOpen) {
        this.collapseOtherMenus(openedId)
      }
    })
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
  ```jsx

  const ourSites = require("../../../assets/fixtures/ourSites.json")
  const language = require("../../../assets/fixtures/languages.json")
  const mainNav = require("../../../assets/fixtures/mainNav.json")
  const favourite = require("../../../assets/fixtures/favourite.json")

  <div style="overflow-y: scroll; min-height: 600px;">
    <vs-header>
      <vs-skip-to-content
        slot="skip-to-content"
        title="Skip to Content"
       />
      <vs-skip-to-search
        slot="skip-to-search"
        title="Skip to Search"
       />
      <vs-desktop-universal-nav
        slot="desktop-universal-nav"
        name="Our sites"
        :dropdown-list="ourSites"
      />
      <vs-mobile-universal-nav
        slot="mobile-universal-nav"
        name="Our sites"
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

      <vs-search-button
        slot="search-button" />
      <vs-mobile-nav-button
        slot="mobile-nav-button" />
      <vs-favourites-button
        slot="favourites-button"
        :href="favourite.href"
        :title="favourite.title" />

      <vs-search
        slot="search" />
      <vs-favourites-list
        slot="favourites-list" />
      
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
        :key="index"
        :toggleId="index+1"
      ></vs-desktop-nav-toggles>

      <vs-desktop-nav-submenu
        slot="desktop-submenu"
        v-for="(item, index) in mainNav"
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :tracking-id="item.trackingId"
        :title="item.title"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
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
          :promo-list="level2.promoList"
          :promo-item="level2.promoItem"
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
            :promo-list="level3.promoList"
            :promo-item="level3.promoItem"
            :key="index3"
          >
          </vs-desktop-nav-list-item>
        </vs-desktop-nav-list-item>
      </vs-desktop-nav-submenu>

      <vs-mobile-nav-list-item
        slot="mobile-nav-items"
        v-for="(item, index) in mainNav"
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
  </div>
  ```
</docs>
