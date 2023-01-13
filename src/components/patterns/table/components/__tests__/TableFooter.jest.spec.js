import { shallowMount } from '@vue/test-utils';

import VsTableFooter from '../TableFooter';

const slotContent = 'A table footer goes here';

const factoryShallowMount = (propsData) => shallowMount(VsTableFooter, {
    propsData: {
        ...propsData,
    },
    slots: {
        default: slotContent,
    },
});

describe('VsTableFooter', () => {
    it('should render a BTFOOT-STUB', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.element.tagName).toBe('BTFOOT-STUB');
    });

    describe(':slots', () => {
        const wrapper = factoryShallowMount();
        it('renders content inserted into the default `slot`', () => {
            expect(wrapper.text()).toContain(slotContent);
        });
    });
});
