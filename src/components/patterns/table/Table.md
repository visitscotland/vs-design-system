## Usage
- Use a table when you need to make information easier to read and digest for a user and ensure it is clearly laid out and easy to understand

- Ensure a caption is included describing what the table is about

- The table must be properly marked up with correct header cells `<VsTableHeaderCell>` and data cells `<VsTableDataCell>` to make it accessible. Headers should describe what the data in rows or columns represents

- A table should never be used for creating page layouts, use the <a href="#/Elements/Grid">grid system</a> instead


### Standard
A simple table used for displaying data. By default, the table is responsive and will scroll when the data is too large for the screen. 

Use the `tableCaption` prop to provide a caption describing the table. 

```js
    <VsTable 
        tableCaption="Ski run information"
    >
        <VsTableHead>
            <VsTableRow>
                <VsTableHeaderCell>
                    Status
                </VsTableHeaderCell>
                <VsTableHeaderCell>
                    Runs
                </VsTableHeaderCell>
                <VsTableHeaderCell>
                    Lifts
                </VsTableHeaderCell>
            </VsTableRow>
        </VsTableHead>

        <VsTableBody>
            <VsTableRow>
                <VsTableDataCell>
                    <VsIcon name="tick" size="xs" class="mr-2"/> Open
                </VsTableDataCell>
                <VsTableDataCell>
                    7/21
                </VsTableDataCell>
                <VsTableDataCell>
                    3/7
                </VsTableDataCell>
            </VsTableRow>
            <VsTableRow>
                <VsTableDataCell>
                    <VsIcon name="expected-open" size="xs" class="mr-2"/> Expected to Open
                </VsTableDataCell>
                <VsTableDataCell>
                    13/21
                </VsTableDataCell>
                <VsTableDataCell>
                    3/7
                </VsTableDataCell>
            </VsTableRow>
            <VsTableRow>
                <VsTableDataCell>
                    <VsIcon name="close" size="xs" class="mr-2"/> Closed
                </VsTableDataCell>
                <VsTableDataCell>
                    7/21
                </VsTableDataCell>
                <VsTableDataCell>
                    1/7
                </VsTableDataCell>
            </VsTableRow>
        </VsTableBody>
        <VsTableFooter>
            <VsTableRow>
                <VsTableDataCell colspan="3" role="cell">
                    Last Updated: 11/01/2023
                </VsTableDataCell>
            </VsTableRow>
        </VsTableFooter>
    </VsTable>
```
### Column & Row Widths
Column and row widths can be adjusted by adding a `rowspan` or `colspan` to the element. 

```js
    <VsTable 
        tableCaption="Ski run information"
    >
        <VsTableHead>
            <VsTableRow>
           		<VsTableHeaderCell>
                    Center
                </VsTableHeaderCell>
                <VsTableHeaderCell>
                    Status
                </VsTableHeaderCell>
                <VsTableHeaderCell>
                    Runs
                </VsTableHeaderCell>
                <VsTableHeaderCell>
                    Lifts
                </VsTableHeaderCell>
            </VsTableRow>
        </VsTableHead>

        <VsTableBody>
            <VsTableRow>
            	<VsTableDataCell rowspan="3">
                    Glencoe
                </VsTableDataCell>
                <VsTableDataCell>
                    <VsIcon name="tick" size="xs" class="mr-2"/> Open
                </VsTableDataCell>
                <VsTableDataCell>
                    7/21
                </VsTableDataCell>
                <VsTableDataCell>
                    3/7
                </VsTableDataCell>
            </VsTableRow>
            <VsTableRow>
                <VsTableDataCell>
                    <VsIcon name="expected-open" size="xs" class="mr-2"/> Expected to Open
                </VsTableDataCell>
                <VsTableDataCell>
                    13/21
                </VsTableDataCell>
                <VsTableDataCell>
                    3/7
                </VsTableDataCell>
            </VsTableRow>
            <VsTableRow>
                <VsTableDataCell>
                    <VsIcon name="close" size="xs" class="mr-2"/> Closed
                </VsTableDataCell>
                <VsTableDataCell>
                    7/21
                </VsTableDataCell>
                <VsTableDataCell>
                    1/7
                </VsTableDataCell>
            </VsTableRow>
            <VsTableRow>
            	<VsTableDataCell rowspan="2">
                    Cairngorms
                </VsTableDataCell>
                <VsTableDataCell>
                    <VsIcon name="tick" size="xs" class="mr-2"/> Open
                </VsTableDataCell>
                <VsTableDataCell>
                    7/21
                </VsTableDataCell>
                <VsTableDataCell>
                    3/7
                </VsTableDataCell>      
            </VsTableRow>
            <VsTableRow>
                <VsTableDataCell>
                    <VsIcon name="close" size="xs" class="mr-2"/> Closed
                </VsTableDataCell>
                <VsTableDataCell>
                    7/21
                </VsTableDataCell>
                <VsTableDataCell>
                    1/7
                </VsTableDataCell>
                </VsTableRow>
        </VsTableBody>
        <VsTableFooter>
            <VsTableRow>
                <VsTableDataCell colspan="4">
                    Last Updated: 11/01/2023
                </VsTableDataCell>
            </VsTableRow>
        </VsTableFooter>
    </VsTable>
```

### Stacked
Generally, anything more than 3 columns of data might not display well on small screens. A stacked version can be used when there is a lot of data to display.

Use the `tableType` prop to set the table to stacked view. 

Use the `stacked-heading` prop on the header or data cells to provide the heading as the `VsTableHead` will not be visible. 

```js
    <VsTable 
        tableCaption="Ski run information"
        tableType="stacked"
    >
        <VsTableHead>
            <VsTableRow>
                <VsTableHeaderCell>
                    Status
                </VsTableHeaderCell>
                <VsTableHeaderCell>
                    Runs
                </VsTableHeaderCell>
                <VsTableHeaderCell>
                    Lifts
                </VsTableHeaderCell>
            </VsTableRow>
        </VsTableHead>

        <VsTableBody>
            <VsTableRow>
                <VsTableHeaderCell stacked-heading="Centre">
                    Glencoe
                </VsTableHeaderCell>
                <VsTableDataCell stacked-heading="Status">
                    Open
                </VsTableDataCell>
                <VsTableDataCell stacked-heading="Runs">
                    7/21
                </VsTableDataCell>
                <VsTableDataCell stacked-heading="Lifts">
                    3/7
                </VsTableDataCell>
            </VsTableRow>
            <VsTableRow>
                <VsTableHeaderCell stacked-heading="Centre">
                    Cairngorms
                </VsTableHeaderCell>
                <VsTableDataCell stacked-heading="Status">
                    Expected to Open
                </VsTableDataCell>
                <VsTableDataCell stacked-heading="Runs">
                    13/21
                </VsTableDataCell>
                <VsTableDataCell stacked-heading="Lifts">
                    3/7
                </VsTableDataCell>
            </VsTableRow>
            <VsTableRow>
                <VsTableHeaderCell stacked-heading="Centre">
                    The Lecht
                </VsTableHeaderCell>
                <VsTableDataCell stacked-heading="Status">
                    Closed
                </VsTableDataCell>
                <VsTableDataCell stacked-heading="Runs">
                    7/21
                </VsTableDataCell>
                <VsTableDataCell stacked-heading="Lifts">
                    1/7
                </VsTableDataCell>
            </VsTableRow>
        </VsTableBody>
    </VsTable>
```
## Accessibility
- By default, the table elements will have the appropriate roles applied
- A table should have a caption applied, this will be visually hidden for screen readers


