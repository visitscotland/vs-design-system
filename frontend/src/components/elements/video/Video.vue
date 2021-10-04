<template>
    <div
        class="vs-video"
        data-test="vs-video"
    >
        <div class="vs-video__iframe-wrapper">
            <!-- eslint-disable-next-line vue/component-name-in-template-casing -->
            <youtube
                :video-id="videoId"
                ref="youtube"
            />
        </div>

        <p
            class="vs-video__duration"
            data-test="vs-video-duration"
            v-if="showDuration"
        >
            Video duration: {{ duration.minutes }}:{{ duration.seconds }}
        </p>
    </div>
</template>

<style lang="scss">
    .vs-video {
        &__iframe-wrapper {
            aspect-ratio: 16 / 9;
            position: relative;

            iframe {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
            }
        }
    }
</style>

<script>
// eslint-disable-next-line import/no-extraneous-dependencies
import VueYoutube from 'vue-youtube';
import Vue from 'vue';

Vue.use(VueYoutube, {
    global: false,
});

/**
 * This component embeds a YouTube video on to the page.
 *
 * It also uses the YouTube Player API so it can be controlled by
 * other components. It listens for a 'video-controls' event which
 * can be used to play or pause the video.
 *
 * It also calculates duration of a video for use in components.
 *
 * @displayName Video
 */

export default {
    name: 'VsVideo',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
        * The YouTube ID for the video
        */
        videoId: {
            type: String,
            required: true,
        },
    },
    data() {
        return {
            duration: {
                minutes: 0,
                seconds: 0,
            },
            showDuration: false,
        };
    },
    computed: {
        /**
         * Return the player instance
         */
        player() {
            return this.$refs.youtube.player;
        },
    },
    mounted() {
        /**
         * Upon promise resolution, if the video ID returns
         * a YouTube video, process the time into the desired format.
         */
        this.player.getDuration().then((response) => {
            if (response === 0) {
                this.showDuration = false;
            } else {
                this.showDuration = true;
                this.formatTime(response);
            }
        });

        /**
         * Sets up listener for play/pause events
         * from $root
         */
        this.$root.$on('video-controls', (action) => {
            if (action === 'play') {
                this.playVideo();
            } else if (action === 'pause') {
                this.pauseVideo();
            }
        });
    },
    methods: {
        /**
         * Plays the video
         */
        playVideo() {
            this.player.playVideo();
        },
        /**
         * Pauses the video
         */
        pauseVideo() {
            this.player.pauseVideo();
        },
        /**
         * Converts time in seconds to minutes and seconds,
         * returns an object.
         */
        formatTime(timeInSeconds) {
            const minutes = Math.floor(timeInSeconds / 60);
            const seconds = timeInSeconds - (minutes * 60);

            this.duration.minutes = minutes;
            this.duration.seconds = seconds;
        },
    },
};
</script>

<docs>
  ```jsx
    <VsContainer>
        <VsRow>
            <VsCol md="6">
                <VsVideo
                    video-id="c05sg3G4oA4"
                    :show-duration="true"
                />
            </VsCol>
        </VsRow>
        <VsRow>
            <VsCol>
                <VsButton
                    @click.native="$root.$emit('video-controls', 'play')"
                    @keydown="$root.$emit('video-controls', 'play')"
                >
                    Play
                </VsButton>

                <VsButton
                    @click.native="$root.$emit('video-controls', 'pause')"
                    @keydown="$root.$emit('video-controls', 'pause')"
                >
                    Pause
                </VsButton>
            </VsCol>
        </VsRow>
    </VsContainer>
  ```
</docs>
