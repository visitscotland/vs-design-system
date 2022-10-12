<template>
    <div
        class="vs-main-map-wrapper-detail"
        data-test="vs-main-map-wrapper-detail"
    />
</template>

<script>
import VsImg from '@components/elements/img/Img';

/**
 * Renders a details of a place in the map component.
 * Content is received by a data object
 *
 * @displayName Main Map Wrapper Detail
 */
export default {
    name: 'VsMainMapWrapperDetail',
    status: 'prototype',
    release: '0.0.1',
    components: [
        VsImg,
    ],
    props: {
        /**
         * Data for component content
         */
        contentData: {
            type: Object,
            required: true,
        },
    },
};
</script>

<style lang="scss">

</style>
