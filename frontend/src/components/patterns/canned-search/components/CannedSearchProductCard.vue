<template>
    <VsCol
        :cols="slideCols.xs"
        :sm="slideCols.sm"
        :lg="slideCols.md"
        :xl="slideCols.lg"
        class="vs-carousel-slide__card"
    >
        <div
            class="vs-product-card"
            :class="!isVisible(slideIndex) ? 'vs-product-card--disabled' : ''"
            data-test="vs-product-card"
        >
            <section
                class="card"
                v-bind="$attrs"
            >
                <div class="vs-product-card__image-container">
                    <div class="vs-product-card__image-inner-container">
                        <VsImg
                            :src="imgSrc"
                            :alt="imgAlt"
                            class="vs-product-card__img"
                            data-test="vs-product-card__img"
                        />
                    </div>
                    <!--
                        @slot Holds badges that appear over the image
                        Expects a VsCannedSearchBadges component
                    -->
                    <slot
                        name="vsCannedSearchBadges"
                    />
                </div>

                <div
                    class="card-body"
                    :class="searchType === 'even' ? 'card-body--short' : ''"
                >
                    <VsHeading
                        level="3"
                        class="card-title vs-product-card__title text-truncate text-truncate--2"
                        data-test="vs-product-card__title"
                    >
                        <VsLink
                            :href="detailLink.link"
                            :type="detailLink.type"
                            class="stretched-link"
                            data-test="vs-product-card__link"
                            :disabled="!isVisible(slideIndex)"
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
                    <!--
                        @slot Holds an optional star rating
                        Expects a VsCannedSearchStars component
                    -->
                    <slot
                        name="vsCannedSearchStarRating"
                    />
                    <!--
                        @slot Holds an optional list of categories
                        Expects a VsCannedSearchCategories component
                    -->
                    <slot
                        name="vsCannedSearchCategories"
                    />
                    <div class="vs-product-card__description">
                        <p class="text-truncate text-truncate--2">
                            {{ description }}
                        </p>
                        <VsLink
                            :href="detailLink.link"
                            class="vs-product-card__description-link"
                        >
                            {{ detailLink.label }}
                        </VsLink>
                    </div>
                </div>
                <div class="vs-product-card__logos-container">
                    <!--
                        @slot Holds an optional list of logos and awards that the product has won
                        Expects a VsCannedSearchLogos component
                    -->
                    <slot
                        name="vsCannedSearchLogos"
                    />
                </div>
            </section>
            <!--
                @slot Holds the summary box for the product card
                Expects a VsCannedSearchSummaryBox component
            -->
            <slot
                name="vsCannedSearchSummary"
            />
        </div>
    </VsCol>
</template>

<script>
import VsImg from '@components/elements/img/Img';
import VsHeading from '@components/elements/heading/Heading';
import VsLink from '@components/elements/link/Link';
import VsCol from '@components/elements/layout/Col';

/**
* Generic product card for canned search
*
* @displayName Canned Search Product Card
*/
export default {
    name: 'VsCannedSearchProductCard',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsImg,
        VsHeading,
        VsLink,
        VsCol,
    },
    props: {
        /**
        * The type of product being searched for by the canned search
        * the card appears in, dictates certain layout elements
        */
        searchType: {
            type: String,
            default: '',
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
        * The location of the product in the card
        */
        location: {
            type: String,
            default: '',
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
        * field, a `label` field and a `type` field
        */
        detailLink: {
            type: Object,
            required: true,
        },
        /**
        * Mandatory index of slide -
        * needed to calculate active slides
        */
        slideIndex: {
            type: String,
            required: true,
        },
    },
    methods: {
        /**
         * Detects if the card is one of the currently visible cards in the carousel
         * so it can be disabled if not
         * @param {String} slideNum - the index of the slide within the canned search
         * carousel
         * @returns {Boolean} true if card is visible
         */
        isVisible(slideNum) {
            const slideInt = parseInt(slideNum, 10);
            if (this.visibleSlides.indexOf(slideInt) >= 0) {
                return true;
            }

            return false;
        },
    },
    inject: ['slideCols', 'visibleSlides'],
};
</script>

<style lang="scss">
    .vs-product-card {
        text-align: left;

        .card {
            transition: box-shadow $duration-slowly;
            border: none;
            position: relative;
            padding-bottom: $spacer-5;

            &:hover {
                box-shadow: 10px 10px 20px $color-gray-tint-4;

                padding: $spacer-2 $spacer-2 $spacer-5;
                margin-left: -$spacer-2;
                margin-right: -$spacer-2;
                margin-top: -$spacer-2;

                .megalink-link-list__title {
                    text-decoration: underline;
                }
            }
        }

        .card-body {
            padding: $spacer-1 $spacer-0 $spacer-0;
            min-height: 11.5rem;

            &--short {
                min-height: 8rem;
            }
        }

        .stretched-link {
            color: $color-base-text;
            text-decoration: none;
            letter-spacing: 0;
            display: block;

            &:focus {
                outline: 2px solid $color-pink;
            }
        }

        .vs-product-card__image-container {
            position: relative;
            height: 0;
            padding-bottom: 66.6%
        }

        .vs-product-card__image-inner-container {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }

        .vs-product-card__img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            align-self: flex-start;
            flex-shrink: 0; // IE11 fix, prevents image vertical stretching
        }

        .vs-product-card__title {
            font-size: $font-size-base;
            line-height: $line-height-s;
            letter-spacing: 1px;
            color: $color-base-text;
            display: flex;
            margin: $spacer-3 $spacer-0 $spacer-2;

            a {
                letter-spacing: inherit;
            }
        }

        .vs-product-card__location {
            font-family: $font-family-base;
            font-size: $font-size-base;
            line-height: 1;
            color: $color-secondary-teal-shade-2;
            letter-spacing: normal;
            margin: $spacer-0;
            margin-bottom: $spacer-3;
        }

        .vs-product-card__description {
            font-size: $font-size-base;
            height: $spacer-8;

            p {
                margin-bottom: $spacer-1;
            }
        }

        .text-truncate {
            text-overflow: ellipsis;
            overflow: hidden;
            display: -webkit-box !important;
            -webkit-box-orient: vertical;
            white-space: normal;

            &--2 {
                -webkit-line-clamp: 2;
            }
        }

        &--disabled {
            .card {
                opacity: $opacity_disabled;
            }

            .card:hover {
                box-shadow: none;
                padding: $spacer-0 $spacer-0 $spacer-2 $spacer-0;
                margin-left: $spacer-0;
                margin-right: $spacer-0;
                margin-top: $spacer-0;
            }

            .stretched-link {
                cursor: default;
            }
        }
    }
</style>

<docs>
```jsx
    const sampleAccom = require("../../../../assets/fixtures/canned-search/sample-accom.json");
    const sampleEvent = require("../../../../assets/fixtures/canned-search/sample-event.json");

    <VsCarousel
        next-text="next page"
        prev-text="previous page"
        slides-xs="1"
        slides-md="2"
        slides-lg="3"
    >
        <VsCannedSearchProductCard
            slideIndex="0"
            :imgSrc="sampleAccom.images[0].mediaUrl"
            :imgAlt="sampleAccom.name"
            :title="sampleAccom.name"
            :location="sampleAccom.address.city + ', ' + sampleAccom.address.county"
            :categories="sampleAccom.locations"
            :description="sampleAccom.description"
            :detailLink="{
                link: sampleAccom.dmsLink.link,
                label: sampleAccom.dmsLink.label,
                type: sampleAccom.dmsLink.type.toLowerCase()
            }"
            searchType="acco"
        >
            <VsCannedSearchStars
                slot="vsCannedSearchStarRating"
                :min="sampleAccom.grading.minStars"
                :max="sampleAccom.grading.maxStars"
                :gold="sampleAccom.grading.gold"
            />
            <VsCannedSearchCategories
                slot="vsCannedSearchCategories"
                v-if="sampleAccom.locations"
                :categories="sampleAccom.locations"
            />
            <VsCannedSearchLogos
                slot="vsCannedSearchLogos"
                :goodToGoLogo="sampleAccom.covidInformation.goodToGo"
                :safeTravelsLogo="sampleAccom.covidInformation.safeTravels"
                :awards="sampleAccom.awards"
            />
            <VsCannedSearchBadges
                slot="vsCannedSearchBadges"
                :badgeOne="sampleAccom.category.name"
                :badgeTwo="sampleAccom.offers"
                :badgeThree="sampleAccom.covidInformation ?
                    sampleAccom.covidInformation.weAreOpen : ''"
            />
            <VsCannedSearchSummaryBox
                slot="vsCannedSearchSummary"
            >
                <VsCannedSearchPrice
                    v-if="sampleAccom.price"
                    slot="vsCannedSearchSummaryLeft"
                    :priceIntro="sampleAccom.price.priceLabel"
                    :price="sampleAccom.price.price"
                    :priceOutro="sampleAccom.price.priceBasis"
                />
                <VsLink
                    :href="sampleAccom.website.link"
                    :type="sampleAccom.website.type.toLowerCase()"
                    slot="vsCannedSearchSummaryRight"
                >
                    {{ sampleAccom.dmsLink.label }}
                </VsLink>
            </VsCannedSearchSummaryBox>
        </VsCannedSearchProductCard>

        <VsCannedSearchProductCard
            slideIndex="1"
            :imgSrc="sampleEvent.images[0].mediaUrl"
            :imgAlt="sampleEvent.name"
            :title="sampleEvent.name"
            :location="sampleEvent.address.city + ', ' + sampleEvent.address.county"
            :categories="sampleEvent.locations"
            :description="sampleEvent.description"
            :detailLink="{
                link: sampleEvent.dmsLink.link,
                label: sampleEvent.dmsLink.label,
                type: sampleEvent.dmsLink.type.toLowerCase()
            }"
            searchType="even"
        >
            <VsCannedSearchLogos
                slot="vsCannedSearchLogos"
                :goodToGoLogo="sampleEvent.covidInformation.goodToGo"
                :safeTravelsLogo="sampleEvent.covidInformation.safeTravels"
                :awards="sampleEvent.awards"
            />
            <VsCannedSearchBadges
                slot="vsCannedSearchBadges"
                :badgeOne="sampleEvent.category.name"
                :badgeTwo="sampleAccom.offers"
                badgeThree="Now On"
            />
            <VsCannedSearchSummaryBox
                slot="vsCannedSearchSummary"
            >
                <VsCannedSearchDates
                    v-if="sampleEvent.opening"
                    slot="vsCannedSearchSummaryTop"
                    :period="sampleEvent.opening.period"
                    :label="sampleEvent.opening.label"
                />
                <VsCannedSearchPrice
                    v-if="sampleEvent.price"
                    slot="vsCannedSearchSummaryLeft"
                    :priceIntro="sampleEvent.price.priceLabel"
                    :price="sampleEvent.price.price"
                    :priceOutro="sampleEvent.price.priceBasis"
                />
                <VsLink
                    :href="sampleEvent.website.link"
                    :type="sampleEvent.website.type.toLowerCase()"
                    slot="vsCannedSearchSummaryRight"
                >
                    {{ sampleEvent.dmsLink.label }}
                </VsLink>
            </VsCannedSearchSummaryBox>
        </VsCannedSearchProductCard>
    </VsCarousel>
```
</docs>
