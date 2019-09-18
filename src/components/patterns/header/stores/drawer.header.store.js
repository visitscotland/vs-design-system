import { get, isFunction, includes } from "lodash"

export const moduleNames = {
  SITE_SEARCH: "site-search",
  FAVOURITES_LIST: "favourites-list",
}

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

    if (includes(moduleNames, moduleName)) {
      state.module = moduleName
      state.returnFocusElement = returnFocusElement

      return true
    }

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

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
}
