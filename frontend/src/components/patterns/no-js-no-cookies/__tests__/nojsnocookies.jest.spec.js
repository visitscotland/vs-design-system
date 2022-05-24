import { shallowMount } from '@vue/test-utils';
import VsNoJsNoCookies from '../NoJsNoCookies';

const factoryShallowMount = () => shallowMount(VsNoJsNoCookies);

describe('VsNoJsNoCookies', () => {
    it('should render a component with the data-test attribute `vs-no-js-cookies`', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.attributes('data-test')).toBe('vs-no-js-cookies');
    });

    describe(':props', () => {
        it('should render an element with the test attr `vs-no-js-cookies__no-js', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.find('[data-test="vs-no-js-cookies__no-js"]').exists()).toBe(true);
        });

        it('should render an element with the test attr `vs-no-js-cookies__no-cookies` if `cookiesMissing` is set to true', async() => {
            const wrapper = factoryShallowMount();

            await wrapper.setProps({
                cookiesMissing: true,
                jsDisabled: false,
            });

            expect(wrapper.find('[data-test="vs-no-js-cookies__no-cookies"]').exists()).toBe(true);
        });
    });
});
