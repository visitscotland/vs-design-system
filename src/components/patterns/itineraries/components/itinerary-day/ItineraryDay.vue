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
        <div v-show="this.show || this.isDesktop">
            <slot name="day-transport" />
            <slot name="day-introduction" />
            <slot name="stops" />
            <slot name="nearby-links" />
        </div>
    </li>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
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
    // TODO: add watcher for when stop activated and expand all the days on mobile
    // so that stops can be scrolled to
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

.vs-itinerary-day__list-item ::v-deep .vs-itinerary-day__title span {
    border-bottom: 1px solid $color-base-text;
    color: $color-theme-secondary-teal;
    margin-bottom: $spacer-4;
    padding: $spacer-3 $spacer-6;
}

.vs-itinerary-stop__list-item ::v-deep {
    background-color: $color-white;
    border: 1px solid $color-gray-tint-5;
    padding: $spacer-4;
    margin-bottom: $spacer-4;

    .flex-fill {
        max-width: 160px;
        @media (min-width: 340px) {
            max-width: none;
        }
    }

    .itinerary-stop__facilities {
        border-top: 1px solid $color-gray-tint-5;
        margin: $spacer-9 -1rem -1rem;
        padding: 1rem 0 0;
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

.vs-itinerary-day__toggle-button {
    border: 1px solid $color-base-text;
    border-radius: 50%;
    top: $spacer-2;
    right: 0;
}
</style>

<docs>
```jsx
	<ul class="list-unstyled">
		<vs-itinerary-day 
			v-for="(day, index) in itineraries.sampleItinerary.days"
			:defaultShow="(day.dayCount < 3) ? true : false"
			:key="index"
            :dayNumber="day.dayCount"
            dayLabel="Day"
            :dayTitle="day.title"
           
		>
			<dl v-if="day.transport.length" class="list-inline text-center" slot="day-transport">
				<dt class="list-inline-item">Transport:</dt>
				<dd class="list-inline-item" v-for="(transportType, transportTypeIndex) in day.transport">
					<vs-tooltip :title="transportType.value">
						<vs-icon :name="transportType.key" variant="dark" size="sm" />
					</vs-tooltip>
					<span class="sr-only">{{transportType.value}}</span>
				</dd>
			</dl>
			<div slot="day-introduction" v-html="day.introduction"></div>
		</vs-itinerary-day>
	</ul>
```
</docs>
