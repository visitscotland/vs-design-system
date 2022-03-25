<template>
    <VsStretchedLinkCard
        :link="linkUrl"
        :type="linkType"
        class="vs-megalink-multi-image p-2"
        :class="multiImageClasses"
        :img-src="imgSrc"
        :img-alt="imgAlt"
        :data-test="featured ? 'megalink-multi-image-featured' : 'megalink-multi-image-card'"
        :theme="theme"
    >
        <span
            slot="stretchedCardHeader"
            class="vs-megalink-multi-image__title"
            data-test="megalink-multi-image__title"
        ><!-- @slot Slot to contain heading --><slot name="vsMultiImageHeading" /></span>

        <VsRichTextWrapper
            slot="stretchedCardContent"
            class="lead vs-megalink-multi-image__content"
            data-test="megalink-multi-image__content"
        >
            <!-- @slot Slot to contain content -->
            <slot name="vsMultiImageContent" />
        </VsRichTextWrapper>
    </VsStretchedLinkCard>
</template>

<script>
/**
* Megalink cards to be used in the megalinks component
* There is a standard and featured variant.
 *
 * @displayName Megalinks Multi-Image
*/

export default {
    name: 'VsMegalinkMultiImage',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
        * If the component should be the featured variant
        */
        featured: {
            required: false,
            type: Boolean,
            default: false,
        },
        /**
        * If the featured component is the last one
        */
        lastFeatured: {
            required: false,
            type: Boolean,
            default: false,
        },
        /**
        * The image to use in the component
        */
        imgSrc: {
            required: true,
            type: String,
        },
        /**
        * The image alt text to use in the component
        */
        imgAlt: {
            required: false,
            type: String,
            default: '',
        },
        /**
        * The type of link. This will set the icon.
        * `external, internal, download`
        */
        linkType: {
            type: String,
            required: true,
            validator: (value) => value.match(/(default|external|internal|download)/),
        },
        /**
        * The link destination
        */
        linkUrl: {
            type: String,
            required: true,
        },
        /**
        * The component color theme
        */
        theme: {
            type: String,
            default: 'light',
            validator: (value) => value.match(/(light|dark)/),
        },
    },
    computed: {
        multiImageClasses() {
            return [
                `vs-megalink-multi-image--${this.theme}`,
                {
                    'vs-megalink-multi-image--featured': this.featured,
                    'vs-megalink-multi-image--featured-last': this.lastFeatured,
                },
            ];
        },
    },
};
</script>

<style lang="scss">
    .vs-megalink-multi-image.card {
        border: none;
        background: transparent;
        position: relative;
        margin-bottom: $spacer-8;
        transition: box-shadow $duration-slowly;

        .vs-stretched-link {
            color: $color-base-text;
            text-decoration: none;
            letter-spacing: 0;

            &:hover {
                .vs-megalink-multi-image__title {
                    text-decoration: underline;
                }
            }

            &:focus {
                outline: 2px solid $color-theme-primary;
            }
        }

        .card-body {
            padding: $spacer-4 0 $spacer-2;
            width: 100%;
        }

        .vs-megalink-multi-image__img {
            max-width: 100%;
        }

        &:hover {
            box-shadow: $shadow_card;
        }

        .vs-megalink-multi-image__title {
            font-size: $font-size-sm;
            line-height: $line-height-s;
            letter-spacing: $letter-spacing-xl;
        }

        .card-title {
            margin-bottom: 0;
            margin-top: 0;
        }

        .vs-link__icon {
            height: 12px;
            width: 12px;
        }

        .vs-megalink-multi-image__content {
            margin-top: $spacer-2;
            line-height: $line-height-s;

            p:last-of-type {
                margin-bottom: 0;
            }
        }
    };

    .vs-megalink-multi-image--dark.card {
        .vs-stretched-link-card__title {
            .stretched-link {
                color: $color-white;
            }
        }

        &:hover {
            box-shadow: 10px 10px 20px $color-theme-dark;
        }
    }

    @include media-breakpoint-up(xl) {
        .vs-megalink-multi-image.card {
            margin-bottom: $spacer-11;

            .vs-megalink-multi-image__title {
                font-size: $small-font-size;
                line-height: $line-height-s;
            }

            .card-body {
                padding-bottom: $spacer-5;
            }
        }

        .vs-megalink-multi-image--featured.card {
            display: flex;
            flex-direction: row;
            justify-content: flex-start;

            .vs-megalink-multi-image__title {
                font-size: $h3-font-size;
                letter-spacing: $letter-spacing-xxl;
            }

            .vs-link__icon {
                height: 16px;
                width: 16px;
            }

            .vs-stretched-link-card__img {
                width: calc(50% - 20px);
            }

            .megalink-multi-image__content {
                font-size: $font-size-md;
                margin-top: $spacer-8;
                line-height: $line-height-m;
            }

            .card-body {
                max-width: calc(50% + 20px);
                padding: $spacer-6 5% $spacer-5;
            }

            &.vs-megalink-multi-image--featured-last {
                flex-direction: row-reverse;
                // margin-top: $spacer-12;
            }
        }

        @include media-breakpoint-up(xl) {
            .megalink-multi-image--featured.card {
                .card-body {
                    padding: $spacer-9 5% $spacer-5;
                }
                .megalink-multi-image__content {
                    margin-top: $spacer-8;
                }
            }
        }
    }
</style>
