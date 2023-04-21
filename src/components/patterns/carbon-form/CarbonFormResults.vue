<template>
    <VsRow>
        <VsCol cols="12">
            <VsHeading
                level="2"
            >
                Results
            </VsHeading>
        </VsCol>
        <VsCol
            cols="12"
            lg="6"
        >
            <VsHeading
                level="3"
                override-style-level="1"
            >
                Total tons: {{ totalTons.toFixed(3) }}
            </VsHeading>
        </VsCol>
        <VsCol
            cols="12"
            lg="6"
        >
            <div
                class="vs-carbon-calculator__chart"
            >
                <PieChart
                    :chart-data="chartData"
                />
            </div>
        </VsCol>
        <hr>
        <VsCol
            cols="6"
        >
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
        <VsCol
            cols="6"
        >
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
        PieChart,
    },
    props: {
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
    },
};
</script>
