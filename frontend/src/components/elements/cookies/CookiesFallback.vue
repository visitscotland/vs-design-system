<template>
    <div
        class="cookie-fallback no-cookie-container-instagram"
        style="display: block"
    >
        <img
            class="warning-icon"
            style="margin: 0 auto; height: 60px; margin-bottom: 2rem"
            src="../../../assets/images/icons/warning.png"
            alt="warning icon"
        >
        <p
            class="fallback-text"
            style="padding: 0"
        >
            <!-- eslint-disable vue/no-v-html -->
            <span v-html="fallbackMessage" />
            <!-- eslint-enable vue/no-v-html -->
            <button
                id="ot-sdk-btn"
                class="ot-sdk-show-settings"
            >
                Button Text
            </button>
        </p>
    </div>
</template>

<script>

/**
 * Component used to show users a message whenever content
 * behind it is locked due to their cookies preferences
 *
 * @displayName Cookies Fallback
 */

export default {
    name: 'VsCookiesFallback',
    props: {
        fallbackMessage: {
            type: String,
            default: 'Cookies are required to view this content. <br>Change your preferences at ',
        },
    },
    mounted() {
        const oneTrustScript = document.createElement('script');
        oneTrustScript.setAttribute('src', 'https://cdn-ukwest.onetrust.com/scripttemplates/otSDKStub.js');
        oneTrustScript.setAttribute('data-document-language', 'true');
        oneTrustScript.setAttribute('type', 'text/javascript');
        oneTrustScript.setAttribute('charset', 'UTF-8');
        oneTrustScript.setAttribute('data-domain-script', '99780805-2fce-47e4-85cc-f679fb814c21-test');
        document.head.appendChild(oneTrustScript);

        // eslint-disable-next-line
        // function OptanonWrapper() { }
    },
};
</script>
