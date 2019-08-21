import Vuex from "vuex"
import Vue from "vue"

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    favourites: {
      namespaced: true,
      state: {
        count: 0,
      },
      mutations: {
        increment(state) {
          state.count++
        },
      },
      actions: {
        increment(context, message) {
          context.commit("increment")
          if (message) {
            alert(message)
          }
        },
      },
    },
  },
})
