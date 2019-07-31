<template>
  <component
    :is="type"
    class="vs-main-nav__list-item"
    :class="'vs-main-nav__list-item--level' + level"
  >
    <button
      v-if="hasChildren"
      class="vs-main-nav__button"
      :class="{
        expanded: show,
        ['vs-main-nav__button--level' + level]: level,
      }"
      ref="trigger"
      data-test-trigger
      @click="triggerToggle()"
      aria-haspopup="true"
      :aria-expanded="show ? 'true' : 'false'"
    >
      {{ title }}
      <div
        class="vs-main-nav__icon-wrapper vs-main-nav__icon-wrapper--spin"
        :class="{
          'vs-main-nav__icon-wrapper--expanded': show,
          ['level' + level]: level,
        }"
      >
        <vs-svg path="icons/chevron-down" height="8" fill="#727272" />
      </div>
    </button>
    <a
      v-else
      class="vs-main-nav__link"
      :href="href"
      :class="{
        external: isExternal,
        ['vs-main-nav__link--level' + level]: level,
      }"
      :target="isExternal ? '_blank' : false"
      :data-vs-track="formattedTrackingId"
      >{{ title }}</a
    >
    <transition name="slide-fade" v-if="hasChildren">
      <div v-if="show">
        <ul
          class="list-unstyled"
          :class="{
            expanded: show,
            ['vs-main-nav__list--level' + incrementLevel]: incrementLevel,
          }"
        >
          <li
            class="vs-main-nav__list-item"
            :class="{
              ['vs-main-nav__list-item--level' + incrementLevel]: incrementLevel,
            }"
            v-if="href !== null"
          >
            <a
              class="vs-main-nav__link vs-main-nav__link--landing-page"
              :href="href"
              :class="[
                isExternal ? 'external' : '',
                level ? 'vs-main-nav__link--level' + incrementLevel : '',
              ]"
              :target="isExternal ? '_blank' : false"
              :data-vs-track="formattedTrackingId"
              >See all {{ lowerCaseTitle }}</a
            >
          </li>
          <slot name="subnav" />
        </ul>
        <VsMainNavPromoItem v-if="promoItem" :item="promoItem" />
        <VsMainNavPromoList v-if="promoList" :list="promoList" />
      </div>
    </transition>
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"
import smoothscroll from "smoothscroll-polyfill"

export default {
  name: "VsMainNavListItem",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
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
  },
  computed: {
    formattedTrackingId() {
      return this.title ? "link.nav." + this.title.toLowerCase().replace(/\s+/g, "-") : ""
    },
    lowerCaseTitle() {
      return this.title ? this.title.toLowerCase() : ""
    },
    hasChildren() {
      if (
        this.subnav !== undefined ||
        this.promoItem !== undefined ||
        this.promoList !== undefined
      ) {
        return true
      }
      return false
    },
    incrementLevel() {
      return this.level + 1
    },
  },
  methods: {
    triggerToggle() {
      this.show = !this.show
      let thisTrigger = this.$refs.trigger
      let headerWrapper = document.querySelector(".vs-header")
      if (this.show) {
        headerWrapper.scrollTo({
          behavior: "smooth",
          left: 0,
          top: thisTrigger.offsetTop + 68,
        })
      } else {
        headerWrapper.scrollTo({
          behavior: "smooth",
          left: 0,
          top: 0,
        })
      }
      thisTrigger.blur()
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
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/type";
@import "../styles/placeholders";
@import "../styles/animations";

.vs-main-nav__icon-wrapper {
  &--spin {
    margin-left: 5px;
    transition: transform 250ms;
  }

  &--expanded {
    transform: rotate(180deg);
    transform-origin: 50% 54%;
  }
}

.vs-main-nav__button {
  @extend %button-reset;
}

.vs-main-nav__button,
.vs-main-nav__link {
  align-items: center;
  border-bottom: 1px solid $color-gray-e6;
  color: $color-gray-38;
  display: flex;
  font-weight: $font-weight-semi-bold;
  justify-content: space-between;
  transition: background-color 250ms ease-in-out;

  &:focus {
    @extend %focus-pink-inset;
  }

  &--level1 {
    font-size: 1.5rem;
    font-weight: $font-weight-normal;
    padding: 0.75rem 1.25rem;
    width: 100%;

    &[aria-expanded="true"] {
      box-shadow: inset 0.75rem 0 0 0 $color-thistle-pink;
    }
  }

  &--level2 {
    background-color: $color-gray-f8;
    box-shadow: inset 0.75rem 0 0 0 $color-gray-e6;
    font-size: 1.125rem;
    padding: 0.75rem 1.25rem 0.75rem 2.25rem;
    width: 100%;

    &[aria-expanded="true"] {
      box-shadow: inset 0.75rem 0 0 0 $color-thistle-pink;
    }
  }

  &--level3 {
    background-color: $color-gray-ef;
    box-shadow: inset 0.75rem 0 0 0 $color-gray-e6;
    font-size: 1.125rem;
    padding: 0.75rem 1.25rem 0.75rem 3.25rem;

    .vs-main-nav__list-item--level3:first-of-type & {
      box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.1), inset 12px 0 0 0 $color-gray-e6;
    }
  }

  &--level3,
  &--level2 {
    font-weight: $font-weight-normal;
  }
}

.vs-main-nav__link {
  &--landing-page {
    background-color: $color-white;
    color: $color-thistle-pink;

    &:hover {
      color: $color-thistle-pink;
    }
  }
  &--level3 {
    border-bottom: none;
  }
}
</style>

<docs>
  ```jsx

  const mainNav = require("../../../../assets/fixtures/mainNav.json")
  const listItemOne = mainNav[0]

  <div style="position: relative; height: 100vh;">
    <ul style="list-style-type: none; margin: 0; padding: 0;">
      <vs-main-nav-list-item
        :level="1"
        :href="href"
        :is-external="isExternal"
        :title="title"
        :subnav="subnav"
        :promo-list="promoList"
        :promo-item="promoItem"
        :key="index"
      />
      </ul>
  </div>
  ```
</docs>
