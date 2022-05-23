export const state = () => ({
    videos: []
})
export const mutations = {
    CREATE_VIDEO_ENTRY: (state, payload) => {
        state.videos.push({
            videoId: payload.id,
            videoDurationMsg: payload.durationMsg,
            videoDuration: payload.duration,
        });
    },
}
export const actions = {
    newVideoRef: ({ commit }, payload) => {
        commit('CREATE_VIDEO_ENTRY', payload);
        return true;
    },
},
    getters: {
        // eslint-disable-next-line arrow-body-style
        getVideoDetails: (state) => (id) => {
            return state.videos.find((video) => video.videoId === id);
        },
    },
});
