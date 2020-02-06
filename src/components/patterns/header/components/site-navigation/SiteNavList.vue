<template>
    <ul
        class="vs-site-nav__list list-unstyled"
        :class="{
            'vs-site-nav__list--open': isOpen,
            ['vs-site-nav__list--level' + level]: true,
        }"
        v-hand-down-focus="firstNavItem"
    >
        <slot />
    </ul>
</template>

<script>
import { get } from "lodash"

import HandDownFocus from "@/directives/hand-down-focus.js"

/**
 * The nearest ancestor with position relative
 */
export default {
    name: "VsSiteNavList",
    status: "prototype",
    release: "0.1.0",
    directives: {
        HandDownFocus,
    },
    props: {
        isOpen: {
            type: Boolean,
        },
        level: {
            type: Number,
            required: true,
        },
    },
    computed: {
        firstNavItem() {
            return get(this.$slots, "default.0")
        },
    },
    created() {
        console.log("site nav created")
    },
    mounted() {
        console.log("site nav mounted")
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "../../styles/placeholders";

// .collapse:not(.show) {
//   display: none;
//   opacity: 0;
// }

// .collapsing {
//   position: relative;
//   height: 0;
//   overflow: hidden;
//   opacity: 1;
//   transition: all 50ms ease-in-out;
// }

.vs-site-nav__list {
    width: 100vw;

    @include media-breakpoint-up(lg) {
        width: 100%;
        display: flex;
    }
    // z-index: 2;

    &.vs-site-nav__list--level1 {
        display: none;
        position: absolute;
        top: 39px;
        left: 0;
        height: 56px;
        margin-bottom: 0;

        &.vs-site-nav__list--open {
            display: initial;
        }

        @include media-breakpoint-up(md) {
            top: 56px;
        }

        @include media-breakpoint-up(lg) {
            position: initial;
            top: 92px;
            display: flex;

            &.vs-site-nav__list--open {
                display: flex;
            }
        }
    }

    &.vs-site-nav__list--level2 {
        @include media-breakpoint-up(lg) {
            position: absolute;
            top: 56px;
            width: 100vw;
            left: 0;
            box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
        }
    }
}
</style>

<docs>
  ```jsx

  ```
</docs>
