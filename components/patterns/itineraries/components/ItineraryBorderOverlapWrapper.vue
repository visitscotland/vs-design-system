<template>
    <div
        class="vs-itinerary-border-overlap__wrapper
        d-flex
        d-sm-inline-flex
        flex-column
        justify-content-start
        border-left"
    >
        <slot />
    </div>
</template>

<script>
/**
 * Wraps the nearby links at the bottom of each day and applies border styling
 *
 * @displayName Itinerary Border Overlap Wrapper
 */
export default {
    name: 'VsItineraryBorderOverlapWrapper',
    status: 'prototype',
    release: '0.0.1',
    components: {
    },
    props: {
    },
};
</script>

<style lang="scss">
.vs-itinerary-border-overlap__wrapper {
    min-height: 200px;
    margin-left: 2.5rem;

    @include media-breakpoint-up(sm) {
        margin-left: $spacer-12;
    }

    .btn {
        margin-left: -2.5rem;
        @include media-breakpoint-up(sm) {
            margin-left: -#{$spacer-4};
            &:first-of-type {
                margin-top: -#{$spacer-4};
            }
        }
    }
}
</style>