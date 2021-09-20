/**
 * This is Vue Design System’s helper util that
 * highlights the currently active nav item.
 *
 * This is quite a hacky solution that really needs
 * to be refactored into proper rsg-components as
 * explained here:
 * https://vue-styleguidist.github.io/docs/Development.html#react-components
 *
 * Alternatively, once CSS selectors level 4 is released
 * and makes parent selectors somehow available, the
 * vueds-active class will be redundant and all can be
 * replaced with a parent selector to get ancestors of
 * li[class*=\"rsg--isSelected\"]. See
 * https://stackoverflow.com/a/1014958/2018496
 *
 */

export default {
    methods: {
        sortList() {
            const tocList = document.querySelectorAll('div[class^="rsg--sidebar"] nav > ul > li > ul > li > ul ');

            if (tocList) {
                Array.from(tocList).forEach((element) => {
                    Array.from(element.children)
                        .sort((a, b) => a.textContent.localeCompare(b.textContent))
                        .forEach((li) => element.appendChild(li));
                });
            }
        },
        init() {
            const sidebar = document.querySelector('div[class^="rsg--sidebar"]');

            if (sidebar) {
                this.sortList();
            }
        },
    },
    mounted() {
        this.init();
    },
};
