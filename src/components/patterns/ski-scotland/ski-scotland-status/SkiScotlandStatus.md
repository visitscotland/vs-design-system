## Usage

```jsx
    <VsHeading level="2">Glencoe Ski Status</VsHeading>
    <hr>
    <VsSkiScotlandStatus
        ski-status-url=" http://glencoe.infonet-online.fr/json/snowreport.json"
    >
        Test
        <div slot="centre-information">
            Centre information placeholder
        </div>
    </VsSkiScotlandStatus>
    <hr>
    <VsHeading level="2">Cairngorm Ski Status</VsHeading>
    <hr>
    <VsSkiScotlandStatus
        ski-status-url="https://www.cairngormmountain.co.uk/_mountain_feed/?json"
    >
        Test
        <div slot="centre-information">
            Centre information placeholder
        </div>
    </VsSkiScotlandStatus>
```