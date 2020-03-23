<template>
    <dl class="vs-icon-description-list mb-0">
        <vs-icon-description-list-term v-if="title">{{ title }}</vs-icon-description-list-term>
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
    props: {
        /**
         * The title for the icon list
         */
        title: {
            type: String,
        },
    },
}
</script>

<style lang="scss" scoped>
.vs-icon-description-list {
    text-align: center;
    font-size: $h6-font-size;
    line-height: $standard-line-height;
}
</style>

<docs>
  ```jsx
    <vs-icon-description-list title="Key Facilities">
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
