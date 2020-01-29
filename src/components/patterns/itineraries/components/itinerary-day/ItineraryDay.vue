<template>
    <li class="vs-itinerary-day__list-item">
        <div class="vs-itinerary-day__header text-center position-relative">
            <vs-heading level="2" thin class="vs-itinerary-day__title">
                <span>{{ dayLabel }} {{ dayNumber }}</span>
                {{ dayTitle }}
            </vs-heading>
            <vs-button
                :animate="false"
                :aria-expanded="show ? 'true' : 'false'"
                :aria-controls="'dayPanel_' + dayNumber"
                @click.native="triggerToggle()"
                aria-haspopup="true"
                class="vs-itinerary-day__toggle-button position-absolute p-0"
                v-if="!this.isDesktop"
                variant="transparent"
            >
                <vs-icon
                    v-if="this.show"
                    name="chevron-down"
                    variant="dark"
                    size="xs"
                    :padding="3"
                />
                <vs-icon v-else name="chevron-up" variant="dark" size="xs" :padding="3" />
            </vs-button>
        </div>
        <div v-show="this.show || this.isDesktop" :id="'dayPanel_' + dayNumber">
            <slot name="day-transport" />
            <slot name="day-introduction" />
            <ul class="list-unstyled">
                <slot name="stops" />
            </ul>
        </div>
    </li>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsHeading from "@components/elements/heading/Heading"
import VsButton from "@components/elements/button/Button"
import { VsRow, VsCol } from "@components/elements/layout"

/**
 * Itinerary Day list items.
 */

export default {
    name: "VsItineraryDay",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsHeading,
        VsButton,
        VsIcon,
        VsRow,
        VsCol,
    },
    data() {
        return {
            show: this.defaultShow,
            isDesktop: window.innerWidth >= 1200 ? true : false,
        }
    },
    props: {
        /**
         * Logic to collapse certain Day list items on mobile by default
         * (e.g. after Day 1 and 2, collapse the days on mobile)
         */
        defaultShow: {
            type: Boolean,
            default: true,
        },
        /**
         * Label used for the word 'Day'
         */
        dayLabel: {
            type: String,
            required: true,
        },
        /**
         * Number of the day in the component
         */
        dayNumber: {
            type: String,
            required: true,
        },
        /**
         * Title of the day in the component
         */
        dayTitle: {
            type: String,
            required: true,
        },
    },
    methods: {
        onResize() {
            this.isDesktop = window.innerWidth >= 1200 ? true : false
        },
        triggerToggle() {
            this.show = !this.show
        },
    },
    mounted() {
        window.addEventListener("resize", this.onResize)
    },
    destroyed() {
        window.removeEventListener("resize", this.onResize)
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-itinerary-day__list-item {
    border-top: 5px solid $color-base-text;
    padding: $spacer-4;
    margin-bottom: $spacer-4;
}

.vs-itinerary-day__title span {
    border-bottom: 1px solid $color-base-text;
    color: $color-theme-secondary-teal;
    margin-bottom: $spacer-4;
    padding: $spacer-3 $spacer-6;
}

.vs-itinerary-day__toggle-button {
    border: 1px solid $color-base-text;
    border-radius: 50%;
    top: $spacer-2;
    right: 0;
}
</style>

<docs>
```jsx
	<ul style="list-style-type: none; padding: 0;">
		<vs-itinerary-day 
			v-for="(day, index) in itineraries.sampleItinerary.days"
			:defaultShow="(day.dayCount < 3) ? true : false"
			:key="index"
            :dayNumber="day.dayCount"
            dayLabel="Day"
            :dayTitle="day.title"
           
		>
            <vs-description-list v-if="day.transport.length" class="list-inline text-center" slot="day-transport">
                <dt class="list-inline-item">Transport:</dt>
				<dd class="list-inline-item" v-for="(transportType, transportTypeIndex) in day.transport">
					<vs-tooltip :title="transportType.value">
						<vs-icon :name="transportType.key" variant="dark" size="sm" />
					</vs-tooltip>
					<span class="sr-only">{{transportType.value}}</span>
				</dd>
            </vs-description-list>

			<div slot="day-introduction" v-html="day.introduction"></div>
		</vs-itinerary-day>
	</ul>
```
</docs>
