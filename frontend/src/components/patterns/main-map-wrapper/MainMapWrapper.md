## Usage
The main map wrapper component displays a map and a side panel, allowing the
user to filter and search for data contained on the map.

```jsx
<VsMainMapWrapper
    categoryHeading="Explore places to go"
>
    <template slot="closeSidePanelText">
        Close search and filter panel
    </template>
    <template slot="openSidePanelText">
        Open search and filter panel
    </template>
</VsMainMapWrapper>
```