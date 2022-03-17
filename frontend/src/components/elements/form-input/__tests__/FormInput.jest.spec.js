import { mount, shallowMount } from '@vue/test-utils';
import VsFormInput from '../FormInput';

const factoryShallowMount = (propsData) => shallowMount(VsFormInput, {
    propsData: {
        fieldName: 'testname',
        type: 'text',
        ...propsData,
    },
});

const factoryMount = (propsData) => mount(VsFormInput, {
    propsData: {
        fieldName: 'testname',
        type: 'text',
        ...propsData,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsFormInput', () => {
    it('should render a bform-input-stub', () => {
        expect(wrapper.attributes('data-test')).toBe('vs-input');
    });

    describe(':props', () => {
        it('size - should be `md` by default', () => {
            const modifiedWrapper = factoryMount();
            expect(modifiedWrapper.find('.vs-input').classes()).toContain('form-control-md');
        });

        it('size - should accept and render a `size` property', () => {
            const testSize = 'lg';
            const modifiedWrapper = factoryMount({
                size: testSize,
            });

            expect(modifiedWrapper.find('.vs-input').classes()).toContain(`form-control-${testSize}`);
        });

        it('value - should accept and render a `value` property', async() => {
            const testValue = 'Test Value';
            wrapper.setProps({
                value: testValue,
            });

            await wrapper.vm.$nextTick();

            expect(wrapper.vm.inputVal).toBe(testValue);
        });

        it('value - should accept and render a `fieldName` property', async() => {
            wrapper.setProps({
                fieldName: 'testValue',
            });

            await wrapper.vm.$nextTick();

            expect(wrapper.find('.vs-input').attributes('name')).toBe('testValue');
        });

        it('value - should accept and render a `type` property', async() => {
            expect(wrapper.find('.vs-input').attributes('type')).toBe('text');
        });
    });

    describe(':computed', () => {
        it('should add a required prop when its included in valdiation rules', async() => {
            wrapper.setProps({
                validationRules: {
                    required: true,
                },
            });

            await wrapper.vm.$nextTick();

            expect(wrapper.find('.vs-input').attributes('required')).toBe('true');
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
