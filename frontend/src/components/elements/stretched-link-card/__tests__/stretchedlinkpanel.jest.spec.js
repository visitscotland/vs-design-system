import { shallowMount } from '@vue/test-utils';
import VsStretchedLinkPanels from '../components/StretchedLinkPanels';

describe('VsStretchedLinkCard', () => {
    describe(':props', () => {
        it('should render a `.vs-stretched-link-panel--days` class if the days prop is defined', () => {
            const wrapper = shallowMount(VsStretchedLinkPanels, {
                propsData: {
                    days: '10',
                },
            });
            expect(wrapper.find('[data-test="vs-stretched-link-panel--days"]').exists()).toBe(true);
        });

        it('should render an element with the text prop content if it is defined', () => {
            const wrapper = shallowMount(VsStretchedLinkPanels, {
                propsData: {
                    transport: 'Panel text',
                },
            });
            expect(wrapper.find('[data-test="vs-stretched-link-panel__icon"]').text()).toBe('Panel text');
        });
    });
});
