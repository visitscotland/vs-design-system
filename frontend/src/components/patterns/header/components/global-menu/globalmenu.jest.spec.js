import { mount } from '@vue/test-utils';

import VsList from '@components/elements/list';
import VsGlobalMenu from './GlobalMenu.vue';
import VsDropdown from '../../../dropdown/Dropdown';

const mainSite = 'https://www.visitscotland.com/';

const factoryMount = (propsData) => mount(VsGlobalMenu, {
    propsData: {
        activeSite: mainSite,
        ...propsData,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryMount();
});

describe('<VsGlobalMenu />', () => {
    it('should render a component with the class `.vs-global-menu`', () => {
        expect(wrapper.classes()).toContain('vs-global-menu');
    });

    it('should open all links that are not the `activeSite` on new tabs', () => {
        const links = wrapper.findAll('a');
        const targets = links.filter((link) => (link.attributes('href') !== mainSite));
        const blankTargets = targets.filter((link) => (link.attributes('target') === '_blank'));

        expect(links.length).toBe(10);
        expect(blankTargets.length).toBe(8);
    });

    it('should open the `activeSite` on the same tab', () => {
        const links = wrapper.findAll('a');
        const targets = links.filter((link) => (link.attributes('href') === mainSite));
        const selfTargets = targets.filter((link) => (link.attributes('target') === '_self'));

        expect(links.length).toBe(10);
        expect(selfTargets.length).toBe(2);
    });

    describe(':mobile', () => {
        it('should contain `VsDropdown` with the class `.vs-global-menu__websites--mobile`', () => {
            const dropdown = wrapper.find('.vs-global-menu__websites--mobile');

            expect(dropdown.exists()).toBe(true);
            expect(dropdown.is(VsDropdown)).toBe(true);
        });

        it('should contain 5 items on the `VsDropdown`', () => {
            const dropdown = wrapper.find('.vs-global-menu__websites--mobile');
            const li = dropdown.findAll('li');

            expect(li.length).toBe(5);
        });

        describe(':props', () => {
            it(':ourWebsitesLabel - should render "Our Websites" as the default translation label', () => {
                const dropdown = wrapper.find('.vs-global-menu__websites--mobile .btn');

                expect(dropdown.text()).toBe('Our Websites');
            });

            it(':ourWebsitesLabel - should receive the translation label by props and render accordingly', () => {
                const translationLabel = 'i nostri siti';

                const modifiedWrapper = factoryMount({
                    ourWebsitesLabel: translationLabel,
                });

                const dropdown = modifiedWrapper.find('.vs-global-menu__websites--mobile .btn');

                expect(dropdown.text()).toBe(translationLabel);
            });
        });
    });

    describe(':desktop', () => {
        it('should contain `VsList` with the class `.vs-global-menu__websites--desktop`', () => {
            const list = wrapper.find('.vs-global-menu__websites--desktop');

            expect(list.exists()).toBe(true);
            expect(list.is(VsList)).toBe(true);
        });

        it('should contain 5 items on the `VsList`', () => {
            const list = wrapper.find('.vs-global-menu__websites--desktop');
            const li = list.findAll('li');
            expect(li.length).toBe(5);
        });

        describe(':props', () => {
            it(':active-site - should render the active website link with the proper class on desktop', () => {
                const link = wrapper.find('.vs-global-menu__websites--desktop .vs-global-menu__websites__item--active a');

                expect(link.exists()).toBe(true);
            });

            it(':active-site - should receive the active site by props and render accordingly', () => {
                const coporateSite = 'https://www.visitscotland.org/';

                const modifiedWrapper = factoryMount({
                    activeSite: coporateSite,
                });

                const link = modifiedWrapper.find('.vs-global-menu__websites--desktop .vs-global-menu__websites__item--active a');

                expect(link.exists()).toBe(true);
                expect(link.attributes('href')).toBe(coporateSite);
            });
        });
    });
});
