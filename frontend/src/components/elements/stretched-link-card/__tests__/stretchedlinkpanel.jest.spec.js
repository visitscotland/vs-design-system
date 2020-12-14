import { shallowMount } from '@vue/test-utils';
import VsStretchedLinkPanels from '../components/StretchedLinkPanels';

const factoryShallowMount = () => shallowMount(VsStretchedLinkPanels, {
    propsData: {
        days: '10',
        transport: 'Transport text',
        daysLabel: 'days',
    },
});

describe('VsStretchedLinkCard', () => {
    describe(':props', () => {
        it('should render a `.vs-stretched-link-panel--days` class if the days prop is defined', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="vs-stretched-link-panel--days"]').text()).toContain('10');
        });

        it('should render an element with the text prop content if it is defined', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="vs-stretched-link-panel__icon"]').text()).toBe('Transport text');
        });

        it('should render the contnet of the `stretchedLinkPanelDays` slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="vs-stretched-link-panel--days"]').text()).toContain('days');
        });
    });
});
