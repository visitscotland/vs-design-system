<template>
    <li class="vs-itinerary-stop">
        <div class="border">
            <div class="d-flex justify-content-start align-items-top">
                <div class="position-relative">
                    <vs-icon
                        name="map-marker-filled"
                        variant="secondary-teal"
                        size="md"
                        :padding="0"
                    />
                    <span class="map-marker__count" aria-hidden="true">{{ stopNumber }}</span>
                </div>
                <vs-heading level="3" thin class="vs-itinerary-stop__title ml-4">
                    <span>{{ stopLabel }} {{ stopNumber }}</span>
                    {{ stopTitle }}
                </vs-heading>
            </div>
            <slot name="stop-details" />
        </div>
        <slot name="nearby-links" />
    </li>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsHeading from "@components/elements/heading/Heading"

/**
 * Itinerary Day list items.
 */

export default {
    name: "VsItineraryStop",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsHeading,
        VsIcon,
    },
    data() {
        return {}
    },
    props: {
        /**
         * Label used for the word 'Stop'
         */
        stopLabel: {
            type: String,
            required: true,
        },
        /**
         * Number of the stop in the component
         */
        stopNumber: {
            type: String,
            required: true,
        },
        /**
         * Title of the stop in the component
         */
        stopTitle: {
            type: String,
            required: true,
        },
    },
    methods: {},
}
</script>

<style lang="scss" scoped>
.vs-itinerary-stop {
    margin-bottom: 3.125rem;

    &:last-of-type {
        margin-bottom: 0;

        .border {
            padding-bottom: $spacer-8;
        }
    }

    .border {
        position: relative;
        background-color: $color-white;
        border: 1px solid $color-gray-tint-5;
        padding: $spacer-4;
    }

    @include media-breakpoint-up(sm) {
        margin-bottom: 6.35rem;
        &:last-of-type {
            margin-bottom: 0;
        }
    }
    .map-marker__count {
        color: $color-white;
        font-family: $headings-font-family;
        font-size: 1rem;
        display: block;
        position: absolute;
        top: 4px;
        left: 0;
        text-align: center;
        width: 100%;
    }
}
</style>

<docs>
```jsx
	<ul style="list-style-type: none; padding: 0;">
		<vs-itinerary-stop 
			v-for="(stop, index) in itineraries.sampleItinerary.days[0].stops"
			:key="index"
            :stopNumber="stop.stopCount"
            stopLabel="Stop"
            :stopTitle="stop.title"
		>
		</vs-itinerary-stop>
	</ul>
```
</docs>
