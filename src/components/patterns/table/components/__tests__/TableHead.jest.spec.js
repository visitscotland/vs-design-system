import { shallowMount } from '@vue/test-utils';

import VsTableHead from '../TableHead';

const slotContent = 'A table head goes here';

const factoryShallowMount = (propsData) => shallowMount(VsTableHead, {
    propsData: {
        ...propsData,
    },
    slots: {
        default: slotContent,
    },
});

describe('VsTableHead', () => {
    it('should render a BTHEAD-STUB', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.element.tagName).toBe('BTHEAD-STUB');
    });

    describe(':slots', () => {
        const wrapper = factoryShallowMount();
        it('renders content inserted into the default `slot`', () => {
            expect(wrapper.text()).toContain(slotContent);
        });
    });
});
