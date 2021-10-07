<template>
    <figcaption
        ref="figcaption"
        data-test="vs-caption"
        class="vs-caption"
        :class="captionClasses"
    >
        <VsRow class="justify-content-center justify-content-sm-start">
            <VsCol
                class="order-2 order-sm-1"
                :class="[!showMap ? 'align-self-center' : '']"
            >
                <div class="caption-info">
                    <p class="image-caption">
                        <slot name="caption" />
                    </p>

                    <p class="image-credit">
                        <slot name="credit" />
                    </p>

                    <slot name="social-link" />
                </div>
            </VsCol>
            <VsCol
                class="col-12 col-sm-auto order-1
                order-sm-2 pl-sm-0 align-self-end
                align-self-sm-start"
                v-if="showMap && variant !== isLargeCaption"
            >
                <div class="map-wrapper pt-3 pt-sm-2 pb-sm-2 pr-sm-4 mx-auto">
                    <VsImageLocationMap
                        :latitude="latitude"
                        :longitude="longitude"
                        :map-outline-color="tokens.color_white"
                        :map-marker-color="tokens.color_secondary_teal_tint_3"
                    />
                </div>
            </VsCol>
        </VsRow>
    </figcaption>
</template>

<script>
import { VsRow, VsCol } from '@components/elements/layout';
import VsImageLocationMap from '@components/patterns/image-location-map/ImageLocationMap';
import designTokens from '@/assets/tokens/tokens.json';

/**
 * TODO: Document usage
 *
 * @displayName Caption
 */
export default {
    name: 'VsCaption',
    components: {
        VsRow,
        VsCol,
        VsImageLocationMap,
    },
    props: {
        /**
         * Option to choose which variant to show
         * `fullwidth, large`
         */
        variant: {
            type: String,
            default: 'fullwidth',
            validator: (value) => value.match(/(fullwidth|large)/),
        },
        /**
         * The image latitude
         */
        latitude: {
            type: String,
            default: '',
        },

        /**
         * The image longitude
         */
        longitude: {
            type: String,
            default: '',
        },

        /**
         * Option to choose text alignment
         * `left, right`
         */
        textAlign: {
            type: String,
            default: 'left',
            validator: (value) => value.match(/(left|right)/),
        },
    },
    data() {
        return {
            tokens: designTokens,
        };
    },
    computed: {
        showMap() {
            return !!(this.longitude && this.latitude);
        },
        isLargeCaption() {
            return this.variant === 'large';
        },
        captionClasses() {
            return [
                {
                    'large-caption-wrapper': this.isLargeCaption,
                    'fullwidth-caption-wrapper': !this.isLargeCaption,
                },
                `caption-wrapper--${this.textAlign}`,
            ];
        },
    },
};
</script>

<style lang="scss">
.vs-caption {
    background-color: $color-gray-shade-6;
    color: $color-white;

    .image-caption,
    .image-credit {
        font-size: $small-font-size;
        line-height: $line-height-standard;
    }

    .image-caption {
        font-weight: $font-weight-semi-bold;
    }

    .image-credit {
        font-weight: $font-weight-light;
        margin-bottom: $spacer-0;
    }

    &.large-caption-wrapper,
    &.fullwidth-caption-wrapper {
        position: absolute;
        top: 0;
        right: 0;
        width: 100%;
        height: 100%;
        z-index: 2;
        display: flex;
        text-align: center;

        @include media-breakpoint-up(sm) {
            display: block;
        }

        > .row {
            margin: 0 auto;
        }

        .image-caption {
            margin-bottom: $spacer-2;
        }

        .map-wrapper {
            max-width: 54px;
        }
    }

    &.fullwidth-caption-wrapper{
        @include media-breakpoint-up(sm) {
            position: relative;
            width: 100%;
            height: auto;
            min-height: 64px;
            text-align: left;
        }
        .caption-info{
            padding: $spacer-3 0;
        }
    }

    &.large-caption-wrapper {
        @include media-breakpoint-up(sm) {
            bottom: -48px;
            top: auto;
            width: 310px;
            height: auto;
            min-height: 96px;
            text-align: left;

            > .row {
                margin: 0 -16px;
            }

            .image-caption {
                margin-bottom: $spacer-8;
            }

            .map-wrapper {
                max-width: 74px;
            }
        }

        .caption-info{
            padding: $spacer-4;
        }
    }

    @include media-breakpoint-up(md) {
        &.caption-wrapper--right {
            p{
                text-align: right;
            }
        }
    }

}
@include no-js {
    .vs-image-with-caption__caption-wrapper{
        @include media-breakpoint-down(xs) {
            .vs-caption.large-caption-wrapper {
                position: relative;
                top: 0;
                width: 100%;
                height: auto;
                text-align: left;
                display: block;

                .order-2 {
                    order: 1;
                }

                .order-1 {
                    order: 2;
                    flex: 0 0 auto;
                    width: auto;
                    max-width: 100%;
                    align-self: auto!important;
                }

                .map-wrapper {
                    padding-top: 0!important;
                }

                .image-caption {
                    margin-bottom: $spacer-5;
                }

                .caption-info{
                    padding: $spacer-3 $spacer-2;
                }
            }
        }

        .large-caption-wrapper,
        .fullwidth-caption-wrapper {
            @include media-breakpoint-down(xs) {
                position: relative;
            }
        }

        .fullwidth-caption-wrapper {
            display: block;
            text-align: left;
        }
    }
}
</style>

<docs>
  ```js
    <h3>Fullwidth Caption Style</h3>
    <VsCaption
        variant="fullwidth"
        style="max-width:500px"
    >
        <span slot="caption">
            A Scottish Castle
        </span>

        <span slot="credit">
            VisitScotland
        </span>
    </VsCaption>

    <h3 style="margin-top: 3rem;">Large Caption Style</h3>
    <VsCaption
        :latitude="55.9485947"
        :longitude="-3.2021022"
        variant="large"
        style="position: relative; bottom: 0;"
    >
        <span slot="caption">
            A Scottish Castle
        </span>

        <span slot="credit">
            VisitScotland
        </span>
    </VsCaption>
  ```
</docs>
