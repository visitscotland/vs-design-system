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
                            :selected-subcategory="selectedSubCategory"
                            :current-stage="currentStage"
                            :selected-item="selectedItem"
                            :heading-level="mainHeadingExists ? '3' : '2'"
                            :subcategory-locations="subCatList"
                            :current-endpoint-data="currentEndpointData"
                            @set-category="setCategory"
                            @set-subcategory="setSubCategory"
                            @subcategories-filtered="filterSubCategories"
                            @set-stage="setStage"
                            @close-panel="closePanel"
                            @show-item-detail="showDetail"
                            @filter-places="filterPlaces"
                        >
                            <template slot="closePanelText">
                                <slot name="closeSidePanelText" />
                            </template>

                            <template slot="resetSidePanelText">
                                <slot name="resetSidePanelText" />
                            </template>

                            <template slot="backBtnText">
                                <slot name="backBtnText" />
                            </template>
                        </VsMainMapWrapperPanel>
                    </div>
                    <div
                        class="vs-main-map-wrapper__map"
                        :class="mapDisplayClass"
                    >
                        <VsButton
                            class="vs-main-map-wrapper__map-toggle"
                            size="sm"
                            variant="secondary"
                            @click.native="openPanel"
                            data-test="vs-main-map-wrapper__map-toggle"
                        >
                            <!-- @slot Text for panel open button  -->
                            <slot name="openSidePanelText" />
                        </VsButton>
                        <VsMap
                            :is-visible="!panelVisible"
                            :labels="{
                            }"
                            :places="activePins"
                            :selected-item="selectedItem"
                            :map-id="mapId"
                            :show-polygons="showRegions"
                            :bounds-data="boundsData"
                            @show-detail="showDetail"
                            @set-category="setCategory"
                        >
                            <template slot="noJs">
                                <!-- @slot Message to show when JS is disabled  -->
                                <slot name="noJs" />
                            </template>
                        </VsMap>
                        <VsButtonToggleGroup
                            :initial-selected="selectedToggle"
                            :options="toggleData"
                            :buttons-label="buttonsLabel"
                            @toggleChanged="onToggleChanged"
                        />
                    </div>
                </div>

                <VsWarning
                    class="vs-main-map-wrapper__no-js"
                    theme="light"
                >
                    <!-- @slot Message to show when JS is disabled  -->
                    <slot name="noJs" />
                </VsWarning>
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
import VsWarning from '@components/patterns/warning/Warning';
import VsButtonToggleGroup from '@components/patterns/button-toggle-group/ButtonToggleGroup';
import axios from 'axios';
import VsMainMapWrapperPanel from './components/MainMapWrapperPanel';
import mapStore from '../../../stores/map.store';

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
        VsButtonToggleGroup,
        VsWarning,
    },
    provide() {
        return {
            filters: this.filters,
            placesData: this.placesData,
            mapId: this.mapId,
            regions: this.regionsData,
            clearSelectionText: this.clearSelectionText,
            applyFiltersText: this.applyFiltersText,
            subCatList: this.subCatList,
            filtersAppliedText: this.filtersAppliedText,
            clearFiltersText: this.clearFiltersText,
        };
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
        /**
         * Unique ID for the map - used to
         * differentiate between multiple map
         * instances in the Vuex store
         */
        mapId: {
            type: String,
            required: true,
        },
        /**
         * Level of the heading to be used in the
         * panel (to allow main heading in section)
         */
        mainHeadingExists: {
            type: Boolean,
            default: false,
        },
        /**
         * The ID of the currently selected item
         */
        initialSelected: {
            type: String,
            default: '',
        },
        /**
         * Data for the toggle buttons
         */
        toggleData: {
            type: Array,
            default: () => [],
        },
        /**
         * Data for the toggle buttons
         */
        buttonsLabel: {
            type: String,
            default: '',
        },
        /**
         * Text for the 'clear selection' button
         */
        clearSelectionText: {
            type: String,
            required: true,
        },
        /**
         * Text for the 'apply filters' button
         */
        applyFiltersText: {
            type: String,
            required: true,
        },
        /**
         * Endpoint for getting place details
         */
        detailsEndpoint: {
            type: String,
            default: '',
        },
        /**
         * Text for clearing filters - to be passed
         * to buttons component
         */
        clearFiltersText: {
            type: String,
            required: true,
        },
        /**
         * Text for applied filters - to be passed
         * to buttons component
         */
        filtersAppliedText: {
            type: String,
            required: true,
        },
        /**
         * ID for map's place
         */
        placeId: {
            type: String,
            default: null,
        },
    },
    data() {
        return {
            panelVisible: false,
            selectedCategory: '',
            filterCategories: this.filters,
            selectedItem: '',
            activePins: this.placesData,
            currentlyHovered: '',
            showRegions: false,
            regions: [
            ],
            subCatList: null,
            selectedToggle: '',
            currentEndpointData: null,
        };
    },
    computed: {
        mapDisplayClass() {
            return this.panelVisible ? 'd-none d-lg-block' : '';
        },
        panelDisplayClass() {
            return this.panelVisible ? '' : 'd-none d-lg-block';
        },
        regionsData() {
            return this.placesData.filter((place) => place.geometry.type === 'Polygon'
                || place.geometry.type === 'MultiPolygon');
        },
        currentStage() {
            return mapStore.getters.getCurrentStage;
        },
        selectedSubCategory() {
            return mapStore.getters.getSelectedSubcat;
        },
    },
    mounted() {
        this.selectedToggle = this.initialSelected;
        mapStore.commit('addMapInstance', {
            id: this.mapId,
            filters: this.filters,
            places: this.placesData,
            activePins: this.activePins,
        });
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
            this.setStage(2);
            this.openPanel();
            if (this.selectedSubCategory === null) {
                this.filterPlaces(this.selectedCategory);
            }
        },
        /**
         * Sets the currently chosen category
         */
        setCategory(cat) {
            this.selectedCategory = cat;
            if (this.selectedSubCategory === null) {
                this.filterPlaces(cat);
            }
        },
        /**
         * Sets a subcategory
         */
        setSubCategory(subcat) {
            mapStore.dispatch('setSelectedSubcat', subcat);
            if (subcat !== null) {
                this.getSubcatMarkerData();
                this.selectedCategory = subcat;
            } else {
                mapStore.dispatch('setActiveSubcatFilters', []);
                this.showAllPlaces();
            }
        },
        /**
         * Filters subcategories
         */
        filterSubCategories(filters) {
            let filterString = '';

            filters.forEach((filter) => {
                const filterSuffix = `&cat=${filter}`;
                filterString += filterSuffix;
            });

            this.getSubcatMarkerData(filterString);
            this.getSubcatPanelData(filterString);
        },
        /**
         * Makes a call to the API to get marker data for
         * the current subcategory
         */
        getSubcatMarkerData(endpointFilters) {
            const subCat = this.filters.filter((cat) => cat.id === this.selectedSubCategory);
            let endpoint = subCat[0].pinsEndpoint;
            if (typeof endpointFilters !== 'undefined') {
                endpoint += endpointFilters;
            }

            axios.get(endpoint).then((response) => {
                this.activePins = [];
                response.data.features.forEach((feature) => {
                    const modifiedFeature = feature;
                    modifiedFeature.properties.apiData = true;
                    this.activePins.push(modifiedFeature);
                });
            });
        },
        /**
         * Makes a call to the endpoint in the subcategory data which
         * provides a random 24 items for the side panel
         */
        getSubcatPanelData(endpointFilters) {
            const subCat = this.filters.filter((cat) => cat.id === this.selectedSubCategory);
            let endpoint = subCat[0].listProductsEndPoint;
            if (typeof endpointFilters !== 'undefined') {
                endpoint += endpointFilters;
            }

            axios.get(endpoint).then((response) => {
                this.subCatList = response.data.data.products;
                this.setStage(1);
            });
        },

        /**
         * Sets the current stage
         */
        setStage(num) {
            // ensure that if data is coming from an endpoint then
            // it is loaded before moving to the next stage
            if (num === 2 && this.detailsEndpoint !== '' && this.selectedSubCategory !== null) {
                const endpoint = `${this.detailsEndpoint}${this.selectedItem}`;
                axios.get(endpoint).then((response) => {
                    const dataArr = [];
                    dataArr.push(response.data.data);
                    this.currentEndpointData = dataArr;
                    mapStore.dispatch('setCurrentStage', num);
                });
            } else {
                mapStore.dispatch('setCurrentStage', num);

                if (this.currentStage === 0) {
                    if (this.selectedSubCategory === null) {
                        this.showAllPlaces();
                    }
                    this.selectedToggle = 'places';
                } else if (this.currentStage === 1) {
                    if (this.selectedSubCategory === null) {
                        this.filterPlaces(this.selectedCategory);
                    }
                }
            }

            if (num !== 2) {
                // if the stage isn't showing a place's details
                // make sure the store doesn't have an active place set
                mapStore.dispatch('setActivePlace', {
                    mapId: this.mapId,
                    placeId: '',
                });

                this.selectedItem = null;
            }
        },
        /**
         * Updates active pins for map
         */
        filterPlaces(id) {
            if (id === 'regions') {
                this.showRegions = true;
                this.activePins = [];
                this.selectedToggle = 'regions';
            } else {
                this.showRegions = false;
                this.selectedToggle = 'places';

                const filteredPlaces = this.placesData
                    .filter((place) => {
                        if (typeof place.properties !== 'undefined') {
                            return place.properties.category.id === id;
                        }

                        return false;
                    });
                this.activePins = filteredPlaces;
            }
        },
        /**
         * Show all pins, remove regions
         */
        showAllPlaces() {
            this.activePins = this.placesData;
            this.showRegions = false;
        },
        /**
         * When toggle is changed, set appropriate category
         */
        onToggleChanged(category) {
            if (category === 'regions') {
                this.setCategory('regions');
                this.setStage(1);
            } else {
                this.showAllPlaces();
                this.setStage(0);
            }
        },
    },
};
</script>

<style lang="scss">
    .vs-main-map-wrapper {
        height: 75vh;
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

        &__no-js {
            display: none;
        }

        .vs-button-toggle-group {
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);

            @include media-breakpoint-up(lg) {
                display: none;
            }
        }
    }

    @include no-js {
        .vs-main-map-wrapper {
            display: none;

            &__no-js {
                display: flex;
            }
        }
    }
</style>
