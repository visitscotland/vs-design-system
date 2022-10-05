<template>
    <section
        class="vs-main-map-wrapper"
        data-test="vs-main-map-wrapper"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    class="vs-main-map-wrapper__side-panel"
                    md="4"
                />
                <VsCol
                    class="vs-main-map-wrapper__map"
                    md="8"
                >
                    <VsMap
                        :labels="{
                        }"
                        :pins="[
                        ]"
                    />
                </VsCol>
            </VsRow>
        </VsContainer>
    </section>
</template>

<script>
import {
    VsContainer,
    VsRow,
    VsCol,
} from '@components/elements/grid';
import VsMap from '@components/elements/map/Map';
/**
 * Renders a widget that display a map
 * and gives filtering options
 *
 * @displayName Main Map Wrapper
 */

export default {
    name: 'VsMainMapWrapper',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsContainer,
        VsRow,
        VsCol,
        VsMap,
    },
};
</script>

<style lang="scss">
    .vs-main-map-wrapper {
        height: 100vh;

        @include media-breakpoint-up(md) {
            height: 500px;
        }

        .container,
        .row {
            height: 100%;
        }

        &__map {
            display: none;
            height: 100%;
            width: 100%;

            @include media-breakpoint-up(md) {
                display: block;
            }
        }
    }
</style>
