<template>
    <VsSiteNavListItem
        data-test="site-promo-list-item"
        class="vs-site-nav__list-promo-item position-relative"
        v-bind="$attrs"
        :style="backgroundImgStyle"
        :class="{
            'vs-site-nav__list-promo-item--image': isFirstSiblingWithImage,
        }"
    >
        <div class="vs-site-nav__list-promo-item-image-copy">
            <slot />

            <VsIcon
                v-if="isFirstSiblingWithImage"
                data-test="mobile-promo-arrow-icon"
                name="reverse-arrow"
                size="sm"
                variant="primary"
                :padding="0"
            />
        </div>
    </VsSiteNavListItem>
</template>

<script>
import { get, find } from "lodash"

import VsIcon from "@components/elements/icon"
import VsSiteNavListItem from "./SiteNavListItem"

export default {
    name: "VsSiteNavListPromoItem",
    status: "prototype",
    release: "0.1.0",
    components: {
        VsIcon,
        VsSiteNavListItem,
    },
    props: {
        imageHref: {
            type: String,
        },
    },
    computed: {
        level() {
            return this.$parent.level
        },
        backgroundImgStyle() {
            if (!this.isFirstSiblingWithImage) {
                return false
            }

            return {
                backgroundImage: `url(${this.imageHref})`,
            }
        },
        isFirstSiblingWithImage() {
            if (!this.imageHref) {
                return false
            }

            const firstSibling = find(this.$parent.$children, (child) => child.$options.name === this.$options.name && child.imageHref)

            return this._uid === get(firstSibling, "_uid")
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../../styles/placeholders";

.vs-site-nav__list-promo-item {
    &.vs-site-nav__list-promo-item--image ::v-deep {
        height: 168px;
        background-position: 50% 50%;
        background-size: cover;
        align-items: flex-end;

        .vs-site-nav__link {
            height: 100%;
            padding: 0;
            align-items: flex-end;

            &:after {
                display: none;
            }
        }

        .vs-site-nav__list-promo-item-image-copy {
            height: 75px;
            padding: 0.5rem;
            padding-right: 2rem;
            line-height: 22px;
            font-size: 1.125rem;
            font-weight: lighter;
            background-color: white;
            position: relative;

            .icon {
                position: absolute;
                right: 10px;
                bottom: 10px;
            }
        }
    }

    // .vs-site-nav__list-item {
    //   display: block;
    // }

    // .vs-site-nav__list-promo-item-image {
    //   position: absolute;
    //   top: 0;
    //   right: 0;
    //   bottom: 0;
    //   left: 0;
    //   background-size: cover;
    //   object-fit: cover;
    // }
}

// .vs-promo-item__link {
//   display: block;
//   height: 200px;
//   position: relative;
//   width: 100%;

//   &:focus {
//     @extend %focus-pink;
//   }
// }

// .vs-promo-item__link-title {
//   color: $color-base-text;
//   display: inline-flex;
//   font-size: 1.125rem;
//   font-weight: $font-weight-light;
//   line-height: 1.25rem;
// }

// .vs-site-nav__list-promo-item-image {
//   background-position: 50% 50%;
//   background-size: cover;
//   height: 200px;
//   left: 50%;
//   object-fit: cover;
//   position: absolute;
//   top: 50%;
//   transform: translate(-50%, -50%);
//   width: 100%;
// }

// .vs-promo-item__wrapper {
//   background-color: $color-white;
//   bottom: 0;
//   left: 0;
//   padding: 1rem 3rem 1rem 1rem;
//   position: absolute;
//   z-index: 1;
// }

// .vs-promo-item__icon-wrapper {
//   bottom: 0;
//   padding: 10px 10px 14px 10px;
//   position: absolute;
//   right: 0;
// }
</style>

<docs>
  ```jsx

  <div style="max-width: 400px;">
    <vs-mobile-nav-promo-item
      :href="header.mainNav[2].promoItem.href"
      :is-external="header.mainNav[2].promoItem.isExternal"
      :title="header.mainNav[2].promoItem.title"
      :button-text="header.mainNav[2].promoItem.buttonText"
      :description="header.mainNav[2].promoItem.description"
      :image-link="header.mainNav[2].promoItem.imageHref"
    />
  </div>
  ```
</docs>
