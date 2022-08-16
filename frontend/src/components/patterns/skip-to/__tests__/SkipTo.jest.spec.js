import { shallowMount } from '@vue/test-utils';
import VsSkipTo from '../SkipTo';

const factoryShallowMount = () => shallowMount(VsSkipTo, {
    slots: {
        skipToText: 'Skip to',
        mainMenuText: 'Main menu',
        mainContentText: 'Main content',
        searchText: 'Search',
        footerText: 'Footer',
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsSiteSearch', () => {
    it('should render a component with the data-test attribute `vs-skip-to`', () => {
        expect(wrapper.find('[data-test=vs-skip-to').exists()).toBe(true);
    });

    describe(':slots', () => {
        it('should render the content of the `Skip to` slot', () => {
            expect(wrapper.text()).toContain('Skip to');
        });

        it('should render the content of the `mainMenuText` slot', () => {
            expect(wrapper.text()).toContain('Main menu');
        });

        it('should render the content of the `mainContentText` slot', () => {
            expect(wrapper.text()).toContain('Main content');
        });

        it('should render the content of the `searchText` slot', () => {
            expect(wrapper.text()).toContain('Search');
        });

        it('should render the content of the `footerText` slot', () => {
            expect(wrapper.text()).toContain('Footer');
        });
    });
});
