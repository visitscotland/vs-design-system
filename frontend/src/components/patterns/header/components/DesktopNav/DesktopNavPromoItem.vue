<template>
    <Component
        :is="type"
        data-test="desktop-nav-list-item"
        class="vs-desktop-nav__list-item col-4 col-xl-3 vs-desktop-nav__list-item--level2 mb-3"
    >
        <a
            data-test="promo-link"
            class="vs-promo-item__link"
            :href="href"
        >
            <div class="vs-promo-item__wrapper">
                <span class="vs-promo-item__link-title">{{ title }}</span>
                <div class="vs-promo-item__icon-wrapper">
                    <VsIcon
                        data-test="svg-icon"
                        name="reverse-arrow"
                        size="xs"
                        variant="primary"
                    />
                </div>
            </div>
            <div
                class="vs-promo-item__image-wrapper"
                v-if="imageLink"
            >
                <img
                    data-test="promo-image"
                    class="lazyload vs-promo-item__image"
                    :data-srcset="imageLink"
                    :data-src="imageLink"
                    :alt="title"
                    :src="imageLink"
                >
            </div>
        </a>
    </Component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"

export default {
    name: "VsDesktopNavPromoItem",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsIcon,
    },
    props: {
        /**
         * The html element name used for the component
         */
        type: {
            type: String,
            default: "li",
        },
        href: {
            type: String,
            default: "",
        },
        isExternal: {
            type: Boolean,
        },
        title: {
            type: String,
            default: "",
        },
        buttonText: {
            type: String,
            default: "",
        },
        description: {
            type: String,
            default: "",
        },
        imageLink: {
            type: String,
            default: "",
        },
    },
    data() {
        return {
        }
    },
}
</script>

<style lang="scss" scoped>
@import "../../styles/placeholders";

.vs-promo-item__wrapper {
  border-bottom: 1px solid $color-gray-tint-8;
}

.vs-promo-item__link {
  display: block;
  height: 200px;
  position: relative;
  width: 100%;
  transition: box-shadow 250ms ease-in-out;

  &:hover,
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
  bottom: 0;
  padding: 10px 10px 14px 10px;
  position: absolute;
  right: 0;
}
</style>

<docs>
  ```jsx

  <ul style="list-style-type: none; margin: 0; max-width: 400px;">
    <vs-desktop-nav-promo-item
      :href="header.mainNav[2].promoItem.href"
      :is-external="header.mainNav[2].promoItem.isExternal"
      :title="header.mainNav[2].promoItem.title"
      :button-text="header.mainNav[2].promoItem.buttonText"
      :description="header.mainNav[2].promoItem.description"
      :image-link="header.mainNav[2].promoItem.imageLink"
    />
  </ul>
  ```
</docs>
