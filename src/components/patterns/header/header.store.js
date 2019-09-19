import Vuex from "vuex"
import Vue from "vue"
import drawerStore from "./stores/drawer.header.store"

Vue.use(Vuex)

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
