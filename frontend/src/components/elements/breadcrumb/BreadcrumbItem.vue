<template>
    <BBreadcrumbItem
        class="vs-breadcrumb-item"
        :active="active"
        :aria-current="ariaCurrent"
        :href="href"
        :text="text"
        v-bind="$attrs"
    />
</template>

<script>
import { BBreadcrumbItem } from "bootstrap-vue"

/**
 * https://bootstrap-vue.js.org/docs/components/breadcrumb#comp-ref-b-breadcrumb-item
 */

export default {
    name: "VsBreadcrumbItem",
    status: "prototype",
    release: "0.0.1",
    components: {
        BBreadcrumbItem,
    },
    props: {
        /**
         * The breadcrumb anchor link href.
         */
        href: {
            type: String,
            default: "",
        },
        /**
         * The active breadcrumb item (last item). By default, BootstrapVue renders as a span.
         */
        active: {
            type: Boolean,
        },
        /**
         * The breadcrumb aria-current attribute.
         */
        ariaCurrent: {
            type: String,
            default: "page",
        },
        /**
         * The breadcrumb text.
         */
        text: {
            type: String,
            default: "",
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/breadcrumb";

.vs-breadcrumb-item {
    color: $color-base-text;
    font-size: $font-size-base;
    font-weight: $font-weight-normal;

    @include media-breakpoint-down(md) {
        font-size: $font-size-sm;
    }

    @include media-breakpoint-down(sm) {
        display: none;

        &:last-child,
        &:nth-last-child(2) {
            display: block;
            max-width: 75%;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        &:nth-last-child(2) {
            padding: 0;

            &::before {
                content: "";
                padding: 0;
            }
        }
    }
}

.vs-breadcrumb-item a {
    color: $color-base-text;
}

.vs-breadcrumb-item:last-child {
    color: $color-base-text;
    font-weight: $font-weight-light;
}
</style>

<docs>
  ```jsx
  <vs-breadcrumb-item href="#" text="Inactive"></vs-breadcrumb-item>
  <vs-breadcrumb-item active href="#" text="Active"></vs-breadcrumb-item>
  ```
</docs>
