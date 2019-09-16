import { get, isFunction, includes } from "lodash"

export const moduleNames = {
  SITE_SEARCH: "site-search",
  FAVOURITES_LIST: "favourites-list",
}

export const state = {
  module: null,
}

export const getters = {
  module(state) {
    return state.module
  },
}

export const mutations = {
  close(state) {
    state.module = null
  },
  switchModule(state, value) {
    if (!value) {
      state.module = null

      return true
    }

    if (includes(moduleNames, value)) {
      state.module = value

      return true
    }

    return false
  },
}

export const actions = {
  showModule({ commit, dispatch }, moduleName) {
    return moduleName ? commit("switchModule", moduleName) : dispatch("close")
  },
  close(context, focusElement) {
    context.commit("close")

    if (isFunction(get(focusElement, "focus"))) {
      focusElement.focus()
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
