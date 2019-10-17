import Vuex from "vuex"
import Vue from "vue"
import { get, isFunction, includes } from "lodash"

Vue.use(Vuex)

export const state = {
  module: null,
  returnFocusElement: null,
}

export const getters = {
  module(state) {
    return state.module
  },
  isCurrentModule: state => moduleName => moduleName === state.module,
}

export const mutations = {
  close(state) {
    state.module = null
  },
  switchModule(state, { moduleName, returnFocusElement }) {
    if (!moduleName) {
      state.module = null

      return true
    }

    state.module = moduleName
    state.returnFocusElement = returnFocusElement

    return false
  },
}

export const actions = {
  showModule({ commit, dispatch }, { moduleName, returnFocusElement }) {
    return moduleName
      ? commit("switchModule", { moduleName, returnFocusElement })
      : dispatch("close")
  },
  close({ state, commit }, { returnFocusElement } = {}) {
    commit("close")

    if (!returnFocusElement) {
      returnFocusElement = state.returnFocusElement
    }

    if (isFunction(get(returnFocusElement, "$el.focus"))) {
      returnFocusElement.$el.focus()
    }
  },
}

export default new Vuex.Store({
  modules: {
    drawer: {
      namespaced: true,
      state,
      getters,
      mutations,
      actions,
    },
  },
})
