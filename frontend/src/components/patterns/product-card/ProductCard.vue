<template>
    <section
        class="card vs-product-card"
        v-bind="$attrs"
    >
        <VsImg
            :src="imgSrc"
            :alt="imgAlt"
            class="vs-stretched-link-card__img"
            data-test="vs-stretched-link-card__img"
        />

        <div class="card-body">
            <VsHeading
                level="3"
                class="card-title vs-product-card__title"
                data-test="vs-product-card__title"
            >
                <VsLink
                    :href="link"
                    :type="linkType"
                    class="stretched-link"
                    data-test="vs-product-link"
                >
                    {{ title }}
                </VsLink>
            </VsHeading>
            <VsHeading
                level="4"
                class="vs-product-card__location"
                v-if="location"
                data-test="vs-product-card__location"
            >
                {{ location }}
            </VsHeading>
            <div
                class="vs-product-card__stars"
                v-if="stars.min && stars.max && stars.min !== stars.max"
            >
                <span
                    class="vs-product-card__star"
                    :class="{'vs-product-card__star--gold' : stars.gold}"
                >
                    &#9733;
                </span>
                {{ stars.min }}-{{ stars.max }}
            </div>
            <div
                class="vs-product-card__stars"
                v-else
            >
                <span
                    v-for="index in stars.min"
                    :key="index"
                    class="vs-product-card__star"
                    :class="{'vs-product-card__star--gold' : stars.gold}"
                >
                    &#9733;
                </span>
            </div>
            <div class="vs-product-card__categories">
                {{ transformedCategories }}
            </div>
            <div class="vs-product-card__description">
                <p>{{ description }}</p>
                <VsLink
                    href="detailLink.url"
                    class="vs-product-card__description-link"
                >
                    {{ detailLink.label }}
                </VsLink>
            </div>
            <div class="vs-product-card__summary-box">
                <VsContainer>
                    <VsRow>
                        <VsCol
                            cols="6"
                            class="vs-product-card__summary-item"
                        >
                            <div
                                class="vs-product-card__summary-price"
                                v-if="price"
                            >
                                <div
                                    v-if="priceIntro"
                                    class="vs-product-card__summary-price-intro"
                                >
                                    {{ priceIntro }}
                                </div>
                                <div
                                    v-if="price"
                                    class="vs-product-card__summary-price-main"
                                >
                                    {{ price }}
                                </div>
                                <div
                                    v-if="priceOutro"
                                    class="vs-product-card__summary-price-outro"
                                >
                                    {{ priceOutro }}
                                </div>
                            </div>
                        </VsCol>
                        <VsCol
                            cols="6"
                            class="vs-product-card__summary-item"
                        >
                            <div
                                class="vs-product-card__summary-website"
                                v-if="websiteLink"
                            >
                                <div v-if="websiteLinkIntro">
                                    {{ websiteLinkIntro }}
                                </div>
                                <VsLink
                                    href="websiteLink.url"
                                    :type="websiteLink.type || 'internal'"
                                    class="vs-product-card__website-link"
                                >
                                    {{ websiteLink.label }}
                                </VsLink>
                            </div>
                        </VsCol>
                    </VsRow>
                </VsContainer>
            </div>
        </div>
    </section>
</template>

<script>
import VsImg from '@components/elements/img/Img';
import VsHeading from '@components/elements/heading/Heading';
import VsLink from '@components/elements/link/Link';
import VsContainer from '@components/elements/layout/Container';
import VsRow from '@components/elements/layout/Row';
import VsCol from '@components/elements/layout/Col';

/**
* Generic product card for canned search
*
* @displayName Product Card
*/
export default {
    name: 'VsProductCard',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsImg,
        VsHeading,
        VsLink,
        VsContainer,
        VsRow,
        VsCol,
    },
    props: {
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
            type: String,
            default: '',
        },
        /**
        * The title of the product in the card
        */
        title: {
            required: true,
            type: String,
        },
        /**
        * The url the card should link to
        */
        link: {
            required: true,
            type: String,
        },
        /**
        * The type of the url the card should link to, defines which
        * icon appears next to the link
        * `external, internal, download`
        */
        linkType: {
            required: true,
            type: String,
        },
        /**
        * The location of the product in the card
        */
        location: {
            type: String,
            default: '',
        },
        /**
        * The star rating of the product, can have a `min`, `max` int
        * and a `gold` boolean
        */
        stars: {
            type: Object,
            default: null,
        },
        /**
        * A list of categories that describe the product, should contain
        * strings
        */
        categories: {
            type: Array,
            default: null,
        },
        /**
        * The description of the product, will be truncated if >2 lines
        */
        description: {
            type: String,
            default: '',
        },
        /**
        * An object containing a link to product details, should contain a `url`
        * field and a `label` field
        */
        detailLink: {
            type: Object,
            required: true,
        },
        /**
        * An object containing a link to product website, should contain a `url`
        * field and a `label` field. Setting this will cause the link to appear in the
        * right half of the summary box
        */
        websiteLink: {
            type: Object,
            default: null,
        },
        /**
        * A line of text that precedes the link to the product website, not rendered
        * if websiteLink not provided
        */
        websiteLinkIntro: {
            type: String,
            default: '',
        },
        /**
        * The price of the product. Setting this will cause the price to appear in the
        * left half of the summary box
        */
        price: {
            type: String,
            default: '',
        },
        /**
        * A line of text that precedes the product price, not rendered if price
        * not provided
        */
        priceIntro: {
            type: String,
            default: '',
        },
        /**
        * A line of text that appears after the product price, not rendered if price
        * not provided
        */
        priceOutro: {
            type: String,
            default: '',
        },
    },
    computed: {
        transformedCategories() {
            return this.categories
                .slice(0, 3)
                .join(' | ');
        },
    },
};
</script>

<style lang="scss">
    .card.vs-product-card {
        transition: box-shadow $duration-slowly;
        border: none;
        position: relative;

        &:hover {
            box-shadow: 10px 10px 20px $color-gray-tint-4;

            .megalink-link-list__title {
                text-decoration: underline;
            }
        }

        .card-body {
            padding: $spacer-1 $spacer-0 $spacer-0;
        }

        .stretched-link {
            color: $color-base-text;
            text-decoration: none;
            letter-spacing: 0;
            display: block;

            &--disabled {
                cursor: default;
            }

            &:focus {
                outline: 2px solid $color-pink;
            }
        }

        .vs-product-card__title {
            font-size: $small-font-size;
            line-height: $line-height-s;
            letter-spacing: 1px;
            color: $color-base-text;
            display: flex;
            margin: $spacer-1 $spacer-0;

            a {
                letter-spacing: inherit;
            }
        }

        .vs-product-card__location {
            font-family: $font-family-base;
            font-size: $small-font-size;
            line-height: $line-height-xs;
            color: $color-secondary-teal-shade-2;
            letter-spacing: normal;
            margin: $spacer-0;
        }

        .vs-product-card__star {
            color: $color-purple;

            &--gold {
                color: $color-yellow;
            }
        }

        .vs-product-card__categories {
            font-size: $small-font-size;
            line-height: $line-height-s;
            font-weight: 600;
            margin-bottom: $spacer-1;
        }

        .vs-product-card__description {
            font-size: $small-font-size;
            line-height: $line-height-s;

            p {
                display: -webkit-box; //ToDo
                -webkit-line-clamp: 2;
                margin-bottom: $spacer-1;
            }
        }

        .vs-product-card__summary-box {
            padding: $spacer-2;
            margin-top: $spacer-2;
            background-color: $color-gray-tint-7;

            .row {
                align-items: center;
            }
        }

        .vs-product-card__summary-item {
            font-size: $small-font-size;
            line-height: $line-height-s;
            text-align: center;

            &:not(:last-child) {
                border-right: 1px solid $color-gray-tint-1;
            }
        }

        .vs-product-card__summary-price {
            &-main {
                font-weight: bold;
            }

            &-outro {
                font-size: $xs-font-size;
            }
        }
    }
</style>

<docs>
    ```
        <VsContainer>
            <VsRow>
                <VsCol cols="12" md="6" lg="4">
                    <VsProductCard
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text"
                        title="Glenegedale House"
                        link="https://google.com"
                        linkType="external"
                        location="Lamlash, Isle of Arran"
                        :stars="{min:3, max:3, gold:false}"
                        :categories="['Island Location', 'Rural', 'Remote', 'Lochside Location']"
                        description="On the shores of Lamlash Bay overlooking Holy Isle, an
                        ideal base from which to explore the enchanting Isle."
                        :detailLink="{link: 'https://twitter.com', label: 'View Details'}"
                        websiteLinkIntro="Visit"
                        :websiteLink="{link: 'https://facebook.com', label: 'Website', type: 'external'}"
                        priceIntro="Price from"
                        price="£50.00"
                        priceOutro="Per person, per night"
                    >
                    </VsProductCard>
                </VsCol>
            </VsRow>
        </VsContainer>
    ```
</docs>
