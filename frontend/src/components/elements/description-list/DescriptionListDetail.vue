<template>
    <dd
        :class="[
            $style.detail,
            {
                col: inline,
                [$style.styled]: !inline,
            }
        ]"
    >
        <div class="position-relative">
            <slot />
        </div>
    </dd>
</template>

<script>
/**
 * Renders a dd element of the styled/inline description list
 */

export default {
    name: 'VsDescriptionListDetail',
    status: 'prototype',
    release: '0.0.1',
    /**
     * Injects inline prop provided by DescriptionList
     */
    inject: ['inline'],
};
</script>

<style lang="scss">
@import "~bootstrap/scss/type";
</style>

<style lang="scss" module>
.root {
    margin-bottom: 0;
}

// Responsive columns to achieve default 2 column list layout
.styled {
    @include make-col(8);
    @include make-col-offset(4);

    @include media-breakpoint-down(md) {
        @include make-col(6);
        @include make-col-offset(6);
    }

    @include media-breakpoint-down(sm) {
        @include make-col(12);
        @include make-col-offset(0);
    }
}
</style>
