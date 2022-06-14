<template>
    <div>
        <VsLink
            variant="dark"
            type="external"
            href="https://www.google.com"
            @click.native.prevent="buttonClick($event)"
            data-vs-tracking-event="externalLink"
            data-vs-tracking-tag-name="VS - GA - External Link"
        >
            Test Link
        </VsLink>
    </div>
    <!-- <span class="d-none" /> -->
</template>

<script>
/* eslint-disable */
import checkVendorLibrary from '../../../utils/check-vendor-library';
import dataLayerPushTemplatesMixin from '../../../mixins/dataLayerPushTemplatesMixin';
import dataLayerNormalizerMixin from '../../../mixins/dataLayerNormalizerMixin';

/**
 * Component used to manage the Google Tag Manager (GTM)
 * data layer and help with pushing tracking data
 *
 * @displayName Data Layer Wrapper
 */
export default {
    name: 'VsDataLayerWrapper',
    status: 'prototype',
    release: '0.0.1',
    mixins: [dataLayerNormalizerMixin, dataLayerPushTemplatesMixin],
    props: {
        /**
         * Receive an external payload to be pushed through
         * the data layer by this component
         */
        payload: {
            type: Object,
            default: () => {},
        },
    },
    data() {
        return {
        };
    },
    created() {},
    mounted() {
        // checkVendorLibrary('dataLayer', () => {
        //     console.log('dataLayer available');
        // });
    },
    methods: {
        buttonClick(event) {
            let data = this.ExternalLinkDataNormalizer(event);
            this.ExternalLinkPushTemplate(data);
        },
    },
};
</script>

<style>
</style>
