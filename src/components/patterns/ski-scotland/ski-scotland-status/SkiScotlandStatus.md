## Usage

```jsx
    <VsHeading level="2">Glencoe Ski Status</VsHeading>
    <hr>
    <VsSkiScotlandStatus
        ski-status-url="http://glencoe.infonet-online.fr/json/snowreport.json"
        locale="en-gb"
    >
        <div slot="centre-information">
            Centre information placeholder
        </div>
        <div slot="data-loading">
            Data is currently loading, please wait...
        </div>
        <div slot="js-required">
            JavaScript is required to load more ski data.
        </div>
    </VsSkiScotlandStatus>
    <hr>
    <VsHeading level="2">Cairngorm Ski Status</VsHeading>
    <hr>
    <VsSkiScotlandStatus
        ski-status-url="https://www.cairngormmountain.co.uk/_mountain_feed/?json"
        locale="fr-fr"
    >
        <div slot="centre-information">
            Centre information placeholder
        </div>
        <div slot="data-loading">
            Data is currently loading, please wait...
        </div>
        <div slot="js-required">
            JavaScript is required to load more ski data.
        </div>
    </VsSkiScotlandStatus>
```