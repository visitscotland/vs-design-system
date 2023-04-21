<template>
    <VsRow>
        <VsCol cols="12">
            <VsHeading
                level="2"
            >
                {{ title }}
            </VsHeading>
        </VsCol>
        <VsCol
            cols="12"
        >
            <VsRow
                class="vs-carbon-form-results__headline"
            >
                <VsCol
                    cols="6"
                >
                    <p>
                        <span class="vs-carbon-form-results__total">
                            {{ totalTons.toFixed(3) }}
                        </span>
                        Tonnes CO2
                    </p>
                </VsCol>
                <VsCol
                    cols="6"
                    class="vs-carbon-form-results__comp-img"
                >
                    <VsImg
                        src="https://placehold.co/150x150"
                    />
                </VsCol>
            </VsRow>
        </VsCol>
        <VsCol
            cols="12"
            class="vs-carbon-form-results__comparison"
        >
            <!-- eslint-disable -->
            <div
                v-html="interpolComparison"
            />
            <!-- eslint-enable -->
        </VsCol>
        <VsCol
            cols="12"
        >
            <div
                class="vs-carbon-calculator__chart"
            >
                <PieChart
                    :chart-data="chartData"
                />
            </div>
        </VsCol>
        <VsCol>
            <VsHeading
                level="5"
            >
                <VsIcon
                    name="transport"
                />
                Travel
            </VsHeading>
            <p>Tons emitted: {{ transportTons.toFixed(3) }}</p>
            <VsRow
                v-if="transportTip"
            >
                <VsCol cols="12">
                    <VsHeading
                        level="6"
                    >
                        Tips
                    </VsHeading>
                </VsCol>
                <VsCol cols="2">
                    <VsIcon
                        :name="transportTip.icon"
                    />
                </VsCol>
                <VsCol cols="10">
                    <p>{{ transportTip.text }}</p>
                </VsCol>
            </VsRow>
        </VsCol>
        <VsCol>
            <VsHeading
                level="5"
            >
                <VsIcon
                    name="food"
                />
                Food
            </VsHeading>
            <p>Tons emitted: {{ foodTons.toFixed(3) }}</p>
            <VsRow
                v-if="foodTip"
            >
                <VsCol cols="12">
                    <VsHeading
                        level="6"
                    >
                        Tips
                    </VsHeading>
                </VsCol>
                <VsCol cols="2">
                    <VsIcon
                        :name="foodTip.icon"
                    />
                </VsCol>
                <VsCol cols="10">
                    <p>{{ foodTip.text }}</p>
                </VsCol>
            </VsRow>
        </VsCol>
    </VsRow>
</template>

<script>
import { PieChart } from 'vue-chart-3';

import { VsCol, VsRow } from '@components/elements/grid';
import VsIcon from '@components/elements/icon';
import VsImg from '@components/elements/img';

/**
 * @displayName Carbon Form Results
 */
export default {
    name: 'VsCarbonFormResults',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsCol,
        VsRow,
        VsIcon,
        VsImg,
        PieChart,
    },
    props: {
        title: {
            type: String,
            default: '',
        },
        comparison: {
            type: String,
            default: '',
        },
        comparisonTons: {
            type: Number,
            default: 0.1,
        },
        totalTons: {
            type: Number,
            default: 0,
        },
        foodTons: {
            type: Number,
            default: 0,
        },
        foodTip: {
            type: Object,
            default: null,
        },
        transportTons: {
            type: Number,
            default: 0,
        },
        transportTip: {
            type: Object,
            default: null,
        },
    },
    computed: {
        chartData() {
            return {
                labels: ['Transport', 'Food'],
                datasets: [
                    {
                        data: [
                            this.transportTons,
                            this.foodTons,
                        ],
                        backgroundColor: [
                            '#700E57',
                            '#FCCA1B',
                        ],
                        legend: {
                            position: 'right',
                        },
                    },
                ],
                plugins: {
                    legend: {
                        position: 'right',
                    },
                },
            };
        },
        interpolComparison() {
            const instances = (this.totalTons / this.comparisonTons).toFixed(3);

            return this.comparison.replace('xxx', instances);
        },
    },
};
</script>

<style lang='scss'>
    .vs-carbon-form-results__headline {
        text-align: center;
        margin-bottom: $spacer-10;
    }

    .vs-carbon-form-results__total {
        display: block;
        font-size: 80px;
        font-weight: $font-weight-bold;
    }

    .vs-carbon-form-results__comp-img {
        display: flex;
        justify-content: center;

        .vs-img {
            height: 8rem;
        }
    }

    .vs-carbon-form-results__comparison {
        margin-bottom: $spacer-10;
    }
</style>
