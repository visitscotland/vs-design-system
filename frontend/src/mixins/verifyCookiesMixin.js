import cookieStore from '../stores/cookies.store';

const cookieCheckerMixin = {
    computed: {
        cookiesSet() {
            return cookieStore.getters.getCookieValues;
        },
        OnetrustActiveGroups() {
            return window.OnetrustActiveGroups;
        },
        requiredCookiesExist() {
            let cookiesExist = true;

            this.requiredCookies.forEach((cookieVal) => {
                if (this.cookiesSet.indexOf(cookieVal) === -1) {
                    cookiesExist = false;
                }
            });

            return cookiesExist;
        },
        cookiesSetStatus() {
            return cookieStore.getters.getTestRunStatus;
        },
    },
};

export default cookieCheckerMixin;
