<template>
  <b-collapse
    v-model="drawer.isOpen"
    class="vs-header__drawer-wrapper"
    id="vs-header__drawer-wrapper"
  >
    <vs-container>
      <vs-row>
        <vs-col cols="1" order="2" v-if="showClose">
          <vs-close-button class="vs-header__drawer__close-button" @click.native="closeDrawer()">
            Close the header drawer
          </vs-close-button>
        </vs-col>
        <vs-col>
          <slot />
        </vs-col>
        <button @focus="endFocus" class="catch-focus__button" />
      </vs-row>
    </vs-container>
  </b-collapse>
</template>

<script>
import smoothscroll from "smoothscroll-polyfill"
import { BCollapse } from "bootstrap-vue"
import VsContainer from "@components/elements/layout/Container"
import VsSvg from "@components/elements/svg/Svg"
import VsRow from "@components/elements/layout/Row"
import VsCol from "@components/elements/layout/Col"
import VsFavouritesButton2 from "@components/patterns/favourites/FavouritesButton2"
import VsCloseButton from "@components/patterns/close-button/CloseButton"
import store, { names as storeNames } from "../../header.store"

export default {
  name: "VsHeaderDrawer",
  status: "prototype",
  release: "0.0.1",
  store,
  components: {
    VsCol,
    VsContainer,
    VsRow,
    BCollapse,
    VsFavouritesButton2,
    VsCloseButton,
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
    favouriteHref: {
      type: String,
    },
    favouriteTitle: {
      type: String,
    },
  },
  computed: {
    showClose() {
      return this.favouritesListIsVisible
    },
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
    },
  },
  methods: {
    closeDrawer() {
      store.dispatch("header/drawer/close")
    },
    endFocus() {
      store.dispatch("header/drawer/close")
    },
  },
  created() {
    smoothscroll.polyfill()
  },
}
</script>

<style lang="scss" scoped>
.vs-header__drawer-wrapper {
  @extend %default-inset-box-shadow;
  background-color: $gray-tint-7;
  padding: 2.5rem 0;
  width: 100%;
}

.vs-header__drawer__close-button {
  right: 1em;
}

.catch-focus__button {
  width: 0;
  height: 0;
  margin: 0;
  padding: 0;
  border: none;
}

.collapse:not(.show) {
  display: none;
}

.collapsing {
  position: relative;
  height: 0;
  overflow: hidden;
  transition: height 100ms ease;
}
</style>

<docs>
  ```vue

  ```
</docs>
