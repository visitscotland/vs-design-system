import Vuex from "vuex"
import Vue from "vue"

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    itineraries: {
      namespaced: true,
      state: {
        activeStop: null,
        highlightedStop: null,
      },
      mutations: {
        SET_STOP_ACTIVE: (state, payload) => {
          state.activeStop = payload
        },
        SET_STOP_HIGHLIGHTED: (state, payload) => {
          state.highlightedStop = payload
        },
      },
      actions: {
        setStopActive: ({ commit, getters }, payload) => {
          if (getters.getActiveStop === payload) {
            return false
          }
          commit("SET_STOP_ACTIVE", payload)

          return true
        },
        setStopHighlighted: ({ commit, getters }, payload) => {
          if (getters.getHighlightedStop === payload) {
            return false
          }
          commit("SET_STOP_HIGHLIGHTED", payload)
          return true
        },
      },
      getters: {
        getActiveStop: state => state.activeStop,
        getHighlightedStop: state => state.highlightedStop,
        getHighlightedStopCoordinates: state =>
          state.highlightedStop === null
            ? [-4.07083, 56.18882]
            : state.highlightedStop.geometry.coordinates,
      },
    },
  },
})
