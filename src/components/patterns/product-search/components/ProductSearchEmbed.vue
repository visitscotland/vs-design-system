<template>
    <div
        class="vs-product-search-embed"
        id="inline-search-container"
        v-bind="prefilAttrs"
    />
</template>

<script>
/**
 * The Product Search embed component is a wrapper for the the product
 * search widget which comes from an external source.
 *
 * @displayName Product Search embed
 */

export default {
    name: 'VsProductSearchEmbed',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * Config for prefilled fields and language
         */
        config: {
            type: Array,
            default() {
                return [];
            },
        },
    },
    data() {
        return {
            langConfig: {
                en: {
                    localeUrl: '',
                },
                fr: {
                    localeUrl: 'fr-fr',
                },
                de: {
                    localeUrl: 'de-de',
                },
                es: {
                    localeUrl: 'es-es',
                },
                it: {
                    localeUrl: 'it-it',
                },
                nl: {
                    localeUrl: 'nl-nl',
                },
            },
        };
    },
    computed: {
        /* Return an array of objects defining data attributes */
        prefilAttrs() {
            const dataAttrs = [];

            this.config.forEach((element) => {
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
        /* returns the URL for the language config file */
        getLangUrl() {
            const langObj = this.config.filter((o) => Object.prototype.hasOwnProperty.call(o, 'lang'));

            if (langObj.length > 0) {
                return this.langConfig[langObj[0].lang].localeUrl;
            }

            return this.langConfig.en.localeUrl;
        },
        /* returns the environment variable to include various files from */
        getEnvironment() {
            if (window.location.hostname.includes('develop')
                || window.location.hostname.includes('localhost')
                || window.location.hostname.includes('feature')) {
                return 'develop';
            }
            return 'www';
        },
    },
    beforeMount() {
        window.VS = {
        };

        const langScriptEl = document.createElement('script');
        langScriptEl.async = false;
        // TODO: the develop version isn't working at the moment so hard-coding
        // the environment for now
        // langScriptEl.setAttribute ('src', `https://${this.getEnvironment}.visitscotland.com/${this.getLangUrl}/data/template/search.js`);
        langScriptEl.setAttribute('src', `https://www.visitscotland.com/${this.getLangUrl}/data/template/search.js`);
        document.head.appendChild(langScriptEl);

        const psrScriptEl = document.createElement('script');
        psrScriptEl.async = false;
        psrScriptEl.setAttribute('src', 'https://api.visitscotland.com/dev/ui/product-search/static/js/bundle.js');
        // psrScriptEl.setAttribute('src', 'http://localhost:9999/static/js/bundle.js');
        document.head.appendChild(psrScriptEl);
    },
};
</script>

<style lang="scss">

@import "https://api.visitscotland.com/dev/ui/product-search/css/main.css";
// @import "http://localhost:9999/css/main.css";

.no-js {
    .vs-product-search-embed {
        display: none;
    }
}
</style>
