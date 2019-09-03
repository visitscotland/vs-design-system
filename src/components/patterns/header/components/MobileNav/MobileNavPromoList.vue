<template>
  <component data-test="mobile-promo-list" :is="type" class="list-unstyled">
    <VsMobileNavListItem
      v-for="(item, index) in allButLastPromos"
      :level="2"
      :href="item.href"
      :is-external="item.isExternal"
      :title="item.title"
      :subnav="item.subnav"
      :promo-list="item.promoList"
      :promo-item="item.promoItem"
      :key="'listPromos' + index"
    />
    <VsMobileNavPromoItem
      :href="lastPromo.href"
      :is-external="lastPromo.isExternal"
      :title="lastPromo.title"
      :button-text="lastPromo.buttonText"
      :description="lastPromo.description"
      :image-link="lastPromo.imageLink"
    />
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
    lastPromo() {
      var lastPromo = this.list.filter((item, index) => {
        if (index === this.list.length - 1) {
          return item
        }
      })
      return lastPromo[0]
    },
    allButLastPromos() {
      var allButLast = this.list.filter((item, index) => {
        if (index !== this.list.length - 1) {
          return item
        }
      })
      return allButLast
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
    <div style="max-width: 400px;">
      <vs-mobile-nav-promo-list
        :list="promoList"
      />
    </div>
  ```
</docs>
