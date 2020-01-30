<template>
    <vs-row tag="dl" class="vs-description-list" :class="{ 'list-inline': inline }">
        <slot />
    </vs-row>
</template>

<script>
import { VsRow } from "@components/elements/layout"
/**
 * A description list can be used where there is a list of terms and descriptions.
 *
 * The default list has 2 columns that collapse to stacked lists on smaller
 * screens. There is also an Inline option that can be passed in for a different style.
 */

export default {
    name: "VsDescriptionList",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsRow,
    },
    props: {
        /**
         * Option to create inline list
         */
        inline: {
            type: Boolean,
        },
    },
    /**
     * Provides inline prop to be injected to child components
     */
    provide: function() {
        return {
            inline: this.inline,
        }
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-description-list {
    & ::v-deep {
        // turns offset column off for first item after a term to avoid layout errors
        .vs-description-list__term + .vs-description-list__detail {
            @include make-col-offset(0);
        }
    }
}
</style>

<docs>
  ```jsx
    <vs-description-list class="mb-6">
        <vs-description-list-term>Highlights</vs-description-list-term>
        <vs-description-list-detail 
            v-for="(highlight, index) in itineraries.sampleItinerary.highlights"
        >
            {{highlight}}
        </vs-description-list-detail>
    </vs-description-list>

    <vs-description-list class="mb-8">
        <vs-description-list-term>Areas Covered</vs-description-list-term>
        <vs-description-list-detail 
            v-for="(areaCovered, index) in itineraries.sampleItinerary.areasCovered"
        >
            {{areaCovered}}
        </vs-description-list-detail>
    </vs-description-list>

    <vs-description-list inline>
        <vs-description-list-term>Transport</vs-description-list-term>
        <vs-description-list-detail>Lorem ipsum dolor sit amet, consectetur adipiscing elit mollis neque quis sem facilisis.</vs-description-list-detail>
    </vs-description-list>
  ```
</docs>
