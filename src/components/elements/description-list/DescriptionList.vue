<template>
    <dl class="vs-description-list">
        <slot />
    </dl>
</template>

<script>
/**
 * TODO: Document usage
 */

export default {
    name: "VsDescriptionList",
    status: "prototype",
    release: "0.0.1",
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-description-list {
    &__title {
        font-family: $headings-font-family;
        border-bottom: 1px solid $color-gray-tint-5;
        margin-bottom: $spacer-3;
        padding: $spacer-1 0;
    }
}
</style>

<docs>
  ```jsx
    <vs-description-list class="mb-9">
        <dt class="vs-description-list__title">Highlights</dt>
        <dd 
            v-for="(highlight, index) in itineraries.sampleItinerary.highlights"
            class="mb-0"
        >
            {{highlight}}
        </dd>
        <dt class="vs-description-list__title mt-6">Areas Covered</dt>
        <dd 
            v-for="(areaCovered, index) in itineraries.sampleItinerary.areasCovered"
            class="mb-0"
        >
            {{areaCovered}}
        </dd>
    </vs-description-list>

    <vs-description-list class="list-inline">
        <dt class="list-inline-item">Start/Finish</dt>
        <dd class="list-inline-item mb-0"
        >
            Angus/Fife
        </dd>
    </vs-description-list>
  ```
</docs>
