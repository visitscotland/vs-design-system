<template>
    <dl class="vs-description-list" :class="{ 'list-inline': inline }">
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
    props: {
        /**
         * Option to create inline list
         */
        inline: {
            type: Boolean,
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
</style>

<docs>
  ```jsx
    <vs-description-list inline>
        <vs-description-list-term inline>Transport</vs-description-list-term>
        <vs-description-list-detail inline>Walking</vs-description-list-detail>
        <vs-description-list-detail inline>Bus</vs-description-list-detail>
    </vs-description-list>

    <vs-description-list>
        <vs-description-list-term>Highlights</vs-description-list-term>
        <vs-description-list-detail 
            v-for="(highlight, index) in itineraries.sampleItinerary.highlights"
        >
            {{highlight}}
        </vs-description-list-detail>
    </vs-description-list>
  ```
</docs>
