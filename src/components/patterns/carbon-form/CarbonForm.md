```jsx
    <VsCarbonFormResults
        title="results"
        :totalTons="0.019"
        :transportTons="0.014"
        :foodTons="0.005"
        comparison="<p>That's the equivalent of xxx journeys!</p>"
        :comparisonTons="0.01"
    >
    </VsCarbonFormResults>
```

```jsx
    <VsCarbonForm
        dataUrl="http://localhost:5000/carbon-calculator/carbon-calculator-fields.json"
        messagingUrl="http://localhost:5000/common/messaging.json"
        countryListUrl="http://localhost:5000/common/countries.json"
    >
    </VsCarbonForm>
```