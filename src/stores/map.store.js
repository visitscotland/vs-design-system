import Vuex from 'vuex';
import Vue from 'vue';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        maps: [],
        currentStage: 0,
        activeSubcatFilters: [],
        selectedSubCategory: null,
    },
    mutations: {
        addMapInstance: (state, payload) => {
            state.maps.push({
                id: payload.id,
                filters: payload.filters,
                places: payload.places,
                activePins: payload.activePins,
                hovered: '',
            });
        },
        SET_HOVERED_PLACE: (state, payload) => {
            state.maps.forEach((map) => {
                if (map.id === payload.mapId) {
                    /* eslint-disable no-param-reassign */
                    map.hovered = payload.hoveredId;
                }
            });
        },
        SET_ACTIVE_PLACE: (state, payload) => {
            state.maps.forEach((map) => {
                if (map.id === payload.mapId) {
                    /* eslint-disable no-param-reassign */
                    map.activePlace = payload.placeId;
                }
            });
        },
        SET_CURRENT_STAGE: (state, payload) => {
            state.currentStage = payload;
        },
        SET_ACTIVE_SUBCAT_FILTERS: (state, payload) => {
            state.activeSubcatFilters = payload;
        },
        SET_SELECTED_SUBCAT: (state, payload) => {
            state.selectedSubCategory = payload;
        },
    },
    actions: {
        setHoveredPlace: ({ commit }, payload) => {
            commit('SET_HOVERED_PLACE', payload);
        },
        setActivePlace: ({ commit }, payload) => {
            commit('SET_ACTIVE_PLACE', payload);
        },
        setCurrentStage: ({ commit }, payload) => {
            commit('SET_CURRENT_STAGE', payload);
        },
        setActiveSubcatFilters: ({ commit }, payload) => {
            commit('SET_ACTIVE_SUBCAT_FILTERS', payload);
        },
        setSelectedSubcat: ({ commit }, payload) => {
            commit('SET_SELECTED_SUBCAT', payload);
        },
    },
    getters: {
        getMaps: (state) => state,
        getMapById: (state) => (id) => state.maps.find((map) => map.id === id),
        getHoveredStop: (state) => (mapId) => {
            const activeMap = state.maps.find((map) => map.id === mapId);
            return activeMap.hovered;
        },
        getActivePlace: (state) => (mapId) => {
            const activeMap = state.maps.find((map) => map.id === mapId);
            return activeMap.activePlace;
        },
        getCurrentStage: (state) => state.currentStage,
        getActiveSubcatFilters: (state) => state.activeSubcatFilters,
        getSelectedSubcat: (state) => state.selectedSubCategory,
    },
});
