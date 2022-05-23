<template>
    <div
        class="vs-summary-box"
        data-test="vs-summary-box-list"
    >
        <ul class="vs-summary-box__list list-unstyled d-flex flex-wrap p-2 p-sm-3">
            <!-- Default slot, should load in a set of 4 SummaryBoxListItem and
            SummaryBoxDistanceListItem elements -->
            <slot />
        </ul>
    </div>
</template>

<script>
/**
 * Summary Box - Wraps a number of VsSummaryBoxListItem and VsSummaryBoxDistanceListItem components
 *
 * @displayName Summary Box
 */
export default {
    name: 'VsSummaryBoxList',
    status: 'prototype',
    release: '0.0.1',
};
</script>

<style lang="scss">
.vs-summary-box {
    display: flex;
    justify-content: center;

    @include media-breakpoint-up(lg) {
        position: absolute;
        top: 0;
        width: calc(100% - #{$spacer-8});
        z-index: 1000;
    }
}

.vs-summary-box__list {
    background-color: $color-yellow;
    width: 350px;

    @include media-breakpoint-up(lg) {
        width: 400px;
    }
}
</style>