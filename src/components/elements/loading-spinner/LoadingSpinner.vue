<template>
    <div
        class="vs-loading-spinner"
        data-test="vs-loading-spinner"
    >
        <div class="vs-loading-spinner__dot" />
        <div class="vs-loading-spinner__dot" />
        <div class="vs-loading-spinner__dot" />
        <div class="vs-loading-spinner__dot" />
        <div class="vs-loading-spinner__dot" />
        <div class="vs-loading-spinner__dot" />
        <div class="vs-loading-spinner__dot" />
    </div>
</template>
<script>

/**
 * The address component uses the HTML address tag
 * to display the data semantically.
 *
 * @displayName Loading Spinner
 */
export default {
    name: 'VsLoadingSpinner',
    status: 'prototype',
    release: '0.1.0',
};
</script>

<style lang="scss">
    @keyframes spin {
        from {
            transform:rotate(0deg);
        }
        to {
            transform:rotate(360deg);
        }
    }

    .vs-loading-spinner {
        width: $spacer-5;
        height: $spacer-5;
        position: relative;
        animation-name: spin;
        animation-duration: 3000ms;
        animation-iteration-count: infinite;
        animation-timing-function: linear;

        @for $i from 1 through 7 {
            .vs-loading-spinner__dot:nth-child(#{$i}) {
                transform: rotate($i * 40deg)
            }
        }
    }

    .vs-loading-spinner__dot {
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;

        &::after {
            content: "";
            position: absolute;
            top: 0;
            left: 50%;
            transform: translateX(-50%);
            width: $spacer-1;
            height: $spacer-1;
            border-radius: 50%;
            background-color: $color_purple;
        }
    }
</style>
