<template>
    <section
        data-test="vs-main-map-wrapper-panel"
        class="vs-main-map-wrapper-panel"
        :class="panelClasses"
    >
        <div
            v-if="currentHeading !== '' || currentStage > 0"
            class="vs-main-map-wrapper-panel__header-section"
            :class="headerClasses"
        >
            <div
                class="vs-main-map-wrapper-panel__back"
                v-if="currentStage > 0 || selectedSubcategory"
            >
                <VsButton
                    icon-only
                    icon="internal-link"
                    icon-orientation="down"
                    size="md"
                    variant="secondary"
                    @click.native="stageBack"
                    data-test="vs-main-map-wrapper-panel--btn-back"
                >
                    <span class="sr-only">
                        <!-- @slot Text for panel back button  -->
                        <slot name="backBtnText" />
                    </span>
                </VsButton>
            </div>

            <VsHeading
                :level="headingLevel"
                override-style-level="4"
                class="vs-main-map-wrapper-panel__heading text-center mt-0"
                :class="currentStage === 2 ? 'd-none d-lg-block' : ''"
                v-if="currentHeading !== ''"
                data-test="vs-main-map-wrapper-panel__heading"
            >
                {{ currentHeading }}
            </VsHeading>

            <div
                class="vs-main-map-wrapper-panel__close d-lg-none"
            >
                <VsButton
                    icon-only
                    icon="close"
                    size="md"
                    variant="secondary"
                    @click.native="closePanel"
                    data-test="vs-main-map-wrapper-panel--btn-close"
                >
                    <span class="sr-only">
                        <!-- @slot Text for panel close button  -->
                        <slot name="closeSidePanelText" />
                    </span>
                </VsButton>
            </div>

            <div
                class="vs-main-map-wrapper-panel__reset"
                :class="currentStage < 2 ? 'd-lg-none' : ''"
            >
                <VsButton
                    icon-only
                    icon="close"
                    size="md"
                    variant="secondary"
                    @click.native="resetPanel"
                    data-test="vs-main-map-wrapper-panel--btn-reset"
                >
                    <span class="sr-only">
                        <!-- @slot Text for panel reset button  -->
                        <slot name="resetSidePanelText" />
                    </span>
                </VsButton>
            </div>
        </div>

        <template v-if="currentStage === 0">
            <template v-if="selectedSubcategory !== null">
                <VsMainMapWrapperSubcategory
                    :data="selectedSubcategoryData[0].subCategory"
                />
                <VsMainMapWrapperControls />
            </template>
            <template v-else>
                <div
                    v-for="filter in filters"
                    :key="filter.id"
                >
                    <VsMainMapWrapperCategory
                        :category-name="filter.label"
                        :type="filter.id"
                        :has-sub-cat="subCatExists(filter)"
                    />
                </div>
            </template>

            <template v-if="panelMessage !== null">
                <div class="vs-main-map-wrapper-panel__bottom-message">
                    <p>
                        {{ panelMessage }}
                    </p>
                </div>
            </template>
        </template>
        <template v-if="currentStage === 1">
            <template v-if="selectedSubcategory !== null">
                <div class="vs-main-map-wrapper-panel__list-container">
                    <div
                        v-for="(place, index) in subcategoryLocations"
                        :key="place.id"
                    >
                        <VsMainMapWrapperListItem
                            :item-data="place"
                            :from-endpoint="true"
                            :focussed="index === currentListItemFocus"
                            @show-item-detail="showDetail(place.id)"
                        />
                    </div>

                    <VsButton
                        v-if="showLoadMore"
                        class="vs-main-map-wrapper-panel__load-more"
                        data-test="vs-main-map-wrapper-panel__load-more"
                        @click.native="loadMorePlaces()"
                        @keyup.native.enter="loadMorePlaces()"
                    >
                        <!-- @slot Text for load more button  -->
                        <slot name="loadMoreText" />
                    </VsButton>
                </div>
                <VsMainMapWrapperButtons
                    :content-data="{}"
                    :filter-count="subCatFilterCount"
                    @clear-filters="clearSubCatFilters"
                />
            </template>
            <template v-else>
                <div
                    v-for="place in currentData"
                    :key="place.id"
                >
                    <VsMainMapWrapperListItem
                        v-if="typeof place.properties !== 'undefined'
                            && place.properties.category.id === selectedCategory"
                        :item-data="place.properties"
                        @show-item-detail="showDetail(place.properties.id)"
                    />
                </div>
            </template>
        </template>
        <template v-if="currentStage === 2">
            <VsMainMapWrapperDetail
                :heading-level="detailHeadingLevel"
                :content-data="currentPlaceData[0]"
            />

            <VsMainMapWrapperButtons
                :content-data="currentPlaceData[0]"
            />
        </template>
        <div
            v-if="panelStatus !== null"
            class="vs-main-map-wrapper-panel__overlay"
        >
            <div
                v-if="!!$slots['panelLoadingMessage']
                    && panelStatus !== 'map-loading'"
                class="vs-main-map-wrapper-panel__overlay-box"
            >
                <p class="vs-main-map-wrapper-panel__overlay-text">
                    <!-- @slot Text for panel reset button  -->
                    <slot name="panelLoadingMessage" />
                </p>
            </div>
        </div>
    </section>
</template>

<script>
import VsButton from '@components/elements/button/Button/';
import VsHeading from '@components/elements/heading/Heading';
import VsMainMapWrapperCategory from './MainMapWrapperCategory';
import VsMainMapWrapperSubcategory from './MainMapWrapperSubcategory';
import VsMainMapWrapperListItem from './MainMapWrapperListItem';
import VsMainMapWrapperDetail from './MainMapWrapperDetail';
import VsMainMapWrapperButtons from './MainMapWrapperButtons';
import VsMainMapWrapperControls from './MainMapWrapperControls';
import mapStore from '../../../../stores/map.store';

/**
 * Renders a side panel for the map wrapper component
 *
 * @displayName Main Map Wrapper Panel
 */

export default {
    name: 'VsMainMapWrapperPanel',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsButton,
        VsMainMapWrapperCategory,
        VsMainMapWrapperSubcategory,
        VsHeading,
        VsMainMapWrapperListItem,
        VsMainMapWrapperDetail,
        VsMainMapWrapperButtons,
        VsMainMapWrapperControls,
    },
    inject: [
        'filters',
        'placesData',
        'regions',
        'mapId',
        'focussedListItem',
    ],
    props: {
        /**
         * Heading for the categories view
         */
        categoryHeading: {
            type: String,
            default: '',
        },
        /**
         * Heading level for panel header
         */
        headingLevel: {
            type: String,
            default: '2',
        },
        /**
         * Currently selected category
         */
        selectedCategory: {
            type: String,
            default: '',
        },
        /**
         * Currently selected subcategory
         */
        selectedSubcategory: {
            type: String,
            default: null,
        },
        /**
         * The current stage
         */
        currentStage: {
            type: Number,
            required: true,
        },
        /**
         * The ID of thecurrently selected item
         */
        selectedItem: {
            type: String,
            default: '',
        },
        /**
         * The ID of the currently hover item
         */
        hovered: {
            type: String,
            default: '',
        },
        /**
         * The ID of the currently hover item
         */
        subcategoryLocations: {
            type: Array,
            default: null,
        },
        /**
         * Place data defined from endpoint
         */
        currentEndpointData: {
            type: Array,
            default: () => [],
        },
        /**
         * Whether or not to show a panel message
         */
        panelStatus: {
            type: String,
            default: null,
        },
        /**
         * A message that appears at the bottom
         * of the side panel
         */
        panelMessage: {
            type: String,
            default: null,
        },
        /**
         * Total amount of places coming from endpoint.
         * Used to work out if there's more to load in panel.
         */
        totalPins: {
            type: Number,
            default: 0,
        },
        /**
         * The number of the list item that should be focussed
         * on creation
         */
        currentListItemFocus: {
            type: Number,
            default: 0,
        },
    },
    data() {
        return {
            placesLoaded: 0,
        };
    },
    computed: {
        currentHeading() {
            let headingText = '';

            if (this.selectedSubcategory !== null) {
                headingText = this.selectedSubcategoryData[0].label;
            } else {
                switch (this.currentStage) {
                case 0:
                    headingText = this.categoryHeading;
                    break;
                case 1:
                    headingText = this.currentFilter.label;
                    break;
                case 2:
                    headingText = this.currentPlaceData[0].properties.title;
                    break;
                default:
                    break;
                }
            }

            return headingText;
        },
        currentData() {
            if (this.selectedCategory === 'regions') {
                return this.regions;
            }

            return this.placesData;
        },
        panelClasses() {
            let panelClasses = '';
            if (this.currentStage === 1) {
                panelClasses += 'justify-content-between';
            }
            if (this.currentStage === 2) {
                panelClasses += ' vs-main-map-wrapper-panel--small-padding';
            }

            return panelClasses;
        },
        headerClasses() {
            if (this.currentStage === 1) {
                return 'vs-main-map-wrapper-panel__header-section--with-spacer';
            }

            if (this.currentStage === 2) {
                return 'vs-main-map-wrapper-panel__header-section--overlapped';
            }

            return '';
        },
        currentFilter() {
            let currentFilter = '';
            this.filters.forEach((filter) => {
                if (filter.id === this.selectedCategory) {
                    currentFilter = filter;
                }
            });

            return currentFilter;
        },
        currentPlaceData() {
            let data = this.placesData;

            if (this.selectedCategory === 'regions') {
                data = this.regions;
            }

            if (this.currentEndpointData.length > 0) {
                return this.refineEndpointData(this.currentEndpointData);
            }

            return data.filter((obj) => {
                if (typeof obj.properties !== 'undefined') {
                    return obj.properties.id === this.selectedItem;
                }

                return false;
            });
        },
        selectedSubcategoryData() {
            if (this.selectedSubcategory) {
                const data = this.filters.filter((item) => item.id === this.selectedSubcategory);

                return data;
            }

            return [];
        },
        subCatFilterCount() {
            return mapStore.getters.getActiveSubcatFilters.length;
        },
        detailHeadingLevel() {
            const headingNum = parseInt(this.headingLevel, 10);
            const newHeading = headingNum + 1;
            const headingStr = newHeading.toString();

            return headingStr;
        },
        showLoadMore() {
            if (this.placesLoaded * 24 > this.totalPins
                || this.placesLoaded === 0) {
                return false;
            }

            return true;
        },
    },
    watch: {
        currentFilter() {
            this.placesLoaded = 0;
        },
    },
    methods: {
        /**
         * Emits 'close-panel'
         */
        closePanel() {
            this.$emit('close-panel');
        },
        /**
         * Moves back stages dependent on current state
         */
        stageBack() {
            if (this.selectedSubcategory && this.currentStage === 0) {
                // if the user is on the subcategory page, keep the stage the same
                // but reset the subcategory
                this.$emit('set-subcategory', null);
            } else if (this.selectedSubcategory !== null && this.subcategoryLocations === null) {
                // if the user has selected a subcategory item straight from the subcategory
                // filter menu, take them back to that stage
                this.setStage(0);
            } else {
                const previousStage = this.currentStage - 1;
                this.setStage(previousStage);
            }
        },
        /**
         * Resets the panel
         */
        resetPanel() {
            mapStore.dispatch('setSelectedSubcat', null);
            this.setStage(0);
        },
        /**
         * Emits the current stage
         */
        setStage(stageNum) {
            if (stageNum === 0) {
                this.placesLoaded = 1;
            }

            this.$emit('set-stage', stageNum);
        },
        /**
         * Determines whether or not a subCategory
         * array exists and has data in it
         */
        subCatExists(cat) {
            if (typeof cat.subCategory !== 'undefined'
                && cat.subCategory.length > 0) {
                return true;
            }

            return false;
        },
        /**
         * transforms endpoint data into format to be used
         */
        refineEndpointData(data) {
            const refinedData = [{
                isEndpoint: true,
                properties: {
                    category: data[0].category[0].id,
                    id: data[0].id,
                    image: data[0].images[0].mediaUrl,
                    placeTitle: data[0].name,
                    description: data[0].description,
                    link: {
                        label: data[0].productLink.label,
                        link: data[0].productLink.link,
                        type: data[0].productLink.type,
                    },
                    website: {
                        label: data[0].website.label,
                        link: data[0].website.link,
                        type: data[0].website.type,
                    },
                    address: {
                        shortAddress: '',
                    },
                },
            }];

            if (typeof data[0].address !== 'undefined') {
                refinedData[0].properties.address.shortAddress = data[0].address.shortAddress;
            }

            return refinedData;
        },
        /**
         * clear the subcategory filters
        */
        clearSubCatFilters() {
            mapStore.dispatch('setActiveSubcatFilters', []);
            this.setStage(0);
        },
        /**
         * Load more places from endpoint
        */
        loadMorePlaces() {
            this.placesLoaded += 1;
            this.$emit('load-more-places', this.placesLoaded);
        },
    },
};
</script>

<style lang="scss">
    .vs-main-map-wrapper-panel {
        padding: $spacer-11 $spacer-3 $spacer-0;
        border: 1px solid $color-gray;
        height: 100%;
        overflow-y: auto;
        overflow-x: hidden;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;

        &--small-padding {
            padding-top: $spacer-6;
        }

        &__heading.vs-heading {
            flex-grow: 1;
            margin: $spacer-11 $spacer-3 $spacer-0;
        }

        &__header-section {
            display: flex;
            min-height: 32px;
            align-items: center;
            margin-bottom: $spacer-5;

            &--overlapped {
                position: absolute;
                width: calc(100% - #{$spacer-6});
            }
        }

        &__close,
        &__back {
            position: absolute;
            top: $spacer-3;
        }

        &__back {
            left: $spacer-3;
        }

        &__close {
            right: $spacer-3;
        }

        &__reset {
            display: none;
        }

        &__overlay {
            position: absolute;
            top: 0;
            left: 0;
            z-index: 20;
            height: 100%;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            background: rgba(255, 255, 255, 0.4);
        }

        &__overlay-box {
            border: 1px solid $color-pink;
            border-radius: $border-radius-default;
            height: 142px;
            width: 200px;
            background: $color-white;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: $spacer-6;
        }

        &__overlay-text {
            font-size: $font-size-3;
            margin-bottom: 0;
            text-align: center;
        }

        &__load-more {
            width: 100%;
            flex-shrink: 0;
            margin: $spacer-4 0;
        }

        &__list-container {
            height: calc(100% - 140px);
            overflow-y: scroll;
            overflow-x: visible;
            display: block;
            margin: -#{$spacer-4} -#{$spacer-4} 0;
            padding: $spacer-4 $spacer-4 0;
        }

        &__bottom-message {
            position: absolute;
            bottom: 1px;
            background: $color-white;
            left: 1px;
            font-size: $font-size-4;
            text-align: center;
            padding: $spacer-4;
            display: flex;
            justify-content: center;
            // to avoid overlap of scroll bar
            width: calc(100% - 15px);

            p {
                margin-bottom: $spacer-0;
                max-width: 75%;
            }
        }

        .vs-main-wrapper-category:last-of-type {
            &::before {
                display: none;

                @include media-breakpoint-up(lg) {
                    display: block;
                }
            }
        }

        @include media-breakpoint-up(lg) {
            padding: $spacer-8 $spacer-4 $spacer-0;
            border-right: none;

            &__header-section {
                display: flex;
                margin-bottom: $spacer-7;

                &--with-spacer {
                    padding-right: $spacer-8;
                }

                &--overlapped {
                    position: relative;
                    width: 100%;
                }
            }

            &__close {
                display: none;
            }

            &__back {
                left: 0;
                top: 0;
                position: relative;
            }

            &__reset {
                display: block;
            }

            &__message {
                position: sticky;
                bottom: -1px;
                padding: $spacer-4 0;
                width: 100%;
                background: $color-white;
                text-align: center;
                margin-bottom: $spacer-0;
                font-size: $font-size-4;

                @include media-breakpoint-up(lg) {
                    padding: $spacer-4;
                }
            }
        }
    }
</style>
