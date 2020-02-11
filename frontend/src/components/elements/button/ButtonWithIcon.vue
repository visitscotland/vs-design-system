<template>
    <vs-button class="vs-button-with-icon" v-bind="$attrs">
        <vs-icon :name="icon" size="sm" :padding="0" />
        <slot />
    </vs-button>
</template>
<script>
import VsButton from "../../elements/button"
import VsIcon from "@components/elements/icon/Icon"

/**
 * This component extends VsButton and is used for buttons with icons and text.
 */

export default {
    name: "VsButtonWithIcon",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsButton,
        VsIcon,
    },
    props: {
        /**
         * The icon name
         */
        icon: {
            type: String,
            required: true,
        },
    },
}
</script>

<style lang="scss" scoped>
.vs-button-with-icon {
    padding-left: 1rem;
    padding-right: 1rem;

    @include media-breakpoint-up(sm) {
        width: auto;
    }

    svg {
        fill: currentColor;
        margin-right: 0.625rem;
        transition: fill 250ms;
    }
    &:hover {
        svg {
            fill: currentColor;
        }
    }
}
</style>

<docs>
```jsx
    <bs-wrapper class="d-flex flex-wrap mb-4">
        <vs-button-with-icon icon="food" variant="outline-primary" class="mr-3">Nearby places to eat</vs-button-with-icon>
        <vs-button-with-icon icon="map">Map View</vs-button-with-icon>
    </bs-wrapper>
```
</docs>
