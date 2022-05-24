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
* A placeholder component to display in place of a component disabled by no-js or by
* a missing mandatory cookie. It should be placed within a position: relative container
* and will fill the space, partially obscuring the component underneath.
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

<docs>
    ```
        <div
            class="position-relative no-js"
            style="width: 12em; height: 12em;"
        >
            <VsNoJsNoCookies
                noJsMessage="JavaScript is needed to watch this video."
            />
        </div>
        <br />
        <div
            class="position-relative"
            style="width: 25em; height: 10em;"
        >
            <VsNoJsNoCookies
                noJsMessage="JavaScript is needed to watch this video."
                cookiesMissing="true"
                noCookiesMessage="Cookies are needed to watch this video."
                :noCookiesLink="{
                    url: 'https://google.com',
                    label: 'Update my cookie settings'
                }"
            />
        </div>
        <br />
        <div
            class="position-relative no-js"
            style="width: 15em; height: 18em;"
        >
            <VsNoJsNoCookies
                cookiesMissing="true"
                noJsMessage="JavaScript is needed to watch this video."
                noCookiesMessage="Cookies are needed to watch this video."
                :noCookiesLink="{
                    url: 'https://google.com',
                    label: 'Update my cookie settings'
                }"
            />
        </div>
    ```
</docs>
