<template>
    <VsButton
        class="vs-button-with-icon"
        v-bind="$attrs"
        :size="buttonSize"
    >
        <VsIcon
            :name="icon"
            :size="iconSize"
            :padding="0"
        />
        <slot />
    </VsButton>
</template>
<script>
import VsIcon from '@components/elements/icon/Icon';
import VsButton from './Button';

/**
 * This component extends VsButton and is used for buttons with icons and text.
 */

export default {
    name: 'VsButtonWithIcon',
    status: 'prototype',
    release: '0.0.1',
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
        /**
         * The button size
         * `sm, md, lg`
         */
        buttonSize: {
            type: String,
            required: true,
            validator: (value) => value.match(/(sm|md|lg)/),
        },
    },
    computed: {
        iconSize() {
            switch (this.buttonSize) {
            case 'sm':
                return 'xs';
            case 'md':
                return 'sm';
            case 'lg':
                return 'md';
            default:
                return 'md';
            }
        },
    },
};
</script>

<style lang="scss">
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
        vertical-align: sub;
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
        <vs-button-with-icon
            icon="food"
            button-size="sm"
            variant="outline-primary"
            class="mr-3"
        >
            Nearby places to eat
        </vs-button-with-icon>
        <vs-button-with-icon
            icon="map"
            button-size="sm"
        >
            Map View
        </vs-button-with-icon>
    </bs-wrapper>

    <bs-wrapper class="d-flex flex-wrap mb-4">
        <vs-button-with-icon
            icon="food"
            button-size="md"
            variant="outline-primary"
            class="mr-3"
        >
            Nearby places to eat
        </vs-button-with-icon>
        <vs-button-with-icon
            icon="map"
            button-size="md"
        >
            Map View
        </vs-button-with-icon>
    </bs-wrapper>

    <bs-wrapper class="d-flex flex-wrap mb-4">
        <vs-button-with-icon
            icon="food"
            button-size="lg"
            variant="outline-primary"
            class="mr-3"
        >
            Nearby places to eat
        </vs-button-with-icon>
        <vs-button-with-icon
            icon="map"
            button-size="lg"
        >
            Map View
        </vs-button-with-icon>
    </bs-wrapper>
```
</docs>
