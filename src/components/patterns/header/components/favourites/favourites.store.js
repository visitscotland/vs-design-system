import Vuex from "vuex"
import Vue from "vue"

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    favourites: {
      namespaced: true,
      state: {
        favourites: [],
      },
      mutations: {
        ADD_FAVOURITE: (state, payload) => {
          var newFavourite = {
            href: payload.href,
            title: payload.title,
          }
          state.favourites.unshift(newFavourite)
        },
        DELETE_FAVOURITE: (state, payload) => {
          var index = state.favourites.findIndex(favourite => favourite.href === payload)
          state.favourites.splice(index, 1)
        },
      },
      actions: {
        addFavourite: (context, payload) => {
          context.commit("ADD_FAVOURITE", payload)
        },
        deleteFavourite: (context, payload) => {
          console.log(payload)
          context.commit("DELETE_FAVOURITE", payload)
        },
      },
      getters: {
        getFavourites: state => state.favourites,
        getFavouritesCount: state => state.favourites.length,
      },
    },
  },
})
