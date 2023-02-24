## Usage
TBC 

### Standard
```jsx
    <VsModuleWrapper>
        <template slot="vsModuleWrapperHeading">
            Your Pictures Of Scottish Castles
        </template>

        <VsEmbedWrapper
            noCookieText="You need cookies enabled to view this content"
            errorText="Sorry, there's been an error, please try again later"
            noJsText="You need Javascript enabled to see this content"
        >
            <template slot="embedIntroCopy">
                Share your snaps with us by using #ScottishCastle or #VisitScotland
            </template>
            <template slot="embedWidget">
                Embed Tag Goes Here
            </template>
        </VsEmbedWrapper>
    </VsModuleWrapper>
```

### No Cookies Required
```jsx
    <VsModuleWrapper>
        <template slot="vsModuleWrapperHeading">
            Your Pictures Of Scottish Castles
        </template>

        <VsEmbedWrapper
            no-cookies-required
            noCookieText="You need cookies enabled to view this content"
            errorText="Sorry, there's been an error, please try again later"
            noJsText="You need Javascript enabled to see this content"
        >
            <template slot="embedIntroCopy">
                Share your snaps with us by using #ScottishCastle or #VisitScotland
            </template>
            <template slot="embedWidget">
                Embed Tag Goes Here
            </template>
        </VsEmbedWrapper>
    </VsModuleWrapper>
```