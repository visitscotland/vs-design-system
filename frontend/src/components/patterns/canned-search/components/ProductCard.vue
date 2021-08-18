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
                    <div
                        v-if="badgeOne"
                        class="vs-product-card__badge vs-product-card__badge--teal
                        vs-product-card__badge--tr"
                    >
                        {{ badgeOne }}
                    </div>
                    <div
                        v-if="badgeTwo"
                        class="vs-product-card__badge vs-product-card__badge--pink
                        vs-product-card__badge--tr2"
                    >
                        {{ badgeTwo }}
                    </div>
                    <div
                        v-if="badgeThree"
                        class="vs-product-card__badge vs-product-card__badge--light-pink
                        vs-product-card__badge--br"
                    >
                        {{ badgeThree }}
                    </div>
                </div>

                <div class="card-body">
                    <div class="vs-product-card__pre-description">
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
                        <div v-if="stars">
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
                        </div>
                        <div
                            class="vs-product-card__categories"
                            data-test="vs-product-card__categories"
                        >
                            {{ transformedCategories }}
                        </div>
                    </div>
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
                <div class="vs-product-card__logos">
                    <VsTooltip
                        :title="goodToGoLogo"
                        v-if="goodToGoLogo"
                        data-test="vs-product-card__good-to-go"
                    >
                        <VsImg
                            src="https://www.visitscotland.com/cms-images/logos/goodToGo.png"
                            :alt="goodToGoLogo"
                            class="vs-product-card__logo"
                        />
                    </VsTooltip>
                    <VsTooltip
                        :title="safeTravelsLogo"
                        v-if="safeTravelsLogo"
                        data-test="vs-product-card__safe-travels"
                    >
                        <VsImg
                            src="https://www.visitscotland.com/cms-images/logos/WTTC-SafeTravels.png"
                            :alt="safeTravelsLogo"
                            class="vs-product-card__logo"
                        />
                    </VsTooltip>
                    <VsTooltip
                        v-for="award in awards"
                        :title="award.name"
                        :key="award.id"
                    >
                        <VsImg
                            :src="award.image"
                            :alt="award.name"
                            class="vs-product-card__logo"
                            data-test="vs-product-card__award-logo"
                        />
                    </VsTooltip>
                </div>
            </section>
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
                            <div
                                class="vs-product-card__summary-padding"
                                v-else
                            />
                        </VsCol>
                        <VsCol
                            cols="6"
                            class="vs-product-card__summary-item"
                        >
                            <div
                                class="vs-product-card__summary-website"
                                v-if="websiteLink"
                            >
                                <VsLink
                                    :href="websiteLink.link"
                                    :type="websiteLink.type || 'internal'"
                                    class="vs-product-card__website-link"
                                    data-test="vs-product-card__website-link"
                                >
                                    {{ websiteLink.label }}
                                </VsLink>
                            </div>
                        </VsCol>
                    </VsRow>
                </VsContainer>
            </div>
        </div>
    </VsCol>
</template>

<script>
import VsImg from '@components/elements/img/Img';
import VsHeading from '@components/elements/heading/Heading';
import VsLink from '@components/elements/link/Link';
import VsContainer from '@components/elements/layout/Container';
import VsRow from '@components/elements/layout/Row';
import VsCol from '@components/elements/layout/Col';
import VsTooltip from '@components/elements/tooltip/Tooltip';

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
        VsTooltip,
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
        * objects, each of which has a `name` prop
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
        * field, a `label` field and a `type` field
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
        /**
        * Appears in a teal badge over the image at the top right. Not rendered if not set
        */
        badgeOne: {
            type: String,
            default: '',
        },
        /**
        * Appears in a pink badge over the image, below the position of badgeOne. Not
        * rendered if not set
        */
        badgeTwo: {
            type: String,
            default: '',
        },
        /**
        * Appears in a white badge over the image at the bottom right. Not rendered
        * if not set
        */
        badgeThree: {
            type: String,
            default: '',
        },
        /**
        * If set, the good to go logo appears. The text set in this property will appear as
        * the tooltip of that logo
        */
        goodToGoLogo: {
            type: String,
            default: '',
        },
        /**
        * If set, the safe travels logo appears. The text set in this property will appear as
        * the tooltip of that logo
        */
        safeTravelsLogo: {
            type: String,
            default: '',
        },
        /**
        * A set of award badges to display, each should contain a unique `id` (String), a `name`
        * (String) that appears as a tooltip and a url to an `image` (String)
        */
        awards: {
            type: Array,
            default: null,
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
    computed: {
        transformedCategories() {
            if (this.categories) {
                return this.categories
                    .slice(0, 3)
                    .map((cat) => cat.name)
                    .join(' | ');
            }

            return '';
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
            padding-bottom: $spacer-2;

            &:hover {
                box-shadow: 10px 10px 20px $color-gray-tint-4;

                padding: $spacer-2;
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

        .vs-product-card__badge {
            font-size: $font-size-sm;
            position: absolute;
            padding: 0 .5em;
            text-transform: uppercase;

            &--teal {
                color: $color-white;
                background-color: $color_secondary_teal;
            }

            &--pink {
                color: $color-white;
                background-color: $color_pink;
            }

            &--light-pink {
                background-color: $color_pink_tint_6;
                color: $color_pink;
            }

            &--tr {
                top: .5em;
                right: .5em;
            }

            &--tr2 {
                top: 2.5em;
                right: .5em;
            }

            &--br {
                bottom: .5em;
                right: .5em;
            }
        }

        .vs-product-card__pre-description {
            min-height: 7rem;
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

        .vs-product-card__stars {
            line-height: 1;
            margin-bottom: $spacer-3;
        }

        .vs-product-card__star {
            color: $color-purple;

            &--gold {
                color: $color-yellow;
            }
        }

        .vs-product-card__categories {
            font-size: $font-size-base;
            line-height: $line-height-s;
            font-weight: 600;
            margin-bottom: $spacer-1;
        }

        .vs-product-card__description {
            font-size: $font-size-base;
            line-height: $line-height-s;
            height: $spacer-8;

            p {
                margin-bottom: $spacer-1;
            }
        }

        .vs-product-card__summary-box {
            padding: $spacer-2;
            background-color: $color-gray-tint-7;

            .row {
                align-items: center;
            }
        }

        .vs-product-card__summary-item {
            font-size: $font-size-base;
            line-height: $line-height-s;
            text-align: center;

            &:not(:last-child) {
                border-right: 1px solid $color-gray-tint-1;
            }
        }

        .vs-product-card__summary-padding {
            height: $spacer-9;
        }

        .vs-product-card__summary-price {
            &-main {
                font-weight: bold;
            }

            &-outro {
                font-size: $xs-font-size;
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

        .vs-product-card__logos {
            margin-top: $spacer-9;
            margin-bottom: $spacer-4;
            min-height: $spacer-9;

            .vs-tooltip {
                z-index: 1;
                cursor: pointer;
            }
        }

        .vs-product-card__logo {
            max-height: $spacer-9;

            &:not(:last-child) {
                padding-right: $spacer-2;
            }
        }

        &--disabled {
            .card {
                opacity: 0.5;
            }

            .card:hover {
                box-shadow: none;
                padding: $spacer-0;
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
        <VsProductCard
            slideIndex="0"
            :imgSrc="sampleAccom.images[0].mediaUrl"
            :imgAlt="sampleAccom.name"
            :title="sampleAccom.name"
            :location="sampleAccom.address.city + ', ' + sampleAccom.address.county"
            :stars="{
                min:sampleAccom.grading.minStars,
                max:sampleAccom.grading.maxStars,
                gold:sampleAccom.grading.gold
            }"
            :categories="sampleAccom.locations"
            :description="sampleAccom.description"
            :detailLink="{
                link: sampleAccom.dmsLink.link,
                label: sampleAccom.dmsLink.label,
                type: sampleAccom.dmsLink.type.toLowerCase()
            }"
            :websiteLink="{
                link: sampleAccom.website.link,
                label: sampleAccom.website.label,
                type: sampleAccom.website.type.toLowerCase()
            }"
            :priceIntro="sampleAccom.price.priceLabel"
            :price="'£' + sampleAccom.price.price"
            :priceOutro="sampleAccom.price.priceBasis"
            :badgeOne="sampleAccom.category.name"
            :badgeTwo="sampleAccom.offers"
            :badgeThree="sampleAccom.covidInformation ?
                sampleAccom.covidInformation.weAreOpen : ''"
            :goodToGoLogo="sampleAccom.covidInformation.goodToGo"
            :safeTravelsLogo="sampleAccom.covidInformation.safeTravels"
            :awards="sampleAccom.awards"
        >
        </VsProductCard>
        <VsProductCard
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
            :websiteLink="{
                link: sampleEvent.website.link,
                label: sampleEvent.website.label,
                type: sampleEvent.website.type.toLowerCase()
            }"
            :priceIntro="sampleEvent.price.priceLabel"
            :price="'£' + sampleEvent.price.price"
            :priceOutro="sampleEvent.price.priceBasis"
            :badgeOne="sampleEvent.category.name"
            :badgeTwo="sampleEvent.offers"
            :awards="sampleEvent.awards"
        >
        </VsProductCard>
    </VsCarousel>
```
</docs>
