import Vuex from 'vuex';
import Vue from 'vue';

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        header: {
            namespaced: true,
            state: {
                showDropdown: false,
            },
            mutations: {
                TOGGLE_DROPDOWN_STATUS: (state) => {
                    /* eslint-disable no-param-reassign */
                    console.log(state);
                    state.showDropdown = !state.showDropdown;
                },
            },
        },
    },
});
