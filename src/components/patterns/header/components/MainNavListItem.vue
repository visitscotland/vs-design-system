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
        <vs-icon name="chevron-down" size="xs" variant="dark" />
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
      <div v-show="show">
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
        <VsMainNavPromoItem
          v-if="promoItem"
          :href="promoItem.href"
          :is-external="promoItem.isExternal"
          :title="promoItem.title"
          :button-text="promoItem.buttonText"
          :description="promoItem.description"
          :image-link="promoItem.imageLink"
        />
        <VsMainNavPromoList v-if="promoList" :list="promoList" />
      </div>
    </transition>
  </component>
</template>

<script>
import VsIcon from "../../../elements/icon/Icon"

export default {
  name: "VsMainNavListItem",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
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
      thisTrigger.blur()
    },
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

.vs-main-nav__list {
  height: 100vh;
  left: 0;
  position: absolute;
  top: 40px;
  width: 100%;
  z-index: 2;

  &--level1 {
    background-color: $color-white;
    box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
  }

  &--level2 {
    background-color: $gray-tint-7;
  }

  &--level3 {
    background-color: $gray-tint-7;
    box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
  }
}

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
  border-bottom: 1px solid $gray-tint-6;
  color: $color-base-text;
  display: flex;
  font-weight: $font-weight-semi-bold;
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

  &--level1 {
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

  &--level2 {
    font-size: 1.125rem;
    padding: 0.75rem 1.25rem 0.75rem 2.25rem;
    width: 100%;

    &::after {
      background-color: $gray-tint-6;
    }

    &[aria-expanded="true"] {
      &::after {
        background-color: $color-pink;
      }
    }
  }

  &--level3 {
    font-size: 1.125rem;
    padding: 0.75rem 1.25rem 0.75rem 3.25rem;

    &::after {
      background-color: $gray-tint-6;
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
    color: $color-pink;

    &:hover {
      color: $color-pink;
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
  const item = mainNav[0]

  <div class="vs-header">
    <ul style="list-style-type: none; margin: 0; padding: 0;">
      <vs-main-nav-list-item
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
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
      </ul>
  </div>
  ```
</docs>
