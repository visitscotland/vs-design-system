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
    },
});
