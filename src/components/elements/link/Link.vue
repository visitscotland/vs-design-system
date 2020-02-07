<template>
    <b-link
        class="vs-link"
        :class="{ 'vs-link--external': external }"
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
    },
}
</script>

<style lang="scss" scoped>
.vs-link {
    &.vs-link--external:hover {
        & ::v-deep {
            .icon {
                fill: $color-gray-shade-7;
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
```
</docs>
