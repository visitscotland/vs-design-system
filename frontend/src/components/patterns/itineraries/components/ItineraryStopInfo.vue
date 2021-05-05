<template>
    <div class="itinerary-stop-info">
        <VsIcon
            name="information"
            class="vs-address__map-marker mr-2"
        />
        <div>
            <!-- TO DO: update to not show if there's not start and/or end time -->
            <p
                v-if="isCurrentTimeframe
                    && (currentDayData[0].startTime || currentDayData[0].startTime .length > 0)
                    && (currentDayData[0].endTime || currentDayData[0].endTime.length > 0)"
                class="itinerary-stop-info__times"
                data-test="vs-itinerary-stop-times"
            >
                {{ hoursMessage ? hoursMessage : currentDayData[0].state.charAt(0).toUpperCase()
                    + currentDayData[0].state.slice(1) }}.

                <template v-if="!closedLongTerm">
                    <br>{{ provisionalMessage }}.
                </template>
                <br>
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
</template>

<script>
/**
 * Itinerary stop information. Dynamically shows whether a stop is open,
 * closed or closing soon, depending on the current UK time. Also shows
 * links to opening times and admission fee information.
 *
 * @displayName Itinerary Stop Info
 */

import VsIcon from '@components/elements/icon/Icon';
import VsLink from '@components/elements/link/Link';

export default {
    name: 'VsItineraryStopInfo',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsIcon,
        VsLink,
    },
    props: {
        /**
        * JSON object of opening hour details
        */
        openingHours: {
            type: [String, Object],
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
        * Text for when stop is closed
        */
        closedText: {
            type: String,
            required: true,
        },
        /**
        * Text for when stop is closing soon
        */
        closingSoonText: {
            type: String,
            required: true,
        },
        /**
        * Text for when stop is open
        */
        openText: {
            type: String,
            required: true,
        },
        /**
        * Text for 'Usually'
        */
        usualText: {
            type: String,
            required: true,
        },
        /**
        * Text for 'Provisionally'
        */
        provisionalText: {
            type: String,
            required: true,
        },
        /**
        * Text for 'Tempoprarily closed'
        */
        temporarilyClosedText: {
            type: String,
            required: true,
        },
    },
    data() {
        return {
            openingMessage: '',
            currentDayData: {
            },
            currentTime: {
            },
            dayDataIndex: 0,
            closedLongTerm: false,
            ukDate: '',
            isCurrentTimeframe: false,
            parsedHours: {
            },
        };
    },
    computed: {
        hoursMessage() {
            let message = '';
            if (this.closedLongTerm) {
                message = this.temporaryClosedText;
            } else if (this.currentDayData.length > 0) {
                const state = this.openingMessage;
                switch (state) {
                case 'closed':
                    message = this.closedText;
                    break;
                case 'closing soon':
                    message = this.closingSoonText;
                    break;
                case 'open':
                    message = this.openText;
                    break;
                default:
                    message = '';
                }
            }

            return message;
        },
        provisionalMessage() {
            // format the message for "provisionally open/closed" and all variants of this
            let provisionalMsg = '';
            if (this.currentDayData[0].provisional) {
                provisionalMsg = `${this.provisionalText} ${this.currentDayData[0].state}  ${this.currentDayData[0].day}`;
            } else {
                provisionalMsg = `${this.usualText} ${this.currentDayData[0].state} ${this.currentDayData[0].day}`;
            }

            // add open/closed times if they exist
            if (this.currentDayData[this.dayDataIndex].startTime !== 'undefined'
                && typeof this.currentDayData[this.dayDataIndex].endTime !== 'undefined'
                && this.currentDayData[this.dayDataIndex].startTime !== ''
                && this.currentDayData[this.dayDataIndex].endTime !== '') {
                this.currentDayData.forEach((timePeriod, index) => {
                    provisionalMsg += ` ${timePeriod.startTime} to ${timePeriod.endTime}`;

                    // if there's more than one time then concatenate them
                    if (index + 1 < this.currentDayData.length) {
                        provisionalMsg += ' and ';
                    }
                });
            }

            return provisionalMsg;
        },
    },
    mounted() {
        // if the opening hours data comes through as a string
        // transform it into an object
        if (typeof this.openingHours !== 'object') {
            this.parsedHours = JSON.parse(this.openingHours);
        } else {
            this.parsedHours = this.openingHours;
        }

        // if the stop is closed long term then mark it as such
        if (this.parsedHours.closedLongTerm) {
            this.closedLongTerm = true;
        }

        this.getCurrentTime();
        this.isActiveDate();
        this.getCurrentHoursInfo();
    },
    methods: {
        getCurrentTime() {
            const date = new Date();
            const ukTime = date.toLocaleTimeString('en-GB', {
                timeZone: 'Europe/London',
            });
            this.ukDate = date.toLocaleDateString('en-GB', {
                timeZone: 'Europe/London',
            });
            const hours = ukTime.substring(0, 2);
            const minutes = ukTime.substring(3, 6);
            const day = date.getDay();

            this.currentTime.hours = parseInt(hours, 10);
            this.currentTime.minutes = parseInt(minutes, 10);
            this.currentTime.day = day;
        },
        isActiveDate() {
            // check that we're currently in the active dates for the supplied hours -
            // if not the times shouldn't be shown

            if (typeof this.parsedHours.period !== 'undefined') {
                // reformat current date so it can be transformed into a JS date more reliably
                const formattedCurrentDate = `${this.ukDate.substring(6, 10)}-${this.ukDate.substring(4, 5)}-${this.ukDate.substring(0, 2)}`;
                const startDate = new Date(this.parsedHours.period.startDay);
                const endDate = new Date(this.parsedHours.period.endDay);
                const nowDate = new Date(formattedCurrentDate);

                if (nowDate < endDate && nowDate > startDate) {
                    this.isCurrentTimeframe = true;
                }
            } else {
                this.isCurrentTimeframe = true;
            }
        },
        getCurrentHoursInfo() {
            const currentDay = this.returnDayName(this.currentTime.day);

            if (typeof this.parsedHours.days !== 'undefined') {
                // find the data that matches today's day name
                const dayMatch = this.parsedHours.days.filter((day) => day.key === currentDay);

                if (typeof dayMatch !== 'undefined') {
                    this.currentDayData = dayMatch;
                    this.compareTimes(this.currentTime, this.currentDayData, this.dayDataIndex);
                }
            }
        },
        compareTimes(currentTime, openingData, index) {
            if (typeof openingData[index].startTime !== 'undefined'
                && typeof openingData[index].endTime !== 'undefined') {
                // get current day to compare with
                const openTime = openingData[index].startTime;
                const closeTime = openingData[index].endTime;

                // split out start and end time into hours and minutes and
                // establish whether they're AM or PM
                const startTimeHours = parseInt(openTime.substring(0, openTime.indexOf(':')), 10);
                const startTimeMins = parseInt(openTime.substring(openTime.indexOf(':') + 1, openTime.length), 10);

                const endTimeHours = parseInt(closeTime.substring(0, closeTime.indexOf(':')), 10);
                const endTimeMins = parseInt(closeTime.substring(closeTime.indexOf(':') + 1, closeTime.length), 10);

                const closingSoonTime = this.calculateClosingSoon(endTimeHours, endTimeMins);

                // work out what type of message should show
                if (startTimeHours > currentTime.hours
                    || (startTimeHours === currentTime.hours && startTimeMins > currentTime.minutes)
                    || endTimeHours < currentTime.hours
                    || (endTimeHours === currentTime.hours && endTimeMins < currentTime.minutes)) {
                    this.openingMessage = 'closed';
                } else if (this.withinClosingSoonTime(currentTime, closingSoonTime)) {
                    this.openingMessage = 'closing soon';
                } else {
                    this.openingMessage = 'open';
                }

                // check if there's more data for the current day, eg. if the
                // stop has morning and afternoon opening hours
                if (this.openingMessage === 'closed' && openingData.length > index + 1) {
                    this.dayDataIndex += 1;
                    this.compareTimes(this.currentTime, this.currentDayData, this.dayDataIndex);
                }
            }
        },
        returnDayName(day) {
            // change day from an integer to a name to match against day name supplied by CMS
            let text = '';

            switch (day) {
            case 1:
                text = 'monday';
                break;
            case 2:
                text = 'tuesday';
                break;
            case 3:
                text = 'wednesday';
                break;
            case 4:
                text = 'thursday';
                break;
            case 5:
                text = 'friday';
                break;
            case 6:
                text = 'saturday';
                break;
            case 7:
                text = 'sunday';
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
    },
};
</script>

<style lang="scss">
    .itinerary-stop-info {
        display: flex;
        margin-top: $spacer-0;
        max-width: 50%;

        &__times {
            margin-bottom: 0;
        }

        @include media-breakpoint-up(md) {
             margin-top: $spacer-6;
        }
    }
</style>

<docs>
```jsx
    <VsItineraryStopInfo
        :openingHours='itineraries.sampleItinerary.openingHours'
        openingTimesLink="https://www.visitscotland.com"
        closedText="Closed"
        closingSoonText="Closing soon"
        openText="Open"
        usualText="Usually"
        provisionalText="Provisionally"
        temporarilyClosedText="Temporarily closed"
    >
        <template slot="stop-to">
            to
        </template>

        <template slot="stop-link-text">
            Check opening times
        </template>

        <template slot="stop-charge-text">
            Admission charge
        </template>
    </VsItineraryStopInfo>
```
</docs>
