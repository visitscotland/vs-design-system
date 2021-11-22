import { shallowMount } from '@vue/test-utils';

import VsTooltip from '../Tooltip';

const slotText = 'Tooltip content';

const factoryShallowMount = (propsData) => shallowMount(VsTooltip, {
    slots: {
        default: slotText,
    },
    propsData: {
        title: 'Bus',
        ...propsData,
    },
});

describe('VsTooltip', () => {
    it('should render a btooltip-stub element', () => {
        const wrapper = factoryShallowMount();
        const tooltip = wrapper.find('btooltip-stub');

        expect(tooltip.exists()).toBe(true);
    });

    describe(':props', () => {
        it(':title - should accept and render an `title` property', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.attributes('title')).toBe('Bus');
        });
    });
});
