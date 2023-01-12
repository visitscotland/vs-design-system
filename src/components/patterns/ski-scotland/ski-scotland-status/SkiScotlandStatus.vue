<template>
    <div
        class="vs-ski-scotland-status"
        data-test="vs-ski-scotland-status"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    md="7"
                >
                    <h3>Run/Lift Status</h3>

                    <VsTable
                        table-caption="Run/Lift Status"
                    >
                        <VsTableHead>
                            <VsTableHeaderCell>Status</VsTableHeaderCell>
                            <VsTableHeaderCell>Runs</VsTableHeaderCell>
                            <VsTableHeaderCell>Lifts</VsTableHeaderCell>
                        </VsTableHead>
                        <VsTableBody>
                            <VsTableRow>
                                <VsTableDataCell>Open</VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.runs.open }}/{{ runs.length }}
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.lifts.open }}/{{ lifts.length }}
                                </VsTableDataCell>
                            </VsTableRow>
                            <VsTableRow>
                                <VsTableDataCell>Opening</VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.runs.opening }}/{{ runs.length }}
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.lifts.opening }}/{{ lifts.length }}
                                </VsTableDataCell>
                            </VsTableRow>
                            <VsTableRow>
                                <VsTableDataCell>Closed</VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.runs.closed }}/{{ runs.length }}
                                </VsTableDataCell>
                                <VsTableDataCell>
                                    {{ statusSummary.lifts.closed }}/{{ lifts.length }}
                                </VsTableDataCell>
                            </VsTableRow>
                        </VsTableBody>
                    </VsTable>
                    <VsTableFooter>
                        <VsTableRow>
                            <VsTableDataCell
                                colspan="3"
                                role="cell"
                            >
                                <p>Last updated: {{ lastUpdate }}</p>
                            </VsTableDataCell>
                        </VsTableRow>
                    </VsTableFooter>
                </VsCol>

                <VsCol
                    cols="12"
                    md="5"
                >
                    Centre information placeholder
                </VsCol>

                <VsCol
                    cols="12"
                    md="8"
                >
                    <h3>Snow Conditions Full report</h3>

                    <p>{{ report }}</p>
                    <h4>Current Weather</h4>
                    <p>{{ currentWeather }}</p>
                    <h4>Weather Forecast</h4>
                    <p>{{ weatherForecast }}</p>
                    <h4>Road Status</h4>
                    <p>{{ roadStatus }}</p>
                    <h4>News from the Slopes</h4>
                    <p>{{ news }}</p>

                    <h3>Lift Status</h3>

                    <VsTable
                        table-caption="Lift Status"
                    >
                        <VsTableHead>
                            <VsTableHeaderCell>Status</VsTableHeaderCell>
                            <VsTableHeaderCell>Lift</VsTableHeaderCell>
                        </VsTableHead>
                        <VsTableBody>
                            <VsTableRow
                                v-for="lift in lifts"
                                :key="lift.name"
                            >
                                <VsTableDataCell v-if="lift.status === '0' || lift.status === 0">
                                    Closed
                                </VsTableDataCell>
                                <VsTableDataCell v-if="lift.status === '1' || lift.status === 1">
                                    Open
                                </VsTableDataCell>
                                <VsTableDataCell v-if="lift.status === '2' || lift.status === 2">
                                    Opening
                                </VsTableDataCell>
                                <VsTableDataCell>{{ lift.name }}</VsTableDataCell>
                            </VsTableRow>
                        </VsTableBody>
                    </VsTable>
                    <VsTableFooter>
                        <VsTableRow>
                            <VsTableDataCell>
                                <p>Last updated: {{ lastUpdate }}</p>
                            </VsTableDataCell>
                        </VsTableRow>
                    </VsTableFooter>

                    <h3>Run Status</h3>
                    <div
                        v-for="level in filteredRunLevels"
                        :key="level.name"
                    >
                        <h4>{{ level.name }}</h4>
                        <VsTable
                            :table-caption="level.colour + ' - ' + level.name"
                        >
                            <VsTableHead>
                                <VsTableHeaderCell>Status</VsTableHeaderCell>
                                <VsTableHeaderCell>Run</VsTableHeaderCell>
                            </VsTableHead>
                            <VsTableBody>
                                <VsTableRow
                                    v-for="run in level.runs"
                                    :key="run.name"
                                >
                                    <VsTableDataCell v-if="run.status === '0' || run.status === 0">
                                        Closed
                                    </VsTableDataCell>
                                    <VsTableDataCell v-if="run.status === '1' || run.status === 1">
                                        Open
                                    </VsTableDataCell>
                                    <VsTableDataCell v-if="run.status === '2' || run.status === 2">
                                        Opening
                                    </VsTableDataCell>
                                    <VsTableDataCell>{{ run.name }}</VsTableDataCell>
                                </VsTableRow>
                            </VsTableBody>
                        </VsTable>
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
import {
    VsContainer, VsRow, VsCol,
} from '@components/elements/grid';

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
    },
    props: {
        /**
        * The url that the skiStatus information should be retrieved from for display
        */
        skiStatusUrl: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            statusSummary: {
                runs: {
                    open: 0,
                    opening: 0,
                    closed: 0,
                },
                lifts: {
                    open: 0,
                    opening: 0,
                    closed: 0,
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
                    name: 'Easy',
                    runs: [],
                },
                {
                    colour: 'blue',
                    name: 'Intermediate',
                    runs: [],
                },
                {
                    colour: 'red',
                    name: 'Difficult',
                    runs: [],
                },
                {
                    colour: 'black',
                    name: 'Very Difficult',
                    runs: [],
                },
                {
                    colour: 'orange',
                    name: 'Itineraries',
                    runs: [],
                },
                {
                    colour: 'grey',
                    name: 'Other',
                    runs: [],
                },
            ],
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
    },
    methods: {
        /**
         * Invoked when mounted, retrieves ski status info from the appropriate slope API
         * and sets up relevant sub-components
         */
        retrieveSkiStatus() {
            axios.get(this.skiStatusUrl)
                .then((response) => {
                    const data = this.cleanData(response.data);
                    this.processLifts(data.lifts);
                    this.processRuns(data.runs);
                    this.processLastUpdate(data.lastUpdate);
                    this.processFullReport(data.report);
                })
                .catch(() => {
                    this.runStatusInfo = null;
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
            this.lastUpdate = `${lastUpdate.hour24}:${lastUpdate.minute} - ${lastUpdate.day} ${lastUpdate.month} ${lastUpdate.year}`;
        },
        processLifts(lifts) {
            for (let x = 0; x < lifts.length; x++) {
                switch (lifts[x].status) {
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
    },
};
</script>
