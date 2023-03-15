## Usage
The main map wrapper component displays a map and a side panel, allowing the
user to filter and search for data contained on the map.

```jsx
<VsMainMapWrapper
    categoryHeading="Explore places to go"
    :filters="maps.mapFiltersSubcategories"
    :places-data="maps.placesDataFeatured.features"
    discover-text="discover"
    map-id="vs-map"
    initialSelected="places"
    :toggleData="[
        {
            text: 'Regions',
            value: 'regions',
            icon: 'map',
        },
        {
            text: 'Places',
            value: 'places',
            icon: 'map-marker',
        },
    ]"
    buttonsLabel="Select map type"
    clearSelectionText="Clear all"
    applyFiltersText="Show results"
    detailsEndpoint="http://172.28.81.65:8089/data/component/mapcard?id="
    :region-bounds="{
        'type': 'bounds',
        'coordinates': [
            [-3.058595889098882, 55.993275047970826],
            [-3.34402565597901, 55.887115661571926]
        ],
    }"
    filtersAppliedText="filters applied"
    clearFiltersText="clear filters"
    mapFilterMessage="Please apply filter(s) to refine results."
    mapNoResultsMessage="There are no results, please try again"
>
    <template slot="closeSidePanelText">
        <span class="sr-only">
            Close map filters
        </span>
    </template>
    <template slot="openSidePanelText">
        Map filters
    </template>
    <template slot="backBtnText">
        Go back one step
    </template>
    <template slot="resetSidePanelText">
        Reset filters
    </template>
    <template slot="mapLoadingText">
        Loading
    </template>
    <template slot="loadMoreText">
        Load more
    </template>
    <template slot="noJs">
        You need Javascript enabled to see this map
    </template>
    <template slot="panelLoadingMessage">
        Loading results
    </template>
    <template slot="zoomTooClose">
        We're sorry, you can't zoom in any more
    </template>
    <template slot="zoomTooFar">
        We're sorry, you can't zoom out any more
    </template>
</VsMainMapWrapper>
```

<!-- ```jsx
<VsMainMapWrapper
    categoryHeading="Explore places to go"
    :filters="maps.mapFilters"
    :places-data="maps.placesData.features"
    discover-text="discover"
    map-id="vs-map2"
    initialSelected="places"
    :toggleData="[
        {
            text: 'Regions',
            value: 'regions',
        },
        {
            text: 'Places',
            value: 'places',
        },
    ]"
    buttonsLabel="Select map type"
    clearSelectionText="Clear all"
    applyFiltersText="Show results"
>
    <template slot="closeSidePanelText">
        <span class="sr-only">
            Close map filters
        </span>
    </template>
    <template slot="openSidePanelText">
        Map filters
    </template>
    <template slot="backBtnText">
        Go back one step
    </template>
    <template slot="resetSidePanelText">
        Reset filters
    </template>
    <template slot="noJs">
        You need Javascript enabled to see this map
    </template>
</VsMainMapWrapper>
``` -->