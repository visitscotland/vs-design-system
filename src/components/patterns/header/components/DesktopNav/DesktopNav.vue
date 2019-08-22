<template>
  <component :is="type" :aria-label="name">
    <ul class="vs-desktop-nav__list vs-desktop-nav__list--level1 list-unstyled">
      <slot />
    </ul>
  </component>
</template>

<script>
import { BCollapse } from "bootstrap-vue"

export default {
  name: "VsDesktopNav",
  status: "prototype",
  release: "0.0.1",
  components: {
    BCollapse,
  },
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
      default: "nav",
    },
    name: {
      type: String,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../../styles/placeholders";
</style>

<docs>
  ```jsx
  const mainNav = require("../../../../../assets/fixtures/mainNav.json")

  <div class="vs-header" style="position: relative; height: 100vh;">
    <vs-desktop-nav name="Desktop navigation"> 
      <vs-desktop-nav-list-item
        v-for="(item, index) in mainNav"
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :subnav="item.subnav"
        :tracking-id="item.trackingId"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
        :key="index"
      >
      <vs-desktop-nav-list-item
          slot="subnav"
          v-for="(level2, index2) in item.subnav"
          :level="2"
          :href="level2.href"
          :is-external="level2.isExternal"
          :title="level2.title"
          :subnav="level2.subnav"
          :tracking-id="item.trackingId"
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
            :tracking-id="item.trackingId"
            :promo-list="level3.promoList"
            :promo-item="level3.promoItem"
            :key="index3"
          >
          </vs-desktop-nav-list-item>
        </vs-desktop-nav-list-item>
      </vs-desktop-nav-list-item>
    </vs-desktop-nav>
  </div>
  ```
</docs>
