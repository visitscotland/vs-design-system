<template>
  <component :is="type" class="list-unstyled">
    <li class="vs-promo__list-item" v-for="(item, index) in list" :key="index">
      <VsMobileNavPromoItem
        v-show="item && index === last"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :button-text="item.buttonText"
        :description="item.description"
        :image-link="item.imageLink"
      />
      <VsMobileNavListItem
        v-show="item && index !== last"
        :level="2"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
        :key="index"
      />
    </li>
  </component>
</template>

<script>
import VsSvg from "../../../../elements/svg/Svg"
import VsMobileNavPromoItem from "./MobileNavPromoItem"
import VsMobileNavListItem from "./MobileNavListItem"

export default {
  name: "VsMobileNavPromoList",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsSvg,
    VsMobileNavPromoItem,
    VsMobileNavListItem,
  },
  data() {
    return {}
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "ul",
    },
    list: {
      type: Array,
      required: true,
    },
  },
  computed: {
    last() {
      return Object.keys(this.list).length - 1
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
</style>

<docs>
  ```jsx
    const mainNav = require("../../../../../assets/fixtures/mainNav.json")
    const promoList = mainNav[4].promoList
    <div>
      <vs-mobile-nav-promo-list
        :list="promoList"
      />
    </div>
  ```
</docs>
