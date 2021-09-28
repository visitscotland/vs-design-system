/**
 * This is a helper util to hide the H1 on a section with
 * nested components on the page
 *
 */

export default {
    methods: {
        hideComponentSectionHeader(heading1) {
            const components = document.querySelector('.section-components');
            const heading2 = document.querySelector('h2[class^="rsg--heading"]');
            const parent = heading1.parentNode;

            // Check if the page also contains a H2
            // Hide H1 if true
            if (this.isInPage(heading2, components)) {
                parent.style.display = 'none';
            }
        },
        isInPage(node, parent) {
            return (node === parent) ? false : parent.contains(node);
        },
        init() {
            const heading1 = document.querySelector('h1[class^="rsg--heading"]');

            if (heading1) {
                this.hideComponentSectionHeader(heading1);
            }
        },
    },
    mounted() {
        this.init();
    },
};
