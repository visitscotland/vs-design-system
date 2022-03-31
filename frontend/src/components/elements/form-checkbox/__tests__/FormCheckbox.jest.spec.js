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

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsFormCheckbox', () => {
    it('should render a BFormCheckbox-stub', () => {
        const checkboxStub = wrapper.findAll('BFORMCHECKBOX-STUB');

        expect(checkboxStub.exists()).toBe(true);
    });

    describe(':props', () => {
        it('should not render if the name prop is missing', async() => {
            wrapper.setProps({
                fieldName: '',
            });

            await wrapper.vm.$nextTick();

            const checkboxStub = wrapper.findAll('BFORMCHECKBOX-STUB');

            expect(checkboxStub.exists()).toBe(false);
        });

        it('should add the value prop to the element', () => {
            const checkboxStub = wrapper.find('BFORMCHECKBOX-STUB');

            expect(checkboxStub.attributes('value')).toBe('accepted');
        });

        it('should display a validation message if validation fails', async() => {
            wrapper.setProps({
                invalid: true,
                validationRules: {
                    required: true,
                },
                validationMessages: {
                    required: 'This is required',
                },
            });

            wrapper.vm.manualValidate();

            await wrapper.vm.$nextTick();

            expect(wrapper.html()).toContain('This is required');
        });
    });

    describe(':watchers', () => {
        it('should call the `manualValidate` method when `triggerValidation` is emitted', async() => {
            const mockedMethod = jest.spyOn(wrapper.vm, 'manualValidate');

            wrapper.setProps({
                triggerValidate: true,
            });

            await wrapper.vm.$nextTick();

            expect(mockedMethod).toBeCalled();
        });
    });
});
