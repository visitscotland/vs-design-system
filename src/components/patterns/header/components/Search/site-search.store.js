import Vuex from "vuex"
import Vue from "vue"
import { get, isFunction } from "lodash"

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    siteSearch: {
      namespaced: true,
      state: {
        open: false,
      },
      getters: {
        open(state) {
          return state.open
        },
      },
      mutations: {
        open(state) {
          state.open = true
        },
        close(state) {
          state.open = false
        },
      },
      actions: {
        toggle({ state, dispatch }, closeFocusElement) {
          if (state.open) {
            dispatch("close", closeFocusElement)
          } else {
            dispatch("open")
          }
        },
        open(context) {
          context.commit("open")
        },
        close(context, focusElement) {
          context.commit("close")

          if (isFunction(get(focusElement, "focus"))) {
            focusElement.focus()
          }
        },
      },
    },
  },
})
