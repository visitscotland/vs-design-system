<template>
    <b-button
        :variant="variant"
        :href="href"
        :tabindex="tabindex"
        class="text-uppercase d-flex align-items-center justify-content-center"
        :class="{
            [animateClass]: animateClass,
        }"
        @click="animateClass ? animateHandler() : null"
        :size="size"
        v-bind="$attrs"
    >
        <slot />
    </b-button>
</template>
<script>
import { BButton } from "bootstrap-vue"

/**
 * TODO: Document usage.
 *
 * NOTE: When listening for the @click event you need to use the `native` modifier
 * in order to listen for the underlying `button`s native event. e.g.
 *
 * <vs-button @click.native="handler">Click me</vs-button>
 */

export default {
    name: "VsButton",
    status: "prototype",
    release: "0.0.1",
    components: {
        BButton,
    },
    props: {
        /**
         * Use this option to render the button as an anchor element with the given href.
         */
        href: {
            type: String,
            default: null,
        },
        /**
         * Tab index value - this is needed as tabindex attribute is sometimes stripped
         * from the button on first update with nested components
         */
        tabindex: {
            type: String,
            default: null,
        },
        /**
         * Style variation to give additional meaning.
         * `primary, secondary, success, danger, warning, info, light, dark, transparent`
         */
        variant: {
            type: String,
            default: "primary",
            validator: value => {
                return value.match(
                    /(primary|secondary|success|danger|warning|info|light|dark|transparent)/
                )
            },
        },
        /**
         * Style the button size.
         * `sm, md, lg`
         */
        size: {
            type: String,
            default: "md",
            validator: value => {
                return value.match(/(sm|md|lg)/)
            },
        },
        /**
         * By default, buttons have an animation behaviour on click. To disable, add an animate=false property
         */
        animate: {
            type: Boolean,
            default: true,
        },
    },
    computed: {
        animateClass() {
            return this.animate ? "btn-animate" : null
        },
    },
    methods: {
        animateHandler() {
            this.$el.classList.add("bubble")
            setTimeout(() => {
                this.$el.classList.remove("bubble")
            }, 1000)
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/buttons";

.btn {
    font-family: $font-family-base;
    font-weight: $font-weight-light;
    transition: $transition-base;
    letter-spacing: 2px;
    position: relative;
    overflow: hidden;

    .btn-dark {
        &:hover {
            background-color: $color-gray-shade-5;
        }
    }

    &.btn-light,
    &.btn-transparent {
        &:focus {
            box-shadow: 0 0 0 0.2rem rgba(0, 0, 0, 0.25);
        }

        &::after {
            background: rgba(0, 0, 0, 0.2);
        }
    }
}

.btn-animate {
    @keyframes bubble {
        0% {
            transform: scale(0, 0);
            opacity: 1;
        }
        100% {
            opacity: 0;
            transform: scale(100, 100);
        }
    }

    &::after {
        background: rgba(255, 255, 255, 0.1);
        border-radius: 50%;
        bottom: 0;
        content: "";
        height: 5px;
        opacity: 0;
        position: absolute;
        right: 0;
        transform-origin: 50% 50%;
        transform: scale(1, 1) translate(-50%);
        width: 5px;
    }

    &.bubble::after {
        animation: bubble 500ms ease-in-out;
    }
}
</style>

<docs>
```jsx
    <h4>Types</h4>
    <bs-wrapper class="d-flex flex-wrap mb-4">
      <vs-button class="mr-2 mb-2">Button</vs-button>
      <vs-button :animate=false class="mr-2 mb-2">Button with no animation</vs-button>
      <vs-button class="mr-2 mb-2" href="https://www.visitscotland.com">Link</vs-button>
    </bs-wrapper>
    <h4>Variants</h4>
    <bs-wrapper class="d-flex flex-wrap mb-4">
      <vs-button variant="primary" class="mr-2 mb-2">Primary (default)</vs-button>
      <vs-button variant="secondary" class="mr-2 mb-2">Secondary</vs-button>
      <vs-button variant="success" class="mr-2 mb-2">Success</vs-button>
      <vs-button variant="danger" class="mr-2 mb-2">Danger</vs-button>
      <vs-button variant="warning" class="mr-2 mb-2">Warning</vs-button>
      <vs-button variant="info" class="mr-2 mb-2">Info</vs-button>
      <vs-button variant="light" class="mr-2 mb-2">Light</vs-button>
      <vs-button variant="dark" class="mr-2 mb-2">Dark</vs-button>
      <vs-button variant="transparent" class="mr-2 mb-2">Transparent</vs-button>
    </bs-wrapper>
    <h4>Outline Color Variants</h4>
    <bs-wrapper class="d-flex flex-wrap mb-4">
      <vs-button variant="outline-primary" class="mr-2 mb-2">Primary</vs-button>
      <vs-button variant="outline-secondary" class="mr-2 mb-2">Secondary</vs-button>
      <vs-button variant="outline-success" class="mr-2 mb-2">Success</vs-button>
      <vs-button variant="outline-danger" class="mr-2 mb-2">Danger</vs-button>
      <vs-button variant="outline-warning" class="mr-2 mb-2">Warning</vs-button>
      <vs-button variant="outline-info" class="mr-2 mb-2">Info</vs-button>
      <vs-button variant="outline-light" class="mr-2 mb-2">Light</vs-button>
      <vs-button variant="outline-dark" class="mr-2 mb-2">Dark</vs-button>
      <vs-button variant="outline-transparent" class="mr-2 mb-2">Transparent</vs-button>
    </bs-wrapper>
    <h4>Sizes</h4>
    <bs-wrapper>
      <vs-button class="mr-2 mb-2" size="sm">Small</vs-button>
      <vs-button class="mr-2 mb-2" size="md">Medium</vs-button>
      <vs-button class="mr-2 mb-2" size="lg">Large</vs-button>
      <vs-button block class="mr-2 mb-2" size="md">Block</vs-button>
    </bs-wrapper>

    <h4>Disabled States</h4>
    <bs-wrapper class="d-flex flex-wrap mb-4">
      <vs-button disabled class="mr-2 mb-2" variant="primary" size="md">Disabled primary</vs-button>
      <vs-button disabled class="mr-2 mb-2" variant="secondary" size="md">Disabled primary pink</vs-button>
      <vs-button disabled class="mr-2 mb-2" variant="success" size="md">Disabled success</vs-button>
      <vs-button disabled class="mr-2 mb-2" variant="danger" size="md">Disabled danger</vs-button>
      <vs-button disabled class="mr-2 mb-2" variant="warning" size="md">Disabled warning</vs-button>
      <vs-button disabled class="mr-2 mb-2" variant="info" size="md">Disabled info</vs-button>
      <vs-button disabled class="mr-2 mb-2" variant="light" size="md">Disabled light</vs-button>
      <vs-button disabled class="mr-2 mb-2" variant="dark" size="md">Disabled dark</vs-button>
      <vs-button disabled class="mr-2 mb-2" variant="transparent" size="md">Disabled transparent</vs-button>
    </bs-wrapper>

```
</docs>
