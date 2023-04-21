<template>
    <div
        class="vs-carbon-form-tip"
    >
        <VsSvg
            path="highland-cow"
            class="vs-carbon-form-tip__icon"
        />

        <p
            class="vs-carbon-form-tip__content"
            v-if="allTips"
        >
            You can view all our sustainable travel tips
            <VsLink href="#">
                here
            </VsLink>
            . We've got you covered!
        </p>

        <p
            class="vs-carbon-form-tip__heading"
            v-if="!allTips"
        >
            Top tip
        </p>

        <p
            class="vs-carbon-form-tip__content"
            v-if="!allTips"
        >
            {{ tip }}
        </p>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg';
import VsLink from '@components/elements/link';

/**
 * @displayName Carbon Form Tip
 */
export default {
    name: 'CarbonFormTip',
    status: 'prototype',
    release: '0.0.1',
    components: [
        VsSvg,
        VsLink,
    ],
    props: {
        tip: {
            type: String,
            default: '',
        },
        allTips: {
            type: Boolean,
            default: false,
        },
    },
};
</script>

<style lang='scss'>
    .vs-carbon-form-tip {
        box-shadow: $shadow_popover;
        padding: $spacer-8 $spacer-11;
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
