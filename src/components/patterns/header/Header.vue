<template>
  <component :is="type" class="vs-header">
    <div class="vs-header__wrapper--top position-relative">
      <vs-container>
        <vs-row>
          <vs-col class="position-static"> <slot name="ourSites"></slot> </vs-col>
          <vs-col class="d-flex justify-content-end position-static">
            <slot name="login"></slot> <slot name="language"></slot>
          </vs-col>
        </vs-row>
      </vs-container>
    </div>

    <div class="vs-header__wrapper--bottom position-relative">
      <vs-container>
        <vs-row>
          <vs-col cols="7"> <slot name="logo"></slot> </vs-col>
          <vs-col cols="5" class="d-flex justify-content-end position-static">
            <div class="vs-controls__wrapper d-flex">
              <slot name="search"></slot> <slot name="favourites"></slot>
              <slot name="mainNav"></slot>
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
  min-height: 600px; // temporary -
  overflow-x: hidden;
  overflow-y: scroll;
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
