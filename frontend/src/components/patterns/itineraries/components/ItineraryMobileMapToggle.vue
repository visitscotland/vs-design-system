<template>
    <Component
        :is="type"
        class="vs-itinerary-mobile-map-toggle"
        variant="primary"
        @click.native="toggleMap()"
    >
        <VsIcon
            :name="showMap ? 'list' : 'map'"
            variant="reverse-white"
            size="md"
        />

        {{ showMap ? 'List View' : 'Map View' }}
    </Component>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
import VsButton from '@components/elements/button/Button';

/**
 * TODO: Document usage
 *
 * @displayName Itinerary Mobile Map Toggle
 */

export default {
    name: 'VsItineraryMobileMapToggle',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsIcon,
        VsButton,
    },
    props: {
        type: {
            type: String,
            default: 'vs-button',
        },
    },
    data() {
        return {
            showMap: false,
        };
    },
    methods: {
        toggleMap() {
            this.showMap = !this.showMap;
        },
    },
};
</script>

<style lang="scss">
.vs-itinerary-mobile-map-toggle {
    padding-left: 1rem;
    padding-right: 1rem;

    svg {
        margin-right: 10px;
    }
}
</style>

<docs>
  ```jsx

    <VsItineraryMobileMapToggle />

  ```
</docs>
