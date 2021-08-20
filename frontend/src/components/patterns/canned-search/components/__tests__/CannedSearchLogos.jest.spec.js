import { mount } from '@vue/test-utils';
import VsCannedSearchLogos from '../CannedSearchLogos';

const goodToGoLogo = 'Good to go';
const safeTravelsLogo = 'Safe travels';
const awards = [
    {
        id: 'qatasteourbest',
        name: 'Taste Our Best',
        image: 'http://visitscotlandpreview.nmdemo.net/wsimgs/awards/TOB-updated-white-back_286148508_714060445.jpg',
    },
    {
        id: 'qag2s',
        name: 'Green Tourism Silver',
        image: 'http://visitscotlandpreview.nmdemo.net/wsimgs/awards/GT_silver_200x200_275519812.png',
        type: 'GREEN_TOURISM',
    },
];

const factoryMount = (propsData) => mount(VsCannedSearchLogos, {
    propsData: {
        goodToGoLogo,
        safeTravelsLogo,
        awards,
        ...propsData,
    },
});

/* eslint-disable */
const originalWarn = console.warn.bind(console.warn);

beforeAll(() => {
    console.warn = (msg) => !msg.toString().includes('tooltip - The provided target is no valid HTML element.') && originalWarn(msg);
});

afterAll(() => {
    console.warn = originalWarn;
});
/* eslint-enable */

let wrapper;
beforeEach(() => {
    wrapper = factoryMount();
});

describe('VsCannedSearchLogos', () => {
    describe(':props', () => {
        it('should render a vs-tooltip with the title provided in the `goodToGoLogo` property', () => {
            const tooltip = wrapper.find('[data-test="vs-canned-search-logos__good-to-go"]');

            expect(tooltip.props('title')).toBe(goodToGoLogo);
        });

        it('should not render the good to go vs-tooltip if no `goodToGoLogo` property is provided', async() => {
            const testGtG = '';

            wrapper.setProps({
                goodToGoLogo: testGtG,
            });

            await wrapper.vm.$nextTick();

            const tooltip = wrapper.find('[data-test="vs-canned-search-logos__good-to-go"]');

            expect(tooltip.exists()).toBe(false);
        });

        it('should render a vs-tooltip with the title provided in the `safeTravelsLogo` property', () => {
            const tooltip = wrapper.find('[data-test="vs-canned-search-logos__safe-travels"]');

            expect(tooltip.props('title')).toBe(safeTravelsLogo);
        });

        it('should not render the safe travels vs-tooltip if no `safeTravelsLogo` property is provided', async() => {
            const testSt = '';

            wrapper.setProps({
                safeTravelsLogo: testSt,
            });

            await wrapper.vm.$nextTick();

            const tooltip = wrapper.find('[data-test="vs-canned-search-logos__safe-travels"]');

            expect(tooltip.exists()).toBe(false);
        });

        it('should render a logo for each entry in the `awards` property', () => {
            expect(wrapper.findAll('[data-test="vs-canned-search-logos__award-logo"]').length).toBe(awards.length);
        });
    });
});
