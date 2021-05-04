<template>
    <div
        class="vs-theme-wrapper"
        v-bind="$attrs"
    >
        <!-- @slot Default slot for module content -->
        <slot />
    </div>
</template>

<script>
/**
* Module wrapper to attach generic theme classes
*
* @displayName Theme Wrapper
*/
export default {
    name: 'VsThemeWrapper',
    status: 'prototype',
    release: '0.0.1',
};
</script>

<style lang="scss">
    .vs-theme-wrapper {
        &.theme-grey {
            background-color: $theme-grey;
        }

        &.theme-dark {
            background-color: $theme-dark;
            color: $color-white;
        }
    }
</style>

<docs>
    ```
        <VsThemeWrapper class="theme-grey">
            <div class="p-3">
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                <p>Donec porta quis sapien nec gravida. Duis et blandit urna.</p>
                <p>Donec condimentum enim id ipsum sollicitudin, non imperdiet velit.</p>
            </div>
        </VsThemeWrapper>

        <VsThemeWrapper class="theme-dark">
            <div class="p-3">
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                <p>Donec porta quis sapien nec gravida. Duis et blandit urna.</p>
                <p>Donec condimentum enim id ipsum sollicitudin, non imperdiet velit.</p>
            </div>
        </VsThemeWrapper>
    ```
</docs>
