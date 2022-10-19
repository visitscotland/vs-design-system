<template>
    <VsContainer
        data-test="vs-main-map-wrapper"
    >
        <VsRow>
            <VsCol>
                <div
                    class="vs-main-map-wrapper"
                >
                    <div
                        class="vs-main-map-wrapper__side-panel"
                        :class="panelDisplayClass"
                        data-test="vs-main-map-wrapper__side-panel"
                    >
                        <VsMainMapWrapperPanel
                            :category-heading="categoryHeading"
                            :selected-category="selectedCategory"
                            :current-stage="currentStage"
                            :selected-item="selectedItem"
                            @set-category="setCategory"
                            @set-stage="setStage"
                            @close-panel="closePanel"
                            @show-item-detail="showDetail"
                            @filter-places="filterPlaces"
                        >
                            <template slot="closePanelText">
                                <slot name="closeSidePanelText" />
                            </template>
                        </VsMainMapWrapperPanel>
                    </div>
                    <div
                        class="vs-main-map-wrapper__map"
                        :class="mapDisplayClass"
                    >
                        <VsButton
                            class="vs-main-map-wrapper__map-toggle"
                            icon-only
                            icon="bars-mobile-menu"
                            size="md"
                            variant="secondary"
                            @click.native="openPanel"
                            data-test="vs-main-map-wrapper__map-toggle"
                        >
                            <span class="sr-only">
                                <!-- @slot Text for panel open button  -->
                                <slot name="openSidePanelText" />
                            </span>
                        </VsButton>
                        <VsMap
                            :is-visible="!panelVisible"
                            :labels="{
                            }"
                            :places="activePins"
                            @show-detail="showDetail"
                        />
                    </div>
                </div>
            </VsCol>
        </VsRow>
    </VsContainer>
</template>

<script>
import {
    VsContainer,
    VsRow,
    VsCol,
} from '@components/elements/grid';
import VsMap from '@components/elements/map/Map';
import VsButton from '@components/elements/button/Button/';
import VsMainMapWrapperPanel from './components/MainMapWrapperPanel';

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
        VsButton,
        VsMainMapWrapperPanel,
    },
    props: {
        /**
         * Heading for the categories view
         */
        categoryHeading: {
            type: String,
            default: '',
        },
        /**
         * Filter categories
         */
        filters: {
            type: Array,
            required: true,
        },
        /**
         * Data for individual places
         */
        placesData: {
            type: Array,
            required: true,
        },
    },
    data() {
        return {
            panelVisible: false,
            currentStage: 0,
            selectedCategory: '',
            filterCategories: this.filters,
            selectedItem: '',
            activePins: this.placesData,
        };
    },
    computed: {
        mapDisplayClass() {
            return this.panelVisible ? 'd-none d-lg-block' : '';
        },
        panelDisplayClass() {
            return this.panelVisible ? '' : 'd-none d-lg-block';
        },
        formatPinData() {
            const pinArray = [];
            this.placesData.forEach((place) => {
                pinArray.push(place.geometry.coordinates);
            });

            return pinArray;
        },
    },
    mounted() {
        this.panelVisible = true;
    },
    methods: {
        /**
         * Close the side panel
         */
        closePanel() {
            this.panelVisible = false;
        },
        /**
         * Open the side panel
         */
        openPanel() {
            this.panelVisible = true;
        },
        /**
         * Show an item's details
         */
        showDetail(id) {
            this.selectedItem = id;
            this.showPlace();
            this.setStage(2);
            this.openPanel();
        },
        /**
         * Sets the currently chosen category
         */
        setCategory(cat) {
            this.selectedCategory = cat;
        },
        /**
         * Sets the current stage
         */
        setStage(num) {
            this.currentStage = num;

            if (this.currentStage === 0) {
                this.showAllPlaces();
            } else if (this.currentStage === 1) {
                this.filterPlaces(this.selectedCategory);
            }
        },
        /**
         * Updates active pins for map
         */
        filterPlaces(id) {
            const filteredPlaces = this.placesData
                .filter((place) => place.properties.category.id === id);
            this.activePins = filteredPlaces;
        },
        /**
         * Show all pins
         */
        showAllPlaces() {
            this.activePins = this.placesData;
        },
        /**
         * Show single place pin
         */
        showPlace() {
            const chosenPlace = (this.placesData
                .filter((place) => this.selectedItem === place.properties.id));
            this.selectedCategory = chosenPlace[0].properties.category.id;
            this.activePins = chosenPlace;
        },
    },
    provide() {
        return {
            filters: this.filters,
            placesData: this.placesData,
            selectedItem: this.selectedItem,
        };
    },
};
</script>

<style lang="scss">
    .vs-main-map-wrapper {
        height: 100vh;
        display: flex;

        @include media-breakpoint-up(lg) {
            height: 500px;
        }

        .container,
        .row {
            height: 100%;
        }

        &__side-panel {
            width: 100%;

            @include media-breakpoint-up(lg) {
                flex: 0 0 354px;
                max-width: 354px;
            }
        }

        &__map {
            position: relative;
            height: 100%;
            width: 100%;
        }

        &__map-toggle.vs-button {
            position: absolute;
            top: $spacer-4;
            left: $spacer-4;
            z-index: 1;

            @include media-breakpoint-up(lg) {
                display: none;
            }
        }
    }
</style>
