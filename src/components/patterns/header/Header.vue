<template>
  <component :is="type" class="vs-header">
    <div class="vs-header__wrapper--top position-relative">
      <vs-container>
        <vs-row>
          <vs-col class="position-static"> <slot name="universal-nav"></slot> </vs-col>
          <vs-col class="d-flex justify-content-end position-static">
            <slot name="login"></slot> <slot name="language"></slot>
          </vs-col>
        </vs-row>
      </vs-container>
    </div>

    <div class="vs-header__wrapper--bottom position-relative">
      <vs-container>
        <vs-row>
          <vs-col cols="7"> <slot name="logo" /> </vs-col>
          <vs-col cols="5" class="d-flex justify-content-end position-static">
            <div class="vs-controls__wrapper d-flex">
              <slot name="search" /> <slot name="favourites" />
              <vs-main-nav name="Main navigation"> <slot name="main-nav-items" /> </vs-main-nav>
            </div>
          </vs-col>
        </vs-row>
      </vs-container>
    </div>
  </component>
</template>

<script>
import VsContainer from "../../elements/layout/Container"
import VsSvg from "../../elements/svg/Svg"
import VsRow from "../../elements/layout/Row"
import VsCol from "../../elements/layout/Col"

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
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/position";
@import "~bootstrap/scss/utilities/screenreaders";
@import "styles/placeholders";

.vs-header {
  position: relative;
}

.vs-header__wrapper--top {
  background-color: $color-theme-primary;
  z-index: 4;
}

.vs-header__wrapper--bottom {
  background-color: $white;
  box-shadow: 0 8px 6px -6px rgba(0, 0, 0, 0.3);
}

.vs-controls__wrapper {
  margin-right: -1rem;
}
</style>

<docs>
  ```jsx

  const ourSites = require("../../../assets/fixtures/ourSites.json")
  const language = require("../../../assets/fixtures/languages.json")
  const mainNav = require("../../../assets/fixtures/mainNav.json")

  <div style="overflow-y: scroll; height: 600px;">
    <vs-header>
      <vs-dropdown
        slot="universal-nav"
        name="Our sites"
        :dropdown-list="ourSites"
      />
      <vs-login
        slot="login" />
      <vs-dropdown
        slot="language"
        name="Language"
        class="vs-dropdown--language"
        :dropdown-list="language"
      />
      <vs-logo 
        slot="logo" />
      <vs-search
        slot="search" />
      <vs-favourites
        slot="favourites" />
        
      <vs-main-nav-list-item
        slot="main-nav-items"
        v-for="(item, index) in mainNav"
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :subnav="item.subnav"
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
            :promo-list="level3.promoList"
            :promo-item="level3.promoItem"
            :key="index3"
          >
          </vs-main-nav-list-item>
        </vs-main-nav-list-item>
      </vs-main-nav-list-item>
    </vs-header>
  </div>
  ```
</docs>
