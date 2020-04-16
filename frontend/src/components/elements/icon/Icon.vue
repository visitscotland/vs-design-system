<template>
    <vs-svg
        :path="path"
        :class="{
            icon: true,
            ['icon-' + size]: true,
            ['icon-' + formattedName]: true,
            ['icon-' + variant]: variant,
            'icon-reverse': reverse,
        }"
        v-bind="$attrs"
    />
</template>

<script>
import VsSvg from "../svg"
import designTokens from "@/assets/tokens/tokens.raw.json"
import { get } from "lodash"

const iconPath = "icons/"

/**
 * Icons are used to visually communicate core parts of the product and
 * available actions. They can act as wayfinding tools to help users more
 * easily understand where they are in the product.
 *
 * Our icons come in specific sizes - xxs, xs, sm, md, lg and xl - and can be any colour,
 * including any of the theme colours.
 */
export default {
    name: "VsIcon",
    status: "prototype",
    release: "0.1.0",
    components: { VsSvg },
    data() {
        return {
            iconLookup: [
                {
                    key: "accessparkdrop",
                    value: "facility-accessparkdrop",
                },
                {
                    key: "acco",
                    value: "product-accommodation",
                },
                {
                    key: "acti",
                    value: "product-activities",
                },
                {
                    key: "attr",
                    value: "product-attractions",
                },
                {
                    key: "audioloop",
                    value: "facility-audioloop",
                },
                {
                    key: "cate",
                    value: "product-food-and-drink",
                },
                {
                    key: "cities",
                    value: "city",
                },
                {
                    key: "cycling",
                    value: "cycle",
                },
                {
                    key: "dsblaccess",
                    value: "facility-dsblaccess",
                },
                {
                    key: "even",
                    value: "product-events",
                },
                {
                    key: "familyev",
                    value: "family",
                },
                {
                    key: "filmev",
                    value: "film-tv",
                },
                {
                    key: "parking",
                    value: "facility-parking",
                },
                {
                    key: "petswelcom",
                    value: "facility-petswelcom",
                },
                {
                    key: "wifi",
                    value: "facility-wifi",
                },
                {
                    key: "public",
                    value: "public-transport",
                },
                {
                    key: "reta",
                    value: "product-shopping",
                },
                {
                    key: "spahealth",
                    value: "wellness",
                },
                {
                    key: "walking",
                    value: "walk",
                },
            ],
        }
    },
    props: {
        /**
         * The name of the icon to display.
         */
        name: {
            required: true,
            default: "search",
        },
        /**
         * The fill color of the SVG icon.
         * `primary, secondary, success, danger, warning, info, light, dark, reverse-white, primary-purple, secondary-teal`
         */
        variant: {
            type: String,
            default: null,
            validator: value => {
                return value.match(
                    /(primary|secondary|success|danger|warning|info|light|dark|reverse-white)/
                )
            },
        },
        /**
         * The size of the icon. Defaults to medium.
         * `small, medium, large`
         */
        size: {
            type: String,
            default: "md",
            validator: value => {
                return value.match(/(xxs|xs|sm|md|lg|xl)/)
            },
        },
        /**
         * Whether to reverse the icon's background and
         * fill colours
         */
        reverse: {
            type: Boolean,
        },
    },
    computed: {
        path() {
            return iconPath + this.formattedName
        },
        dimension() {
            return get(designTokens, "props.icon_size_" + this.size + ".value", "40px")
        },
        formattedName() {
            /*
             * To facilitate more readable icon names and organise / group icons within the design system
             * there is a lookup for how keys may be passed from the backend
             */
            var formattedNameLookup = this.iconLookup.find(({ key }) => key === this.name)

            return formattedNameLookup !== undefined ? formattedNameLookup.value : this.name
        },
    },
}
</script>

<style lang="scss" scoped>
// @include reset;

@mixin icon-dimensions($size, $dimension) {
}

$sizes: (
    xxs: $icon-size-xxs,
    xs: $icon-size-xs,
    sm: $icon-size-sm,
    md: $icon-size-md,
    lg: $icon-size-lg,
    xl: $icon-size-xl,
);

$variants: (
    primary: $color-theme-primary,
    secondary: $color-theme-secondary,
    success: $color-theme-success,
    danger: $color-theme-danger,
    warning: $color-theme-warning,
    info: $color-theme-info,
    light: $color-theme-light,
    dark: $color-theme-dark,
    reverse-white: $color-white,
    primary-purple: $color-theme-primary-purple,
    secondary-teal: $color-theme-secondary-teal,
);

.icon {
    fill: $color-black;
    overflow: visible;

    &.icon-reverse {
        background-color: $color-black;
        fill: $color-white;
    }

    @each $size in map-keys($sizes) {
        $this-size: map-get($sizes, $size);

        &.icon-#{$size} {
            height: $this-size;
            width: $this-size;
            padding: 0;

            &.icon-reverse {
                border-radius: $this-size / 2;
            }
        }
    }

    @each $variant in map-keys($variants) {
        &.icon-#{$variant} {
            fill: map-get($variants, $variant);

            &.icon-reverse {
                fill: $color-white;
                background: map-get($variants, $variant);
            }
        }
    }
}
</style>

<docs>
  ```jsx
  <div>
    <bs-wrapper class="row mb-5">    
      <bs-wrapper class="col">
        <h3>Default</h3>
        <vs-icon name="search" />
      </bs-wrapper>
    </bs-wrapper>
    
    <bs-wrapper class="row mb-5">    
      <bs-wrapper class="col">
        <h3>Variant</h3>
        <vs-icon name="user" variant="primary" />
        <vs-icon name="user" variant="secondary" />
        <vs-icon name="user" variant="success" />
        <vs-icon name="user" variant="warning" />
        <vs-icon name="user" variant="info" />
        <vs-icon name="user" variant="danger" />
        <vs-icon name="user" variant="dark" />
        <vs-icon name="user" variant="light" />
      </bs-wrapper>
    </bs-wrapper>
   
    <bs-wrapper class="row mb-5">    
      <bs-wrapper class="col">
        <h3>Reverse</h3>
        <vs-icon name="favourite" reverse />
        <vs-icon name="favourite" reverse variant="primary" />
        <vs-icon name="favourite" reverse variant="secondary" />
        <vs-icon name="favourite" reverse variant="success" />
        <vs-icon name="favourite" reverse variant="warning" />
        <vs-icon name="favourite" reverse variant="info" />
        <vs-icon name="favourite" reverse variant="danger" />
        <vs-icon name="favourite" reverse variant="dark" />
        <vs-icon name="favourite" reverse variant="light" />
      </bs-wrapper>
    </bs-wrapper>

    <bs-wrapper class="row mb-5">    
      <bs-wrapper class="col">
        <h3>Size</h3>

        <bs-wrapper class="d-flex">
          <bs-wrapper class="d-flex flex-column mr-3 align-items-center">
            <h4>xxs</h4>
            <vs-icon name="favourite" size="xxs" />
          </bs-wrapper>

          <bs-wrapper class="d-flex flex-column mr-3 align-items-center">
            <h4>xs</h4>
            <vs-icon name="favourite" size="xs" />
          </bs-wrapper>
          
          <bs-wrapper class="d-flex flex-column mr-3 align-items-center">
            <h4>sm</h4>
            <vs-icon name="favourite" size="sm" />
          </bs-wrapper>

          <bs-wrapper class="d-flex flex-column mr-3 align-items-center">
            <h4>md</h4>
            <vs-icon name="favourite" size="md" />
          </bs-wrapper>
          
          <bs-wrapper class="d-flex flex-column mr-3 align-items-center">
            <h4>lg</h4>
            <vs-icon name="favourite" size="lg" />
          </bs-wrapper>
          
          <bs-wrapper class="d-flex flex-column mr-3 align-items-center">
            <h4>xl</h4>
            <vs-icon name="favourite" size="xl" />
          </bs-wrapper>
        </bs-wrapper>
      </bs-wrapper>
    </bs-wrapper>
  </div>
  ```
</docs>
