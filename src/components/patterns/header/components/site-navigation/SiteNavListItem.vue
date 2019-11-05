<template>
  <li
    data-test="site-nav-list-item"
    class="vs-site-nav__list-item"
    :class="'vs-site-nav__list-item--level' + level"
  >
    <vs-button
      v-if="hasChildren"
      data-test="site-nav-button"
      class="vs-site-nav__button d-flex align-items-center px-2"
      :class="{
        ['vs-site-nav__button--level' + level]: level,
      }"
      ref="trigger"
      @click.native="triggerToggle()"
      aria-haspopup="true"
      :aria-expanded="show ? 'true' : 'false'"
      :animate="false"
    >
      <slot />
      <div
        class="vs-mobile-nav__icon-wrapper vs-mobile-nav__icon-wrapper--spin"
        :class="{
          'vs-mobile-nav__icon-wrapper--expanded': show,
          ['level' + level]: level,
        }"
      >
        <vs-icon
          data-test="mobile-nav-chevron-svg"
          name="chevron-down"
          size="xs"
          variant="dark"
          class="d-lg-none"
        />
      </div>
    </vs-button>
    <a
      v-else
      class="vs-mobile-nav__link"
      :href="href"
      :class="{
        external: isExternal,
        ['vs-mobile-nav__link--level' + level]: level,
      }"
      :target="isExternal ? '_blank' : false"
      :data-vs-track="trackingId"
      ><slot
    /></a>
    <transition name="slide-fade" v-if="hasChildren">
      <vs-site-nav-list :level="level + 1" v-show="show">
        <!-- <div v-show="show"> -->
        <!-- <ul
          data-test="sitenav-submenu-list"
          class="vs-site-nav__list list-unstyled"
          :class="{
            ['vs-mobile-nav__list--level' + incrementLevel]: incrementLevel,
          }"
        > -->
        <li
          class="vs-mobile-nav__list-item"
          :class="{
            ['vs-mobile-nav__list-item--level' + incrementLevel]: incrementLevel,
          }"
          v-if="href !== null"
        >
          <a
            class="vs-mobile-nav__link vs-mobile-nav__link--landing-page"
            :href="href"
            :class="[
              isExternal ? 'external' : '',
              level ? 'vs-mobile-nav__link--level' + incrementLevel : '',
            ]"
            :target="isExternal ? '_blank' : false"
            :data-vs-track="trackingId"
            >See all {{ lowerCaseTitle }}</a
          >
        </li>
        <slot name="subnav" />
      </vs-site-nav-list>
      <!-- </ul> -->
      <!-- <VsMobileNavPromoItem
          v-if="promoItem"
          :href="promoItem.href"
          :is-external="promoItem.isExternal"
          :title="promoItem.title"
          :button-text="promoItem.buttonText"
          :description="promoItem.description"
          :image-link="promoItem.imageLink"
        />
        <VsMobileNavPromoList v-if="promoList" :list="promoList" /> -->
      <!-- </div> -->
    </transition>
  </li>
</template>

<script>
import VsIcon from "@components/elements/icon"
import VsButton from "@components/elements/button"
import VsSiteNavList from "./SiteNavList"

export default {
  name: "VsSiteNavListItem",
  status: "prototype",
  release: "0.1.0",
  components: { VsIcon, VsButton, VsSiteNavList },
  data() {
    return {
      show: false,
    }
  },
  props: {
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
      return this.level < 3
    },
    incrementLevel() {
      return this.level + 1
    },
  },
  methods: {
    reset() {
      this.show = false
    },
    triggerToggle() {
      this.show = !this.show
      let thisTrigger = this.$refs.trigger
      if (this.show) {
        this.$parent.$emit("setScrollOffset", thisTrigger.offsetTop)
      } else {
        this.$parent.$emit("setScrollOffset", 0)
      }
      thisTrigger.$el.blur()
    },
  },
  mounted() {
    this.$root.$on("resetMenus", this.reset)
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/position";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/type";
@import "../../styles/placeholders";
@import "../../styles/animations";

.vs-site-nav__list-item {
  @include media-breakpoint-up(lg) {
    height: 100%;
  }

  .vs-site-nav__button,
  .vs-site-nav__link {
    border-bottom: 1px solid $color-gray-tint-6;
    color: $color-base-text;
    font-weight: $font-weight-bold;
    position: relative;
    justify-content: space-between;
    transition: background-color 250ms ease-in-out;

    @include media-breakpoint-up(lg) {
      height: 100%;
      font-size: 18px;
      background-color: transparent;
      letter-spacing: 0;
      text-transform: none !important;
      white-space: nowrap;
      border: none;
    }

    &.vs-site-nav__button--level1,
    &.vs-site-nav__link--level1 {
      font-size: 1.5rem;
      font-weight: $font-weight-normal;
      padding: 0.75rem 1.25rem;
      width: 100%;

      @include media-breakpoint-up(lg) {
        font-size: 1rem;
      }

      &[aria-expanded="true"] {
        &::after {
          background-color: $color-pink;
        }
      }
    }

    &.vs-site-nav__button--level2,
    &.vs-site-nav__link--level2 {
      font-size: 1.125rem;
      font-weight: $font-weight-normal;
      padding: 0.75rem 1.25rem 0.75rem 2.25rem;
      width: 100%;

      &::after {
        background-color: $color-gray-tint-6;
      }

      &[aria-expanded="true"] {
        &::after {
          background-color: $color-pink;
        }
      }
    }

    &.vs-site-nav__button--level3,
    &.vs-site-nav__link--level3 {
      font-size: 1.125rem;
      font-weight: $font-weight-normal;
      padding: 0.75rem 1.25rem 0.75rem 3.25rem;

      &::after {
        background-color: $color-gray-tint-6;
      }
    }
  }
}

// .vs-mobile-nav__list {
//   height: 100vh;
//   left: 0;
//   position: absolute;
//   top: 40px;
//   width: 100%;
//   z-index: 2;
// }

.vs-mobile-nav__list--level1 {
  background-color: $color-white;
  box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
}

.vs-mobile-nav__list--level2 {
  background-color: $color-gray-tint-7;
}

.vs-mobile-nav__list--level3 {
  background-color: $color-gray-tint-7;
  box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
}

.vs-mobile-nav__icon-wrapper--spin {
  margin-left: 5px;
  transition: transform 250ms;
}

.vs-mobile-nav__icon-wrapper--expanded {
  transform: rotate(180deg);
  transform-origin: 50% 54%;
}

.vs-mobile-nav__button {
  @extend %button-reset;
}

.vs-mobile-nav__button,
.vs-mobile-nav__link {
  align-items: center;
  border-bottom: 1px solid $color-gray-tint-6;
  color: $color-base-text;
  display: flex;
  font-weight: $font-weight-bold;
  position: relative;
  justify-content: space-between;
  transition: background-color 250ms ease-in-out;

  &::after {
    content: "";
    position: absolute;
    width: 12px;
    height: 100%;
    left: 0;
  }

  &:focus {
    @extend %focus-pink-inset;
  }

  &.vs-mobile-nav__button--level1,
  &.vs-mobile-nav__link--level1 {
    font-size: 1.5rem;
    font-weight: $font-weight-normal;
    padding: 0.75rem 1.25rem;
    width: 100%;

    &[aria-expanded="true"] {
      &::after {
        background-color: $color-pink;
      }
    }
  }

  &.vs-mobile-nav__button--level2,
  &.vs-mobile-nav__link--level2 {
    font-size: 1.125rem;
    font-weight: $font-weight-normal;
    padding: 0.75rem 1.25rem 0.75rem 2.25rem;
    width: 100%;

    &::after {
      background-color: $color-gray-tint-6;
    }

    &[aria-expanded="true"] {
      &::after {
        background-color: $color-pink;
      }
    }
  }

  &.vs-mobile-nav__button--level3,
  &.vs-mobile-nav__link--level3 {
    font-size: 1.125rem;
    font-weight: $font-weight-normal;
    padding: 0.75rem 1.25rem 0.75rem 3.25rem;

    &::after {
      background-color: $color-gray-tint-6;
    }
  }
}

.vs-mobile-nav__link--landing-page {
  background-color: $color-white;
  color: $color-pink;

  &:hover {
    color: $color-pink;
  }
}

.vs-mobile-nav__link--level3 {
  border-bottom: none;
}
</style>

<docs>
  ```jsx

  <div class="vs-header">
    <ul style="list-style-type: none; margin: 0; padding: 0; max-width: 400px;">
      <vs-mobile-nav-list-item
        :level="1"
        :href="header.mainNav[0].href"
        :is-external="header.mainNav[0].isExternal"
        :title="header.mainNav[0].title"
        :subnav="header.mainNav[0].subnav"
        :promo-list="header.mainNav[0].promoList"
        :promo-item="header.mainNav[0].promoItem"
      >
      <vs-mobile-nav-list-item
          slot="subnav"
          v-for="(level2, index2) in header.mainNav[0].subnav"
          :level="2"
          :href="level2.href"
          :is-external="level2.isExternal"
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
            :subnav="level3.subnav"
            :promo-list="level3.promoList"
            :promo-item="level3.promoItem"
            :key="index3"
          >
          </vs-mobile-nav-list-item>
        </vs-mobile-nav-list-item>
      </vs-mobile-nav-list-item>
      </ul>
  </div>
  ```
</docs>
