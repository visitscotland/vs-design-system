<template>
  <component
    :is="type"
    class="vs-desktop-nav__list-item"
    :class="{
      ['vs-desktop-nav__list-item--level' + level]: level,
      ['col-4 col-xl-3']: level === 2,
    }"
  >
    <a
      v-if="href !== null"
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
    <span
      v-else
      class="vs-desktop-nav__span"
      :class="{
        ['vs-desktop-nav__span--level' + level]: level,
      }"
      >{{ title }}</span
    >
    <div v-if="hasChildren">
      <ul
        class="list-unstyled"
        :class="{
          ['vs-desktop-nav__list--level' + incrementLevel]: incrementLevel,
        }"
      >
        <li
          class="vs-desktop-nav__list-item"
          :class="{
            ['vs-desktop-nav__list-item--level' + incrementLevel]: incrementLevel,
          }"
          v-if="href !== null"
        >
          <a
            class="vs-desktop-nav__link vs-desktop-nav__link--landing-page"
            :href="href"
            :class="[
              isExternal ? 'external' : '',
              level ? 'vs-desktop-nav__link--level' + incrementLevel : '',
            ]"
            :target="isExternal ? '_blank' : false"
            :data-vs-track="trackingId"
            >See all {{ lowerCaseTitle }}</a
          >
        </li>
        <slot name="subnav" />
      </ul>
      <VsDesktopNavPromoItem
        v-if="promoItem"
        :href="promoItem.href"
        :is-external="promoItem.isExternal"
        :title="promoItem.title"
        :button-text="promoItem.buttonText"
        :description="promoItem.description"
        :image-link="promoItem.imageLink"
      />
      <VsDesktopNavPromoItem v-if="promoList" :list="promoList" />
    </div>
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"

export default {
  name: "VsDesktopNavListItem",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
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
    formattedSubnavId() {
      return "subnav" + this.subnavId
    },
  },
  methods: {},
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities/spacing";
@import "../../styles/mixins";

.vs-desktop-nav__span,
.vs-desktop-nav__link {
  color: $gray-shade-2;
  display: block;

  &--level2 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }

  &--level3 {
    font-size: 1.0625rem;
    padding: 0.25rem 0;
  }
}

.vs-desktop-nav__list-item--header-link {
  display: block;
}

.vs-desktop-nav__list {
  &--level3 {
    margin-bottom: 2rem;
  }
}

.vs-desktop-nav__link {
  &--level2 {
    &:focus {
      color: $color-pink;
      outline: none;
      position: relative;

      &::after {
        content: "";
        display: block;
        background-color: $gray-tint-5;
        width: 5px;
        height: 100%;
        position: absolute;
        left: -1rem;
        top: 0;
      }
    }
    &:hover {
      color: $color-pink;
    }
  }

  &--level3 {
    &:focus {
      color: $color-pink;
      outline: none;
      position: relative;

      &::after {
        content: "";
        display: block;
        background-color: $gray-tint-5;
        width: 5px;
        height: 100%;
        position: absolute;
        left: -1rem;
        top: 0;
      }
    }
    &:hover {
      @include focus-underline($color-pink, -1px);
    }
  }
}
</style>

<docs>
  ```jsx

  const mainNav = require("../../../../../assets/fixtures/mainNav.json")
  const item = mainNav[0]

  <div class="vs-header">
  <ul style="list-style-type: none; margin: 0; padding: 0;">
    <vs-desktop-nav-toggles
      :level="1"
      :href="item.href"
      :is-external="item.isExternal"
      :title="item.title"
      :subnav="item.subnav"
      :promo-list="item.promoList"
      :promo-item="item.promoItem"
      :toggleId="1"
    />
  </ul>
    <ul style="list-style-type: none; margin: 0; padding: 0;">
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
  </div>
  ```
</docs>
