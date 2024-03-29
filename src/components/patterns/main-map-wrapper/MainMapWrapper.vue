<template>
    <VsContainer
        data-test="vs-main-map-wrapper"
    >
        <VsRow>
            <VsCol>
                <div
                    class="vs-main-map-wrapper"
                    :ref="mapId"
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
                            :panel-status="panelStatus"
                            :panel-message="currentStage === 0 ? panelMessage : null"
                            :total-pins="totalEndpointPins"
                            :current-list-item-focus="focussedListItem"
                            @set-category="setCategory"
                            @set-subcategory="setSubCategory"
                            @subcategories-filtered="filterSubCategories"
                            @set-stage="setStage"
                            @close-panel="closePanel"
                            @show-item-detail="showDetail"
                            @filter-places="filterPlaces"
                            @load-more-places="loadMorePlaces"
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

                            <template slot="panelLoadingMessage">
                                <slot name="panelLoadingMessage" />
                            </template>

                            <template slot="loadMoreText">
                                <slot name="loadMoreText" />
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
                            :show-info-message="mapStatus !== ''"
                            @show-detail="showDetail"
                            @set-category="setCategory"
                            @map-ready="setMapReady"
                            @zoom-reset="resetZoom = false"
                            :bounds-data="regionBounds"
                            :reset-zoom="resetZoom"
                        >
                            <template slot="mapLoadingText">
                                <!-- @slot Message to show when map is loading  -->
                                <slot name="mapLoadingText" />
                            </template>
                            <template slot="infoMessage">
                                {{ infoMessage }}
                            </template>
                            <template slot="zoomTooClose">
                                <!-- @slot Message to show when map zoom is too close -->
                                <slot name="zoomTooClose" />
                            </template>
                            <template slot="zoomTooFar">
                                <!-- @slot Message to show when map zoom is too far -->
                                <slot name="zoomTooFar" />
                            </template>
                            <template slot="noJs">
                                <!-- @slot Message to show when JS is disabled  -->
                                <slot name="noJs" />
                            </template>
                        </VsMap>
                        <VsButtonToggleGroup
                            data-test="vs-main-map-wrapper__map-toggle-group"
                            v-if="!hideMobileToggle"
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
            focussedListItem: this.focussedListItem,
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
         * Bounds if map needs to show a specific area
         */
        regionBounds: {
            type: Object,
            default: () => {},
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
        /**
         * Text to show on map propmpting user to filter results
         */
        mapFilterMessage: {
            type: String,
            required: true,
        },
        /**
         * Text to show on map when there are no results
         */
        mapNoResultsMessage: {
            type: String,
            required: true,
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
         * Option to hide the mobile toggle buttons
         */
        hideMobileToggle: {
            type: Boolean,
            default: false,
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
            currentEndpointData: [],
            mapStatus: '',
            panelStatus: 'map-loading',
            mapReady: false,
            showPanelMessage: null,
            currentPanelEndpointFilters: '',
            totalEndpointPins: 0,
            focussedListItem: 0,
            resetZoom: false,
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
        subCatActiveFilters() {
            return mapStore.getters.getActiveSubcatFilters;
        },
        infoMessage() {
            let msg = '';

            switch (this.mapStatus) {
            case ('no-results'):
                msg = this.mapNoResultsMessage;
                break;
            case ('filter-results'):
                msg = this.mapFilterMessage;
                break;
            default:
                break;
            }

            return msg;
        },
    },
    watch: {
        selectedSubCategory(val) {
            if (val === null) {
                this.mapStatus = '';
                this.resetZoom = true;
            }
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

        this.$root.$on('clearSelectedSubcats', () => {
            this.resetZoom = true;
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
            if (document.fullscreenElement) {
                document.exitFullscreen();
            }
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

            this.currentPanelEndpointFilters = filterString;

            this.getSubcatMarkerData(filterString);
            this.getSubcatPanelData(filterString, 0);
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

            this.activePins = [];

            // show markers only if the subcategory has been filtered
            if (typeof endpointFilters === 'undefined') {
                this.activePins = [];
                this.mapStatus = 'filter-results';
            } else {
                axios.get(endpoint).then((response) => {
                    this.totalEndpointPins = response.data.features.length;

                    if (this.totalEndpointPins === 0) {
                        this.mapStatus = 'no-results';
                    } else {
                        response.data.features.forEach((feature) => {
                            const modifiedFeature = feature;
                            modifiedFeature.properties.apiData = true;
                            this.activePins.push(modifiedFeature);
                        });
                        this.mapStatus = '';
                    }
                });
            }
        },
        /**
         * Makes a call to the endpoint in the subcategory data which
         * provides a random 24 items for the side panel
         */
        getSubcatPanelData(endpointFilters, page) {
            const subCat = this.filters.filter((cat) => cat.id === this.selectedSubCategory);
            let endpoint = subCat[0].listProductsEndPoint;
            if (typeof endpointFilters !== 'undefined') {
                endpoint += endpointFilters;
            }

            if (page !== 0) {
                endpoint += `${endpointFilters}&page=${page}`;
            }

            this.panelStatus = 'loading-data';

            axios.get(endpoint).then((response) => {
                if (typeof response.data.data.products !== 'undefined') {
                    this.setStage(1);
                    if (page === 0) {
                        this.subCatList = response.data.data.products;
                        this.focussedListItem = 0;
                    } else {
                        this.focussedListItem = page * 24;
                        this.subCatList = this.subCatList.concat(response.data.data.products);
                    }
                } else {
                    this.mapStatus = 'no-results';
                }
                this.panelStatus = null;
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
                    this.currentEndpointData = [];
                    if (this.selectedSubCategory === null) {
                        this.resetZoom = true;
                        this.showAllPlaces();
                        this.mapStatus = '';
                    } else {
                        this.getSubcatMarkerData();
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
         * Load more places from endpoint
         */
        loadMorePlaces(page) {
            this.getSubcatPanelData(this.currentPanelEndpointFilters, page);
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
            } else if (category === 'icentres') {
                this.setCategory('serv');
                this.setStage(1);
            } else if (category === 'places-regional') {
                this.setCategory('twnv');
                this.setStage(1);
            } else {
                this.showAllPlaces();
                this.setStage(0);
            }
        },
        /**
         * Set whether or not map is ready to use
         */
        setMapReady(val) {
            this.mapReady = val;
            if (val) {
                this.panelStatus = null;
            } else {
                this.panelStatus = 'loading';
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
            position: relative;
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
            top: $spacer-2;
            left: $spacer-2;
            max-width: calc(100% - 60px);
            padding: $spacer-1 $spacer-3;
            // ensure button can always be clicked
            // to avoid user getting trapped by overlay
            z-index: 100;

            @include media-breakpoint-up(lg) {
                display: none;
            }
        }

        &__no-js {
            display: none;
        }

        .vs-button-toggle-group {
            position: absolute;
            top: 100%;
            left: 50%;
            transform: translate(-50%, -50%);
            margin-bottom: 0;

            @include media-breakpoint-up(lg) {
                display: none;
            }
        }
    }

    .os-api-branding.logo,
    .os-api-branding.copyright {
        margin-bottom: $spacer-8 !important; //important needed for overriding OS styles
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
