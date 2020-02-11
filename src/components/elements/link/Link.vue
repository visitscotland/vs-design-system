<template>
    <b-link
        class="vs-link"
        :class="[variant, { 'vs-link--external': external }]"
        :href="href"
        :target="external ? '_blank' : '_self'"
    >
        <slot />
        <vs-icon v-if="external" name="external-link" variant="primary" size="xs" :padding="2" />
    </b-link>
</template>

<script>
import { BLink } from "bootstrap-vue"
import VsIcon from "@components/elements/icon/Icon"

/**
 * TODO: Document usage.
 */

export default {
    name: "VsLink",
    status: "prototype",
    release: "0.0.1",
    components: {
        BLink,
        VsIcon,
    },
    props: {
        /**
         * The URL the link will point to
         */
        href: {
            type: String,
            default: null,
        },
        /**
         * Option to create external link which will open URL in blank target and add icon
         */
        external: {
            type: Boolean,
            default: false,
        },
        /**
         * Option to choose a pre-defined style variant
         */
        variant: {
            type: String,
            default: "primary",
        },
    },
}
</script>

<style lang="scss" scoped>
.vs-link {
    &.primary {
        color: $color_pink;

        &:hover {
            color: $color-gray-shade-7;
        }

        &.vs-link--external {
            .icon {
                fill: $color_pink;
            }

            &:hover {
                .icon {
                    fill: $color-gray-shade-7;
                }
            }
        }
    }

    &.dark {
        color: $color_yellow;

        &:hover {
            color: $color_white;
        }

        &.vs-link--external {
            .icon {
                fill: $color_yellow;
            }

            &:hover {
                .icon {
                    fill: $color_white;
                }
            }
        }
    }
}
</style>

<docs>
```jsx
    <vs-link class="mb-2" href="#foo">
        Link
    </vs-link>

    <vs-link class="ml-6 mb-2" external href="https://www.visitscotland.com">
        External Link
    </vs-link>

    <vs-link variant="dark" class="ml-6 mb-2" href="#foo">
        Dark Link
    </vs-link>

    <vs-link variant="dark" external class="ml-6 mb-2" href="#foo">
        External Dark Link
    </vs-link>
```
</docs>
