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
        },
        {
            text: 'Places',
            value: 'places',
        },
    ]"
    buttonsLabel="Select map type"
    clearSelectionText="Clear all"
    applyFiltersText="Show results"
    detailsEndpoint="http://172.28.81.65:8089/data/component/mapcard?id="
    :region-bounds="{
        'type': 'bounds',
        'coordinates': [
            [-1.9512628517431096, 57.21203739352487],
            [-2.253781748576017, 57.10308348515494]
        ],
    }"
    filtersAppliedText="filters applied"
    clearFiltersText="clear filters"
    mapFilterMessage="Please apply filter(s) to refine results."
    mapNoResultsMessage="There are no results, please try again"
    panelMessage="Get friendly and helpful advice for your trip at our iCentres"
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
    <template slot="loadMoreText">
        Load more
    </template>
    <template slot="noJs">
        You need Javascript enabled to see this map
    </template>
    <template slot="panelLoadingMessage">
        Loading results
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