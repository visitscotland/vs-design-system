import Vuex from "vuex"
import Vue from "vue"
import { find, matches } from "lodash"

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    itineraries: {
      namespaced: true,
      state: {
        stops: [],
      },
      mutations: {
        ADD_STOP: (state, payload) => {
          var newStop = {
            title: payload.title,
            imageSrc: payload.image.imageSrc,
            altText: payload.image.altText,
            stopCount: payload.stopCount,
            active: false,
          }
          state.stops.unshift(newStop)
        },
        SET_ALL_STOPS_INACTIVE: state => {
          state.stops.map(stop => {
            stop.active = false
          })
        },
        SET_STOP_INACTIVE: (state, payload) => {
          var index = state.stops.findIndex(stop => stop.stopCount === payload)
          state.stops[index].active = false
        },
        SET_STOP_ACTIVE: (state, payload) => {
          var index = state.stops.findIndex(stop => stop.stopCount === payload)
          state.stops[index].active = true
        },
      },
      actions: {
        addStop: ({ commit, getters }, payload) => {
          if (getters.getStop(payload)) {
            return false
          }

          commit("ADD_STOP", payload)

          return true
        },
        setStopActive: (context, payload) => {
          context.commit("SET_STOP_ACTIVE", payload)
        },
        setStopInactive: (context, payload) => {
          context.commit("SET_STOP_INACTIVE", payload)
        },
        setAllStopsInactive: context => {
          context.commit("SET_ALL_STOPS_INACTIVE")
        },
      },
      getters: {
        getStops: state => state.stops,
        getActiveStop: state => state.stops.filter(stop => stop.active === true),
        getStop: state => subject => {
          return find(state.stops, matches(subject))
        },
      },
    },
  },
})
