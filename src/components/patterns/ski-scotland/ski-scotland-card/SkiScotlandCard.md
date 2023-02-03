## Usage

```jsx
    <VsContainer>
        <VsRow
            class="mx-n4 mx-lg-n8"
        >
            <VsCol
                cols="12"
                sm="6"
                md="4"
                class="px-4 px-lg-8"
            >
                <VsSkiScotlandCard
                    centre-info-url="https://glencoe.infonet-online.fr/json/snowreport.json"
                    locale="en-gb"
                    img-src="https://via.placeholder.com/400x200"
                    img-alt="Glencoe ski centre"
                    more-details-link="#"
                    piste-map-link="#"
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
            <VsCol
                cols="12"
                sm="6"
                md="4"
                class="px-4 px-lg-8"
            >
                <VsSkiScotlandCard
                    centre-info-url="https://www.cairngormmountain.co.uk/_mountain_feed/?json"
                    locale="fr-fr"
                    img-src="https://via.placeholder.com/400x200"
                    img-alt="Cairngorm ski centre"
                >
                    <template slot="centre-name">Cairngorms</template>
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
            <VsCol
                cols="12"
                sm="6"
                md="4"
                class="px-4 px-lg-8"
            >
                <VsSkiScotlandCard
                    centre-info-url="https://nevis-range.infonet-online.fr/json/snowreport.json"
                    locale="de-de"
                    img-src="https://via.placeholder.com/400x200"
                    img-alt="Nevis Range ski centre"
                >
                    <template slot="centre-name">Nevis Range</template>
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
            <VsCol
                cols="12"
                sm="6"
                md="4"
                class="px-4 px-lg-8"
            >
                <VsSkiScotlandCard
                    centre-info-url="http://the-lecht.infonet-online.fr/json/snowreport.json"
                    locale="en-us"
                    img-src="https://via.placeholder.com/400x200"
                    img-alt="The Lecht ski centre"
                >
                    <template slot="centre-name">The Lecht</template>
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
            <VsCol
                cols="12"
                sm="6"
                md="4"
                class="px-4 px-lg-8"
            >
                <VsSkiScotlandCard
                    centre-info-url="http://glenshee.infonet-online.fr/json/snowreport.json"
                    locale="es-es"
                    img-src="https://via.placeholder.com/400x200"
                    img-alt="Glenshee ski centre"
                >
                    <template slot="centre-name">Glenshee</template>
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
