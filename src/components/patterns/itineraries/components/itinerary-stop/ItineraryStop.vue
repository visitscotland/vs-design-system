<template>
    <li class="vs-itinerary-stop">
        <div class="border">
            <div class="d-flex justify-content-start align-items-top">
                <vs-icon name="map-marker-filled" variant="secondary-teal" size="md" :padding="0" />
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
    margin-bottom: $spacer-4;
    .border {
        background-color: $color-white;
        border: 1px solid $color-gray-tint-5;
        padding: $spacer-4;
    }

    &:last-of-type {
        .border {
            padding-bottom: 50px;
        }
    }
}
.vs-itinerary-stop ::v-deep {
    .itinerary-stop__facilities {
        border-top: 1px solid $color-gray-tint-5;
        margin: $spacer-9 -1rem -1rem;
        padding: 1rem 0;
        text-align: center;

        @include media-breakpoint-up(sm) {
            margin: $spacer-9 0 0;
        }

        dt {
            margin-bottom: 1rem;
            flex-basis: 1;
            display: block;
            width: 100%;
        }

        dd {
            display: inline-table;
            vertical-align: top;
            text-align: center;
            width: 90px;
            position: relative;

            svg {
                display: block;
                margin: 0 auto;
                width: 100%;
            }
        }
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
