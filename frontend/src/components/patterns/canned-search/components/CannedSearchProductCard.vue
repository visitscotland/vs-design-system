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
                    <div
                        v-if="!badgeThree && nowOn"
                        class="vs-product-card__badge vs-product-card__badge--light-pink
                        vs-product-card__badge--br"
                    >
                        {{ dates.nowOnLabel }}
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
            <div class="vs-product-card__summary-box">
                <VsContainer>
                    <VsRow
                        v-if="dates"
                    >
                        <VsCol
                            cols="12"
                            class="vs-product-card__summary-item"
                        >
                            <div
                                class="vs-product-card__dates-intro"
                            >
                                {{ dates.dateLabel }}
                            </div>
                            <div
                                class="vs-product-card__dates-main"
                            >
                                {{ datePeriod }}
                            </div>
                        </VsCol>
                    </VsRow>
                    <VsRow
                        class="row--tall"
                    >
                        <VsCol
                            cols="6"
                            class="vs-product-card__summary-item"
                            v-if="!!this.$slots['vsCannedSearchSummaryLeft']"
                        >
                            <!--
                                @slot Holds the content for the second optional item in the grey
                                summary box  at the bottom of the card, usually a
                                vsCannedSearchPrice

                                Expects html
                            -->
                            <slot
                                name="vsCannedSearchSummaryLeft"
                            />
                        </VsCol>
                        <VsCol
                            cols="6"
                            class="vs-product-card__summary-item"
                            v-if="!!this.$slots['vsCannedSearchSummaryRight']"
                        >
                            <!--
                                @slot Holds the content for the second optional item in the grey
                                summary box  at the bottom of the card, usually a link

                                Expects html
                            -->
                            <slot
                                name="vsCannedSearchSummaryRight"
                            />
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
        * Should contain a `startDay` and an `endDay` property, each of which has a
        * `yyyy-mm-dd` formatted date in it, and `dateLabel` and `nowOnLabel` strings.
        * If this is present the dates will be rendered in the summary box and
        * those dates will be used to calculate whether the event is currently occurring.
        */
        dates: {
            type: Object,
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
        nowOn() {
            if (this.dates && this.dates.period) {
                const fromDate = new Date(this.dates.period.startDay);
                const toDate = new Date(this.dates.period.endDay);
                const currentDate = new Date();

                return currentDate > fromDate && currentDate < toDate;
            }

            return false;
        },
        datePeriod() {
            if (this.dates && this.dates.period) {
                if (this.dates.period.startDay && this.dates.period.endDay) {
                    return `${this.dates.period.startDay} - ${this.dates.period.endDay}`;
                }

                if (this.dates.period.day) {
                    return this.dates.period.day;
                }
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
            min-height: 11rem;
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
            line-height: $line-height-s;
            height: $spacer-8;

            p {
                margin-bottom: $spacer-1;
            }
        }

        .vs-product-card__logos-container {
            margin-top: $spacer-9;
            margin-bottom: $spacer-4;
            min-height: $spacer-9;
        }

        .vs-product-card__dates-intro {
            font-size: $font-size-sm;
        }

        .vs-product-card__dates-main {
            font-weight: bold;
        }

        .vs-product-card__summary-box {
            padding: $spacer-2;
            background-color: $color-gray-tint-7;
            min-height: 4.5rem;

            .row {
                align-items: center;
                justify-content: center;
                min-height: $spacer-9;

                &--tall {
                    min-height: $spacer-10;
                }
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
                opacity: 0.5;
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
            :badgeOne="sampleAccom.category.name"
            :badgeTwo="sampleAccom.offers"
            :badgeThree="sampleAccom.covidInformation ?
                sampleAccom.covidInformation.weAreOpen : ''"
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
            <VsCannedSearchPrice
                v-if="sampleAccom.price"
                slot="vsCannedSearchSummaryLeft"
                :priceIntro="sampleAccom.price.priceLabel"
                :price="'£' + sampleAccom.price.price"
                :priceOutro="sampleAccom.price.priceBasis"
            />
            <VsLink
                v-if="sampleAccom.website"
                :href="sampleAccom.website.link"
                :type="sampleAccom.website.type.toLowerCase()"
                slot="vsCannedSearchSummaryRight"
            >
                {{ sampleAccom.website.label }}
            </VsLink>
        </VsCannedSearchProductCard>
        <VsCannedSearchProductCard
            slideIndex="1"
            :imgSrc="sampleEvent.images[0].mediaUrl"
            :imgAlt="sampleEvent.name"
            :title="sampleEvent.name"
            :location="sampleEvent.address.line1 + ', ' + sampleEvent.address.county"
            :categories="sampleEvent.locations"
            :description="sampleEvent.description"
            :detailLink="{
                link: sampleEvent.dmsLink.link,
                label: sampleEvent.dmsLink.label,
                type: sampleEvent.dmsLink.type.toLowerCase()
            }"
            :badgeOne="sampleEvent.category.name"
            :dates="sampleEvent.opening ? sampleEvent.opening : null"
        >
            <VsCannedSearchPrice
                v-if="sampleEvent.price"
                slot="vsCannedSearchSummaryLeft"
                :priceIntro="sampleEvent.price.priceLabel"
                :price="'£' + sampleEvent.price.price"
                :priceOutro="sampleEvent.price.priceBasis"
            />
            <VsLink
                v-if="sampleEvent.website"
                :href="sampleEvent.website.link"
                :type="sampleEvent.website.type.toLowerCase()"
                slot="vsCannedSearchSummaryRight"
            >
                {{ sampleEvent.website.label }}
            </VsLink>
        </VsCannedSearchProductCard>
    </VsCarousel>
```
</docs>
