import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    namespaced: true,
    state: {
        pageUrl: '',
        pageLanguage: 'en',
        tagsTestRun: false,
    },
    mutations: {
        SET_TEST_RUN: (state, payload) => {
            // eslint-disable-next-line no-param-reassign
            state.tagsTestRun = payload;
        },
        PAGE_LOADED_URL: (state, payload) => {
            // eslint-disable-next-line no-param-reassign
            state.pageUrl = payload;
        },
        /**
         * This will receive a payload like:
         * payload = {"page-category-1": "PageCategoryTest1"}
         *
         * And push the key:value pairs to the stare state
         */
        PAYLOAD_UPDATE: (state, payload) => {
            // eslint-disable-next-line no-param-reassign
            state[payload.key] = payload.value;
        },
    },
    actions: {
        setTestRun: ({ commit }, payload) => {
            commit('SET_TEST_RUN', payload);
            return true;
        },
        setPageUrl: ({ commit }, payload) => {
            commit('PAGE_LOADED_URL', payload);
            return true;
        },
        /**
         * This is a general action that receives a payload object and uses the PAYLOAD_UPDATE
         * and pushes all the key:value pairs from the payload to the store
         */
        processPayload: ({ commit }, payload) => {
            commit('PAYLOAD_UPDATE', payload);
            return true;
        },
    },
    getters: {
        getTestRunStatus: (state) => state.tagsTestRun,
        // eslint-disable-next-line arrow-body-style
        getPageUrl: (state) => {
            return state.pageUrl;
        },
        // eslint-disable-next-line arrow-body-style
        getPageLanguage: (state) => {
            return state.pageLanguage;
        },

        // This is a general getter to retrieve any value from the store:
        getValueFromKey: (state) => (key) => state[key],
    },
});
