<template>
    <div
        class="vs-canned-search"
        data-test="vs-canned-search"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="10"
                    offset="1"
                    md="8"
                    offset-md="2"
                    lg="6"
                    offset-lg="3"
                    class="vs-canned-search__buttons"
                >
                    <!-- @slot Holds one or more navigation buttons  -->
                    <slot
                        name="cannedSearchButtons"
                    />
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>

/**
* Wrapper for canned search, invokes api call to provided endpoint and
* creates cards for products
*
* @displayName Canned Search
*/

export default {
    name: 'VsCannedSearch',
    status: 'prototype',
    release: '0.0.1',
    components: {
    },
    props: {
        /**
        * The url that products should be retrieved from for display
        */
        apiUrl: {
            type: String,
            default: '',
        },
    },
};

</script>

<style lang="scss">

</style>

<docs>
```jsx
    <VsModuleWrapper>
        <template slot="vsModuleWrapperHeading">
            B&Bs, guesthouses and hostels in Loch Lomond and The Trossachs national park
        </template>
        <template slot="vsModuleWrapperIntro">
            Find your perfect place to stay from a wide range of rural, coastal and mountain
            locations...
        </template>

        <VsCannedSearch>
            <template slot="cannedSearchButtons">
                <VsButton
                    class="mr-2"
                    href="https://www.visitscotland.com"
                >
                    View All B&Bs
                </VsButton>
                <VsButton
                    class="ml-2"
                    href="https://www.visitscotland.com"
                    icon="map"
                >
                    View on Map
                </VsButton>
            </template>
        </VsCannedSearch>
    </VsModuleWrapper>
```
</docs>
