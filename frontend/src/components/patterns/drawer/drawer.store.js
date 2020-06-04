import Vuex from "vuex"
import Vue from "vue"
import { get, set /* , isFunction */ } from "lodash"
import {
    GET_ACTIVE_CONTENT,
    GET_RETURN_FOCUS_ELEMENT,
    IS_ACTIVE_CONTENT,
} from "./drawer.store.getter-types"
import {
    REGISTER_DRAWER, CLOSE_DRAWER, SHOW_DRAWER_CONTENT,
} from "./drawer.store.action-types"

Vue.use(Vuex)

/* eslint-disable no-shadow */

const stateTypes = {
    activeContents: "active_contents",
    returnFocusElements: "return_focus_elements",
}

const mutationTypes = {
    setActiveContent: "setActiveContent",
    setReturnFocusElement: "setReturnFocusElement",
    addDrawer: "addDrawer",
}

/**
 * This store controls drawer content and visibility. Each drawer
 * registers itself on creation using the `register` action. When
 * a drawer's activeContents value is null, it is closed, otherwise
 * it is open.
 */
export const state = {
    /**
     * Map of currently active content for each drawer. Keys are drawer name/id
     * and values are content set name/id.
     */
    [stateTypes.activeContents]: {
    },

    /**
     * Map of return focus elements for each drawer. Keys are drawer name/id
     * and values are Vue component refs.
     */
    [stateTypes.returnFocusElements]: {
    },
}

export const getters = {
    [GET_ACTIVE_CONTENT](state) {
        return (drawerKey) => get(state[stateTypes.activeContents], drawerKey)
    },
    [GET_RETURN_FOCUS_ELEMENT](state) {
        return (drawerKey) => get(state[stateTypes.returnFocusElements], drawerKey)
    },
    [IS_ACTIVE_CONTENT](state, getters) {
        return (contentKey, drawerKey) => contentKey === getters[GET_ACTIVE_CONTENT](drawerKey)
    },
}

export const mutations = {
    [mutationTypes.setActiveContent](state, { drawerKey, contentKey }) {
        set(state[stateTypes.activeContents], drawerKey, contentKey)
    },
    [mutationTypes.setReturnFocusElement](state, { drawerKey, returnFocusElement }) {
        set(state[stateTypes.returnFocusElements], drawerKey, returnFocusElement)
    },
    [mutationTypes.addDrawer](state, { drawerKey }) {
        Vue.set(state[stateTypes.activeContents], drawerKey, null)
        Vue.set(state[stateTypes.returnFocusElements], drawerKey, null)
    },
}

export const actions = {
    [REGISTER_DRAWER]({ commit }, { drawerKey }) {
        commit("addDrawer", {
            drawerKey,
        })
    },
    [SHOW_DRAWER_CONTENT]({ commit }, { drawerKey, contentKey, returnFocusElement }) {
        return new Promise((resolve, reject) => {
            if (!drawerKey) {
                reject(new Error("Drawer key missing"))
            }

            commit(mutationTypes.setActiveContent, {
                drawerKey,
                contentKey,
            })
            commit(mutationTypes.setReturnFocusElement, {
                drawerKey,
                returnFocusElement,
            })

            resolve()
        })
    },
    [CLOSE_DRAWER]({ getters, commit }, { drawerKey }) {
        return new Promise((resolve, reject) => {
            if (!drawerKey) {
                reject(new Error("Drawer key missing"))
            }

            const returnFocusElement = getters[GET_RETURN_FOCUS_ELEMENT](drawerKey)

            commit(mutationTypes.setActiveContent, {
                drawerKey,
                contentKey: null,
            })
            commit(mutationTypes.setReturnFocusElement, {
                drawerKey,
                returnFocusElement: null,
            })

            resolve(returnFocusElement)
        })
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

/* eslint-enable no-shadow */
