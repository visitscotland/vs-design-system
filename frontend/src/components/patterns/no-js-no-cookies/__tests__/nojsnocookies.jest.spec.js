import { shallowMount } from '@vue/test-utils';
import VsNoJsNoCookies from '../NoJsNoCookies';

const factoryShallowMount = (slotData) => shallowMount(VsNoJsNoCookies, {
    ...slotData,
});

describe('VsNoJsNoCookies', () => {
    it('should render a component with the data-test attribute `vs-no-js-no-cookies`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-no-js-no-cookies');
    });
});
