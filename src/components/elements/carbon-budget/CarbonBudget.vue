<template>
    <div>
        <p>Is a green host: {{ isGreenHost }}</p>
        <p>Carbon per MegaByte: {{ perMegabyte.toFixed(3) }} grams</p>
        <p
            v-if="!isGreenHost"
        >
            Carbon per MegaByte if this had a green host: {{ perMegabyteIfGreen.toFixed(3) }} grams
        </p>
    </div>
</template>

<script>
import { co2, hosting } from '@tgwf/co2';

/**
 * @displayName Carbon Budget
 */

export default {
    name: 'VsCarbonBudget',
    status: 'prototype',
    release: '0.0.1',
    data() {
        return {
            isGreenHost: false,
            perMegabyte: 0,
            perMegabyteIfGreen: 0,
        };
    },
    mounted() {
        this.calculate();
    },
    methods: {
        calculate() {
            const domain = window.location.host;

            hosting.check(domain).then((result) => {
                // eslint-disable-next-line
                const oneByte = new co2({ model: "1byte", });
                this.isGreenHost = result;
                this.perMegabyte = oneByte.perByte(1000000, result);
                if (!this.isGreenHost) {
                    this.perMegabyteIfGreen = oneByte.perByte(1000000, true);
                }
            });
        },
    },
};
</script>
