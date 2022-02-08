import { shallowMount } from '@vue/test-utils';

import VsFormSelect from '../FormSelect';

const factoryShallowMount = (propsData) => shallowMount(VsFormSelect, {
    propsData: {
        fieldName: 'testselect',
        options: [
            {
                text: 'Item 1',
                value: 'first',
            },
            {
                text: 'Item 2',
                value: 'second',
            },
        ],
        ...propsData,
    },
});

describe('VsFormSelect', () => {
    it('should render a BFormSelect-stub', () => {
        const wrapper = factoryShallowMount();

        const selectStub = wrapper.findAll('BFORMSELECT-STUB');

        expect(selectStub.exists()).toBe(true);
    });

    describe(':props', () => {
        it('should render the element with the `fieldName` prop as the name attribute', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.find('[data-test="vs-form-select"]').attributes('name')).toBe('testselect');
        });
    });
});
