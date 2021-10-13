<template>
    <div
        class="vs-psr"
        id="inline-search-container"
        v-bind="prefilAttrs"
    />
</template>

<script>
/**
 * The PSR widget is embedded from an external source.
 * It can be customised with data attributes.
 *
 * @displayName PSR
 */

export default {
    name: 'VsPsr',
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
        psrScriptEl.setAttribute('src', 'https://develop.visitscotland.com/api/dev/ui/product-search/static/js/bundle.js');
        document.head.appendChild(psrScriptEl);
    },
};
</script>

<style lang="scss">

@import "https://develop.visitscotland.com/api/dev/ui/product-search/css/main.css";

.no-js {
    .vs-psr {
        display: none;
    }
}

</style>

<docs>
```jsx
    <vs-psr
        :prefilled="[{'subSearchType': 'cate'},{'loc': 'Inverary'}]"
    />
```
</docs>
