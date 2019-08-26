<template>
  <component :is="type" :aria-label="name">
    <ul class="list-unstyled">
      <slot name="desktop-submenu" />
    </ul>
  </component>
</template>

<script>
export default {
  name: "VsDesktopNav",
  status: "prototype",
  release: "0.0.1",

  data() {
    return {}
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
@import "~bootstrap/scss/type";
</style>

<docs>
  ```jsx
  const mainNav = require("../../../../../assets/fixtures/mainNav.json")

  <div class="vs-header" style="position: relative; height: 100vh;">
    <vs-desktop-nav name="Desktop navigation"> 
      <vs-desktop-nav-submenu
        slot="desktop-submenu"
        v-for="(item, index) in mainNav"
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :tracking-id="item.trackingId"
        :title="item.title"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
        :key="index"
        :subnavId="index+1"
      >
        <vs-desktop-nav-list-item
          slot="subnav"
          v-for="(level2, index2) in item.subnav"
          :level="2"
          :href="level2.href"
          :is-external="level2.isExternal"
          :tracking-id="level2.trackingId"
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
            :tracking-id="level3.trackingId"
            :subnav="level3.subnav"
            :promo-list="level3.promoList"
            :promo-item="level3.promoItem"
            :key="index3"
          >
          </vs-desktop-nav-list-item>
        </vs-desktop-nav-list-item>
      </vs-desktop-nav-submenu>
    </vs-desktop-nav>
  </div>
  ```
</docs>
