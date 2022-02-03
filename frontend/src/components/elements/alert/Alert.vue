<template>
    <div
        class="vs-alert"
        data-test="vs-alert"
    >
        <div class="vs-alert__icon-holder">
            <VsSvg
                class="vs-alert__icon"
                path="info-toggle"
            />
        </div>
        <div class="vs-alert__content-wrapper">
            <div class="vs-alert__content">
                <slot />
            </div>
        </div>
    </div>
</template>
<script>
import VsSvg from '@components/elements/svg';

/**
 * A simple alert component, displays text in a responsive, highlighted box.
 * Should be used at the top of a block of content or page to provide
 * important contextual information to the user.
 *
 * @displayName Alert
 */
export default {
    name: 'VsAlert',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsSvg,
    },
};
</script>

<style lang="scss">
    .vs-alert {
        display: flex;
        flex-flow: row;
    }

    .vs-alert__icon-holder {
        background-color: $color-pink-shade-1;
        display: inline-flex;
        justify-content: center;
        align-items: start;
        padding: $spacer-4 $spacer-0;
        width: 2.5rem;
        min-height: 2.5rem;

        @include media-breakpoint-up(lg) {
            width: $spacer-9;
            min-height: $spacer-9;
        }
    }

    .vs-alert__icon {
        fill: $color-pink-shade-1;
        width: $spacer-4;
        width: $spacer-4;

        @include media-breakpoint-up(lg) {
            width: $spacer-6;
            width: $spacer-6;
        }
    }

    .vs-alert__content-wrapper {
        flex: 1;
        display: flex;
        align-items: flex-start;
    }

    .vs-alert__content {
        display: flex;
        align-items: center;
        padding: $spacer-3;
        background-color: $color-pink-tint-6;
        border: 1px solid $color-pink-tint-1;
        min-height: 100%;
    }
</style>

<docs>
  ```js
    <VsAlert>
        <span>This guide is only for visitors living outside the UK.</span>
    </VsAlert>

    <br>

    <VsAlert>
        <span>This guide is only for visitors living outside the UK.
        This guide is only for visitors living outside the UK.</span>
    </VsAlert>

    <br>

    <VsAlert>
        <span>This guide is only for visitors living outside the UK.
        This guide is only for visitors living outside the UK.
        This guide is only for visitors living outside the UK.</span>
    </VsAlert>
  ```
</docs>
