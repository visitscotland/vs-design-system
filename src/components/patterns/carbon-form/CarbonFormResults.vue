<template>
    <VsRow>
        <VsCol cols="12">
            <VsHeading
                level="4"
            >
                Results
            </VsHeading>
        </VsCol>
        <VsCol
            cols="12"
            lg="6"
        >
            <p>Total tons: {{ totalTons.toFixed(3) }}</p>
        </VsCol>
        <VsCol
            cols="12"
            lg="6"
            v-if="fullResults"
        >
            Chart
        </VsCol>
        <VsCol
            cols="12"
            lg="6"
            v-if="!fullResults"
        />
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
        fullResults: {
            type: Boolean,
            default: false,
        },
    },
};
</script>
