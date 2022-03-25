<template>
    <div
        class="vs-megalink-link-list"
        :class="`vs-megalink-link-list--${theme}`"
    >
        <VsStretchedLinkCard
            :link="linkUrl"
            :type="linkType"
            class="vs-megalink-link-list__wrapper"
            :img-src="imgSrc"
            :img-alt="imgAlt"
            :theme="theme"
        >
            <span
                slot="stretchedCardHeader"
                class="vs-megalink-link-list__title"
                data-test="megalink-link-list__title"
            >
                <!-- @slot Slot to contain heading -->
                <slot name="vsLinkListHeading" />
            </span>

            <VsRichTextWrapper
                slot="stretchedCardContent"
                class="lead vs-megalink-link-list__content"
                data-test="megalink-link-list__content"
            >
                <!-- @slot Slot to contain content -->
                <slot name="vsLinkListContent" />
            </VsRichTextWrapper>
        </VsStretchedLinkCard>
    </div>
</template>

<script>
/**
* Megalink link list cards to be used in the megalinks component
* There is a standard and featured variant.
*
* @displayName Megalinks Link List
*/

export default {
    name: 'VsMegalinkLinkList',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
        * The source of the image used in the component
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
        * The component color theme
        */
        theme: {
            type: String,
            default: 'light',
            validator: (value) => value.match(/(light|dark)/),
        },
        /**
        * The link destination
        */
        linkUrl: {
            type: String,
            required: true,
        },
    },
};
</script>

<style lang="scss">
    .vs-megalink-link-list {
        border: none;
        padding: $spacer-2 0 $spacer-3;
        position: relative;
        height: 100%;

        &:after {
            content: '';
            border-bottom: 1px solid $color-gray-tint-5;
            position: absolute;
            width: calc(100% - 16px);
            left: 8px;
            bottom: 0;
        }

        .vs-megalink-link-list__wrapper.card {
            display: flex;
            flex-direction: row;
            padding: $spacer-2;
            border: none;
            height: 100%;
            background: transparent;
            transition: box-shadow $duration-slowly;

            &:hover {
                box-shadow: $shadow_card;
            }

            .stretched-link {
                text-decoration: none;
            }

            .card-body {
                background: transparent;
                padding: 0;
                align-self: flex-start;
                width: 66%;
            }

            .vs-stretched-link-card__img {
                width: 33%;
                max-width: 33%;
                align-self: flex-start;
                margin-right: $spacer-4;
            }

            .vs-megalink-link-list__title {
                font-size: $font-size-sm;
                letter-spacing: $letter-spacing-l;
                line-height: $line-height-m;
            }

            .card-title {
                display: flex;
                margin-bottom: 0;
                margin-top: 0;

                & > a {
                    line-height: 0;
                }
            }

            .vs-megalink-link-list__content {
                display: none;

                p {
                    display: -webkit-box;
                    -webkit-line-clamp: 3;
                    -webkit-box-orient: vertical;
                    overflow: hidden;
                }
            }
        }

        &--dark {
            .vs-megalink-link-list__wrapper.card {
                .vs-megalink-link-list__title {
                    color: $color-white;
                }
            }
        }

        @include media-breakpoint-up(sm) {
            .megalink-link-list__wrapper.card {
                .megalink-link-list__content {
                    display: block;
                }
            }
        }

        @include media-breakpoint-up(md) {
            .vs-megalink-link-list__wrapper.card {
                .vs-megalink-link-list__title {
                    font-size: $small-font-size;
                }

                .vs-megalink-link-list__content {
                    margin: $spacer-2 0 0;
                    line-height: $line-height-s;
                    display: block;

                    p {
                        display: -webkit-box;
                        -webkit-line-clamp: 3;
                        -webkit-box-orient: vertical;
                        overflow: hidden;
                        font-size: $font-size-md;
                        margin-bottom: 0;
                    }
                }
            }

            @include media-breakpoint-up(lg) {
                .vs-megalink-link-list__wrapper.card {
                    .vs-megalink-link-list__content p {
                         font-size: $font-size-base;
                    }
                }
            }
        }
    }
</style>
