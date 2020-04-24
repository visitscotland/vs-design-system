<template>
    <Component
        data-test="mobile-promo-list-item"
        :is="type"
        class="vs-mobile-nav__list-item vs-mobile-nav__list-item--level2"
    >
        <a
            data-test="mobile-promo-list-link"
            class="vs-promo-item__link"
            :href="href"
        >
            <div class="vs-promo-item__wrapper">
                <span class="vs-promo-item__link-title">{{ title }}</span>
                <div class="vs-promo-item__icon-wrapper">
                    <VsIcon
                        data-test="mobile-promo-arrow-icon"
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
                    data-test="mobile-promo-image"
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
    name: "VsMobileNavPromoItem",
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
    data() {
        return {
        }
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "../../styles/placeholders";

.vs-promo-item__wrapper {
    border-bottom: 1px solid $color-gray-tint-8;
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
    bottom: 0;
    padding: 10px 10px 14px 10px;
    position: absolute;
    right: 0;
}
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
      :image-link="header.mainNav[2].promoItem.imageLink"
    />
  </div>
  ```
</docs>
