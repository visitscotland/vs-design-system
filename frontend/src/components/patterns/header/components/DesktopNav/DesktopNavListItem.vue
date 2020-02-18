<template>
  <component
    :is="type"
    data-test="desktop-nav-list-item"
    class="vs-desktop-nav__list-item"
    :class="{
      ['vs-desktop-nav__list-item--level' + level]: level,
      ['col-4 col-xl-3 divide-left']: level === 2,
    }"
  >
    <a
      v-if="href !== null"
      data-test="desktop-nav-link"
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
        data-test="desktop-nav-submenu-list"
        :class="{
          ['vs-desktop-nav__list--level' + incrementLevel]: incrementLevel,
        }"
      >
        <li
          class="vs-desktop-nav__list-item"
          data-test="desktop-nav-submenu-list-item"
          :class="{
            ['vs-desktop-nav__list-item--level' + incrementLevel]: incrementLevel,
          }"
          v-if="href !== null"
        >
          <a
            class="vs-desktop-nav__link vs-desktop-nav__link--landing-page"
            data-test="desktop-nav-submenu-link"
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
    subnavId: {
      type: Number,
    },
  },
  computed: {
    lowerCaseTitle() {
      return this.title ? this.title.toLowerCase() : ""
    },
    hasChildren() {
      if (this.subnav !== undefined) {
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
@import "../../styles/placeholders";
@import "../../styles/mixins";

.divide-left {
  @extend %divide-left;
}

.vs-desktop-nav__span,
.vs-desktop-nav__link {
  color: $color-gray-shade-2;
  display: block;

  &.vs-desktop-nav__span--level2,
  &.vs-desktop-nav__link--level2 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }

  &.vs-desktop-nav__span--level3,
  &.vs-desktop-nav__link--level3 {
    font-size: 1.0625rem;
    padding: 0.25rem 0;
  }
}

.vs-desktop-nav__link--level2 {
  &:hover {
    color: $color-pink;
  }
  &:focus {
    color: $color-pink;
    outline: none;
    position: relative;

    &::after {
      content: "";
      display: block;
      background-color: $color-gray-tint-5;
      height: 100%;
      position: absolute;
      left: -1rem;
      top: 0;
      transition: width 250ms ease;
      width: 5px;
    }
  }
}

.vs-desktop-nav__link--level3 {
  transition: box-shadow 250ms ease;

  &::after {
    content: "";
    display: block;
    height: 0;
    transition: height 250ms cubic-bezier(0.78, 0.27, 0.36, 0.76);
    width: 0;
  }

  &:focus {
    color: $color-pink;
    outline: none;
    position: relative;

    &::after {
      background-color: $color-gray-tint-5;
      content: "";
      display: block;
      height: 100%;
      left: -1rem;
      position: absolute;
      top: 0;
      width: 5px;
    }
  }
  &:hover {
    @include focus-underline($color-pink, -1px);
  }
}

.vs-desktop-nav__list-item--header-link {
  display: block;
}

.vs-desktop-nav__list--level3 {
  margin-bottom: 2rem;
}
</style>

<docs>
  ```jsx

  <div class="vs-header">
  <ul style="list-style-type: none; margin: 0; padding: 0;">
    <vs-desktop-nav-toggles
      :level="1"
      :href="header.mainNav[0].href"
      :is-external="header.mainNav[0].isExternal"
      :title="header.mainNav[0].title"
      :subnav="header.mainNav[0].subnav"
      :toggleId="1"
    />
  </ul>
    <ul style="list-style-type: none; margin: 0; padding: 0;">
      <vs-desktop-nav-submenu
        :level="1"
        :href="header.mainNav[0].href"
        :is-external="header.mainNav[0].isExternal"
        :title="header.mainNav[0].title"
        :subnav="header.mainNav[0].subnav"
        :promo-list="header.mainNav[0].promoList"
        :promo-item="header.mainNav[0].promoItem"
        :widget="header.mainNav[0].widget"
        :subnav-id="1"
      >
      <vs-desktop-nav-list-item
          slot="subnav"
          v-for="(level2, index2) in header.mainNav[0].subnav"
          :level="2"
          :href="level2.href"
          :is-external="level2.isExternal"
          :title="level2.title"
          :subnav="level2.subnav"
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
            :key="index3"
          >
          </vs-desktop-nav-list-item>
        </vs-desktop-nav-list-item>
      </vs-desktop-nav-submenu>

      </ul>
  </div>
  ```
</docs>
