<template>
  <component :is="type" class="vs-promo__list">
    <li class="vs-promo__list-item" v-for="(item, index) in list" :key="index">
      <VsMainNavPromoItem v-if="item && index === 0" :item="item" />
      <VsMainNavListItem v-if="item && index !== 0" :item="item" :level="2" />
    </li>
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"
import VsMainNavPromoItem from "./MainNavPromoItem"
import VsMainNavListItem from "./MainNavListItem"

export default {
  name: "VsMainNavPromoList",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsSvg,
    VsMainNavPromoItem,
    VsMainNavListItem,
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
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../styles/placeholders";

.vs-promo {
  &__list {
    @extend %list-reset;
  }
}
</style>

<docs>
  ```jsx
    const mainNav = require("../../../../assets/fixtures/mainNav.json")
    const promoList = mainNav[4].promoList
    <div>
      <vs-main-nav-promo-list
        :list="promoList"
      />
    </div>
  ```
</docs>
