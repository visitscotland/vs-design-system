<template>
    <li
        class="vs-itinerary-stop"
        data-test="vs-itinerary-stop"
    >
        <div class="border">
            <div
                class="d-flex justify-content-start align-items-top"
                data-test="vs-itinerary-stop-heading"
            >
                <div class="position-relative">
                    <VsIcon
                        name="map-marker-filled"
                        variant="secondary-teal"
                        size="xl"
                    />
                    <span
                        class="map-marker__count"
                        aria-hidden="true"
                        data-test="vs-itinerary-stop-marker"
                    >{{ stopNumber }}</span>
                </div>

                <VsHeading
                    level="3"
                    class="vs-itinerary-stop__title ml-4"
                >
                    {{ stopLabel }}

                    <template slot="sub-heading">
                        {{ stopTitle }}
                    </template>
                </VsHeading>
            </div>

            <!-- @slot The image component for the stop -->
            <slot name="stop-image" />

            <!-- @slot The description content for the stop -->
            <slot name="stop-description" />

            <div class="vs-itinerary-stop__details-container">
                <div
                    class="vs-itinerary-stop__address"
                    v-if="!!this.$slots['stop-address']"
                    data-test="vs-itinerary-stop-address"
                >
                    <!-- @slot The address component for the stop -->
                    <slot name="stop-address" />
                </div>

                <!-- @slot The tips content for the stop -->
                <slot name="stop-tips" />

                <div
                    class="vs-itinerary-stop__info"
                >
                    <VsIcon
                        name="information"
                        class="vs-address__map-marker mr-2"
                    />
                    <div>
                        <p
                            v-if="openingMessage !== 'none'"
                            class="vs-itinerary-stop__times"
                            data-test="vs-itinerary-stop-times"
                        >
                            <template v-if="openingMessage === 'closed'">
                                <!-- @slot The text if the stop is closed -->
                                <slot name="stop-closed" />
                            </template>
                            <template v-else-if="openingMessage === 'open'">
                                <!-- @slot The text if the stop is open -->
                                <slot name="stop-open" />
                            </template>
                            <template v-else-if="openingMessage === 'closing soon'">
                                <!-- @slot The text if the stop is closing soon -->
                                <slot name="stop-closing-soon" />
                            </template>
                            <br>
                        </p>

                        <p
                            v-if="!!this.$slots['stop-opening-text']"
                            data-test="vs-itinerary-stop-opening"
                        >
                            <!-- @slot The info content for the stop -->
                            <slot name="stop-opening-text" />
                        </p>

                        <p
                            v-if="!!this.$slots['stop-link-text'] && openingTimesLink !== null"
                            data-test="vs-stop-link"
                        >
                            <VsLink :href="openingTimesLink">
                                <!-- @slot The link to opening time details -->
                                <slot name="stop-link-text" />
                            </VsLink>
                        </p>

                        <p
                            v-if="!!this.$slots['stop-charge-text']"
                            data-test="vs-stop-charge-text"
                        >
                            <slot name="stop-charge-text" />
                        </p>
                    </div>
                </div>
            </div>

            <!-- @slot The facilities content for the stop -->
            <slot name="stop-facilities" />

            <template v-if="!!this.$slots['stop-button']">
                <!-- @slot The button content for the stop -->
                <slot name="stop-button" />
            </template>
        </div>
    </li>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
import VsHeading from '@components/elements/heading/Heading';
import VsLink from '@components/elements/link/Link';

/**
 * Itinerary Day list items.
 *
 * @displayName Itinerary Stop
 */

export default {
    name: 'VsItineraryStop',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsHeading,
        VsIcon,
        VsLink,
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
        /**
        * Opening time of stop
        */
        openingTime: {
            type: String,
            required: false,
            default: null,
        },
        /**
        * Closing time of stop
        */
        closingTime: {
            type: String,
            required: false,
            default: null,
        },
        /**
        * URL for full opening times details
        */
        openingTimesLink: {
            type: String,
            required: false,
            default: null,
        },
        /**
        * Day of week provided by server
        * used to ensure that days match
        * so there's not confusion between
        * time zones
        */
        dayName: {
            type: String,
            required: true,
        },

    },
    data() {
        return {
            openingMessage: '',
            currentTime: {
            },
        };
    },
    mounted() {
        this.getCurrentTime();
        this.compareTimes(this.currentTime, this.openingTime, this.closingTime);
    },
    methods: {
        getCurrentTime() {
            // refactor to UTC (with daylight saving)
            const date = new Date();
            const hours = date.getHours();
            const minutes = date.getMinutes();
            const day = date.getDay();

            this.currentTime.hours = hours;
            this.currentTime.minutes = minutes;
            this.currentTime.day = day;
        },
        compareTimes(currentTime, startTime, endTime) {
            // get current day to compare with
            const currentDay = this.returnDayName(currentTime.day);

            // split out start and end time into hours and minutes and
            // establish whether they're AM or PM
            let startTimeHours = parseInt(startTime.substring(0, startTime.indexOf(':')), 10);
            const startTimeMins = parseInt(startTime.substring(startTime.indexOf(':') + 1, startTime.length - 2), 10);
            const startTimeAMPM = this.determineAMPM(startTime);

            let endTimeHours = parseInt(endTime.substring(0, endTime.indexOf(':')), 10);
            const endTimeMins = parseInt(endTime.substring(endTime.indexOf(':') + 1, endTime.length - 2), 10);
            const endTimeAMPM = this.determineAMPM(endTime);

            // adjust time for 24 clock if not done already
            if (startTimeAMPM === 'PM' && startTimeHours < 13) {
                startTimeHours += 12;
            }

            if (endTimeAMPM === 'PM' && endTimeHours < 13) {
                endTimeHours += 12;
            }

            const closingSoonTime = this.calculateClosingSoon(endTimeHours, endTimeMins);

            // work out what type of message should show
            if (currentDay !== this.dayName) {
                this.openingMessage = 'none';
            } else if (startTimeHours > currentTime.hours
                || (startTimeHours === currentTime.hours && startTimeMins > currentTime.minutes)
                || endTimeHours < currentTime.hours
                || (endTimeHours === currentTime.hours && endTimeMins < currentTime.minutes)) {
                this.openingMessage = 'closed';
            } else if (this.withinClosingSoonTime(currentTime, closingSoonTime)) {
                this.openingMessage = 'closing soon';
            } else {
                this.openingMessage = 'open';
            }
        },
        returnDayName(day) {
            // change day from an integer to a name to match against day name supplied by CMS
            let text = '';

            switch (day) {
            case 1:
                text = 'Monday';
                break;
            case 2:
                text = 'Tuesday';
                break;
            case 3:
                text = 'Wednesday';
                break;
            case 4:
                text = 'Thursday';
                break;
            case 5:
                text = 'Friday';
                break;
            case 6:
                text = 'Saturday';
                break;
            case 7:
                text = 'Sunday';
                break;
            default:
                text = '';
            };

            return text;
        },
        calculateClosingSoon(endTimeHours, endTimeMinutes) {
            // calculate 30 minutes before the end time so we can display 'closing soon'
            // if the current time is within that
            let closingSoonHours;
            let closingSoonMinutes;
            if (endTimeMinutes < 30) {
                closingSoonHours = endTimeHours - 1;
                closingSoonMinutes = 60 + (endTimeMinutes - 30);
            } else {
                closingSoonHours = endTimeHours;
                closingSoonMinutes = endTimeMinutes - 30;
            }

            return {
                hours: closingSoonHours,
                mins: closingSoonMinutes,
            };
        },
        withinClosingSoonTime(currentTime, closingSoonTime) {
            // compare current time against closing soon time
            if (closingSoonTime.hours > currentTime.hours
                || (closingSoonTime.hours === currentTime.hours
                    && closingSoonTime.mins > currentTime.minutes)
            ) {
                return false;
            }

            return true;
        },
        determineAMPM(time) {
            let AMPM;

            if (time.includes('AM') || time.includes('PM')) {
                AMPM = time.includes('AM') ? 'AM' : 'PM';
            }

            return AMPM;
        },
    },
};
</script>

<style lang="scss">
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

    .vs-icon-list {
        border-top: 1px solid $color-gray-tint-5;
        margin-top: $spacer-4;

        .vs-icon-list__title {
            margin-top: $spacer-4;
        }
    }

    &__address {
        @include media-breakpoint-up(md) {
            border-right: 1px solid $color-gray-tint-5;
            margin-bottom: -#{$spacer-4};
            padding: $spacer-4 $spacer-0;
            margin-right: $spacer-6;
        }
    }

    &__address,
    &__info {
        @include media-breakpoint-up(md) {
            width: 50%;
        }
    }

    &__details-container {
        @include media-breakpoint-up(md) {
            display: flex;
        }
    }

    &__info {
        @include media-breakpoint-up(md) {
            padding: $spacer-6 0;
        }
        display: flex;
    }

    &__times {
        margin-bottom: 0;
    }
}
</style>

<docs>
```jsx
    <VsContainer>
        <VsRow>
            <VsCol cols="12" lg="6">
                <ul style="list-style-type: none; padding: 0;">
                    <VsItineraryStop
                        v-for="(stop, index) in itineraries.sampleItinerary.days[0].stops"
                        :key="index"
                        :stopNumber="stop.stopCount"
                        stopLabel="Stop"
                        :stopTitle="stop.title"
                        :openingTime="stop.opening"
                        :closingTime="stop.closing"
                        dayName="Thursday"
                        openingTimesLink="https://www.visitscotland.com"
                    >

                        <VsImageWithCaption
                            slot="stop-image"
                            :altText="stop.image.altText"
                            :image-src="stop.image.imageSrc"
                            variant="fullwidth"
                        >
                            <VsImg
                                class="lazyload"
                                :src="stop.image.imageSrc"
                                :data-srcset="stop.image.imageSrc"
                                :alt="stop.image.altText"
                                data-sizes="auto">
                            </VsImg>

                            <VsSvg
                                slot="toggle-icon"
                                path="info-toggle"
                                height="24"
                                width="24"
                            />

                            <span slot="caption">
                                {{ stop.image.caption }}
                            </span>

                            <span slot="credit">
                                &copy; {{ stop.image.credit }}
                            </span>
                        </VsImageWithCaption>
                        <div v-html="stop.description" slot="stop-description"></div>
                        <VsLink href="stop.href">
                            Find out more
                        </VsLink>
                        <VsDescriptionList class="my-4 mb-0 justify-content-start" inline>
                            <VsDescriptionListItem title class="mb-0 mr-0 pr-1 col-auto">
                                Time to explore
                            </VsDescriptionListItem>
                            <VsDescriptionListItem class="mb-0 col-auto px-0">
                                {{stop.timeToExplore}}
                            </VsDescriptionListItem>
                        </VsDescriptionList>
                        <VsItineraryTips v-if="stop.tips.tipsBody.length
                            || stop.tips.tipsTitle.length">
                            <div slot="stop-tips">
                            <strong>{{stop.tips.tipsTitle}}</strong>
                            <div v-html="stop.tips.tipsBody"></div>
                            </div>
                            <VsSvg slot="svg" path="highland-cow" />
                        </VsItineraryTips>
                        <template slot="stop-address">
                            <VsAddress v-if="stop.address && stop.address.line1">
                                <span v-if="stop.address.line1">{{ stop.address.line1 }},<br></span>
                                <span v-if="stop.address.line2">{{ stop.address.line2 }},<br></span>
                                <span v-if="stop.address.line3">{{ stop.address.line3 }},<br></span>
                                <span v-if="stop.address.city">{{ stop.address.city }},<br></span>
                                <span
                                    v-if="stop.address.postcode"
                                >
                                    {{ stop.address.postcode }}
                                </span>
                            </VsAddress>
                        </template>

                        <template
                            v-if="stop.info"
                            slot="stop-info"
                        >
                            {{ stop.info }}
                        </template>
                        <template slot="stop-closed">
                            Closed
                        </template>

                        <template slot="stop-open">
                            Open now
                        </template>

                        <template slot="stop-closing-soon">
                            Closing soon
                        </template>

                        <template slot="stop-opening-text">
                            Usually open
                        </template>

                        <template slot="stop-link-text">
                            Check opening times
                        </template>

                        <template slot="stop-charge-text">
                            Admission charge
                        </template>

                        <VsIconList
                            v-if="stop.facilities.length"
                            title="Key facilities"
                            slot="stop-facilities"
                        >
                            <VsIconListItem
                                v-for="(facility, facilitiesIndex) in stop.facilities"
                                :key="facilitiesIndex"
                                :label="facility.value"
                                :icon="facility.key"
                                />
                        </VsIconList>
                        <!-- mimic only showing these links on the last stop of the day -->
                        <VsItineraryBorderOverlapWrapper
                            slot="nearby-links"
                            v-if="index == itineraries.sampleItinerary.days[0].stops.length - 1"
                        >
                            <VsButton
                                class="mb-3"
                                button-size="md"
                                background="white"
                                variant="outline-primary"
                                href="#"
                                icon="food"
                            >
                                Nearby places to eat
                            </VsButton>
                            <VsButton
                                background="white"
                                variant="outline-primary"
                                href="#"
                                icon="product-accommodation"
                            >
                                Nearby places to stay
                            </VsButton>
                        </VsItineraryBorderOverlapWrapper>
                    </VsItineraryStop>
                </ul>
            </VsCol>
        </VsRow>
    </VsContainer>
```
</docs>
