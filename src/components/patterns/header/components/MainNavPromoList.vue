<template>
  <component :is="type" class="vs-promo__list">
    <li class="vs-promo__list-item" v-for="(item, index) in list" :key="index">
      <VsMainNavPromoItem v-if="item" :item="item" />
    </li>
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"

export default {
  name: "VsMainNavPromoList",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
  data() {
    return {}
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "div",
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
      list=[
        {
            title: 'Romantic',
            href: 'https://www.visitscotland.com/inspiration/romantic',
            isExternal: false,
            isActive: false,
            trackingID: 1,
            imageLink: ''
        },
        {
            title: 'Island hopping',
            href: 'https://www.visitscotland.com/inpiration/island-hopping',
            isExternal: false,
            isActive: false,
            trackingID: 1,
            imageLink: ''
        },
        {
            title: 'Family',
            href: 'https://www.visitscotland.com/inpiration/family',
            isExternal: false,
            isActive: false,
            trackingID: 1,
            imageLink: ''
        },
        {
            title: 'Farm stays',
            href: 'https://www.visitscotland.com/inpiration/farm-stays',
            isExternal: false,
            isActive: false,
            trackingID: 1,
            imageLink: ''
        },
        {
            title: 'Wellness',
            href: 'https://www.visitscotland.com/inpiration/wellness',
            isExternal: false,
            isActive: false,
            trackingID: 1,
            imageLink: ''
        }
    ]
    />
  </div>
  ```
</docs>
