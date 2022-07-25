<template>
    <span class="d-none" />
</template>

<script>
/* eslint-disable */
import checkVendorLibrary from '../../../utils/check-vendor-library';
import dataLayerStore from '../../../stores/dataLayer.store';

/**
 * This is a hidden "Global" component that sits on
 * a higher level of the page hierarchy
 * and controls reading and updating global analytic details
 * to and from the Google Tag Manager (GTM) Vuex Store
 * created by dataLayer.store.js
 *
 * @displayName Tag Manager Wrapper
 */
export default {
    name: 'VsTagManagerWrapper',
    status: 'prototype',
    release: '0.0.1',
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
    mounted() {
        this.processPayload(this.payload)

        document.addEventListener('DOMContentLoaded', event => {
            dataLayerStore.dispatch('setTestRun', true);
            dataLayerStore.dispatch('setPageUrl', window.location.href);
        });
    },
    methods: {
        /**
         * This function receives a payload as props
         * then replaces all "-" for "_" to match the keys on the templates from iProspect
         * and after that pushes all the key:values pairs to the store
         * so any component can retrieve it using the general getter function:
         * dataLayerStore.getters.getValueFromKey("key_name")
         */
        processPayload(payload) {
            if (payload == undefined) return

            // Convert all the keys from kebab-case to snake_case
            for (let key in payload) {
                const newKey = key.replaceAll("-", "_")

                // This function add a new property to the payload using the newKey name
                Object.defineProperty(
                    payload, // Object that contains the property to be modified
                    newKey, // The property name to be added to the object
                    Object.getOwnPropertyDescriptor(payload, key) // Payload = object that contains the property | key = the name of the property
                );

                // Copying the value from the old key
                payload[newKey] = payload[key]

                // Removing the old key:value from the payload
                delete payload[key];

                // Pushing the new payload with processed key names to the store
                dataLayerStore.dispatch('processPayload', {
                    'key': newKey,
                    'value': payload[newKey],
                })
            }
        }
    }
};
</script>

<style>
</style>
