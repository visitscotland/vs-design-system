<template>
  <component :is="type">
    <b-collapse :id="formattedCollapsePaneId" class="vs-desktop-submenu__wrapper">
      <vs-container>
        <vs-row class="mb-3">
          <vs-col class="position-relative">
            <span class="vs-desktop-submenu__header">{{ title }}</span>
            <button
              class="vs-desktop-submenu__close-button"
              :id="formattedSubmenuCloseId"
              v-b-toggle="formattedCollapsePaneId"
              @click="setFocusOnToggle()"
            >
              <span class="sr-only">Close the {{ lowerCaseTitle }} submenu</span>
              <vs-icon name="close" size="xs" variant="dark" />
            </button>
          </vs-col>
        </vs-row>
        <ul class="list-unstyled row">
          <slot name="subnav" />
          <VsDesktopNavPromoItem
            v-if="promoItem"
            :href="promoItem.href"
            :is-external="promoItem.isExternal"
            :title="promoItem.title"
            :button-text="promoItem.buttonText"
            :description="promoItem.description"
            :image-link="promoItem.imageLink"
          />
          <template v-if="promoList">
            <VsDesktopNavPromoItem
              v-for="(item, index) in promoList"
              :key="index"
              :href="item.href"
              :is-external="item.isExternal"
              :title="item.title"
              :button-text="item.buttonText"
              :description="item.description"
              :image-link="item.imageLink"
            />
          </template>
          <li
            class="vs-desktop-submenu__list-item vs-desktop-submenu__list-item--landing-page-link"
          >
            <a
              :href="href"
              class="vs-desktop-submenu__landing-page-link"
              @keydown="checkKeydown($event)"
              >See all {{ lowerCaseTitle }}</a
            >
          </li>
        </ul>
      </vs-container>
    </b-collapse>
  </component>
</template>

<script>
import { BCollapse, VBToggle } from "bootstrap-vue"
import VsIcon from "../../../../elements/icon/Icon"
import VsContainer from "../../../../elements/layout/Container"
import VsRow from "../../../../elements/layout/Row"
import VsCol from "../../../../elements/layout/Col"

export default {
  name: "VsDesktopNavSubmenu",
  status: "prototype",
  release: "0.0.1",
  components: {
    BCollapse,
    VsIcon,
    VsContainer,
    VsRow,
    VsCol,
  },
  directives: { "b-toggle": VBToggle },
  data() {
    return {}
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "li",
    },
    href: {
      type: String,
    },
    isExternal: {
      type: Boolean,
    },
    trackingId: {
      type: String,
    },
    title: {
      type: String,
    },
    level: {
      type: Number,
    },
    subnav: {
      type: Array,
    },
    promoList: {
      type: Array,
    },
    promoItem: {
      type: Object,
    },
    subnavId: {
      type: Number,
    },
  },
  computed: {
    formattedCollapsePaneId() {
      return "collapse-subnav-" + this.subnavId
    },
    formattedSubmenuCloseId() {
      return "submenu-close-" + this.subnavId
    },
    lowerCaseTitle() {
      return this.title ? this.title.toLowerCase() : ""
    },
  },
  methods: {
    close() {
      this.setFocusOnToggle()
      this.$root.$emit("bv::toggle::collapse", this.formattedCollapsePaneId)
    },
    checkKeydown($event) {
      if ($event.key === "Tab" && !$event.shiftKey) {
        this.close()
      }
    },
    setFocusOnToggle() {
      var menuToggle = "collapse-toggle-" + this.subnavId
      setTimeout(() => {
        document.getElementById(menuToggle).focus()
      }, 100)
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities/position";
@import "~bootstrap/scss/utilities/spacing";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../../styles/placeholders";
@import "../../styles/mixins";

.collapse:not(.show) {
  display: none;
  opacity: 0;
}

.collapsing {
  position: relative;
  height: 0;
  overflow: hidden;
  opacity: 1;
  transition: all 50ms ease-in-out;
}

.vs-desktop-submenu__wrapper {
  @extend %default-inset-box-shadow;
  position: relative;
  margin-bottom: 40px;
  padding: 40px 0;
}

.vs-desktop-submenu__header {
  color: $gray-shade-2;
  display: inline-block;
  font-size: 2rem;
  font-weight: $font-weight-light;
  letter-spacing: 1px;
  text-transform: uppercase;
}

.vs-desktop-submenu__close-button {
  @extend %button-reset;
  display: inline-block;
  position: absolute;
  right: 1rem;
  height: 50px;
  width: 50px;

  &:hover,
  &:focus {
    @include focus-underline($color-pink);
  }
}

.vs-desktop-submenu__landing-page-link {
  color: $color-white;
  background-color: $color-gray;
  font-size: 1.1875rem;
  display: block;
  padding: 0.5rem 1rem;
  transition: background-color 250ms ease-in-out;

  &:hover {
    background-color: $gray-shade-2;
    color: $color-white;
  }
}

.vs-desktop-submenu__list-item--landing-page-link {
  position: absolute;
  bottom: 0;
  right: 0;
  min-width: 33%;

  @include media-breakpoint-up(xl) {
    min-width: 25%;
  }
}
</style>

<docs>
  ```jsx

  const mainNav = require("../../../../../assets/fixtures/mainNav.json")
  const item = mainNav[0]

  <ul class="list-unstyled">
    <vs-desktop-nav-submenu
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
        :subnav-id="1"
      >
      <vs-desktop-nav-list-item
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
          <vs-desktop-nav-list-item
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
          </vs-desktop-nav-list-item>
        </vs-desktop-nav-list-item>
      </vs-desktop-nav-submenu>
  </ul>
  ```
</docs>
