<template>
    <section
        class="vs-no-js-cookies"
        data-test="vs-no-js-cookies"
    >
        <VsWarning
            class="vs-no-js-cookies__js"
            data-test="vs-no-js-cookies__no-js"
            :warning-message="noJsMessage"
        />
        <VsWarning
            class="vs-no-js-cookies__cookies"
            data-test="vs-no-js-cookies__no-cookies"
            v-if="cookiesMissing"
            :warning-message="noCookiesMessage"
            :warning-link="noCookiesLink"
        />
    </section>
</template>

<script>
import VsWarning from '@components/elements/warning/Warning';

/**
* A wrapper for vs-warning that handles checking if a componen is missing mandatory
* cookies, or if javascript is disabled
*
* @displayName No Js / No Cookie Placeholder
*/
export default {
    name: 'VsNoJsNoCookies',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsWarning,
    },
    props: {
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
    .vs-no-js-cookies {
        position: absolute;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
    }

    .vs-no-js-cookies__js {
        display: none;
    }

    .vs-no-js-cookies__cookies {
        display: flex;
    }

    @include no-js {
        .vs-no-js-cookies__js {
            display: flex;
        }

        .vs-no-js-cookies__cookies {
            display: none;
        }
    }
</style>
