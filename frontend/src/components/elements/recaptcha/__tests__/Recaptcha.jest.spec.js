import { shallowMount } from '@vue/test-utils';

import VsRecaptcha from '../Recaptcha';

const factoryShallowMount = () => shallowMount(VsRecaptcha, {
    propsData: {
        siteKey: '123124',
        invalid: true,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsRecaptcha', () => {
    it('should render a div with the class `vs-recaptcha`', () => {
        wrapper = factoryShallowMount();

        expect(wrapper.classes('vs-recaptcha')).toBe(true);
    });

    describe(':props', () => {
        it('should render an invalid class if the invalid prop is true', () => {
            expect(wrapper.classes('vs-recaptcha--invalid')).toBe(true);
        });
    });

    describe(':methods', () => {
        it('should emit a `verified` event when response method is called', () => {
            wrapper = factoryShallowMount();
            wrapper.vm.verified('response');

            expect(wrapper.emitted('verified')).toBeTruthy();
        });
    });
});
