<template>
    <component
        :is="type"
        class="vs-itinerary-mobile-map-toggle"
        variant="primary"
        @click.native="toggleMap()"
    >
        <vs-icon :name="showMap ? 'list' : 'map'" variant="reverse-white" size="sm" :padding="0" />
        <span v-html="showMap ? 'List View' : 'Map View'"></span>
    </component>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"

/**
 * TODO: Document usage
 */

export default {
    name: "VsItineraryMobileMapToggle",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsIcon,
        VsButton,
    },
    data() {
        return {
            showMap: false,
        }
    },
    props: {
        type: {
            type: String,
            default: "vs-button",
        },
    },
    methods: {
        toggleMap() {
            this.showMap = !this.showMap
        },
    },
}
</script>

<style lang="scss" scoped>
.vs-itinerary-mobile-map-toggle ::v-deep {
    padding-left: 1rem;
    padding-right: 1rem;

    svg {
        margin-right: 10px;
    }
}
</style>

<docs>
  ```jsx

    <vs-itinerary-mobile-map-toggle />

  ``` 
</docs>
