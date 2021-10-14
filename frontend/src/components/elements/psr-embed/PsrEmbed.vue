<template>
    <div
        class="vs-psr-embed"
        id="inline-search-container"
        v-bind="prefilAttrs"
    />
</template>

<script>
/**
 * The PSR widget is embedded from an external source.
 * It can be customised with data attributes.
 *
 * @displayName PSR Embed
 */

export default {
    name: 'VsPsrEmbed',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * Pre-filled fields
         */
        prefilled: {
            type: Array,
            default() {
                return [];
            },
        },
    },
    computed: {
        /* Return an array of objects defining data attributes */
        prefilAttrs() {
            const dataAttrs = [];

            this.prefilled.forEach((element) => {
                const attrObj = {
                };
                const keyName = Object.keys(element);
                const attrKey = `data-${keyName}`;
                attrObj[attrKey] = element[keyName];

                dataAttrs.push(
                    attrObj,
                );
            });

            return dataAttrs;
        },
    },
    mounted() {
        const psrScriptEl = document.createElement('script');
        // psrScriptEl.setAttribute('src', 'https://develop.visitscotland.com/api/dev/ui/product-search/static/js/bundle.js');
        psrScriptEl.setAttribute('src', 'http://localhost:9999//static/js/bundle.js');
        document.head.appendChild(psrScriptEl);
    },
};
</script>

<style lang="scss">

// @import "https://develop.visitscotland.com/api/dev/ui/product-search/css/main.css";
@import "http://localhost:9999/css/main.css";

.no-js {
    .vs-psr {
        display: none;
    }
}

</style>

<docs>
```jsx
    <VsContainer>
        <VsRow>
            <VsCol md="6">
                <vs-psr-embed
                    :prefilled="[{'subSearchType': 'cate'},{'loc': 'Inverary'}]"
                />
            </VsCol>
        </VsRow>
    </VsContainer>
```
</docs>
