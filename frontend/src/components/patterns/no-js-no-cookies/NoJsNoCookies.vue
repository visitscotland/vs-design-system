<template>
    <section
        class="vs-no-js-cookies"
        data-test="vs-no-js-cookies"
    >
        <div
            class="vs-no-js-cookies__inner"
            data-test="vs-no-js-cookies__no-js"
            v-if="jsDisabled"
        >
            <p>{{ noJsMessage }}</p>
        </div>
        <div
            class="vs-no-js-cookies__inner"
            data-test="vs-no-js-cookies__no-cookies"
            v-if="cookiesMissing && !jsDisabled"
        >
            <p>{{ noCookiesMessage }}</p>
            <VsLink
                v-if="noCookiesLink && noCookiesLink.url"
                data-test="vs-no-js-cookies__no-cookies-link"
                :href="noCookiesLink.url"
            >
                {{ noCookiesLink.label }}
            </VsLink>
        </div>
    </section>
</template>

<script>
import VsLink from '@components/elements/link/Link';

/**
* A placeholder component to display in place of a component disabled by no-js or by
* a missing mandatory cookie
*
* @displayName No Js / No Cookie Placeholder
*/
export default {
    name: 'VsNoJsNoCookies',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsLink,
    },
    props: {
        /**
        * Set to true if JavaScript is disabled and the module cannot function, takes
        * priority over missing cookies
        */
        jsDisabled: {
            type: Boolean,
            default: false,
        },
        /**
        * Set to true if a cookie that is required for the module to function is not
        * accepted by the user
        */
        cookiesMissing: {
            type: Boolean,
            default: false,
        },
        /**
        * A message explaining why the component has been disabled with no js
        */
        noJsMessage: {
            type: String,
            default: '',
        },
        /**
        * A message explaining why the component has been disabled with disabled cookies
        */
        noCookiesMessage: {
            type: String,
            default: '',
        },
        /**
        * An object containing a link to the cookie settings page, should contain a `url`
        * field and a `label` field
        */
        noCookiesLink: {
            type: Object,
            default: null,
        },
    },
};
</script>

<style lang="scss">

</style>

<docs>
    ```
        <VsNoJsNoCookies
            jsDisabled="true"
            noJsMessage="JavaScript is needed to watch this video."
        />
        <VsNoJsNoCookies
            cookiesMissing="true"
            noCookiesMessage="Cookies are needed to watch this video."
            :noCookiesLink="{
                url: 'https://google.com',
                label: 'Update my cookie settings'
            }"
        />
        <VsNoJsNoCookies
            jsDisabled="true"
            cookiesMissing="true"
            noJsMessage="JavaScript is needed to watch this video."
            noCookiesMessage="Cookies are needed to watch this video."
            :noCookiesLink="{
                url: 'https://google.com',
                label: 'Update my cookie settings'
            }"
        />
    ```
</docs>
