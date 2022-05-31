import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({

    namespaced: true,
    state: {
        triggerNext: 0,
        triggerPrev: 0,
    },
    mutations: {
        TRIGGER_NEXT: (state) => {
            Object.assign(state, {
                // eslint-disable-next-line no-param-reassign
                triggerNext: state.triggerNext += 1,
            });
        },
        TRIGGER_PREV: (state) => {
            Object.assign(state, {
                // eslint-disable-next-line no-param-reassign
                triggerPrev: state.triggerPrev += 1,
            });
        },
    },
});
