<template>
  <component :is="type" class="vs-promo-panel__link" :href="item.href">
    <span class="vs-promo-panel__span">
      {{ item.title }}
      <span class="vs-promo-panel__icon-wrapper">
        <vs-svg path="icons/chevron-right" height="10" fill="#FFFFFF" />
      </span>
    </span>
    <div class="vs-promo-panel__image-wrapper" v-if="item.imageLink">
      <img
        class="lazyload vs-promo-panel__image"
        :data-srcset="item.imageLink"
        :data-src="item.imageLink"
        :alt="item.title"
        :src="item.imageLink"
      />
    </div>
  </component>
</template>

<script>
import VsSvg from "../../elements/svg/Svg"

export default {
  name: "VsMainNavPromoPanel",
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
@import "styles/placeholders";

.vs-promo-panel {
  &__link {
    color: $color-total-eclipse;
    display: block;
    position: relative;
    overflow: hidden;
    height: 200px;
    width: 100%;
  }

  &__span {
    position: absolute;
    bottom: 0;
    display: flex;
    align-items: bottom;
    left: 0;
    background-color: $color-white;
    z-index: 1;
    padding: 1rem;
    width: 50%;
  }

  &__image,
  &__image-wrapper {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 100%;
    height: 100%;
    background-size: cover;
    background-position: 50% 50%;
    transform: translate(-50%, -50%);
    object-fit: cover;
  }

  &__icon-wrapper {
    background-color: $color-thistle-pink;
    position: relative;
    border-radius: 50%;
    display: block;
    width: 1rem;
    height: 1rem;

    svg {
      top: 50%;
      left: 50%;
      position: absolute;
      transform: translate(-50%, -50%);
    }
  }
}
</style>

<docs>
  ```jsx
    <vs-main-nav-promo-panel
      item={
          title: 'VisitScotland iCentres',
          href: 'https://www.visitscotland.com/about/practical-information/vic/',
          isExternal: false,
          isActive: false,
          buttonText: 'Read more',
          trackingID: 1,
          description: 'Expert local knowledge, gifts and inspiration.',
          imageLink: 'https://cimg.visitscotland.com/cms-images/navigation/travel-rc?size=md'
      }
    />
  ```
</docs>
