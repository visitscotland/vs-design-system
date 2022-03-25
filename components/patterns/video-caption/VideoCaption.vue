<template>
    <div
        class="vs-video-caption"
        data-test="video-caption"
        v-if="videoLoaded"
    >
        <VsButton
            class="vs-video-caption__button"
            icon="play"
            size="md"
            ref="videoShow"
            @click.native="emitShowModal"
        >
            {{ videoBtnText }}
        </VsButton>

        <VsToggleButton
            v-if="withToggleBtn"
            @toggleAction="emitToggle"
        />

        <div class="vs-video-caption__details">
            <p class="vs-video-caption__title">
                <!-- @slot Slot for video title -->
                <slot name="video-title" />
            </p>

            <p class="vs-video-caption__duration">
                {{ videoDetails.videoDurationMsg }}
            </p>
        </div>
    </div>
    <div
        v-else
        class="vs-video-caption vs-video-caption--no-js"
        data-test="video-caption-nojs"
    >
        <div class="vs-video-caption__details">
            <div class="vs-video-caption__alert">
                <VsIcon
                    name="review"
                    custom-colour="gold"
                    size="lg"
                />

                <p>
                    <!-- @slot Slot for no-js alert message -->
                    <slot name="video-alert" />
                </p>
            </div>
        </div>
    </div>
</template>

<script>

/**
 * Caption to be used for opening a video
 *
 * @displayName Video Caption
 */
export default {
    name: 'VsVideoCaption',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * Text for the play video button
         */
        videoBtnText: {
            type: String,
            default: 'Play video',
        },
        /**
         * If the video button should include a toggle button
         * for another caption
         */
        withToggleBtn: {
            type: Boolean,
            default: false,
        },
        /**
         * The YouTube ID for the video
         */
        videoId: {
            type: String,
            required: true,
        },
    },
    computed: {
        videoDetails() {
            return videoStore.getters.getVideoDetails(this.videoId);
        },
        videoLoaded() {
            if (typeof this.videoDetails !== 'undefined' && this.videoDetails.videoDuration > 0) {
                return true;
            }

            return false;
        },
    },
    methods: {
        emitToggle() {
            this.$emit('toggleAction');
        },
        emitShowModal() {
            this.$root.$emit('bv::show::modal', this.videoId, '#videoShow');
        },
    },
};
</script>

<style lang="scss">
    .vs-video-caption {
        width: 100%;
        position: relative;

        &--no-js {
            display: none;
        }

        &__details {
            background-color: $color-gray-shade-6;
            color: $color-white;
            padding: $spacer-4 $spacer-3 $spacer-3;
        }

        .vs-toggle-btn {
            position: absolute;
            right: 0;
            top: 0;
        }

        .vs-toggle-btn.vs-button.btn {
            .vs-icon {
                margin: 0;
            }
        }

        &__title {
            font-size: $h4-font-size;
            font-weight: $font-weight-bold;
            margin-bottom: $spacer-2;
        }

        &__duration {
            font-size: $font-size-base;
            font-weight: $font-weight-light;
            margin: 0;
        }

        &__alert {
             display: none;

            .vs-icon {
                margin-right: $spacer-7;
            }

            p {
                font-size: $font-size-base;
                line-height: 2;
                margin: -10px 0 0;
            }
        }

        @include media-breakpoint-up(sm) {
            &__details {
                display: flex;
                align-items: baseline;
                padding: $spacer-4 $spacer-3 $spacer-5;
            }

            &__title {
                font-size: $lead-font-size;
                margin-right: $spacer-4;
                margin-bottom: 0;
            }
        }

        @include media-breakpoint-up(lg) {
            &__details {
                display: block;
            }

            &__title {
                margin-bottom: $spacer-1;
            }
        }
    }

    @include no-js {
        .vs-video-caption {
            &--no-js {
                display: block;
            }

            @include media-breakpoint-up(sm) {
                &__details {
                    max-width: 84%;
                    margin: 0 auto;
                    border-bottom: $color-secondary-gray 1px solid;
                }
            }

            @include media-breakpoint-up(lg) {
                &__details {
                    max-width: 100%;
                    margin: 0;
                    border-bottom: none;
                }
            }

            &__alert {
                display: flex;
                justify-content: flex-start;
            }

            &__title,
            &__duration,
            &__button,
            .vs-toggle-btn {
                display: none;
            }
        }
    }
</style>
