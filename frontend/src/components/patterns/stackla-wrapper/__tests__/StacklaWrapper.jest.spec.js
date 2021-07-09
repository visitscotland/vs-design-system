import { shallowMount } from '@vue/test-utils';
import VsStacklaWrapper from '../StacklaWrapper';

const introContent = 'Intro text';
const noJsContent = 'Js is off';
const noCookiesContent = 'Cookies are off';
const widgetcontent = 'A script tag';

const factoryShallowMount = () => shallowMount(VsStacklaWrapper, {
    slots: {
        stacklaIntroCopy: introContent,
        stacklaIntroCopyNoJs: noJsContent,
        stacklaIntroCopyNoCookies: noCookiesContent,
        stacklaWidget: widgetcontent,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsStacklaWrapper', () => {
    it('should render a component with the data-test attribute `.vs-stackla-wrapper`', () => {
        expect(wrapper.attributes('data-test')).toBe('vs-stackla-wrapper');
    });

    describe(':slots', () => {
        it('renders content inserted into the `stacklaIntroCopy` slot', () => {
            expect(wrapper.text()).toContain(introContent);
        });

        it('renders content inserted into the `stacklaIntroCopyNoJs` slot', () => {
            expect(wrapper.text()).toContain(noJsContent);
        });

        it('renders content inserted into the `stacklaIntroCopyNoCookies` slot', () => {
            expect(wrapper.text()).toContain(noCookiesContent);
        });

        it('renders content inserted into the `stacklaWidget` slot', () => {
            expect(wrapper.text()).toContain(widgetcontent);
        });
    });
});
