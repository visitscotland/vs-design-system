<template>
    <li class="vs-itinerary-day__list-item">
        <div class="vs-itinerary-day__header text-center position-relative">
            <VsHeading
                level="2"
                class="mt-9"
            >
                <span class="vs-itinerary-day__title d-inline-block">
                    {{ dayLabel }} {{ dayNumber }}
                </span>
                <span slot="sub-heading">{{ dayTitle }}</span>
            </VsHeading>
            <VsButton
                :animate="false"
                :aria-expanded="show ? 'true' : 'false'"
                :aria-controls="'dayPanel_' + dayNumber"
                @click.native="triggerToggle()"
                aria-haspopup="true"
                class="vs-itinerary-day__toggle-button position-absolute p-0"
                v-if="!isDesktop"
                variant="transparent"
            >
                <VsIcon
                    v-if="show"
                    name="chevron-down"
                    variant="dark"
                    size="xs"
                    :padding="3"
                />
                <VsIcon
                    v-else
                    name="chevron-up"
                    variant="dark"
                    size="xs"
                    :padding="3"
                />
            </VsButton>
        </div>
        <div
            v-show="show || isDesktop"
            :id="'dayPanel_' + dayNumber"
        >
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
    data() {
        return {
            show: this.defaultShow,
            isDesktop: false,
        }
    },
    mounted() {
        if(window) {
            window.addEventListener("resize", this.onResize)
        }
    },
    destroyed() {
        if(window) {
            window.removeEventListener("resize", this.onResize)
        }
    },
    methods: {
        onResize() {
            this.isDesktop = window.innerWidth >= 1200
        },
        triggerToggle() {
            this.show = !this.show
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-itinerary-day__list-item {
    border-top: 5px solid $color-base-text;
    padding: $spacer-4 $spacer-4 0;

    @include media-breakpoint-up(lg) {
        &:first-of-type {
            border-top: none;
        }
    }
}

.vs-itinerary-day__title  {
    border-bottom: 1px solid $color-base-text;
    color: $color-theme-secondary-teal;
    padding: 0 $spacer-6 $spacer-3;
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
            <vs-description-list
                v-if="day.transport.length"
                class="text-center justify-content-center align-items-center"
                slot="day-transport"
            >
                <dt class="list-inline-item">Transport:</dt>
                <dd
                    class="list-inline-item"
                    v-for="(transportType, transportTypeIndex) in day.transport"
                >
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
