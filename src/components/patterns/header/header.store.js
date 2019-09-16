import Vuex from "vuex"
import Vue from "vue"
import drawerStore, { moduleNames } from "./stores/drawer.header.store"

Vue.use(Vuex)

export const names = {
  drawer: {
    moduleNames,
  },
}

export default new Vuex.Store({
  modules: {
    header: {
      namespaced: true,
      modules: {
        drawer: drawerStore,
      },
    },
  },
})
