<template>
    <div>
        <VsLink
            variant="dark"
            type="external"
            href="https://www.google.com"
        >
            Test Link
        </VsLink>
        <br>
        <VsLink
            variant="dark"
            type="internal"
            href="#"
        >
            Test Link
        </VsLink>
    </div>
    <!-- <span class="d-none" /> -->
</template>

<script>
/* eslint-disable */
import checkVendorLibrary from '../../../utils/check-vendor-library';
import dataLayerMixin from '../../../mixins/dataLayerMixin';

/**
 * This component is only being used as a placeholder to test out
 * GTM integration with vue components and the vuex store <br>
 * <strong style="color: red;">Has to be deleted (Used in development only, DO NOT push into production)</strong>
 *
 * @displayName Data Layer Wrapper
 */
export default {
    name: 'VsDataLayerWrapper',
    status: 'prototype',
    release: '0.0.1',
    mixins: [dataLayerMixin],
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
        // buttonClick(event) {
        //     this.externalLinkDataEvent(event);
        // },
    },
};
</script>

<style>
</style>
