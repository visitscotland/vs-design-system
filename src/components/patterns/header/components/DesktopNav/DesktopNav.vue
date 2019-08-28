<template>
  <component :is="type" :aria-label="name">
    <ul class="list-unstyled">
      <slot />
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
  const item = mainNav[0]

  <div class="vs-header">
    <ul style="list-style-type: none; margin: 0; padding: 0; display: flex;">
      <!--  Only displaying one nav item, as clicking on different
            different lists emits events controlled by the header component -->
      <vs-desktop-nav-toggles
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
        :toggleId="1"
      ></vs-desktop-nav-toggles>
    </ul>
    <vs-desktop-nav name="Desktop navigation"> 
      <vs-desktop-nav-submenu
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :tracking-id="item.trackingId"
        :title="item.title"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
        :subnavId="1"
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
