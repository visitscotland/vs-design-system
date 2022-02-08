import { shallowMount } from '@vue/test-utils';

import VsFormCheckbox from '../FormCheckbox';

const factoryShallowMount = (propsData) => shallowMount(VsFormCheckbox, {
    propsData: {
        value: 'accepted',
        label: 'Test label',
        fieldName: 'testname',
        ...propsData,
    },
});

describe('VsFormCheckbox', () => {
    it('should render a BFormCheckbox-stub', () => {
        const wrapper = factoryShallowMount();

        const checkboxStub = wrapper.findAll('BFORMCHECKBOX-STUB');

        expect(checkboxStub.exists()).toBe(true);
    });

    describe(':props', () => {
        it('should not render if the name prop is missing', async() => {
            const wrapper = factoryShallowMount();

            wrapper.setProps({
                fieldName: '',
            });

            await wrapper.vm.$nextTick();

            const checkboxStub = wrapper.findAll('BFORMCHECKBOX-STUB');

            expect(checkboxStub.exists()).toBe(false);
        });

        it('should add the value prop to the element', () => {
            const wrapper = factoryShallowMount();
            const checkboxStub = wrapper.find('BFORMCHECKBOX-STUB');

            expect(checkboxStub.attributes('value')).toBe('accepted');
        });

        it('should add the requiredText prop to the label if needed', () => {
            const wrapper = factoryShallowMount({
                validationRules: {
                    required: true,
                },
                requiredText: 'required',
            });

            expect(wrapper.text()).toContain('required');
        });
    });

    describe(':data', () => {
        it('should show the error class if the errors array contains items', async() => {
            const wrapper = factoryShallowMount();

            wrapper.setData({
                errors: ['required'],
            });

            await wrapper.vm.$nextTick();

            expect(wrapper.find('[data-test="vs-form-checkbox"]').classes()).toContain('vs-form-checkbox__invalid');
        });
    });
});
