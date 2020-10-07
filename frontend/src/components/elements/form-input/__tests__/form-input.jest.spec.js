import { mount, shallowMount } from '@vue/test-utils';
import VsFormInput from '../FormInput';

const factoryShallowMount = (propsData) => shallowMount(VsFormInput, {
    propsData: {
        ...propsData,
    },
});

const factoryMount = (propsData) => mount(VsFormInput, {
    propsData: {
        ...propsData,
    },
    attrs: {
        'test-attribute': 'test-value',
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('<VsFormInput />', () => {
    it('should render a <bform-input-stub />', () => {
        expect(wrapper.is('bforminput-stub')).toBe(true);
    });

    it('should bind given attributes to <bform-input-stub />', () => {
        const modifiedWrapper = factoryMount();
        const formInput = modifiedWrapper.find('input');

        expect(formInput.attributes('test-attribute')).toBe('test-value');
    });

    describe(':props', () => {
        it(':size - should be `md` by default', () => {
            expect(wrapper.attributes('size')).toBe('md');
        });

        it(':size - should accept and render a `size` property', () => {
            const testSize = 'lg';
            const modifiedWrapper = factoryMount({
                size: testSize,
            });

            expect(modifiedWrapper.classes()).toContain(`form-control-${testSize}`);
        });

        it(':value - should accept and render a `value` property', () => {
            const testValue = 'Test Value';
            wrapper.setProps({
                value: testValue,
            });

            expect(wrapper.vm.inputVal).toBe(testValue);
        });
    });

    it(':events - it should emit an `input` event when `value` changes', () => {
        const testValue = 'Test Value';
        const modifiedWrapper = factoryMount();
        modifiedWrapper.setProps({
            value: testValue,
        });

        expect(modifiedWrapper.emitted('input')).toBeTruthy();
        expect(modifiedWrapper.emitted('input')[0]).toEqual([testValue]);
    });
});
