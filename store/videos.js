export const state = () => ({
  videos: [],
});

export const mutations = {
  CREATE_VIDEO_ENTRY: (state, payload) => {
      state.videos.push({
          videoId: payload.id,
          videoDurationMsg: payload.durationMsg,
          videoDuration: payload.duration,
      });
  },
  add(state, text) {
    state.videos.push({
      text,
      done: false
    });
  },
  remove(state, { todo }) {
    state.videos.splice(state.videos.indexOf(todo), 1);
  },
  toggle(state, todo) {
    todo.done = !todo.done;
  }
};

export const actions = {
  newVideoRef: ({ commit }, payload) => {
      commit('CREATE_VIDEO_ENTRY', payload);
      return true;
  },
};

export const getters = {
  // eslint-disable-next-line arrow-body-style
  getVideoDetails: (state) => (id) => {
      return state.videos.find((video) => video.videoId === id);
  },
};