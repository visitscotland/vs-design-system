<template>
    <div
        class="vs-icon-list"
        data-test="vs-icon-list"
    >
        <div
            class="vs-icon-list__title"
            v-if="title"
        >
            {{ title }}
        </div>
        <ul class="vs-icon-list__list mb-0">
            <slot />
        </ul>
    </div>
</template>

<script>
/**
 * An icon list can be used where there is a list icons with a caption with optional heading.
 * An example use is to create a list of key facilities for a product.
 *
 * @displayName Icon List
 */

export default {
    name: 'VsIconList',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * The title for the icon list
         */
        title: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
.vs-icon-list {
    text-align: center;

    &__title {
        font-size: $h4-font-size;
        font-weight: $lead-font-weight;
        margin-bottom: $spacer-5;
    }

    &__list {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        margin: 0 auto;
        padding: 0;
        text-align: left;
    }

    @include media-breakpoint-up(md) {
        &__list {
            padding: 0 $spacer-5;
        }
    }

    @include media-breakpoint-up(xxl) {
        &__list {
            padding: 0 $spacer-10;
        }
    }
}
</style>

<docs>
  ```jsx
    <VsIconList title="Key Facilities">
        <VsIconListItem
            icon="facility-wifi"
            label="WiFi"
        />
        <VsIconListItem
            icon="facility-petswelcom"
            label="Pets Welcome"
        />
        <VsIconListItem
            icon="facility-parking"
            label="Parking"
        />
        <VsIconListItem
            icon="facility-dsblaccess"
            label="Level Access"
        />
        <VsIconListItem
            icon="facility-audioloop"
            label="Hearing Loop"
        />
        <VsIconListItem
            icon="publictlt"
            label="Public Toilets">
        </VsIconListItem>
        <VsIconListItem
            icon="giftshop"
            label="Gift Shop">
        </VsIconListItem>
        <VsIconListItem
            icon="facility-accessparkdrop"
            label="Accessible Parking or Drop-off Point"
        />
    </VsIconList>
  ```
</docs>
