<template>
    <dl class="vs-icon-description-list">
        <slot />
    </dl>
</template>

<script>
/**
 * An icon description list can be used where there is a list of terms and descriptions with icons.
 * An example use is to create a list of key facilities for a product.
 */

export default {
    name: "VsIconDescriptionList",
    status: "prototype",
    release: "0.0.1",
    props: {},
}
</script>

<style lang="scss" scoped>
.vs-icon-description-list {
    border-top: 1px solid $color-gray-tint-5;
    margin: $spacer-4 -1rem -1rem;
    padding: 1rem 0 0;
    text-align: center;

    @include media-breakpoint-up(sm) {
        margin: $spacer-4 0 0;
    }
}
</style>

<docs>
  ```jsx
    <vs-icon-description-list>
        <vs-icon-description-list-term>Key facilities</vs-icon-description-list-term>
        <vs-icon-description-list-detail 
            icon="facility-wifi"
            label="WiFi"
        />
        <vs-icon-description-list-detail 
            icon="facility-petswelcom"
            label="Pets Welcome"
        />
        <vs-icon-description-list-detail 
            icon="facility-parking"
            label="Parking"
        />
        <vs-icon-description-list-detail 
            icon="facility-dsblaccess"
            label="Level Access"
        />
        <vs-icon-description-list-detail 
            icon="facility-audioloop"
            label="Hearing Loop"
        />
        <vs-icon-description-list-detail 
            icon="facility-accessparkdrop"
            label="Accessible Parking or Drop-off Point"
        />
    </vs-icon-description-list>
  ```
</docs>
