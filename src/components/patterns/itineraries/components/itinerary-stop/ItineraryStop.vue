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
            <div slot="stop-details">
                <vs-image-with-caption
                    :altText="stop.image.altText"
                    :credit="stop.image.credit"
                    :caption="stop.image.caption"
                    :image-src="stop.image.imageSrc"
                    :latitude="stop.image.latitude"
                    :longitude="stop.image.longitude"
                    >
                    <img 
                    class="lazyload" 
                    :src="stop.image.imageSrc"
                    srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                    :data-srcset="stop.image.imageSrc" 
                    :alt="stop.image.altText"
                    data-sizes="auto" />
                </vs-image-with-caption>
                <div v-html="stop.description"></div>
                <vs-link href="stop.href">
                    Find out more
                </vs-link>
                <vs-description-list class="my-4 mb-0 justify-content-start" inline>
                    <vs-description-list-term class="mb-0 mr-0 col-auto">Time to explore</vs-description-list-term>
                    <vs-description-list-detail class="mb-0 col-auto px-0">{{stop.timeToExplore}}</vs-description-list-detail>
                </vs-description-list>
                <vs-itinerary-tips v-if="stop.tips.tipsBody.length || stop.tips.tipsTitle.length">
                    <div slot="text">
                    <strong>{{stop.tips.tipsTitle}}</strong>
                    <div v-html="stop.tips.tipsBody"></div>
                    </div>
                    <vs-svg slot="svg" path="highland-cow" />
                </vs-itinerary-tips>
                <vs-icon-description-list v-if="stop.facilities.length">
                    <vs-icon-description-list-term>Key facilities</vs-icon-description-list-term>
                    <vs-icon-description-list-detail 
                        v-for="(facility, facilitiesIndex) in stop.facilities"
                        :key="facilitiesIndex"
                        :label="facility.value"
                        :icon="facility.key"
                        />
                </vs-icon-description-list>
              </div>
            <!-- mimic only showing these links on the last stop of the day -->
              <template v-if="index == itineraries.sampleItinerary.days[0].stops.length - 1">
                <vs-itinerary-nearby-links-wrapper slot="nearby-links">
                    <vs-button-with-icon class="mb-3" variant="outline-primary" href="#" icon="food">
                        Nearby places to eat
                    </vs-button-with-icon>
                    <vs-button-with-icon variant="outline-primary" href="#" icon="product-accommodation">
                        Nearby places to stay
                    </vs-button-with-icon>
                </vs-itinerary-nearby-links-wrapper>
            </template>
		</vs-itinerary-stop>
	</ul>
```
</docs>
