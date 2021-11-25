<template>
    <div
        class="vs-video-caption"
        data-test="video-caption"
    >
        <VsButton class="vs-video-caption__button">
            {{ videoBtnText }}
        </VsButton>

        <VsToggleButton
            v-if="withToggleBtn"
            @toggleAction="emitToggle"
        />

        <div class="vs-video-caption__details">
            <div class="vs-video-caption__alert">
                <VsIcon
                    name="information"
                />

                <p>
                    <slot name="video-alert" />
                </p>
            </div>

            <p>
                <!-- @slot Slot for video title -->
                <slot name="video-title" />
            </p>

            <p>
                <!-- @slot Slot for video duration text -->
                <slot name="video-duration" />
            </p>
        </div>
    </div>
</template>

<script>
import VsButton from '@components/elements/button/Button';
import VsIcon from '@components/elements/icon/Icon';
import VsToggleButton from '@components/patterns/toggle-button/ToggleButton';
/**
 * Caption to be used for opening a video
 *
 * @displayName Video Caption
 */
export default {
    name: 'VsVideoCaption',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsButton,
        VsIcon,
        VsToggleButton,
    },
    props: {
        /**
         * Text for the play video button
         */
        videoBtnText: {
            type: String,
            required: true,
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
    },
    methods: {
        emitToggle() {
            this.$emit('toggleAction', this.show);
        },
    },
};
</script>

<style lang="scss">
    .vs-video-caption {
        width: 100%;
        position: relative;

        &__details {
            background-color: $color-gray-shade-6;
            color: $color-white;
            padding: $spacer-3;

            p {
                margin: 0;
            }
        }

        &__alert {
            display: none;
        }

        .vs-toggle-btn {
            position: absolute;
            right: 0;
            top: 0;
        }

        @include media-breakpoint-up(sm) {
            width: 310px;
        }
    }

    @include no-js {
        .vs-video-caption {
            &__alert {
                display: block;
            }

            &__wrapper {
                display: none;
            }
        }
    }
</style>

<docs>
    ``` jsx
    <div class="mb-5">
        <VsVideoCaption>
            <template slot="video-title">
                This is the video title
            </template>
            <template slot="video-duration">
                This is the video length
            </template>
            <template slot="video-alert">
                This is no-js alert message
            </template>
        </VsVideoCaption>
    </div>

    <VsVideoCaption
        withToggleBtn
    >
        <template slot="video-title">
            This is the video title
        </template>
        <template slot="video-duration">
            This is the video length
        </template>
        <template slot="video-alert">
            This is no-js alert message
        </template>
    </VsVideoCaption>
    ```
</docs>
