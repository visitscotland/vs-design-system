import Vuex from "vuex"
import Vue from "vue"
import { find, matches } from "lodash"

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
        SET_STOP_UNHIGHLIGHTED: state => {
          state.highlightedStop = null
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
        setStopUnhighlighted: context => {
          context.commit("SET_STOP_UNHIGHLIGHTED")
        },
      },
      getters: {
        getActiveStop: state => state.activeStop,
        getHighlightedStop: state => state.highlightedStop,
      },
    },
  },
})
