<template>
    <div class="vs-itinerary-nearby-links">
        <slot />
    </div>
</template>

<script>
/**
 * Itinerary Nearby Links Wrapper.
 */

export default {
    name: "VsItineraryNearbyLinks",
    status: "prototype",
    release: "0.0.1",
    components: {},
    data() {
        return {}
    },
    props: {},
    methods: {},
}
</script>

<style lang="scss" scoped>
.vs-itinerary-nearby-links ::v-deep {
    .btn {
        display: block;
        width: 100%;

        @include media-breakpoint-up(sm) {
            width: auto;
        }
    }
}
</style>

<docs>
```jsx
	<vs-itinerary-nearby-links>
        <vs-button
            class="d-inline-flex mb-4"
            variant="outline-primary"
            href="https://www.visitscotland.com"
        >
        <vs-icon name="food" variant="primary" size="sm"></vs-icon>
            Nearby places to eat
        </vs-button>
        <br />
        <vs-button
            class="d-inline-flex"
            variant="outline-primary"
            href="https://www.visitscotland.com"
        >
            <vs-icon name="product-accommodation" variant="primary" size="sm"></vs-icon>
            Nearby places to stay
        </vs-button>
	</vs-itinerary-nearby-links>
```
</docs>
