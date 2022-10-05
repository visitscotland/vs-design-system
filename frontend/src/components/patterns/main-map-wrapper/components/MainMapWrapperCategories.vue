<template>
    <div
        class="vs-main-map-categories"
        data-test="vs-main-map-categories"
    >
        <!-- TO DO: make categories iterative once correct data
            is supplied -->
        <div class="vs-main-map-categories__category">
            <VsSvg
                class="vs-main-map-categories__icon"
                slot="svg"
                path="marker-cities"
            />
            <VsButton
                class="vs-main-map-categories__button text-capitalize"
                variant="transparent"
                icon="internal-link"
                icon-position="right"
                size="sm"
            >
                Cities
            </VsButton>
        </div>
        <div class="vs-main-map-categories__category">
            <VsSvg
                class="vs-main-map-categories__icon"
                slot="svg"
                path="marker-towns"
            />
            <VsButton
                class="vs-main-map-categories__button text-capitalize"
                variant="transparent"
                icon="internal-link"
                icon-position="right"
                size="sm"
            >
                Towns
            </VsButton>
        </div>
        <div class="vs-main-map-categories__category">
            <VsSvg
                class="vs-main-map-categories__icon"
                slot="svg"
                path="marker-islands"
            />
            <VsButton
                class="vs-main-map-categories__button text-capitalize"
                variant="transparent"
                icon="internal-link"
                icon-position="right"
                size="sm"
            >
                Islands
            </VsButton>
        </div>
        <div class="vs-main-map-categories__category">
            <VsSvg
                class="vs-main-map-categories__icon"
                slot="svg"
                path="marker-regions"
            />
            <VsButton
                class="vs-main-map-categories__button text-capitalize"
                variant="transparent"
                icon="internal-link"
                icon-position="right"
                size="sm"
            >
                Regions
            </VsButton>
        </div>
        <div class="vs-main-map-categories__category">
            <VsSvg
                class="vs-main-map-categories__icon"
                slot="svg"
                path="marker-featured"
            />
            <VsButton
                class="vs-main-map-categories__button text-capitalize"
                variant="transparent"
                icon="internal-link"
                icon-position="right"
                size="sm"
            >
                Featured Places
            </VsButton>
        </div>
    </div>
</template>

<script>
import VsButton from '@components/elements/button/Button';
import VsSvg from '@components/elements/svg/Svg';

export default {
    name: 'VsMainMapWrapperCategories',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsButton,
        VsSvg,
    },
};
</script>

<style lang="scss">
    .vs-main-map-categories {
        &__category {
            position: relative;
            display: flex;
            align-items: center;
            padding: $spacer-4 0;

            &::before {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                background: $color-gray-tint-5;
                height: 1px;
            }

            &:last-of-type {
                &::before {
                    display: none;

                    @include media-breakpoint-up(lg) {
                        display: block;
                    }
                }
            }
        }

        &__icon {
            width: 1.875rem;
            margin-right: $spacer-4;
        }

        &__button.vs-button.btn-transparent {
            font-size: $font-size-5;
            font-weight: $font-weight-bold;
            text-transform: none;
            justify-content: space-between;
            width: 100%;
            text-align: left;
            padding: 0;

            .vs-icon {
                fill: $color-pink;
            }

            @include media-breakpoint-up(lg) {
                font-size: $font-size-6;
                font-weight: $font-weight-normal;
            }
        }
    }
</style>
