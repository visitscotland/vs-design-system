<template>
    <dl class="vs-description-list py-7 mb-0">
        <dt>{{ title }}</dt>
        <dd v-for="item in items" :key="item" class="mb-0">
            {{ item }}
        </dd>
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
         * The Title for this component
         */
        title: {
            type: String,
            required: true,
        },
        /**
         * List of Items to be displayed
         */
        items: {
            type: Array,
            required: true,
        },
    },
}
</script>

<style lang="scss" scoped>
.vs-description-list {
    dt {
        font-family: $headings-font-family;
        border-bottom: 1px solid $color-gray-tint-5;
        margin-bottom: $spacer-3;
        padding: $spacer-1 0;
    }
}
</style>

<docs>
  ```jsx
    <vs-description-list
        title="Highlights"
        :items="itineraries.sampleItinerary.highlights"
    >
    </vs-description-list>
  ```
</docs>
