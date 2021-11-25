<template>
    <div
        class="vs-video-caption"
        data-test="video-caption"
    >
        <VsButton class="vs-video-caption__button">
            {{ videoBtnText }}
        </VsButton>

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
    },
    props: {
        videoBtnText: {
            type: String,
            required: true,
            default: 'Play video',
        },
    },
};
</script>

<style lang="scss">
    .vs-video-caption {
        width: 100%;

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
    ```
</docs>
