## Usage

```jsx
    <VsContainer>
        <VsRow>
            <VsCol
                cols="12"
                sm="6"
                md="4"
            >
                <VsSkiScotlandCard
                    centre-info-url="https://glencoe.infonet-online.fr/json/snowreport.json"
                    locale="en-gb"
                    img-src="https://via.placeholder.com/400x200"
                    img-alt="Glencoe ski centre"
                >
                    <template slot="centre-name">Glencoe</template>
                    <div slot="data-loading">
                        Data is currently loading, please wait...
                    </div>
                    <div slot="data-unavailable">
                        Data is currently unavailable, please try again later.
                    </div>
                    <div slot="js-required">
                        JavaScript is required to load more ski data.
                    </div>
                </VsSkiScotlandCard>
            </VsCol>
        </VsRow>
    </VsContainer>
```
