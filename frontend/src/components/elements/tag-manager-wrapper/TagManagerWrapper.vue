<template>
    <span class="d-none" />
</template>

<script>
/* eslint-disable */
import checkVendorLibrary from '../../../utils/check-vendor-library';
import dataLayerStore from '../../../stores/dataLayer.store';
// import dataLayerPushTemplatesMixin from '../../../mixins/dataLayerPushTemplatesMixin';
// import dataLayerNormalizerMixin from '../../../mixins/dataLayerNormalizerMixin';

/**
 * Component used to manage data related to the page
 * and pass it to the Google Tag Manager (GTM)
 * data layer
 *
 * @displayName Tag Manager Wrapper
 */
export default {
    name: 'VsTagManagerWrapper',
    status: 'prototype',
    release: '0.0.1',
    // mixins: [dataLayerNormalizerMixin],
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
        return {};
    },
    created() {},
    mounted() {
        // checkVendorLibrary('dataLayer', () => {
        //     console.log('dataLayer available');
        // });

        document.addEventListener('DOMContentLoaded', event => {
            dataLayerStore.dispatch('setTestRun', true);
            dataLayerStore.dispatch('setPageUrl', window.location.href);
        });
    },
    methods: {
        // buttonClick(e) {
        //     console.log('button clicked');
        //     let data = this.ExternalLinkDataNormalizer(e);
        //     this.ExternalLinkPushTemplate(data);
        // },
    },
};
</script>

<style>
</style>
