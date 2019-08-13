import Vuex from "vuex"
import Vue from "vue"

let store

Vue.use(Vuex)

if (!store) {
  store = new Vuex.Store({
    modules: {
      example: {
        namespaced: true,
        state: {
          /**
           * Example state
           */
          count: 0,
        },
        mutations: {
          /**
           * Example mutation
           */
          increment(state) {
            state.count++
          },
        },
        actions: {
          /**
           * Example action
           */
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
}

export default store
