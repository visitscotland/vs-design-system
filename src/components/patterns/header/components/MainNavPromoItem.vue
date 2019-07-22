<template>
  <component :is="type" class="vs-promo-item__link" :href="item.href">
    <div class="vs-promo-item__wrapper">
      <span class="vs-promo-item__link-title"> {{ item.title }} </span>
      <div class="vs-promo-item__icon-wrapper">
        <vs-svg path="icons/chevron-right" height="10" fill="#FFFFFF" />
      </div>
    </div>
    <div class="vs-promo-item__image-wrapper" v-if="item.imageLink">
      <img
        class="lazyload vs-promo-item__image"
        :data-srcset="item.imageLink"
        :data-src="item.imageLink"
        :alt="item.title"
        :src="item.imageLink"
      />
    </div>
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"

export default {
  name: "VsMainNavPromoItem",
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
      default: "a",
    },
    item: {
      type: Object,
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

.vs-promo-item {
  &__link {
    display: block;
    position: relative;
    height: 200px;
    width: 100%;

    &:focus {
      @extend %focus-pink;
    }
  }

  &__link-title {
    display: inline-flex;
    color: $color-total-eclipse;
    font-weight: $font-weight-light;
    font-size: 1.125rem;
    line-height: 1.25rem;
  }

  &__image,
  &__image-wrapper {
    background-position: 50% 50%;
    background-size: cover;
    height: 200px;
    left: 50%;
    object-fit: cover;
    position: absolute;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 100%;
  }

  &__wrapper {
    position: absolute;
    background-color: $color-white;
    bottom: 0;
    left: 0;
    padding: 1rem 3rem 1rem 1rem;
    z-index: 1;
  }

  &__icon-wrapper {
    right: 1rem;
    bottom: 1rem;
    position: absolute;
    padding: 10px;

    svg {
      position: absolute;
      bottom: 5px;
      right: 6px;
    }

    &::before {
      background-color: $color-thistle-pink;
      border-radius: 50%;
      bottom: 0;
      content: "";
      display: block;
      height: 20px;
      right: 0;
      position: absolute;
      width: 20px;
      bottom: 0;
    }
  }
}
</style>

<docs>
  ```jsx
  <div>
    <vs-main-nav-promo-item
      :item="{
        title: 'Unusual Accommodation',
        href: 'https://www.visitscotland.com/accommodation/unusual-places-to-stay/',
        isExternal: false,
        isActive: false,
        buttonText: 'Read more',
        description: 'Why not stay in a castle, a lighthouse or on a working farm?',
        imageLink: 'https://cimg.visitscotland.com/cms-images/accommodation/accommodation-new/glamping-domes-sauchope-holiday-park?size=xs'
    }"
    />
  </div>
  ```
</docs>
