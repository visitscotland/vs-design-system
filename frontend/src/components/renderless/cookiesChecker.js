/* eslint-disable */

// import Vue from 'vue/dist/vue.js';
// import CookieFallback from './CookiesFallback.vue';
import EventBus from '../../utils/event-bus';
import OneTrustVS from '../../utils/one-trust';
import cookieStore from '../../stores/cookies.store';

/**
 *  C0001 - STRICTLY NECESSARY
 *  C0002 - ANALYTICAL COOKIES
 *  C0003 - FUNCTIONAL COOKIES
 *  C0004 - MARKETING COOKIES
 */

/**
 * Component used to check which cookies preferences
 * the user is using and provide access to certain content
 * depending on those preferences
 *
 * @displayName Cookies Checker
 */

export default {
    render() {
        return () => null;
    },
    data() {
        return {
            oneTrust: new OneTrustVS(),
            containers: {
            },
            activeGroups: '',
        };
    },
    watch: {
        activeGroups: function () {
            const activeCookieArray = this.activeGroups.split(',');

            const filteredArray = activeCookieArray.filter(function (el) {
                return el != '';
            });

            cookieStore.dispatch('setCookieArray', filteredArray);
            cookieStore.dispatch('setTestRun', true);
        },
    },
    created() {
        //   Add eventbus to check for events related to OneTrust and cookies
        if (!(typeof window.OneTrustEventBus === 'function')) {
            window.OneTrustEventBus = new EventBus();

            window.OneTrustEventBus.addEventListener('instagram-visible', event => {
                // get category
                const OT_CATEGORY = event.detail.cookie_category;

                // get fallback from module
                let vsModule = document.getElementsByClassName(event.detail.module)[0];
                let container = document.querySelectorAll(`.${event.detail.container}`);
                let fallback = document.querySelector(`.${event.detail.fallback}`);

                container = container.length > 1 ? container : container[0];
                this.oneTrust.instaOT(OT_CATEGORY, vsModule, fallback, container);
            });
        }
    },
    mounted() {
        // should this be added with headContributions??
        const oneTrustScript = document.createElement('script');
        oneTrustScript.setAttribute(
            'src',
            'https://cdn-ukwest.onetrust.com/scripttemplates/otSDKStub.js'
        );
        oneTrustScript.setAttribute('data-document-language', 'true');
        oneTrustScript.setAttribute('type', 'text/javascript');
        oneTrustScript.setAttribute('charset', 'UTF-8');
        oneTrustScript.setAttribute(
            'data-domain-script',
            '99780805-2fce-47e4-85cc-f679fb814c21-test'
        );
        document.head.appendChild(oneTrustScript);

        // eslint-disable-next-line
        function OptanonWrapper() {}

        this.oneTrust.checkThirdPartyLibrary('OneTrust', () => {
            window.OneTrust.OnConsentChanged(() => {
                this.oneTrust.checkThirdPartyLibrary('OnetrustActiveGroups', () => {
                    // Check what cookies were accepted
                    this.activeGroups = window.OnetrustActiveGroups;
                });
            });
        });

        this.oneTrust.checkThirdPartyLibrary('OnetrustActiveGroups', () => {
            // Check what cookies were accepted
            this.activeGroups = window.OnetrustActiveGroups;
        });
    },
}