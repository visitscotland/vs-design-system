import { shallowMount } from '@vue/test-utils';

import VsTable from '../Table';

const factoryShallowMount = (propsData) => shallowMount(VsTable, {
    slots: {
        ...propsData,
    },
    propsData: {
        ...propsData,
    },
});

describe('VsTable', () => {
    it('should render a btable-stub', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.element.tagName).toBe('BTABLE-STUB');
    });
});
