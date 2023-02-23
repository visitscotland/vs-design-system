import { shallowMount, mount } from '@vue/test-utils';
import VsEmbedWrapper from '../EmbedWrapper';

const introContent = 'Intro text';
const noJsContent = 'Js is off';
const noCookiesContent = 'Cookies are off';
const widgetcontent = 'A script tag';
const errorContent = 'Error content';

const factoryShallowMount = (computed) => shallowMount(VsEmbedWrapper, {
    slots: {
        embedIntroCopy: introContent,
        embedWidget: widgetcontent,
    },
    propsData: {
        noCookieText: noCookiesContent,
        errorText: errorContent,
        noJsText: noJsContent,
    },
    computed: {
        ...computed,
    },
});

const factoryMount = (computed) => mount(VsEmbedWrapper, {
    slots: {
        embedIntroCopy: introContent,
        embedWidget: widgetcontent,
    },
    propsData: {
        noCookieText: noCookiesContent,
        errorText: errorContent,
        noJsText: noJsContent,
    },
    computed: {
        ...computed,
    },
});

describe('VsEmbedWrapper', () => {
    it('should render a component with the data-test attribute `.vs-embed-wrapper`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-embed-wrapper');
    });

    describe(':props', () => {
        it('should render an introduction if there is slot content and cookies have been initialised', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                cookiesInitStatus: true,
            });
            await wrapper.vm.$nextTick();

            expect(wrapper.find('[data-test="vs-module-wrapper__intro"]').exists()).toBe(true);
        });

        it('should render an introduction if `noCookiesRequired ` is true', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setProps({
                noCookiesRequired: true,
            });
            wrapper.setData({
                cookiesInitStatus: 'error',
            });
            await wrapper.vm.$nextTick();

            expect(wrapper.find('[data-test="vs-module-wrapper__intro"]').exists()).toBe(true);
        });

        it('should not render an introduction if cookies have not been initialised', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                cookiesInitStatus: 'error',
            });
            await wrapper.vm.$nextTick();

            expect(wrapper.find('[data-test="vs-module-wrapper__intro"]').exists()).toBe(false);
        });

        it('should not render an introduction if there is no slot content', async() => {
            const wrapper = shallowMount(VsEmbedWrapper, {
                propsData: {
                    noCookieText: noCookiesContent,
                    errorText: errorContent,
                    noJsText: noJsContent,
                },
                data() {
                    return {
                        cookiesInitStatus: true,
                    };
                },
            });
            await wrapper.vm.$nextTick();

            expect(wrapper.find('[data-test="vs-module-wrapper__intro"]').exists()).toBe(false);
        });

        it('should not apply `d-none` to embedded content if `noCookiesRequired` is true', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setProps({
                noCookiesRequired: true,
            });
            wrapper.setData({
                cookiesInitStatus: true,
            });
            await wrapper.vm.$nextTick();

            const container = wrapper.find('[data-test="vs-embed-wrapper"]').find('.vs-embed-wrapper__container');
            expect(container.classes('d-none')).toBe(false);
        });

        it('should apply `d-none` to embedded content if `noCookiesRequired` or `requiredCookiesExist` are false ', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                cookiesInitStatus: false,
            });
            await wrapper.vm.$nextTick();

            const container = wrapper.find('[data-test="vs-embed-wrapper"]').find('.vs-embed-wrapper__container');
            expect(container.classes('d-none')).toBe(true);
        });

        it('should display a warning div if cookies have not been accepted', async() => {
            const wrapper = factoryMount({
                requiredCookiesExist: {
                    get() {
                        return false;
                    },
                },
            });
            wrapper.setData({
                cookiesInitStatus: true,
            });
            await wrapper.vm.$nextTick();

            const warning = wrapper.find('[data-test="vs-embed-wrapper__error"]');
            expect(warning.exists()).toBe(true);
        });

        it('should display a warning div if cookies have not been initialised', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                cookiesInitStatus: 'error',
            });
            await wrapper.vm.$nextTick();

            const warning = wrapper.find('[data-test="vs-embed-wrapper__error"]');
            expect(warning.exists()).toBe(true);
        });

        it('should not display a warning div if cookies are initialised and have been accepted', async() => {
            const wrapper = factoryMount({
                requiredCookiesExist: {
                    get() {
                        return true;
                    },
                },
            });
            wrapper.setData({
                cookiesInitStatus: true,
            });
            await wrapper.vm.$nextTick();

            const warning = wrapper.find('[data-test="vs-embed-wrapper__error"]');
            expect(warning.exists()).toBe(false);
        });

        it('should not display a warning div if `noCookiesRequired` is true', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setProps({
                noCookiesRequired: true,
            });
            wrapper.setData({
                cookiesInitStatus: 'error',
            });
            await wrapper.vm.$nextTick();

            const warning = wrapper.find('[data-test="vs-embed-wrapper__error"]');
            expect(warning.exists()).toBe(false);
        });

        it('should set correct warning type to `cookie` if cookies have been initialised', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                cookiesInitStatus: true,
            });
            await wrapper.vm.$nextTick();

            const warning = wrapper.find('[data-test="vs-embed-wrapper__error"]');
            expect(warning.attributes('type')).toBe('cookie');
        });

        it('should set correct warning type to `normal` if cookies have been initialised', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                cookiesInitStatus: 'error',
            });
            await wrapper.vm.$nextTick();

            const warning = wrapper.find('[data-test="vs-embed-wrapper__error"]');
            expect(warning.attributes('type')).toBe('normal');
        });
    });

    describe(':slots', () => {
        it('renders content inserted into the `embedIntroCopy` slot', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                cookiesInitStatus: true,
            });
            await wrapper.vm.$nextTick();

            expect(wrapper.text()).toContain(introContent);
        });

        it('renders content inserted into the `embedIntroCopyNoJs` slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.text()).toContain(noJsContent);
        });

        it('renders content inserted into the `embedIntroCopyNoCookies` slot', async() => {
            const wrapper = factoryMount({
                requiredCookiesExist: {
                    get() {
                        return false;
                    },
                },
                showError: {
                    get() {
                        return true;
                    },
                },
            });
            wrapper.setData({
                cookiesInitStatus: true,
            });

            await wrapper.vm.$nextTick();

            expect(wrapper.text()).toContain(noCookiesContent);
        });

        it('renders content inserted into the `embedWidget` slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.text()).toContain(widgetcontent);
        });
    });
});
