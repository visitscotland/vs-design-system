<template>
    <div
        class="vs-carbon-form-tip"
    >
        <VsSvg
            path="highland-cow"
            class="vs-carbon-form-tip__icon"
        />

        <p
            class="vs-carbon-form-tip__heading"
        >
            Top tip
        </p>

        <p
            class="vs-carbon-form-tip__content"
        >
            {{ tip }}
        </p>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg';

/**
 * @displayName Carbon Form Tip
 */
export default {
    name: 'CarbonFormTip',
    status: 'prototype',
    release: '0.0.1',
    components: [
        VsSvg,
    ],
    props: {
        tip: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang='scss'>
    .vs-carbon-form-tip {
        box-shadow: $shadow_popover;
        padding: $spacer-4 $spacer-10;
    }

    .vs-carbon-form-tip__heading {
        font-weight: $font-weight-bold;
    }

    .vs-carbon-form-tip__content {
        margin-bottom: $spacer-0;
    }

    .vs-carbon-form-tip__icon {
        position: absolute;
        left: -#{$spacer-2};
        bottom: -2px;
    }
</style>
