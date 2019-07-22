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
  <div>
    <vs-main-nav-promo-list
      :list="[
      {
        title: 'Romantic',
        href: 'https://www.visitscotland.com/inspiration/romantic',
        isExternal: false,
        isActive: false,
        trackingID: 1,
        imageLink: 'https://cimg.visitscotland.com/cms-images/about/couple-beach-south-queensferry-walk?size=sm'
      },
      {
        title: 'Island hopping',
        href: 'https://www.visitscotland.com/inpiration/island-hopping',
        isExternal: false,
        isActive: false,
        trackingID: 1,
        imageLink: 'https://cimg.visitscotland.com/cms-images/destinations/argyll-and-the-isles/kiloran-bay?size=sm'
      },
      {
        title: 'Family',
        href: 'https://www.visitscotland.com/inpiration/family',
        isExternal: false,
        isActive: false,
        trackingID: 1,
        imageLink: 'https://cimg.visitscotland.com/cms-images/itineraries/loch-insh-family-paddleboarding?size=sm'
      },
      {
        title: 'Farm stays',
        href: 'https://www.visitscotland.com/inpiration/farm-stays',
        isExternal: false,
        isActive: false,
        trackingID: 1,
        imageLink: 'https://cimg.visitscotland.com/cms-images/attractions/cattle-grazing-cliffs-westray?size=md'
      },
      {
        title: 'Wellness',
        href: 'https://www.visitscotland.com/inpiration/wellness',
        isExternal: false,
        isActive: false,
        trackingID: 1,
        imageLink: 'https://cimg.visitscotland.com/cms-images/holidays/outdoor-spa-gleneagles?size=md'
      }
    ]"
    />
  </div>
  ```
</docs>
