<template>
    <div
        class="card vs-ski-scotland-card"
        data-test="vs-ski-scotland-card"
    >
        <div
            class="vs-ski-scotland-card__img-container"
        >
            <template
                v-if="imgSrc"
            >
                <VsImg
                    :src="imgSrc"
                    :alt="imgAlt"
                    :srcset="`${imgSrc}?size=xs 300w,
                        ${imgSrc}?size=sm 600w,
                        ${imgSrc}?size=md 1200w,
                        ${imgSrc}?size=lg 2048w`"
                    sizes="(min-width: 768px) 50vw, 100vw"
                    :low-res-image="`${imgSrc}?size=xxs`"
                    class="vs-ski-scotland-card__img"
                    data-test="vs-ski-scotland-card__img"
                />
            </template>
        </div>
        <div class="card-body">
            <VsHeading
                level="3"
            >
                <!--
                    @slot Slot for ski centre name
                    Expects text
                -->
                <slot name="centre-name" />
            </VsHeading>
            <div
                class="text-center pt-8 py-4 position-relative"
                v-if="jsDisabled || isLoading || displayError"
            >
                <template v-if="!jsDisabled && isLoading">
                    <VsLoadingSpinner />
                    <!--
                        @slot Slot for data loading message
                        Expects text
                    -->
                    <slot name="data-loading" />
                </template>
                <template v-if="!jsDisabled && displayError">
                    <VsWarning
                        theme="light"
                        size="small"
                        data-test="vs-ski-scotland-card__error"
                    >
                        <!--
                            @slot Slot for data unavailable message
                            Expects text
                        -->
                        <slot name="data-unavailable" />
                    </VsWarning>
                </template>
                <template v-if="jsDisabled">
                    <VsWarning
                        theme="light"
                        size="small"
                        data-test="vs-ski-scotland-card__js-disabled"
                    >
                        <!--
                            @slot Slot for JS required message
                            Expects text
                        -->
                        <slot name="js-required" />
                    </VsWarning>
                </template>
            </div>
            <VsTable
                :table-caption="runsLiftsStatusLabel"
                v-if="!jsDisabled && !isLoading && !displayError"
            >
                <VsTableHead>
                    <VsTableHeaderCell>
                        <span data-test="vs-ski__status-label">
                            {{ statusLabel }}
                        </span>
                    </VsTableHeaderCell>
                    <VsTableHeaderCell
                        v-if="runs.length"
                    >
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
                        <VsTableDataCell
                            v-if="runs.length"
                        >
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
                                name="ski-boot"
                                size="xs"
                                class="mr-2"
                            />
                            <span data-test="vs-ski__limited-patrol-label">
                                {{ summaryLimitedPatrolLabel }}
                            </span>
                        </VsTableDataCell>
                        <VsTableDataCell
                            v-if="runs.length"
                        >
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
                        <VsTableDataCell
                            v-if="runs.length"
                        >
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
                        <VsTableDataCell
                            v-if="runs.length"
                        >
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
                                name="hourglass"
                                size="xs"
                                class="mr-2"
                            />
                            <span data-test="vs-ski__on-hold-label">
                                {{ summaryOnHoldLabel }}
                            </span>
                        </VsTableDataCell>
                        <VsTableDataCell
                            v-if="runs.length"
                        >
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
            <div
                class="vs-ski-scotland-card__button-holder
                vs-ski-scotland-card__button-holder--left"
            >
                <VsButton
                    v-if="pisteMapLink"
                    variant="secondary"
                    :href="pisteMapLink"
                    size="sm"
                >
                    {{ pisteMapLabel }}
                </VsButton>
                <VsButton
                    v-else
                    disabled
                    size="sm"
                >
                    {{ pisteMapLabel }}
                </VsButton>
            </div><!--
            --><div
                class="vs-ski-scotland-card__button-holder
                vs-ski-scotland-card__button-holder--right"
            >
                <VsButton
                    :href="moreDetailsLink"
                    size="sm"
                >
                    {{ moreDetailsLabel }}
                </VsButton>
            </div>
        </div>
    </div>
</template>

<script>
import VsImg from '@components/elements/img/Img';
import VsHeading from '@components/elements/heading/Heading';
import VsIcon from '@components/elements/icon/Icon';
import VsButton from '@components/elements/button/Button';
import VsWarning from '@components/patterns/warning/Warning';
import VsLoadingSpinner from '@components/elements/loading-spinner/LoadingSpinner';
import VsTable from '@components/patterns/table/Table';
import VsTableHead from '@components/patterns/table/components/TableHead';
import VsTableHeaderCell from '@components/patterns/table/components/TableHeaderCell';
import VsTableBody from '@components/patterns/table/components/TableBody';
import VsTableRow from '@components/patterns/table/components/TableRow';
import VsTableDataCell from '@components/patterns/table/components/TableDataCell';
import VsTableFooter from '@components/patterns/table/components/TableFooter';

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
        VsImg,
        VsHeading,
        VsIcon,
        VsWarning,
        VsLoadingSpinner,
        VsButton,
        VsTable,
        VsTableHead,
        VsTableHeaderCell,
        VsTableBody,
        VsTableRow,
        VsTableDataCell,
        VsTableFooter,
    },
    props: {
        /**
        * The url that the centre's summary information should be retrieved from for display
        */
        centreInfoUrl: {
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
         * Localisable label, translation of "run/lift status" for the full
         * report
         */
        runsLiftsStatusLabel: {
            type: String,
            default: 'Run/Lift Status',
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
        * The image to use in the card
        */
        imgSrc: {
            required: true,
            type: String,
        },
        /**
        * The image alt text to use in the card
        */
        imgAlt: {
            type: String,
            default: '',
        },
        /**
        * Localisable label, translation of "view piste map" for the
        * card links
        */
        pisteMapLabel: {
            type: String,
            default: 'View Piste Map',
        },
        /**
        * Localisable label, translation of "more details" for the
        * card links
        */
        moreDetailsLabel: {
            type: String,
            default: 'More Details',
        },
        /**
        * An href pointing to the piste map for the ski centre
        */
        pisteMapLink: {
            type: String,
            default: '',
        },
        /**
        * An href pointing to the ski status page for the ski centre
        */
        moreDetailsLink: {
            type: String,
            required: true,
            default: '#',
        },
    },
    data() {
        return {
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
            runs: [],
            lifts: [],
            lastUpdate: '',
            jsDisabled: true,
            isLoading: true,
            displayError: false,
        };
    },
    mounted() {
        if (this.centreInfoUrl) {
            this.retrieveCentreInfo();
        }

        // If component successfully mounted, declare js is enabled to hide the warning
        // from the user
        this.jsDisabled = false;
    },
    methods: {
        /**
         * Invoked when mounted, retrieves ski status info from the appropriate slope API
         */
        retrieveCentreInfo() {
            const errorTimeout = setTimeout(() => {
                this.displayError = true;
            }, this.timeoutDuration);

            axios.get(this.centreInfoUrl)
                .then((response) => {
                    const data = this.cleanData(response.data);
                    this.processLifts(data.lifts);
                    if (data.runs) {
                        this.processRuns(data.runs);
                    }
                    this.processLastUpdate(data.lastUpdate);
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

                // Glenshee and the Lecht don't have any run data, just lifts
                if (data.run.areas) {
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
            }

            return output;
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
            }

            this.runs = runs;
        },
    },
};
</script>

<style lang="scss">
    .vs-ski-scotland-card {
        border: none;
        border-radius: $border_radius_xl;
        overflow: hidden;
        box-shadow: $shadow_card_dark;
        margin-bottom: $spacer-9;
        height: calc(100% - #{$spacer-9});

        &::nth-last-child(-n + 3) {
            margin-bottom: $spacer-0;
        }

        .vs-ski-scotland-card__img-container {
            width: 100%;
            max-width: 100%;
            position: relative;
            aspect-ratio: 2/1;

            @supports not (aspect-ratio: 2/1) {
                padding-bottom: 50%;
            }
        }

        .vs-ski-scotland-card__img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            align-self: flex-start;
            flex-shrink: 0; // IE11 fix, prevents image vertical stretching
        }

        .card-body {
            position: relative;
            padding: $spacer-4 $spacer-4 $spacer-10;

            @include media-breakpoint-up(sm) {
                padding-bottom: $spacer-12;
            }

            @include media-breakpoint-up(lg) {
                padding-bottom: $spacer-10;
            }
        }

        .vs-heading {
            margin-top: $spacer-0;
        }

        .vs-ski-scotland-card__button-holder {
            width: calc(50% - #{$spacer-4});
            display: inline-block;
            padding: $spacer-1 $spacer-2;
            position: absolute;
            bottom: $spacer-4;

            &--left {
                left: $spacer-4;
            }

            &--right {
                right: $spacer-4;
            }

            @include media-breakpoint-up(sm) {
                width: calc(100% - #{$spacer-8});

                &--left {
                    right: $spacer-4;
                    bottom: $spacer-10;
                }

                &--right {
                    left: $spacer-4;
                }
            }

            @include media-breakpoint-up(lg) {
                width: calc(50% - #{$spacer-4});

                &--left {
                    right: auto;
                    bottom: $spacer-4;
                }

                &--right {
                    left: auto;
                }
            }
        }

        .vs-button {
            width: 100%;
            padding: $spacer-2 $spacer-2;

            &.disabled {
                margin-bottom: 2px;
            }
        }

        .vs-loading-spinner {
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
            top: 0.5rem;
        }
    }

</style>
