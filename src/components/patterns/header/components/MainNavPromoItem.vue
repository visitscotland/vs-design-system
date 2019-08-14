<template>
  <component :is="type" class="vs-promo-item__link" :href="href">
    <div class="vs-promo-item__wrapper">
      <span class="vs-promo-item__link-title">{{ title }}</span>
      <div class="vs-promo-item__icon-wrapper">
        <vs-svg path="icons/chevron-right" height="10" fill="#FFFFFF" />
      </div>
    </div>
    <div class="vs-promo-item__image-wrapper" v-if="imageLink">
      <img
        class="lazyload vs-promo-item__image"
        :data-srcset="imageLink"
        :data-src="imageLink"
        :alt="title"
        :src="imageLink"
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
    href: {
      type: String,
    },
    isExternal: {
      type: Boolean,
    },
    title: {
      type: String,
    },
    buttonText: {
      type: String,
    },
    description: {
      type: String,
    },
    imageLink: {
      type: String,
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

.vs-promo-item__wrapper {
  border-bottom: 1px solid $gray-tint-8;
}

.vs-promo-item__link {
  display: block;
  height: 200px;
  position: relative;
  width: 100%;

  &:focus {
    @extend %focus-pink;
  }
}

.vs-promo-item__link-title {
  color: $color-base-text;
  display: inline-flex;
  font-size: 1.125rem;
  font-weight: $font-weight-light;
  line-height: 1.25rem;
}

.vs-promo-item__image,
.vs-promo-item__image-wrapper {
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

.vs-promo-item__wrapper {
  background-color: $color-white;
  bottom: 0;
  left: 0;
  padding: 1rem 3rem 1rem 1rem;
  position: absolute;
  z-index: 1;
}

.vs-promo-item__icon-wrapper {
  bottom: 1rem;
  padding: 10px;
  position: absolute;
  right: 1rem;

  svg {
    bottom: 5px;
    position: absolute;
    right: 6px;
  }

  &::before {
    background-color: $color-pink;
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
</style>

<docs>
  ```jsx

  const mainNav = require("../../../../assets/fixtures/mainNav.json")
  const promoItem = mainNav[2].promoItem
  <div>
    <vs-main-nav-promo-item
      :href="promoItem.href"
      :is-external="promoItem.isExternal"
      :title="promoItem.title"
      :button-text="promoItem.buttonText"
      :description="promoItem.description"
      :image-link="promoItem.imageLink"
    />
  </div>
  ```
</docs>
