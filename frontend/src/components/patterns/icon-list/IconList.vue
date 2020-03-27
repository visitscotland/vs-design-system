<template>
    <div class="vs-icon-list">
        <div class="vs-icon-list__title" v-if="title">{{ title }}</div>
        <ul class="vs-icon-list__list mb-0">
            <slot />
        </ul>
    </div>
</template>

<script>
/**
 * An icon list can be used where there is a list icons with a caption with optional heading.
 * An example use is to create a list of key facilities for a product.
 */

export default {
    name: "VsIconList",
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
.vs-icon-list {
    text-align: center;

    .vs-icon-list__title {
        font-size: $h4-font-size;
        font-weight: $lead-font-weight;
        margin-bottom: $spacer-5;
    }

    .vs-icon-list__list {
        display: inline-block;
        margin: 0 auto;
        padding: 0;
        text-align: left;
    }
}
</style>

<docs>
  ```jsx
    <vs-icon-list title="Key Facilities">
        <vs-icon-list-item
            icon="facility-wifi"
            label="WiFi"
        />
        <vs-icon-list-item
            icon="facility-petswelcom"
            label="Pets Welcome"
        />
        <vs-icon-list-item
            icon="facility-parking"
            label="Parking"
        />
        <vs-icon-list-item
            icon="facility-dsblaccess"
            label="Level Access"
        />
        <vs-icon-list-item
            icon="facility-audioloop"
            label="Hearing Loop"
        />
        <vs-icon-list-item
            icon="facility-publictlt"
            label="Public Toilets">
        </vs-icon-list-item>
        <vs-icon-list-item
            icon="facility-giftshop"
            label="Gift Shop">
        </vs-icon-list-item>
        <vs-icon-list-item
            icon="facility-accessparkdrop"
            label="Accessible Parking or Drop-off Point"
        />
    </vs-icon-list>
  ```
</docs>
