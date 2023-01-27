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
                                                v-if="run.status === '2' || run.status === 2"
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
                                            <VsTableDataCell>
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
 * The ski scotland card component displays ski run status information for a specific
 * ski resort. It is intended to be used in a grid with other
 *
 * @displayName Ski Scotland Card
 */
export default {
    name: 'VsSkiScotlandCard',
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
         * Localisable label, translation of "last updated" for status tables
         */
        lastUpdatedLabel: {
            type: String,
            default: 'Last Updated',
        },
        /**
         * Localisable label, translation of "lifts" for status tables
         */
        liftsLabel: {
            type: String,
            default: 'Lifts',
        },
        /**
         * Localisable label, translation of "runs" for status tables
         */
        runsLabel: {
            type: String,
            default: 'Runs',
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
    },
    data() {
        return {
            componentId: 0,
            skiResorts: [],
            jsDisabled: true,
            isLoading: true,
            displayError: false,
        };
    },
    mounted() {
        if (this.skiStatusUrl) {
            this.retrieveSkiStatus();
        }

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
                    this.displayError = true;
                    this.isLoading = false;
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

                // Some of the sites (Nevis Range) return multiple areas, some runs appear in
                // multiple areas and some are only in one so we have to join them, then filter
                // out dupes.
                const runs = data.run.areas
                    .map((area) => area.runs)
                    .reduce((pre, cur) => pre.concat(cur))
                    .filter((value, index, self) => index === self.findIndex((t) => (
                        t.name === value.name
                    )));

                output.runs = runs;
            }

            // Some sites return itineraries with null difficulty rather than the standard
            // orange
            for (let x = 0; x < output.runs.length; x++) {
                if (output.runs[x].difficulty === null) {
                    output.runs[x].difficulty = 'orange';
                }
            }

            return output;
        },
    },
};
</script>

<style lang="scss">
</style>
