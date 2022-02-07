import { shallowMount } from '@vue/test-utils';

import VsFormCheckbox from '../FormCheckbox';

const factoryShallowMount = (propsData) => shallowMount(VsFormCheckbox, {
    slots: {
        'checkbox-label': 'slotText',
    },
    propsData: {
        value: 'accepted',
        ...propsData,
    },
});

describe('VsFormCheckbox', () => {
    it('should render a BFormCheckbox-stub', () => {
        const wrapper = factoryShallowMount({
            name: 'test-checkbox',
        });

        const checkboxStub = wrapper.findAll('BFORMCHECKBOX-STUB');

        expect(checkboxStub.exists()).toBe(true);
    });

    describe(':props', () => {
        it('should not render if the name prop is missing', () => {
            const wrapper = factoryShallowMount();
            const checkboxStub = wrapper.findAll('BFORMCHECKBOX-STUB');

            expect(checkboxStub.exists()).toBe(false);
        });

        it('should add the value prop to the element', () => {
            const wrapper = factoryShallowMount({
                name: 'test-checkbox',
            });
            const checkboxStub = wrapper.find('BFORMCHECKBOX-STUB');

            expect(checkboxStub.attributes('value')).toBe('accepted');
        });
    });
});
