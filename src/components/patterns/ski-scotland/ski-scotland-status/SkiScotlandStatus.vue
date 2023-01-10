<template>
    <div
        class="vs-ski-scotland-status"
        data-test="vs-ski-scotland-status"
    >
        <h3>Run/Lift Status</h3>

        <table>
            <thead>
                <td>Status</td>
                <td>Runs</td>
                <td>Lifts</td>
            </thead>
            <tbody>
                <tr>
                    <td>Open</td>
                    <td>{{ statusSummary.runs.open }}</td>
                    <td>{{ statusSummary.lifts.open }}</td>
                </tr>
                <tr>
                    <td>Opening</td>
                    <td>{{ statusSummary.runs.opening }}</td>
                    <td>{{ statusSummary.lifts.opening }}</td>
                </tr>
                <tr>
                    <td>Closed</td>
                    <td>{{ statusSummary.runs.closed }}</td>
                    <td>{{ statusSummary.lifts.closed }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>

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
        };
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
            }
        },
    },
};
</script>
