import { shallowMount } from '@vue/test-utils';
import VsNoJsNoCookies from '../NoJsNoCookies';

const factoryShallowMount = () => shallowMount(VsNoJsNoCookies, {
    propsData: {
        jsDisabled: true,
    },
});

describe('VsNoJsNoCookies', () => {
    it('should render a component with the data-test attribute `vs-no-js-cookies`', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.attributes('data-test')).toBe('vs-no-js-cookies');
    });

    describe(':props', () => {
        it('should render a wrapper with the test attr `vs-no-js-cookies__no-js` if `jsDisabled` is set to true', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.find('[data-test="vs-no-js-cookies__no-js"]').exists()).toBe(true);
        });

        it('should render a wrapper with the test attr `vs-no-js-cookies__no-cookies` if `cookiesMissing` is set to true', async() => {
            const wrapper = factoryShallowMount();

            await wrapper.setProps({
                cookiesMissing: true,
                jsDisabled: false,
            });

            expect(wrapper.find('[data-test="vs-no-js-cookies__no-cookies"]').exists()).toBe(true);
        });

        it('should render the no-js wrapper and not the no-cookies wrapper if both `jsDisabled` and `cookiesMissing` are set to true', async() => {
            const wrapper = factoryShallowMount();

            await wrapper.setProps({
                jsDisabled: true,
                cookiesMissing: true,
            });

            expect(wrapper.find('[data-test="vs-no-js-cookies__no-js"]').exists()).toBe(true);
            expect(wrapper.find('[data-test="vs-no-js-cookies__no-cookies"]').exists()).toBe(false);
        });

        it('should render the `noJsMessage` prop if `jsDisabled` is set to true', async() => {
            const wrapper = factoryShallowMount();

            const noJsMessage = 'There is no javascript';

            await wrapper.setProps({
                jsDisabled: true,
                noJsMessage,
            });

            expect(wrapper.html()).toContain(noJsMessage);
        });

        it('should render the `noCookiesMessage` prop if `cookiesMissing` is set to true', async() => {
            const wrapper = factoryShallowMount();

            const noCookiesMessage = 'There are missing cookies';

            await wrapper.setProps({
                jsDisabled: false,
                cookiesMissing: true,
                noCookiesMessage,
            });

            expect(wrapper.html()).toContain(noCookiesMessage);
        });

        it('should render a link with the test attr `vs-no-js-cookies__no-cookies-link` if `noCookiesLink` provided and `cookiesMissing` is set to true', async() => {
            const wrapper = factoryShallowMount();

            const noCookiesLink = {
                url: 'https://google.com',
                lable: 'Update settings here',
            };

            await wrapper.setProps({
                jsDisabled: false,
                cookiesMissing: true,
                noCookiesLink,
            });

            expect(wrapper.find('[data-test="vs-no-js-cookies__no-cookies-link"]').exists()).toBe(true);
        });

        it('should render a link with the href provided in `noCookiesLink` if `cookiesMissing` is set to true', async() => {
            const wrapper = factoryShallowMount();

            const noCookiesLink = {
                url: 'https://google.com',
                label: 'Update settings here',
            };

            await wrapper.setProps({
                jsDisabled: false,
                cookiesMissing: true,
                noCookiesLink,
            });

            expect(wrapper.find('[data-test="vs-no-js-cookies__no-cookies-link"]').props().href).toBe(noCookiesLink.url);
        });

        it('should render a link with the content provided in `noCookiesLink` if `cookiesMissing` is set to true', async() => {
            const wrapper = factoryShallowMount();

            const noCookiesLink = {
                url: 'https://google.com',
                label: 'Update settings here',
            };

            await wrapper.setProps({
                jsDisabled: false,
                cookiesMissing: true,
                noCookiesLink,
            });

            expect(wrapper.find('[data-test="vs-no-js-cookies__no-cookies-link"]').html()).toContain(noCookiesLink.label);
        });
    });
});
