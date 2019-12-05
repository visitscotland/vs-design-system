<template>
  <component
    :is="type"
    data-test="desktop-nav-list-item"
    class="vs-desktop-nav__list-item"
    :class="'vs-desktop-nav__list-item--level' + level"
  >
    <button
      v-if="hasChildren"
      data-test="desktop-nav-button"
      class="vs-desktop-nav__button"
      :class="{
        ['vs-desktop-nav__button--level' + level]: level,
      }"
      :id="formattedToggleId"
      v-b-toggle="formattedCollapsePaneId"
      @click="setFocus()"
    >
      {{ title }}
    </button>
    <a
      v-else
      class="vs-desktop-nav__link"
      :href="href"
      :class="{
        external: isExternal,
        ['vs-desktop-nav__link--level' + level]: level,
      }"
      :target="isExternal ? '_blank' : false"
      :data-vs-track="trackingId"
      >{{ title }}</a
    >
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"
import { VBToggle } from "bootstrap-vue"

export default {
  name: "VsDesktopNavToggles",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
  directives: { "b-toggle": VBToggle },
  data() {
    return {
      show: false,
    }
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
    chartWidgets: {
      type: Array,
    },
    toggleId: {
      type: Number,
    },
  },
  computed: {
    hasChildren() {
      if (
        this.subnav !== undefined ||
        this.promoItem !== undefined ||
        this.promoList !== undefined ||
        this.chartWidgets !== undefined
      ) {
        return true
      }
      return false
    },
    formattedCollapsePaneId() {
      return "collapse-subnav-" + this.toggleId
    },
    formattedToggleId() {
      return "collapse-toggle-" + this.toggleId
    },
  },
  methods: {
    setFocus() {
      var closeButton = "submenu-close-" + this.toggleId
      setTimeout(() => {
        let $closeButton = document.getElementById(closeButton)

        if ($closeButton) {
          $closeButton.focus()
        }
      }, 100)
    },
  },
}
</script>

<style lang="scss" scoped>
@import "../../styles/placeholders";
@import "../../styles/animations";

.vs-desktop-nav__button--level1,
.vs-desktop-nav__link--level1 {
  @extend %button-reset;
  @extend %main-nav-button-style;
  width: auto;
  padding: 0 0.5rem;
  position: relative;

  @include media-breakpoint-up(xl) {
    font-size: 1.125rem;
  }

  &[aria-expanded="true"],
  &:hover,
  &:focus {
    @extend %focus-pink-inset;
  }
}
</style>

<docs>
  ```jsx

  <ul style="display: flex; list-style-type: none; margin: 0; padding: 0;">
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
      :toggleId="index + 1"
      :key="index"
    />
  </ul>
  ```
</docs>
