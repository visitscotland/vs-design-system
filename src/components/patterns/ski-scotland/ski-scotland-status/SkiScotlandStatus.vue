<template>
    <div
        class="vs-ski-scotland-status"
        data-test="vs-ski-scotland-status"
    >
        <VsContainer v-if="!jsDisabled && isLoading">
            <VsRow>
                <VsCol class="text-center py-4">
                    <VsLoadingSpinner />
                    <!--
                        @slot Slot for data loading message
                        Expects text
                    -->
                    <slot name="data-loading" />
                </VsCol>
            </VsRow>
        </VsContainer>
        <VsContainer v-if="!jsDisabled && displayError">
            <VsRow>
                <VsCol class="text-center py-4">
                    <VsLoadingSpinner />
                    <!--
                        @slot Slot for data unavailable message
                        Expects text
                    -->
                    <slot name="data-unavailable" />
                </VsCol>
            </VsRow>
        </VsContainer>
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    md="6"
                    v-if="jsDisabled"
                    data-test="vs-ski__js-disabled"
                >
                    <VsWarning
                        theme="light"
                    >
                        <!--
                            @slot Slot for JS required message
                            Expects text
                        -->
                        <slot name="js-required" />
                    </VsWarning>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    v-if="!jsDisabled && !isLoading && !displayError"
                >
                    <VsHeading
                        level="3"
                        override-style-level="5"
                        data-test="vs-ski__runs-lifts-status-label"
                        class="d-none d-md-block"
                    >
                        {{ runsLiftsStatusLabel }}
                    </VsHeading>

                    <VsTable
                        :table-caption="runsLiftsStatusLabel"
                    >
                        <VsTableHead>
                            <VsTableHeaderCell>
                                <span data-test="vs-ski__status-label">
                                    {{ statusLabel }}
                                </span>
                            </VsTableHeaderCell>
                            <VsTableHeaderCell>
                                <span data-test="vs-ski__runs-label">
                                    {{ runsLabel }}
                                </span>
                            </VsTableHeaderCell>
                            <VsTableHeaderCell>
                                <span data-test="vs-ski__lifts-label">
                                    {{ liftsLabel }}
                                </span>
                            </VsTableHeaderCell>
                        </VsTableHead>
                        <VsTableBody>
                            <VsTableRow>
                                <VsTableDataCell>
                                    <VsIcon
                                        name="tick"
                                        size="xs"
                                        class="mr-2"
                                    />
                                    <span data-test="vs-ski__open-label">
                                        {{ summaryOpenLabel }}
                                    </span>
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.runs.open }}/{{ runs.length }}
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.lifts.open }}/{{ lifts.length }}
                                </VsTableDataCell>
                            </VsTableRow>
                            <VsTableRow
                                v-if="statusSummary.runs.limitedPatrol
                                    || statusSummary.lifts.limitedPatrol"
                            >
                                <VsTableDataCell>
                                    <VsIcon
                                        name="tick"
                                        size="xs"
                                        class="mr-2"
                                    />
                                    <span data-test="vs-ski__open-label">
                                        {{ summaryLimitedPatrolLabel }}
                                    </span>
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.runs.limitedPatrol }}/{{ runs.length }}
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.lifts.limitedPatrol }}/{{ lifts.length }}
                                </VsTableDataCell>
                            </VsTableRow>
                            <VsTableRow>
                                <VsTableDataCell>
                                    <VsIcon
                                        name="expected-open"
                                        size="xs"
                                        class="mr-2"
                                    />
                                    <span data-test="vs-ski__opening-label">
                                        {{ summaryOpeningLabel }}
                                    </span>
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.runs.opening }}/{{ runs.length }}
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.lifts.opening }}/{{ lifts.length }}
                                </VsTableDataCell>
                            </VsTableRow>
                            <VsTableRow>
                                <VsTableDataCell>
                                    <VsIcon
                                        name="status-closed"
                                        size="xs"
                                        class="mr-2"
                                    />
                                    <span data-test="vs-ski__closed-label">
                                        {{ summaryClosedLabel }}
                                    </span>
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.runs.closed }}/{{ runs.length }}
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.lifts.closed }}/{{ lifts.length }}
                                </VsTableDataCell>
                            </VsTableRow>
                            <VsTableRow
                                v-if="statusSummary.runs.onHold || statusSummary.lifts.onHold"
                            >
                                <VsTableDataCell>
                                    <VsIcon
                                        name="status-closed"
                                        size="xs"
                                        class="mr-2"
                                    />
                                    <span data-test="vs-ski__open-label">
                                        {{ summaryOnHoldLabel }}
                                    </span>
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.runs.onHold }}/{{ runs.length }}
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.lifts.onHold }}/{{ lifts.length }}
                                </VsTableDataCell>
                            </VsTableRow>
                        </VsTableBody>
                        <VsTableFooter>
                            <VsTableRow>
                                <VsTableDataCell
                                    colspan="3"
                                    role="cell"
                                >
                                    <span data-test="vs-ski__last-updated-label">
                                        {{ lastUpdatedLabel }}: {{ lastUpdate }}
                                    </span>
                                </VsTableDataCell>
                            </VsTableRow>
                        </VsTableFooter>
                    </VsTable>
                    <VsLink
                        :href="`#vs-ski-scotland-status__detailed-status-${id}`"
                        type="default"
                        class="vs-ski-scotland-status__detailed-status-link"
                    >
                        {{ detailedStatusLabel }}
                    </VsLink>
                </VsCol>

                <VsCol
                    cols="12"
                    md="3"
                    offset-md="1"
                    class="vs-ski-scotland-status__centre-info"
                >
                    <!--
                        @slot Slot for centre information content
                        Expects html
                    -->
                    <slot name="centre-information" />
                </VsCol>
            </VsRow>
            <VsRow
                v-if="!jsDisabled && !isLoading && !displayError"
            >
                <VsCol
                    cols="12"
                    md="9"
                    class="vs-ski-scotland-status__full-report"
                >
                    <!-- eslint-disable vue/no-v-html -->
                    <VsHeading
                        level="2"
                        data-test="vs-ski__snow-conditions-label"
                    >
                        {{ snowConditionsLabel }}
                    </VsHeading>
                    <div
                        v-html="report"
                    />
                    <VsHeading
                        level="3"
                        override-style-level="5"
                        data-test="vs-ski__current-weather-label"
                        v-if="currentWeather.length"
                    >
                        {{ currentWeatherLabel }}
                    </VsHeading>
                    <div
                        v-if="currentWeather.length"
                        v-html="currentWeather"
                    />
                    <VsHeading
                        level="3"
                        override-style-level="5"
                        data-test="vs-ski__weather-forecast-label"
                        v-if="weatherForecast.length"
                    >
                        {{ weatherForecastLabel }}
                    </VsHeading>
                    <div
                        v-if="weatherForecast.length"
                        v-html="weatherForecast"
                    />
                    <VsHeading
                        level="3"
                        override-style-level="5"
                        data-test="vs-ski__roads-label"
                        v-if="roadStatus.length"
                    >
                        {{ roadsLabel }}
                    </VsHeading>
                    <div
                        v-if="roadStatus.length"
                        v-html="roadStatus"
                    />
                    <VsHeading
                        level="3"
                        override-style-level="5"
                        data-test="vs-ski__news-label"
                        v-if="news.length"
                    >
                        {{ newsLabel }}
                    </VsHeading>
                    <div
                        v-if="news.length"
                        v-html="news"
                    />
                    <!-- eslint-enable vue/no-v-html -->
                </VsCol>
            </VsRow>
            <VsRow
                v-if="!jsDisabled && !isLoading && !displayError"
            >
                <VsCol
                    cols="12"
                    md="9"
                >
                    <VsHeading
                        level="3"
                        data-test="vs-ski__lift-status-label"
                        :id="`vs-ski-scotland-status__detailed-status-${id}`"
                    >
                        {{ liftStatusLabel }}
                    </VsHeading>

                    <VsTable
                        :table-caption="liftStatusLabel"
                    >
                        <VsTableHead>
                            <VsTableHeaderCell>{{ statusLabel }}</VsTableHeaderCell>
                            <VsTableHeaderCell>{{ liftLabel }}</VsTableHeaderCell>
                        </VsTableHead>
                        <VsTableBody>
                            <VsTableRow
                                v-for="lift in lifts"
                                :key="lift.name"
                            >
                                <VsTableDataCell v-if="lift.status === '0' || lift.status === 0">
                                    <VsIcon
                                        name="status-closed"
                                        size="xs"
                                        class="mr-2"
                                    /> {{ statusClosedLabel }}
                                </VsTableDataCell>
                                <VsTableDataCell v-if="lift.status === '1' || lift.status === 1">
                                    <VsIcon
                                        name="tick"
                                        size="xs"
                                        class="mr-2"
                                    /> {{ statusOpenLabel }}
                                </VsTableDataCell>
                                <VsTableDataCell v-if="lift.status === '2' || lift.status === 2">
                                    <VsIcon
                                        name="expected-open"
                                        size="xs"
                                        class="mr-2"
                                    /> {{ statusOpeningLabel }}
                                </VsTableDataCell>
                                <VsTableDataCell v-if="lift.status === '3' || lift.status === 3">
                                    <VsIcon
                                        name="tick"
                                        size="xs"
                                        class="mr-2"
                                    /> {{ statusLimitedPatrolLabel }}
                                </VsTableDataCell>
                                <VsTableDataCell v-if="lift.status === '4' || lift.status === 4">
                                    <VsIcon
                                        name="status-closed"
                                        size="xs"
                                        class="mr-2"
                                    /> {{ statusOnHoldLabel }}
                                </VsTableDataCell>
                                <VsTableDataCell>{{ lift.name }}</VsTableDataCell>
                            </VsTableRow>
                        </VsTableBody>
                        <VsTableFooter>
                            <VsTableRow>
                                <VsTableDataCell
                                    colspan="2"
                                    role="cell"
                                >
                                    <p>{{ lastUpdatedLabel }}: {{ lastUpdate }}</p>
                                </VsTableDataCell>
                            </VsTableRow>
                        </VsTableFooter>
                    </VsTable>
                </VsCol>
            </VsRow>
            <VsRow
                v-if="!jsDisabled && !isLoading && !displayError"
            >
                <VsCol
                    cols="12"
                    md="9"
                >
                    <VsHeading
                        level="3"
                        data-test="vs-ski__run-status-label"
                    >
                        {{ runStatusLabel }}
                    </VsHeading>
                    <div
                        v-for="level in filteredRunLevels"
                        :key="level.name"
                    >
                        <VsAccordion>
                            <VsAccordionItem
                                variant="transparent"
                                :control-id="'accordion_item_' + level.name"
                                :colour-badge="level.colour"
                            >
                                <div
                                    slot="title"
                                    :data-test="`vs-ski__${level.name}-label`"
                                    class="d-inline-block"
                                >
                                    <span class="sr-only">{{ getColourLabel(level.colour) }}</span>
                                    {{ level.name }}
                                </div>
                                <VsTable
                                    :table-caption="level.colour + ' - ' + level.name"
                                >
                                    <VsTableHead>
                                        <VsTableHeaderCell>{{ statusLabel }}</VsTableHeaderCell>
                                        <VsTableHeaderCell>{{ runLabel }}</VsTableHeaderCell>
                                    </VsTableHead>
                                    <VsTableBody>
                                        <VsTableRow
                                            v-for="run in level.runs"
                                            :key="run.name"
                                            :data-test="`vs-ski__${level.name}-row`"
                                        >
                                            <VsTableDataCell
                                                v-if="run.status === '0' || run.status === 0"
                                            >
                                                <VsIcon
                                                    name="status-closed"
                                                    size="xs"
                                                    class="mr-2"
                                                /> {{ statusClosedLabel }}
                                            </VsTableDataCell>
                                            <VsTableDataCell
                                                v-if="run.status === '1' || run.status === 1"
                                            >
                                                <VsIcon
                                                    name="tick"
                                                    size="xs"
                                                    class="mr-2"
                                                /> {{ statusOpenLabel }}
                                            </VsTableDataCell>
                                            <VsTableDataCell
                                                v-if="run.status === '2' || run.status === 2"
                                            >
                                                <VsIcon
                                                    name="expected-open"
                                                    size="xs"
                                                    class="mr-2"
                                                /> {{ statusOpeningLabel }}
                                            </VsTableDataCell>
                                            <VsTableDataCell
                                                v-if="run.status === '3' || run.status === 3"
                                            >
                                                <VsIcon
                                                    name="tick"
                                                    size="xs"
                                                    class="mr-2"
                                                /> {{ statusLimitedPatrolLabel }}
                                            </VsTableDataCell>
                                            <VsTableDataCell
                                                v-if="run.status === '4' || run.status === 4"
                                            >
                                                <VsIcon
                                                    name="status-closed"
                                                    size="xs"
                                                    class="mr-2"
                                                /> {{ statusOnHoldLabel }}
                                            </VsTableDataCell>
                                            <VsTableDataCell>{{ run.name }}</VsTableDataCell>
                                        </VsTableRow>
                                    </VsTableBody>
                                    <VsTableFooter>
                                        <VsTableRow>
                                            <VsTableDataCell
                                                colspan="2"
                                                role="cell"
                                            >
                                                <p>{{ lastUpdatedLabel }}: {{ lastUpdate }}</p>
                                            </VsTableDataCell>
                                        </VsTableRow>
                                    </VsTableFooter>
                                </VsTable>
                            </VsAccordionItem>
                        </VsAccordion>
                    </div>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>

import VsTable from '@components/patterns/table/Table';
import VsTableHead from '@components/patterns/table/components/TableHead';
import VsTableHeaderCell from '@components/patterns/table/components/TableHeaderCell';
import VsTableBody from '@components/patterns/table/components/TableBody';
import VsTableRow from '@components/patterns/table/components/TableRow';
import VsTableDataCell from '@components/patterns/table/components/TableDataCell';
import VsTableFooter from '@components/patterns/table/components/TableFooter';
import VsAccordion from '@components/patterns/accordion/Accordion';
import VsAccordionItem from '@components/patterns/accordion/components/AccordionItem';
import VsWarning from '@components/patterns/warning/Warning';
import {
    VsContainer, VsRow, VsCol,
} from '@components/elements/grid';
import VsIcon from '@components/elements/icon/Icon';
import VsLink from '@components/elements/link/Link';
import VsHeading from '@components/elements/heading/Heading';
import VsLoadingSpinner from '@components/elements/loading-spinner/LoadingSpinner';

const axios = require('axios');

/**
 * The ski scotland status component is a wrapper for snow status components
 *
 * @displayName Ski Scotland Status
 */
export default {
    name: 'VsSkiScotlandStatus',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsTable,
        VsTableHead,
        VsTableHeaderCell,
        VsTableBody,
        VsTableRow,
        VsTableDataCell,
        VsTableFooter,
        VsContainer,
        VsRow,
        VsCol,
        VsAccordion,
        VsAccordionItem,
        VsIcon,
        VsLink,
        VsHeading,
        VsLoadingSpinner,
        VsWarning,
    },
    props: {
        /**
        * The url that the skiStatus information should be retrieved from for display
        */
        skiStatusUrl: {
            type: String,
            default: '',
        },
        /**
         * Locale string determined by the language selected on the site. Used to localise
         * the month in getLastUpdated
         */
        locale: {
            type: String,
            default: 'en-gb',
        },
        /**
         * Determines how long the request should wait for a response from the api before
         * giving up and displaying an error. Should be provided in milliseconds.
         */
        timeoutDuration: {
            type: Number,
            default: 30000,
        },
        /**
         * Localisable label, translation of "current weather" for the full
         * report
         */
        currentWeatherLabel: {
            type: String,
            default: 'Current Weather',
        },
        /**
         * Localisable label, translation of "Detailed Status"
         */
        detailedStatusLabel: {
            type: String,
            default: 'Detailed Status',
        },
        /**
         * Localisable label, translation of "difficult" to indicate run difficulty
         */
        difficultLabel: {
            type: String,
            default: 'Difficult',
        },
        /**
         * Localisable label, translation of "easy" to indicate run difficulty
         */
        easyLabel: {
            type: String,
            default: 'Easy',
        },
        /**
         * Localisable label, translation of "intermediate" to indicate run difficulty
         */
        intermediateLabel: {
            type: String,
            default: 'Intermediate',
        },
        /**
         * Localisable label, translation of "itineraries" to indicate run difficulty
         */
        itinerariesLabel: {
            type: String,
            default: 'Itineraries',
        },
        /**
         * Localisable label, translation of "last updated" for status tables
         */
        lastUpdatedLabel: {
            type: String,
            default: 'Last Updated',
        },
        /**
         * Localisable label, translation of "lift" for status tables
         */
        liftLabel: {
            type: String,
            default: 'Lift',
        },
        /**
         * Localisable label, translation of "lifts" for status tables
         */
        liftsLabel: {
            type: String,
            default: 'Lifts',
        },
        /**
         * Localisable label, translation of "lift status" for status tables
         */
        liftStatusLabel: {
            type: String,
            default: 'Lift Status',
        },
        /**
         * Localisable label, translation of "news from the slopes" for the full
         * report
         */
        newsLabel: {
            type: String,
            default: 'News From The Slopes',
        },
        /**
         * Localisable label, translation of "other" to indicate run difficulty
         */
        otherLabel: {
            type: String,
            default: 'Other',
        },
        /**
         * Localisable label, translation of "road status" for the full
         * report
         */
        roadsLabel: {
            type: String,
            default: 'Road Status',
        },
        /**
         * Localisable label, translation of "run" for status tables
         */
        runLabel: {
            type: String,
            default: 'Run',
        },
        /**
         * Localisable label, translation of "runs" for status tables
         */
        runsLabel: {
            type: String,
            default: 'Runs',
        },
        /**
         * Localisable label, translation of "run/lift status" for the full
         * report
         */
        runsLiftsStatusLabel: {
            type: String,
            default: 'Run/Lift Status',
        },
        /**
         * Localisable label, translation of "run status" for status tables
         */
        runStatusLabel: {
            type: String,
            default: 'Run Status',
        },
        /**
         * Localisable label, translation of "snow conditions full report" for
         * the full report
         */
        snowConditionsLabel: {
            type: String,
            default: 'Snow Conditions Full Report',
        },
        /**
         * Localisable label, translation of "status" for status tables
         */
        statusLabel: {
            type: String,
            default: 'Status',
        },
        /**
         * Localisable label, translation of "closed" for the summary table
         */
        summaryClosedLabel: {
            type: String,
            default: 'Closed',
        },
        /**
         * Localisable label, translation of "open" for the summary table
         */
        summaryOpenLabel: {
            type: String,
            default: 'Open',
        },
        /**
         * Localisable label, translation of "opening" for the summary table
         */
        summaryOpeningLabel: {
            type: String,
            default: 'Opening',
        },
        /**
         * Localisable label, translation of "limited patrol" for the summary table
         */
        summaryLimitedPatrolLabel: {
            type: String,
            default: 'Limited Patrol',
        },
        /**
         * Localisable label, translation of "on hold" for the summary table
         */
        summaryOnHoldLabel: {
            type: String,
            default: 'On Hold',
        },
        /**
         * Localisable label, translation of "closed" for the detailed status tables
         */
        statusClosedLabel: {
            type: String,
            default: 'Closed',
        },
        /**
         * Localisable label, translation of "open" for the detailed status tables
         */
        statusOpenLabel: {
            type: String,
            default: 'Open',
        },
        /**
         * Localisable label, translation of "opening" for the detailed status tables
         */
        statusOpeningLabel: {
            type: String,
            default: 'Opening',
        },
        /**
         * Localisable label, translation of "limited patrol" for the detailed status tables
         */
        statusLimitedPatrolLabel: {
            type: String,
            default: 'Limited Patrol',
        },
        /**
         * Localisable label, translation of "on hold" for the detailed status tables
         */
        statusOnHoldLabel: {
            type: String,
            default: 'On Hold',
        },
        /**
         * Localisable label, translation of "very difficult" to indicate run difficulty
         */
        veryDifficultLabel: {
            type: String,
            default: 'Very Difficult',
        },
        /**
         * Localisable label, translation of "weather forecast" for the full
         * report
         */
        weatherForecastLabel: {
            type: String,
            default: 'Weather Forecast',
        },
        /**
         * Localisable label, translation of "green" to indicate run difficulty
         */
        greenLabel: {
            type: String,
            default: 'Green',
        },
        /**
         * Localisable label, translation of "blue" to indicate run difficulty
         */
        blueLabel: {
            type: String,
            default: 'Blue',
        },
        /**
         * Localisable label, translation of "red" to indicate run difficulty
         */
        redLabel: {
            type: String,
            default: 'Red',
        },
        /**
         * Localisable label, translation of "black" to indicate run difficulty
         */
        blackLabel: {
            type: String,
            default: 'Black',
        },
        /**
         * Localisable label, translation of "orange" to indicate run difficulty
         */
        orangeLabel: {
            type: String,
            default: 'Orange',
        },
        /**
         * Localisable label, translation of "grey" to indicate run difficulty
         */
        greyLabel: {
            type: String,
            default: 'Grey',
        },
    },
    data() {
        return {
            componentId: 0,
            statusSummary: {
                runs: {
                    open: 0,
                    opening: 0,
                    closed: 0,
                    onHold: 0,
                    limitedPatrol: 0,
                },
                lifts: {
                    open: 0,
                    opening: 0,
                    closed: 0,
                    onHold: 0,
                    limitedPatrol: 0,
                },
            },
            lifts: [],
            runs: [],
            lastUpdate: '',
            report: '',
            currentWeather: '',
            weatherForecast: '',
            roadStatus: '',
            news: '',
            runLevels: [
                {
                    colour: 'green',
                    name: this.easyLabel,
                    runs: [],
                },
                {
                    colour: 'blue',
                    name: this.intermediateLabel,
                    runs: [],
                },
                {
                    colour: 'red',
                    name: this.difficultLabel,
                    runs: [],
                },
                {
                    colour: 'black',
                    name: this.veryDifficultLabel,
                    runs: [],
                },
                {
                    colour: 'orange',
                    name: this.itinerariesLabel,
                    runs: [],
                },
                {
                    colour: 'grey',
                    name: this.otherLabel,
                    runs: [],
                },
            ],
            jsDisabled: true,
            isLoading: true,
            displayError: false,
            isCairngorms: false,
        };
    },
    computed: {
        filteredRunLevels() {
            return this.runLevels.filter((level) => level.runs.length > 0);
        },
    },
    mounted() {
        if (this.skiStatusUrl) {
            this.retrieveSkiStatus();
        }

        this.id = this._uid;

        // If component successfully mounted, declare js is enabled to hide the warning
        // from the user
        this.jsDisabled = false;
    },
    methods: {
        /**
         * Invoked when mounted, retrieves ski status info from the appropriate slope API
         * and sets up relevant sub-components
         */
        retrieveSkiStatus() {
            const errorTimeout = setTimeout(() => {
                this.displayError = true;
            }, this.timeoutDuration);

            axios.get(this.skiStatusUrl)
                .then((response) => {
                    const data = this.cleanData(response.data);
                    this.processLifts(data.lifts);
                    this.processRuns(data.runs);
                    this.processLastUpdate(data.lastUpdate);
                    this.processFullReport(data.report);
                    this.isLoading = false;
                    clearTimeout(errorTimeout);
                })
                .catch(() => {
                    this.runStatusInfo = null;
                    this.displayError = false;
                    clearTimeout(errorTimeout);
                });
        },
        // Tidy up small formatting differences between Cairngorms and SkiScotland
        // data feeds
        cleanData(data) {
            const output = {
                contact: data.contact,
                report: data.report,
            };

            if (data.snowReport) {
                // Cairngorms formatting
                output.lastUpdate = data.snowReport.lastupdate;
                output.lifts = data.lifts.sector.items;
                output.runs = data.runs.items;
                this.isCairngorms = true;
            } else {
                // SkiScotland formatting
                output.lastUpdate = data.lastupdate;
                [output.lifts] = data.lift.sectors;
                output.lifts = output.lifts.lifts;
                [output.runs] = data.run.areas;
                output.runs = output.runs.runs;
            }

            return output;
        },
        processFullReport(data) {
            this.report = data.runs;
            this.currentWeather = data.current_weather_conditions;
            this.weatherForecast = data.weather_forecast;
            this.roadStatus = data.access_roads;
            this.news = data.news_from_the_slopes;
        },
        processLastUpdate(lastUpdate) {
            const event = new Date(
                lastUpdate.year,
                parseInt(lastUpdate.month, 10) - 1,
                lastUpdate.day
            );
            const options = {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
            };
            const formattedDate = event.toLocaleDateString(this.locale, options);
            this.lastUpdate = `${lastUpdate.hour24}:${lastUpdate.minute} - ${formattedDate}`;
        },
        processLifts(lifts) {
            for (let x = 0; x < lifts.length; x++) {
                switch (lifts[x].status) {
                case '4':
                case 4:
                    this.statusSummary.lifts.onHold += 1;
                    break;
                case '3':
                case 3:
                    this.statusSummary.lifts.limitedPatrol += 1;
                    break;
                case '2':
                case 2:
                    this.statusSummary.lifts.opening += 1;
                    break;
                case '1':
                case 1:
                    this.statusSummary.lifts.open += 1;
                    break;
                default:
                    this.statusSummary.lifts.closed += 1;
                    break;
                }
            }

            this.lifts = lifts;
        },
        processRuns(runs) {
            for (let x = 0; x < runs.length; x++) {
                switch (runs[x].status) {
                case '4':
                case 4:
                    this.statusSummary.runs.onHold += 1;
                    break;
                case '3':
                case 3:
                    this.statusSummary.runs.limitedPatrol += 1;
                    break;
                case '2':
                case 2:
                    this.statusSummary.runs.opening += 1;
                    break;
                case '1':
                case 1:
                    this.statusSummary.runs.open += 1;
                    break;
                default:
                    this.statusSummary.runs.closed += 1;
                    break;
                }

                for (let y = 0; y < this.runLevels.length; y++) {
                    if (this.runLevels[y].colour === runs[x].difficulty) {
                        this.runLevels[y].runs.push(runs[x]);
                    }
                }
            }

            this.runs = runs;
        },
        // Returns the localised label value for a given colour
        getColourLabel(colour) {
            return this[`${colour}Label`];
        },
    },
};
</script>

<style lang="scss">
    .vs-ski-scotland-status {
        text-align: left;

        &__full-report {
            h2 {
                margin-bottom: $spacer-4;

                @include media-breakpoint-up(md) {
                    margin-bottom: $spacer-7;
                }
            }

            h3 {
                margin-bottom: $spacer-2 !important;
                margin-top: $spacer-6 !important;
            }
        }

        &__centre-info {
            font-size: $font-size-4;
        }

        &__detailed-status-link {
            font-size: $font-size-4;
        }

        .row {
            &:not(:first-child) {
                margin-top: $spacer-8 + $spacer-2;

                @include media-breakpoint-up(md) {
                    margin-top: $spacer-10;
                }
            }
        }
    }
</style>
