import Vuex from "vuex"
import Vue from "vue"

Vue.use(Vuex)

export default new Vuex.Store({
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
