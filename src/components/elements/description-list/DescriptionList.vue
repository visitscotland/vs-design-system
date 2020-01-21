<template>
    <dl v-if="items" class="vs-description-list py-7 mb-0">
        <dt>{{ title }}</dt>
        <dd v-for="item in items" :key="item" class="mb-0">
            {{ item }}
        </dd>
    </dl>

    <dl v-else-if="text" class="vs-description-list py-7 mb-0">
        <dt>{{ title }}</dt>
        <dd class="mb-0">
            {{ text }}
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
         * The Title for the terms being defined
         */
        title: {
            type: String,
            required: true,
        },
        /**
         * List of Items to be displayed in the definition
         */
        items: {
            type: Array,
            required: false,
        },
        /**
         * String to be displayed in the defintion
         */
        text: {
            type: String,
            required: false,
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

    <vs-description-list
        title="Areas Covered"
        text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque pulvinar justo id risus imperdiet tristique."
    >
    </vs-description-list>
  ```
</docs>
